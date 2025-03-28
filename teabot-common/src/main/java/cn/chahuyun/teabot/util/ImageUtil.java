package cn.chahuyun.teabot.util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 工具类，用于显示图像
 *
 * @author Moyuyanli
 * @date 2025-3-3 10:10
 */
public class ImageUtil {

    private static final Map<String, JFrame> frameMap = new HashMap<>(2);

    /**
     * 显示 BufferedImage 在一个 Swing 窗口中
     *
     * @param id    窗口的唯一标识符
     * @param image 要显示的 BufferedImage 对象
     */
    public static void view(String id, BufferedImage image) {
        SwingUtilities.invokeLater(() -> {
            // 检查是否已经存在对应ID的窗口，如果存在则关闭它
            if (frameMap.containsKey(id)) {
                JFrame existingFrame = frameMap.get(id);
                existingFrame.dispose();
                frameMap.remove(id);
            }

            // 创建一个新的 JFrame 实例
            JFrame frame = new JFrame("请登录(UUID):" + id);
            // 设置关闭操作为 DISPOSE_ON_CLOSE，这样关闭窗口时仅释放此窗口的资源而不结束程序
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            // 设置窗口大小略大于图片
            frame.setSize(image.getWidth() + 50, image.getHeight() + 50);

            // 创建一个 JLabel 并设置它的图标为 BufferedImage
            JLabel label = new JLabel(new ImageIcon(image));

            // 把 JLabel 添加到 JFrame 中
            frame.add(label, BorderLayout.CENTER);

            // 设置 JFrame 可见
            frame.setVisible(true);

            // 将新的 JFrame 实例存入 map 中
            frameMap.put(id, frame);
        });
    }

    /**
     * 根据ID关闭对应的窗口
     *
     * @param id 窗口的唯一标识符
     */
    public static void close(String id) {
        SwingUtilities.invokeLater(() -> {
            if (frameMap.containsKey(id)) {
                JFrame frameToClose = frameMap.get(id);
                frameToClose.dispose();
                frameMap.remove(id);
            }
        });
    }


    /**
     * 根据图片生成base64编码
     */
    public static String  ImageToBase64Converter(String filePath) throws IOException {

        Path path = Paths.get(filePath);
        byte[] imageData = Files.readAllBytes(path);
        String base64 = Base64.getEncoder().encodeToString(imageData);
        System.out.println(base64);
        return base64;
    }
}