package cn.chahuyun.teabot.adapter.bot.padplus;

import cn.chahuyun.teabot.adapter.bot.WeChatUser;
import cn.chahuyun.teabot.adapter.http.HttpUtil;
import cn.chahuyun.teabot.adapter.http.padplus.PadPlusHttpUtil;
import cn.chahuyun.teabot.adapter.http.padplus.PadPlusService;
import cn.chahuyun.teabot.adapter.http.padplus.vo.*;
import cn.chahuyun.teabot.api.config.BotAdapter;
import cn.chahuyun.teabot.api.contact.Contact;
import cn.chahuyun.teabot.api.contact.Friend;
import cn.chahuyun.teabot.api.message.MessageReceipt;
import cn.chahuyun.teabot.api.message.SingleMessage;
import cn.chahuyun.teabot.message.MessageKey;
import cn.chahuyun.teabot.util.ImageUtil;
import cn.hutool.cron.CronUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import retrofit2.Response;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * padPlus的bot适配
 *
 * @author Moyuyanli
 * @date 2025-2-26 17:17
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
@Slf4j
public class PadPlusBotAdapter implements BotAdapter {

    private transient PadPlusService service;
    private final PadPlusBotConfig config;

    @Getter
    private WeChatUser user;

    @Getter
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

    public boolean login() {
        GetQrRes qrCode = PadPlusHttpUtil.getQrCode(service, config);
        String qrBase64 = qrCode.getQrBase64();
        String userId = config.getUserId();
        String uuid = qrCode.getUuid();

        try {
            BufferedImage image = processQRImage(qrBase64, userId, uuid);

            ImageUtil.view(uuid, image);

            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<CheckQrRes> reference = new AtomicReference<>();
            AtomicBoolean isLogin = new AtomicBoolean(false);

            CronUtil.schedule(uuid, "* * * * *", () -> {
                if (isLogin.get()) {
                    CronUtil.remove(uuid);
                    latch.countDown(); // 通知主线程继续
                    return;
                }

                reference.set(PadPlusHttpUtil.checkQrCode(service, uuid));
                if (reference.get() != null) {
                    isLogin.set(true);
                    latch.countDown(); // 通知主线程继续
                }
            });

            CronUtil.start();

            // 等待，直到latch的计数变为0
            try {
                latch.await(60L, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 恢复中断状态
            }

            CheckQrRes res = reference.get();
            user = res.getAcctSectResp();
            wxid = user.getUserName();
            ImageUtil.close(uuid);
            return true;


        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return false; // 或者根据实际情况返回true
    }

    @Override
    public boolean logout() {
        return false;
    }

    /**
     * 发送消息
     *
     * @param  receipt 消息源
     * @return true 成功
     */
    @Override
    public <C extends Contact> boolean sendMessage(MessageReceipt<C> receipt) {
        for (SingleMessage singleMessage : receipt.getMessageChain()) {
            //文本
            if (singleMessage.key().equals(MessageKey.PLAIN_TEXT.getType())) {
                SendTextMessageReq req = new SendTextMessageReq();
                req.setContent(singleMessage.content());
                req.setWxid(wxid);
                req.setToWxid(receipt.getTarget().getId());
                // TODO: 2025/3/25 处理@消息
                req.setType(0);
                SendTextMessageRes sendTextMessageRes = PadPlusHttpUtil.SendMessage(service, req);
                if (sendTextMessageRes != null && sendTextMessageRes.getCode() == 0) {
                    return true;
                }
            }
            //语音
            else if (singleMessage.key().equals(MessageKey.VOICE.getType())){
                SendVideoMessageReq req = new SendVideoMessageReq();
                req.setWxid(wxid);
                req.setToWxid(receipt.getTarget().getId());
                req.setBase64("xxx");
                req.setImageBase64("xxx");
                req.setPlayLength(4);
            }
        }
        return false;
    }


    /**
     * 监听消息
     */
    @Override
    public void listeningMessages() {
        CronUtil.schedule(getWxid(), "* * * * *", () -> {
            try {
                Response<Results> execute = service.syncMessage(new SyncMessageReq(wxid)).execute();


            } catch (IOException e) {
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


    private void contentHandle() {

    }

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

}
