package cn.chahuyun.teabot.core.adapter.bot;


import cn.chahuyun.teabot.conf.bot.BotConfiguration;
import cn.chahuyun.teabot.core.bot.Bot;
import cn.chahuyun.teabot.core.bot.BotFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PadPlusBotAdapterTest {


    private BotConfiguration botConfiguration;

    @BeforeEach
    public void setUp() {
        botConfiguration = new BotConfiguration();
        botConfiguration.setBaseUrl("http://192.168.0.116:9999/");
        botConfiguration.setUserId("Moyuyanli");
        botConfiguration.setDriveName("xiaomi");
    }

    @Test
    public void testLogin() {
        Bot bot = BotFactory.newBot(botConfiguration);

        assertNotNull(bot,"bot不为空");
    }
}
