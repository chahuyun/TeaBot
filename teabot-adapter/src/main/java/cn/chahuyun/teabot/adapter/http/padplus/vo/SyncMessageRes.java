package cn.chahuyun.teabot.adapter.http.padplus.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-7 10:15
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("SpellCheckingInspection")
public class SyncMessageRes {

    private List<PadPlusMessage> AddMsgs;

    private boolean isOnline = true;

    public List<PadPlusMessage> getAddMsgs() {
        if (AddMsgs == null) {
            return List.of();
        }
        return AddMsgs;
    }
}



