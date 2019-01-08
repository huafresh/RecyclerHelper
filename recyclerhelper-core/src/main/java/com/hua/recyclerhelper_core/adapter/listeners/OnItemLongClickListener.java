package com.hua.recyclerhelper_core.adapter.listeners;

import com.hua.recyclerhelper_core.adapter.BaseViewHolder;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/8 9:53
 */

public interface OnItemLongClickListener<T> {

    void onLongClick(BaseViewHolder holder, T data);
}
