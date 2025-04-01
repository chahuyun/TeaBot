package cn.chahuyun.teabot.experiment;

import cn.chahuyun.teabot.api.event.GroupMessageEvent;
import cn.chahuyun.teabot.core.event.GlobalEventChannel;
import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-31 17:28
 */
@Slf4j
public class SimpleExperiment {


    public static void tes() {

        GlobalEventChannel.subscribe(GroupMessageEvent.class)
                .filter(event -> {
                    String content = event.getMessageChain().content();
                    log.info("收到消息->{}", content);
                    return content.equals("测试");
                })
                .handle((event, context) -> {
                    event.sendMessage("成功!");
                });

    }


}
