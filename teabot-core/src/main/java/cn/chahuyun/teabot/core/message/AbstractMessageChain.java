package cn.chahuyun.teabot.core.message;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-3-6 15:38
 */
public abstract class AbstractMessageChain extends ArrayList<SingleMessage> implements MessageChain {

    //处理不可重复添加

    @Override
    public boolean add(SingleMessage singleMessage) {
        return !this.contains(singleMessage) && super.add(singleMessage);
    }

    @Override
    public boolean addAll(Collection<? extends SingleMessage> c) {
        return this.addAll(size(), c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends SingleMessage> c) {
        int oldSize = size();
        for (SingleMessage element : c) {
            if (!contains(element)) {
                super.add(index++, element);
            }
        }
        return size() != oldSize;
    }

}
