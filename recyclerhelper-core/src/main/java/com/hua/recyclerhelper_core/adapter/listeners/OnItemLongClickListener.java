package com.hua.recyclerhelper_core.adapter.listeners;

import android.view.View;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/8 9:53
 */

public interface OnItemLongClickListener<D> {
    /**
     * item被长按时调用
     *
     * @param view     被长按的item
     * @param position item的位置
     */
    void onLongClick(View view, D data, int position);
}
