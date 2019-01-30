package com.hua.rvhelper_core.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;

import java.util.List;

/**
 * 为支持链式构造引入的一层Adapter。
 *
 * @author hua
 * @version V1.0
 * @date 2019/1/9 8:59
 */

@SuppressWarnings("unchecked")
public final class CommLvAdapter<T> extends BaseLvAdapter<T> {
    private IBindHolder<BaseLvViewHolder, T> bindHolder;

    private CommLvAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    private CommLvAdapter(Context context, int layoutId, List<T> dataList) {
        super(context, layoutId, dataList);
    }

    public static <T> CommLvAdapter<T> newAdapter(Context context, @LayoutRes int layoutId, Class<T> model) {
        return new CommLvAdapter<>(context, layoutId);
    }


    public CommLvAdapter<T> setBinder(IBindHolder<BaseLvViewHolder, T> bindHolder) {
        this.bindHolder = bindHolder;
        return this;
    }

    @Override
    protected void convert(BaseLvViewHolder viewHolder, T bean) {
        bindHolder.bind(viewHolder, bean);
    }
}
