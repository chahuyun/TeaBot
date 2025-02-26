package cn.chahuyun.teabot.api.app.web;

import cn.chahuyun.teabot.api.dto.R;
import cn.chahuyun.teabot.core.bot.BotFactory;
import cn.chahuyun.teabot.core.data.bot.entity.BotConfig;
import cn.chahuyun.teabot.repository.server.bot.BotConfigService;
import cn.hutool.core.bean.BeanUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import spark.Request;
import spark.Response;
import spark.RouteGroup;
import spark.Spark;

/**
 * 机器人管理
 *
 * @author Moyuyanli
 * @date 2025-2-26 10:13
 */
@Slf4j
public class BotController {


    public static RouteGroup getRoutes() {
        return () -> {
            Spark.post("/add", BotController::addBot);
            Spark.post("/online", BotController::goOnline);
        };
    }

    private static R addBot(Request request, Response response) {
        BotConfig botConfig = new Gson().fromJson(request.body(), BotConfig.class);
        if (BeanUtil.hasNullField(botConfig, "id")) {
            return R.error("配置不能为空");
        }
        return R.ok("添加成功", BotConfigService.addBotConfig(botConfig));
    }

    private static R goOnline(Request request, Response response) {
        JsonObject object = new Gson().fromJson(request.body(), JsonObject.class);
        if (!object.has("id")) {
            return R.error("id 不能为空!");
        }
        int id = object.get("id").getAsInt();
        BotConfig botConfig = BotConfigService.getBotConfig(id);
        if (botConfig == null) {
            return R.error("不存在该bot配置:" + id);
        }
        try {
            BotFactory.newBot(botConfig.botConfiguration());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error("bot 尝试登录失败:" + e.getMessage());
        }
        return R.ok("登录成功");
    }




}
