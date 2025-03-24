package cn.chahuyun.teabot.api.app.web;

import cn.chahuyun.teabot.api.dto.R;
import spark.Request;
import spark.Response;

/**
 * 异常处理器
 *
 * @author Moyuyanli
 * @date 2025-2-26 16:13
 */
public class ExceptionHandle {

    /**
     * 请求异常全局处理
     *
     * @param exception 捕获到的异常
     * @param request   请求对象
     * @param response  响应对象
     */
    public static void handle(Exception exception, Request request, Response response) {
        // 设置响应类型为JSON
        response.type("application/json");

        // 设置HTTP状态码为500（服务器内部错误），可以根据实际情况调整
        response.status(500);

        // 构造错误响应
        R errorResponse = R.error(500, "服务器内部错误: " + exception.getMessage());

        // 将JSON响应写入response body
        response.body(errorResponse.toString());
    }
}