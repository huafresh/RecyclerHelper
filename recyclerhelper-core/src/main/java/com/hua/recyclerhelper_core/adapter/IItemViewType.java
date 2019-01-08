package com.hua.recyclerhelper_core.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author hua
 * @version 1.0
 * @since 2018/8/26
 */
public interface IItemViewType<T, VH extends BaseViewHolder> {
    /**
     * @return item xml layout id
     */
    @LayoutRes
    int layoutId();

    /**
     * @return create item view by yourself, this call before than {@link #layoutId()}
     */
    @Nullable
    View getItemView();

    /**
     * judge if the given position is for this type.
     *
     * @param data     item data at <code>position</code>.
     * @param position position to query
     */
    boolean isForViewType(T data, int position);

    /**
     * create RecyclerView viewHolder.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param itemView return by {@link #getItemView()} or inflate from xml layout
     * @return viewHolder
     */
    VH createViewHolder(@NonNull ViewGroup parent, @NonNull View itemView);

    /**
     * bind item data to item view.
     */
    void bind(VH viewHolder, T data);

}
