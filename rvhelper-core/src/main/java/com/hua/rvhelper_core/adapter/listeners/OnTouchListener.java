package com.hua.rvhelper_core.adapter.listeners;

import android.view.MotionEvent;
import android.view.View;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/8 9:53
 */

public interface OnTouchListener {
    /**
     * item被触摸时调用
     *
     * @param v        被触摸的item
     * @param event    触摸事件
     * @param position item的位置
     * @return 是否消费
     */
    boolean onTouch(View v, MotionEvent event, int position);
}
