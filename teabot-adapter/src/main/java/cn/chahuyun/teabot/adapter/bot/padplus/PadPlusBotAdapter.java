package cn.chahuyun.teabot.adapter.bot.padplus;

import cn.chahuyun.teabot.adapter.http.padplus.vo.SendTextMessageReq;
import cn.chahuyun.teabot.adapter.http.padplus.vo.SendTextMessageRes;
import cn.chahuyun.teabot.api.config.BotAdapter;
import cn.chahuyun.teabot.adapter.http.HttpUtil;
import cn.chahuyun.teabot.api.contact.Friend;
import cn.chahuyun.teabot.api.message.MessageChain;
import cn.chahuyun.teabot.adapter.http.padplus.PadPlusHttpUtil;
import cn.chahuyun.teabot.adapter.http.padplus.PadPlusService;
import cn.chahuyun.teabot.adapter.http.padplus.vo.CheckQrRes;
import cn.chahuyun.teabot.adapter.http.padplus.vo.GetQrRes;
import cn.chahuyun.teabot.api.message.SingleMessage;
import cn.chahuyun.teabot.message.MessageKey;
import cn.chahuyun.teabot.util.ImageUtil;
import cn.hutool.cron.CronUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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

    private final PadPlusService service;
    private final PadPlusBotConfig config;

    @Setter
    private String wxid;

//    @Getter
//    private WeChatUser user;

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
//            user = res.getAcctSectResp();
//            wxid = user.getUserName();
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
     * 发送文本消息
     *
     * @param message 消息
     * @return true 成功
     */
    @Override
    public boolean sendMessage(MessageChain message) {
        for (SingleMessage singleMessage : message) {
            if (singleMessage.key().equals(MessageKey.PLAIN_TEXT)) {
                SendTextMessageReq req = new SendTextMessageReq();
                req.setContent(singleMessage.content());
                req.setWxid(wxid);
                req.setToWxid();
                req.setAt();
                req.setType();
                PadPlusHttpUtil.SendMessage()




            }
        }

        return false;
    }

    /**
     * 监听消息
     */
    @Override
    public void listeningMessages() {


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
