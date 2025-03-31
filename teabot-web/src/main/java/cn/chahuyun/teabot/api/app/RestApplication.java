package cn.chahuyun.teabot.api.app;


import cn.chahuyun.teabot.api.app.web.AuthFilter;
import cn.chahuyun.teabot.api.app.web.BotController;
import cn.chahuyun.teabot.api.app.web.ExceptionHandle;
import cn.chahuyun.teabot.common.conf.system.ConfigService;
import cn.chahuyun.teabot.common.conf.system.entity.SystemConfig;
import cn.chahuyun.teabot.repository.RepositoryLoader;
import spark.Spark;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-25 11:24
 */
public class RestApplication {

    public static void init() {
        SystemConfig config = ConfigService.getConfig();
        Spark.port(config.getServer().getPort());
        Spark.before(AuthFilter::handle);
        Spark.path("/bot", BotController.getRoutes());
        Spark.exception(Exception.class, ExceptionHandle::handle);

        Spark.get("/db/loader", (req, res) -> {
            String content = req.queryParams("id");
            if (content.isEmpty() || content.isBlank()) {
                return "id不能为空";
            }
            try {
                RepositoryLoader.load(Class.forName(content));
                return "加载成功";
            } catch (ClassNotFoundException e) {
                return "加载失败";
            }
        });

    }

    public static void main(String[] args) {
        init();
    }

}
