package cn.chahuyun.teabot.core.adapter.bot;

import cn.chahuyun.teabot.conf.bot.BotConfiguration;
import cn.chahuyun.teabot.core.data.bot.WeChatUser;
import cn.chahuyun.teabot.core.util.ImageUtil;
import cn.chahuyun.teabot.core.util.http.HttpUtil;
import cn.chahuyun.teabot.core.util.http.padplus.PadPlusHttpUtil;
import cn.chahuyun.teabot.core.util.http.padplus.PadPlusService;
import cn.chahuyun.teabot.core.util.http.padplus.vo.CheckQrRes;
import cn.chahuyun.teabot.core.util.http.padplus.vo.GetQrRes;
import cn.hutool.cron.CronUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Retrofit;

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


    private final Retrofit retrofit;

    private final BotConfiguration configuration;

    @Setter
    private String wxid;

    @Getter
    private WeChatUser user;

    public PadPlusBotAdapter(BotConfiguration configuration) {
        this.configuration = configuration;
        this.retrofit = HttpUtil.getRetrofit(configuration.getBaseUrl());
    }

    public boolean login() {
        PadPlusService service = retrofit.create(PadPlusService.class);

        GetQrRes qrCode = PadPlusHttpUtil.getQrCode(service, configuration);
        String qrBase64 = qrCode.getQrBase64();
        String userId = configuration.getUserId();
        String uuid = qrCode.getUuid();

        // 移除 data URL 前缀
        if (qrBase64.startsWith("data:image/jpg;base64,")) {
            qrBase64 = qrBase64.substring("data:image/jpg;base64,".length());
        }

        // 将Base64字符串解码为BufferedImage
        byte[] imageByte;
        BufferedImage image;
        try {
            imageByte = Base64.getDecoder().decode(qrBase64);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();

            // 在图像上绘制UUID
            Graphics2D g = image.createGraphics();
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

    @Override
    public boolean sendMessage(String message) {
        return false;
    }

    @Override
    public boolean sendImage(String imageUrl) {
        return false;
    }

    @Override
    public boolean messageSource() {
        //心跳调用检查新消息
        return false;
    }
}
