package cn.chahuyun.teabot.core.util.http.padplus;

import cn.chahuyun.teabot.core.util.http.padplus.vo.GetQrReq;
import cn.chahuyun.teabot.core.util.http.padplus.vo.Results;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * padPlus的接口
 *
 * @author Moyuyanli
 * @date 2025-2-27 14:12
 */
public interface PadPlusService {

    /**
     * 获取二维码
     * @param params 参数
     * @return Call<String>
     */
    @POST("/Login/GetQR")
    Call<Results> getQr(GetQrReq params);

}
