package cn.chahuyun.teabot.api.dto.entity;

import lombok.Data;

/**
 * @Description : 创建登陆二维码（安卓Pad）接收参数

 * @Author :Obi
 */
@Data
public class GetQRResData  {

            private String qrBase64;
            private String uuid;
            private String qrUrl;
            private String expiredTime;

}
