package com.hua.recyclerhelper_core.adapter.listeners;

import android.view.View;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/8 9:52
 */

public interface OnItemClickListener<D> {
    /**
     * item被点击时调用
     *
     * @param view     被点击的item
     * @param position item的位置
     */
    void onClick(View view, D data, int position);
}
