package cn.chahuyun.teabot.core.util.http.padplus.vo;

import com.google.gson.JsonObject;
import lombok.Data;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-27 14:31
 */
@Data
public class Results {

    private int code;

    private boolean Success;

    private String Message;


    private JsonObject Data;
}