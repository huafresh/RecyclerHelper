package com.hua.recyclerhelper_core.adapter;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/8 11:20
 */

public abstract class BaseSingleItemType<T> implements IItemViewType<T> {

    @Override
    public boolean isForViewType(T data, int position) {
        return true;
    }
}
