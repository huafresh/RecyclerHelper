package com.hua.rvhelper_core.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * 对应一个列表item类型。
 *
 * @author hua
 * @version 1.0
 * @since 2018/8/26
 * @see BaseRvItemType
 */
public interface IRvItemType<T> {
    /**
     * @return item xml layout id
     */
    @LayoutRes
    int layoutId();

    /**
     * @return create item view by yourself, this method has more priority than {@link #layoutId()}.
     */
    @Nullable
    View getItemView();

    /**
     * judge if the given position is for this item type.
     *
     * @param data     item data at <code>position</code>.
     * @param position position to query
     */
    boolean isForViewType(T data, int position);

    /**
     * bind item data to item view.
     */
    void bind(BaseViewHolder viewHolder, T data);

}
