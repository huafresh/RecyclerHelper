package com.hua.recyclerhelper_core.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/8 17:43
 */

class HeaderFooterItem implements IItemViewType<Object, BaseViewHolder> {
    private View view;
    Object itemData;
    boolean isHeader;
    int index;

    HeaderFooterItem(View view, boolean isHeader, int index) {
        this.view = view;
        this.itemData = new Object();
        this.isHeader = isHeader;
        this.index = index;
    }

    @Nullable
    @Override
    public View getItemView() {
        return this.view;
    }

    @Override
    public int layoutId() {
        return 0;
    }

    @Override
    public boolean isForViewType(Object data, int position) {
        return this.itemData == data;
    }

    @Override
    public BaseViewHolder createViewHolder(@NonNull ViewGroup parent, @NonNull View itemView) {
        return null;
    }

    @Override
    public void bind(BaseViewHolder viewHolder, Object data) {

    }
}
