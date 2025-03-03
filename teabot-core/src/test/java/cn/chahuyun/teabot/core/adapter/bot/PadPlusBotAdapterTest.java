package cn.chahuyun.teabot.core.adapter.bot;


import cn.chahuyun.teabot.conf.bot.BotConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class PadPlusBotAdapterTest {


    private BotConfiguration botConfiguration;

    @BeforeEach
    public void setUp() {
        botConfiguration = new BotConfiguration();
        botConfiguration.setBaseUrl("http://192.168.0.116:9999");
        botConfiguration.setUserId("Moyuyanli");
        botConfiguration.setDriveName("xiaomi");
    }

    @Test
    public void testLogin() {
        PadPlusBotAdapter adapter = new PadPlusBotAdapter();

        boolean login = adapter.login(botConfiguration);


        assertTrue(login, "true 成功");
    }
}
