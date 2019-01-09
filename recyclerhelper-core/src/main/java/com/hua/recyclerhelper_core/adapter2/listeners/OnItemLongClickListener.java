package com.hua.recyclerhelper_core.adapter2.listeners;


import com.hua.recyclerhelper_core.adapter2.BaseViewHolder;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/8 9:53
 */

public interface OnItemLongClickListener<T> {

    void onLongClick(BaseViewHolder holder, T data);
}
