package com.hua.recyclerhelper_core.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * @author hua
 * @version 1.0
 * @since 2018/8/26
 */
interface IItemViewTypeInternal<T> extends IItemViewType<T, BaseViewHolder> {

    View getView(Context context);

}
