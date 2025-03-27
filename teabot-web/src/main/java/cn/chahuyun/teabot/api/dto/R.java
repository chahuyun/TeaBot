package cn.chahuyun.teabot.api.dto;

import cn.chahuyun.teabot.util.GsonUtil;
import lombok.Data;

/**
 * api请求结果
 *
 * @author Moyuyanli
 * @date 2025-2-26 15:35
 */
@Data
public class R {

    private int code;

    private String msg;

    private Object data;


    public R(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static R ok() {
        return new R(200, "success", null);
    }

    public static R ok(String msg) {
        return new R(200, msg, null);
    }

    public static R ok(Object data) {
        return new R(200, "success", data);
    }


    public static R ok(String msg, Object data) {
        return new R(200, msg, data);
    }


    public static R warn(int code, String msg) {
        return new R(code, msg, null);
    }

    public static R error() {
        return new R(500, "error", null);
    }

    public static R error(String msg) {
        return new R(500, msg, null);
    }

    public static R error(int code, String msg) {
        return new R(code, msg, null);
    }


    @Override
    public String toString() {
        return GsonUtil.toJson(this);
    }
}
