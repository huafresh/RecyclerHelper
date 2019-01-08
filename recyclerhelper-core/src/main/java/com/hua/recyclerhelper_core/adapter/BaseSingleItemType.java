package com.hua.recyclerhelper_core.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/8 11:20
 */

public abstract class BaseSingleItemType<T> implements IItemViewType<T, BaseViewHolder> {

    @Override
    public boolean isForViewType(T data, int position) {
        return true;
    }

    @Nullable
    @Override
    public View getItemView() {
        return null;
    }

    @Override
    public BaseViewHolder createViewHolder(@NonNull ViewGroup parent, @NonNull View itemView) {
        return new BaseViewHolder(itemView);
    }
}
