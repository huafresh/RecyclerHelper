package com.hua.rvhelper_core.adapter;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/9 9:14
 */

public interface IBindHolder<VH, T> {
    void bind(VH holder, T data);
}
