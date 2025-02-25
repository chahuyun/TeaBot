package cn.chahuyun.teabot.api.app;


import spark.Spark;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-25 11:24
 */
public class RestApplication {

    public static void init() {
        Spark.port(8080);


        Spark.get("/hello", (req, res) -> {
            String name = req.queryParams("name");
            return "Hello, " + (name == null ? "World" : name) + "!";
        });

    }

    public static void main(String[] args) {
        Spark.port(8080);


        Spark.get("/hello", (req, res) -> {
            String name = req.queryParams("name");
            return "Hello, " + (name == null ? "World" : name) + "!";
        });

    }

}
