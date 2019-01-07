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

public abstract class BaseSingleRvAdapter<T> extends BaseMultiItemRvAdapter {

    public BaseSingleRvAdapter(Context context, @LayoutRes int layoutId) {
        this(context, null, layoutId);
    }

    public BaseSingleRvAdapter(Context context, List<T> dataList, @LayoutRes final int layoutId) {
        super(context);
        addItemViewType(new IItemViewType<T>() {
            @Override
            public int layoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T data, int position) {
                return true;
            }

            @Override
            public void convert(MyViewHolder viewHolder, T data, int position) {
                BaseSingleRvAdapter.this.convert(viewHolder, data, position);
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
    protected abstract void convert(MyViewHolder holder, T data, int position);

}
