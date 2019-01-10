package com.hua.recyclerhelper_core.adapter2;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/9 9:14
 */

public interface IBindHolder<T> {
    void bind(BaseViewHolder holder, T data);
}
