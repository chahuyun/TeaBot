package cn.chahuyun.teabot.system;

import cn.chahuyun.teabot.common.conf.system.TeaBotFileCore;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-26 11:38
 */
@Slf4j
public class Banner {

    public static void load() {
        try (InputStream stream = TeaBotFileCore.getResourceAsStream("banner.txt")) {

            // 使用Java原生方式读取
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            log.info("\n{}", content);

        } catch (IOException e) {
            log.error("Banner加载失败", e);
        }
    }

}
