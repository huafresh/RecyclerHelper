package com.hua.recyclerhelper_core.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import java.util.List;

/**
 * recyclerView adapter的进一步封装，适用于item类型单一的情况。
 *
 * @author hua
 * @date 2017/6/16
 */

@SuppressWarnings("unchecked")
public abstract class BaseSingleRvAdapter<T> extends BaseMultiTypeRvAdapter {

    public BaseSingleRvAdapter(@LayoutRes int layoutId) {
        this(null, layoutId);
    }

    public BaseSingleRvAdapter(List<T> dataList, @LayoutRes final int layoutId) {
        super();
        addItemViewType(new BaseSingleItemType() {
            @Override
            public int layoutId() {
                return layoutId;
            }

            @Override
            public void convert(BaseViewHolder viewHolder, Object data, int position) {
                BaseSingleRvAdapter.this.convert(viewHolder, (T) data, position);
            }
        });
        setDataList(dataList);
    }

    /**
     * 具体的bean与item绑定时调用此方法
     *
     * @param holder   itemView holder
     * @param data     bean
     * @param position item位置
     */
    protected abstract void convert(BaseViewHolder holder, T data, int position);

}
