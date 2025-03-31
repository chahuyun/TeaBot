package cn.chahuyun.teabot.api.app.web;

import cn.chahuyun.teabot.api.dto.R;
import cn.chahuyun.teabot.core.bot.server.BotServer;
import cn.chahuyun.teabot.repository.bot.entity.BotConfigEntity;
import cn.chahuyun.teabot.repository.server.bot.BotConfigService;
import cn.chahuyun.teabot.common.util.GsonUtil;
import cn.hutool.core.bean.BeanUtil;
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
            Spark.post("/get", BotController::getBot);
            Spark.post("/online", BotController::goOnline);
            Spark.post("/sync", BotController::goOnline);
        };
    }

    private static R addBot(Request request, Response response) {
        BotConfigEntity botConfig = GsonUtil.fromJson(request.body(), BotConfigEntity.class);
        if (BeanUtil.hasNullField(botConfig, "id", "status")) {
            return R.error("配置不能为空");
        }
        return R.ok("添加成功", BotConfigService.addBotConfig(botConfig));
    }

    private static R getBot(Request request, Response response) {
        BotConfigEntity botConfig = GsonUtil.fromJson(request.body(), BotConfigEntity.class);
        String botId = botConfig.getBotId();
        if (botId.isEmpty() || botId.isBlank()) {
            return R.error("botId不能为空!");
        }
        return R.ok("添加成功", BotConfigService.getBotConfig(botId));
    }


    private static R goOnline(Request request, Response response) {
        JsonObject object = GsonUtil.fromJson(request.body(), JsonObject.class);
        if (!object.has("id")) {
            return R.error("id 不能为空!");
        }
        int id = object.get("id").getAsInt();
        BotConfigEntity botConfig = BotConfigService.getBotConfig(id);
        if (botConfig == null) {
            return R.error("不存在该bot配置:" + id);
        }

        if (botConfig.getStatus()) {
            return R.ok("该bot已经启动!");
        }

        try {
            BotServer.newBot(botConfig);
            botConfig.setStatus(true);
            BotConfigService.updateBotConfig(botConfig);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error("bot 尝试登录失败:" + e.getMessage());
        }
        return R.ok("登录成功");
    }

    public static R sync(Request request, Response response) {
        log.debug(request.body());
        return R.ok();
    }

}
