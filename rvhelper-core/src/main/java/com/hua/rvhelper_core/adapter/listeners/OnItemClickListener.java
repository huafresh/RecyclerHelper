package com.hua.rvhelper_core.adapter.listeners;


import com.hua.rvhelper_core.adapter.BaseViewHolder;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/8 9:52
 */

public interface OnItemClickListener<T> {
    void onClick(BaseViewHolder holder, T data);
}
