package com.hua.recyclerhelper_core.adapter;

import android.support.annotation.LayoutRes;

/**
 * @author hua
 * @version 1.0
 * @since 2018/8/26
 */
public interface IItemViewType<T> {

    @LayoutRes
    int layoutId();

    boolean isForViewType(T data, int position);

    void convert(BaseViewHolder viewHolder, T data, int position);

}
