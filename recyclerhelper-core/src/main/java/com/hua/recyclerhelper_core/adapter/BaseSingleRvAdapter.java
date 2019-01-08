package com.hua.recyclerhelper_core.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

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
        addItemViewType(new BaseSingleItemType<Object>() {
            @Override
            public int layoutId() {
                return layoutId;
            }

            @Override
            public void bind(BaseViewHolder viewHolder, Object data) {
                BaseSingleRvAdapter.this.bind(viewHolder, (T) data);
            }
        });
        setDataList(dataList);
    }

    /**
     * 具体的bean与item绑定时调用此方法
     *
     * @param holder   itemView holder
     * @param data     bean
     */
    protected abstract void bind(BaseViewHolder holder, T data);

}
