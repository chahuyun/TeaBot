package cn.chahuyun.teabot.adapter.bot.padplus;

import cn.chahuyun.teabot.adapter.bot.WeChatUser;
import cn.chahuyun.teabot.adapter.http.HttpUtil;
import cn.chahuyun.teabot.adapter.http.padplus.PadPlusHttpUtil;
import cn.chahuyun.teabot.adapter.http.padplus.PadPlusService;
import cn.chahuyun.teabot.adapter.http.padplus.vo.*;
import cn.chahuyun.teabot.api.bot.BotContainer;
import cn.chahuyun.teabot.api.config.BotAdapter;
import cn.chahuyun.teabot.api.config.BotConfig;
import cn.chahuyun.teabot.api.contact.*;
import cn.chahuyun.teabot.api.event.EventBus;
import cn.chahuyun.teabot.api.event.GroupMessageEvent;
import cn.chahuyun.teabot.api.factory.ContactFactory;
import cn.chahuyun.teabot.api.factory.MessageEventFactory;
import cn.chahuyun.teabot.api.message.*;
import cn.chahuyun.teabot.exp.BotNotLoginException;
import cn.chahuyun.teabot.util.ImageUtil;
import cn.chahuyun.teabot.util.SpiUtil;
import cn.hutool.cron.CronUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * padPlus的bot适配
 *
 * @author Moyuyanli
 * @date 2025-2-26 17:17
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
@Slf4j
public class PadPlusBotAdapter implements BotAdapter, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String heartbeat = "heartbeat-";
    private static final String syncMessage = "sync-message-";

    private transient PadPlusService service;
    private final PadPlusBotConfig config;

    /**
     * 是否在线
     * true 在线
     */
    private final AtomicBoolean isOnline = new AtomicBoolean(false);

    @Getter
    @Setter
    private WeChatUser user;

    @Getter
    @Setter
    private String wxid;


    // 自定义序列化
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    // 自定义反序列化
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        // 重新初始化不可序列化的字段
        this.service = HttpUtil.getRetrofit(config.getBaseUrl()).create(PadPlusService.class);
        // 重新注册定时任务（如监听消息的 CronUtil）
    }


    public PadPlusBotAdapter(PadPlusBotConfig config) {
        this.config = config;
        this.service = HttpUtil.getRetrofit(config.getBaseUrl()).create(PadPlusService.class);
    }

    /**
     * 获取配置信息
     *
     * @return BotConfig
     */
    @Override
    public BotConfig getConfig() {
        return config;
    }

    /**
     * 获取这个适配器服务的botid
     *
     * @return botid
     */
    @Override
    public String getId() {
        return wxid;
    }

    /**
     * 当前bot是否在线
     *
     * @return true 在线
     */
    @Override
    public boolean isOnline() {
        heartbeat();
        return isOnline.get();
    }

    @Override
    public boolean login() {
        //登录时检测一次心跳
        heartbeat();

        //在线就报登录成功，返回true
        if (isOnline.get()) {
            CronUtil.schedule(heartbeat + wxid, "0/5 * * * * ?", this::heartbeat);
            return true;
        }

        //不在线进行用户登录流程
        GetQrRes qrCode = PadPlusHttpUtil.getQrCode(service, config);
        String qrBase64 = qrCode.getQrBase64();
        String userId = config.getUserId();
        String uuid = qrCode.getUuid();

        try {
            BufferedImage image = processQRImage(qrBase64, userId, uuid);

            ImageUtil.view(uuid, image);

            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<CheckQrRes> reference = new AtomicReference<>();

            CronUtil.schedule(uuid, "0/2 * * * * ?", () -> {
                if (isOnline.get()) {
                    CronUtil.remove(uuid);
                    latch.countDown(); // 通知主线程继续
                    return;
                }
                log.debug("uuid->{},扫码检测", uuid);
                reference.set(PadPlusHttpUtil.checkQrCode(service, uuid));
                if (reference.get() != null) {
                    isOnline.set(true);
                    latch.countDown(); // 通知主线程继续
                }
            });

            // 等待，直到latch的计数变为0
            try {
                latch.await(60L, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 恢复中断状态
                log.error("等待检测登录二维码超时!uuid->{}", uuid);
                CronUtil.remove(uuid);
                return false;
            }

            CheckQrRes res = reference.get();
            if (res == null) {
                log.debug("bot登录失败!");
                CronUtil.remove(uuid);
                return false;
            }
            user = res.getAcctSectResp();
            wxid = user.getUserName();
            CronUtil.remove(uuid);
            ImageUtil.close(uuid);

            CronUtil.schedule(heartbeat + wxid, "0/5 * * * * ?", this::heartbeat);

            return true;


        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        CronUtil.remove(uuid);
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }

    /**
     * 发送文本消息
     *
     * @param  receipt 消息源
     * @return true 成功
     */
    @Override
    public <C extends Contact> boolean sendMessage(MessageReceipt<C> receipt) {
        for (SingleMessage singleMessage : receipt.getMessageChain()) {
            switch (singleMessage.getType()) {
                case PLAIN_TEXT:
                    handleText(singleMessage, receipt.getTarget());
                    log.info("成功发送语音消息");
                    break;
                case IMAGE:
                    handleImage(singleMessage, receipt.getTarget());
                    log.info("成功发送图片消息");
                    break;
                case VOICE:
                    handleVoice(singleMessage, receipt.getTarget());
                    log.info("成功发送语音消息");
                    break;
                default:
                    log.error("不支持的消息类型");
            }
        }
        return false;
    }

    /**
     * 监听消息
     */
    @Override
    public void listeningMessages() {
        heartbeat();
        if (!isOnline.get()) {
            throw new BotNotLoginException("bot未登录!");
        }

        CronUtil.schedule(syncMessage + wxid, "* * * * * ?", () -> {
            try {
                SyncMessageRes syncMessageRes = PadPlusHttpUtil.syncMessage(service, wxid);
                if (syncMessageRes == null) {
                    return;
                }
                if (!syncMessageRes.isOnline()) {
                    downline();
                    return;
                }
                for (PadPlusMessage msg : syncMessageRes.getAddMsgs()) {
                    int msgType = msg.getMsgType();
                    switch (msgType) {
                        case 1 -> {
                            String subject = msg.getFromUserName().getString();
                            if (Pattern.matches("\\d+@chatroom", subject)) {
                                MessageEventFactory messageEventFactory = SpiUtil.getImpl(MessageEventFactory.class);
                                Bot bot = BotContainer.getBot(wxid);
                                Group group = getGroup(subject);

                                String content = msg.getContent().getString();

                                Matcher matcher = Pattern.compile("^(.*):(.*)").matcher(content);
                                if (matcher.find()) {
                                    String userName = matcher.group(1);
                                    Member member = getMember(group.getId(), userName);
                                    String message = matcher.group(2);
                                    MessageChain messageChain = MessageChain.builder().add(PlainText.of(message)).build();

                                    GroupMessageEvent event = messageEventFactory.createGroupMessageEvent(bot,
                                            group, member, messageChain, msg.getCreateTime());

                                    EventBus bus = SpiUtil.getImpl(EventBus.class);
                                    bus.fire(event);
                                }
                            }
                        }
                        default -> {
                            log.warn("暂不支持的消息类型:{}", msgType);
                        }
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        //todo 1.文本消息监听
        //todo 2.进群事件
    }

    /**
     * 获取好友信息
     *
     * @param id 好友id
     * @return 好友
     */
    @Override
    public Friend getFriend(String id) {
        return null;
    }

    /**
     * 获取群信息
     *
     * @param groupId 群id
     * @return Group
     */
    @Override
    public Group getGroup(String groupId) {
        GetGroupInfoRes res = PadPlusHttpUtil.getGroupInfo(service, wxid, groupId);
        if (res == null) {
            throw new RuntimeException("获取群信息失败!");
        }

        ContactFactory impl = SpiUtil.getImpl(ContactFactory.class);
        Bot bot = BotContainer.getBot(wxid);
        GetGroupInfoRes.ChatroomData chatroomData = res.getNewChatroomData();
        List<String> collect = chatroomData.getChatRoomMember().stream().map(
                GetGroupInfoRes.ChatRoomMember::getUserName
        ).collect(Collectors.toList());
        return impl.getGroup(bot, groupId, res.getNickName().getString(), res.getSmallHeadImgUrl(), res.getChatRoomOwner(), collect);
    }

    /**
     * 获取群成员信息
     *
     * @param groupId  群id
     * @param memberId 成员id
     * @return Member
     */
    @Override
    public Member getMember(String groupId, String memberId) {
        GetGroupMemberInfoRes res = PadPlusHttpUtil.getGroupMemberInfo(service, wxid, groupId);
        if (res == null) {
            throw new RuntimeException("获取群信息失败!");
        }

        ContactFactory impl = SpiUtil.getImpl(ContactFactory.class);
        Bot bot = BotContainer.getBot(wxid);
        GetGroupInfoRes.ChatroomData chatroomData = res.getNewChatroomData();

        for (GetGroupInfoRes.ChatRoomMember chatRoomMember : chatroomData.getChatRoomMember()) {
            if (chatRoomMember.getUserName().equals(memberId)) {
                return impl.getMember(bot, groupId, memberId, chatRoomMember.getNickName(), chatRoomMember.getSmallHeadImgUrl());
            }
        }
        throw new RuntimeException("未找到成员!");
    }

    //====================================private=================================================


    private BufferedImage processQRImage(String qrBase64, String userId, String uuid) throws IOException {
        if (qrBase64.startsWith("data:image/jpg;base64,")) {
            qrBase64 = qrBase64.substring("data:image/jpg;base64,".length());
        }

        byte[] imageByte = Base64.getDecoder().decode(qrBase64);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageByte)) {
            BufferedImage image = ImageIO.read(bis);

            // 文字绘制建议使用抗锯齿
            Graphics2D g = image.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 在图像上绘制UUID
            g.setColor(Color.BLACK); // 设置字体颜色
            g.setFont(new Font("Serif", Font.BOLD, 20)); // 设置字体样式和大小
            String text = "userId: " + userId;

            // 设置字体并获取FontMetrics以计算文本宽度
            g.setFont(new Font("Serif", Font.BOLD, 20)); // 确保与前面设置的字体一致
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int textWidth = metrics.stringWidth(text);
            int textHeight = metrics.getHeight();

            // 计算文本绘制的起始点
            int x = (image.getWidth() - textWidth) / 2; // 水平居中

            // 绘制文本
            g.drawString(text, x, textHeight);

            return image;
        }
    }

    /**
     * 心跳检测，验证登录状态
     */
    private void heartbeat() {
        try {
            isOnline.set(PadPlusHttpUtil.heartBeat(service, wxid));
        } catch (Exception e) {
            isOnline.set(false);
            throw new RuntimeException(e);
        }
    }

    private void downline() {
        isOnline.set(false);
        CronUtil.remove(syncMessage + wxid);
        CronUtil.remove(heartbeat + wxid);
    }

    //发送文本消息
    private void handleText(SingleMessage message, Contact contact) {
        PlainText text = message.as(PlainText.class);
        if (text != null) {
            SendTextMessageReq req = new SendTextMessageReq();
            req.setContent(text.content());
            req.setWxid(wxid);
            req.setToWxid(contact.getId());
            // TODO: 2025/3/25 处理@消息
            req.setType(0);
            SendTextMessageRes sendTextMessageRes = PadPlusHttpUtil.SendMessage(service, req);
            if (sendTextMessageRes != null && sendTextMessageRes.getCode() == 0) {
            }
        }
    }

    //发送语音消息
    private void handleVoice(SingleMessage message, Contact contact) {
        VoiceMessage voiceMessage = message.as(VoiceMessage.class);
        SendVoiceMessageReq req = new SendVoiceMessageReq();
        // TODO: 2025/3/26 生成语音文件
        req.setType(4);
        req.setVoiceTime(2);//1000为一秒
        req.setBase64(voiceMessage.getBase64());
        req.setWxid(wxid);
        req.setToWxid(contact.getId());
        SendVideoMessageRes res = PadPlusHttpUtil.SendVoiceMessage(service, req);
        if (res != null && res.getCode() == 0) {
        }


    }

    //发送图片消息
    private void handleImage(SingleMessage message, Contact contact) {
        ImageMessage imageMessage = message.as(ImageMessage.class);
        SendImageMessageReq req = new SendImageMessageReq();
        // TODO: 2025/3/26 把图片的url转为base64
        req.setBase64(imageMessage.getBase64());
        req.setWxid(wxid);
        req.setToWxid(contact.getId());
        SendImageMessageRes res = PadPlusHttpUtil.SendImageMessage(service, req);
        if (res != null && res.getCode() == 0) {
        }
    }


}
