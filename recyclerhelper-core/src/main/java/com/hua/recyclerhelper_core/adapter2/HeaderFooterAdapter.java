package com.hua.recyclerhelper_core.adapter2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.hua.recyclerhelper_core.adapter2.listeners.OnItemClickListener;

import java.util.List;

/**
 * 扩展Adapter实现了添加头部和底部视图的功能。
 *
 * @author hua
 * @version V1.0
 * @date 2019/1/9 9:05
 */

@SuppressWarnings("unchecked")
public class HeaderFooterAdapter<T> extends CommRvAdapter<T> {
    private AbstractRvAdapter<BaseViewHolder, T> adapter;
    private SparseArray<View> mHeadViews = new SparseArray<>();
    private SparseArray<View> mFootViews = new SparseArray<>();
    private static final int BASE_ITEM_TYPE_HEAD = 1000;
    private static final int BASE_ITEM_TYPE_FOOT = 2000;

    public HeaderFooterAdapter(AbstractRvAdapter<BaseViewHolder, T> adapter) {
        super(adapter.context);
        this.adapter = adapter;
        this.listenerInfo = adapter.listenerInfo;
    }

    public static <T> HeaderFooterAdapter<T> wrap(AbstractRvAdapter<BaseViewHolder, T> adapter) {
        return new HeaderFooterAdapter<>(adapter);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPos(position)) {
            return mHeadViews.keyAt(position);
        } else if (isFooterPos(position)) {
            return mFootViews.keyAt(footerCount() - getItemCount() + position);
        }
        return adapter.getItemViewType(convertToRealPosition(position));
    }

    @Override
    BaseViewHolder createHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mHeadViews.get(viewType);
        if (itemView == null) {
            itemView = mFootViews.get(viewType);
        }

        BaseViewHolder viewHolder = null;
        if (itemView != null) {
            parent.removeView(itemView);
            viewHolder = new BaseViewHolder(itemView);
        } else {
            viewHolder = adapter.onCreateViewHolder(parent, viewType);
        }
        return viewHolder;
    }

    @Override
    void onBind(BaseViewHolder holder, T data, int position) {
        if (!isHeaderPos(position) && !isFooterPos(position)) {
            final int realPos = convertToRealPosition(position);
            adapter.onBindViewHolder(holder, realPos);
        }
    }

    private int convertToRealPosition(int position) {
        return position - headerCount();
    }

    private boolean isFooterPos(int position) {
        return getItemCount() - position < footerCount() + 1;
    }

    private boolean isHeaderPos(int position) {
        return position < headerCount();
    }

    @Override
    public int getItemCount() {
        return realCount() + headerCount() + footerCount();
    }

    private int realCount() {
        return adapter.getItemCount();
    }

    public int headerCount() {
        return mHeadViews != null ? mHeadViews.size() : 0;
    }

    public int footerCount() {
        return mFootViews != null ? mFootViews.size() : 0;
    }

    public HeaderFooterAdapter<T> addHeaderView(View view) {
        if (mHeadViews != null) {
            mHeadViews.put(mHeadViews.size() + BASE_ITEM_TYPE_HEAD, view);
        } else {
            mHeadViews = new SparseArray<>();
            mHeadViews.put(mHeadViews.size() + BASE_ITEM_TYPE_HEAD, view);
        }
        return this;
    }

    HeaderFooterAdapter removeHeadView(View view) {
        if (mHeadViews != null) {
            int index = mHeadViews.indexOfValue(view);
            if (index != -1) {
                mHeadViews.removeAt(index);
            }
        }
        return this;
    }

    public HeaderFooterAdapter<T> addFooterView(View view) {
        if (mFootViews != null) {
            mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOT, view);
        } else {
            mFootViews = new SparseArray<>();
            mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOT, view);
        }
        return this;
    }

    @Override
    T getItemData(int position) {
        if (isHeaderPos(position) || isFooterPos(position)) {
            return null;
        }
        return adapter.getItemData(convertToRealPosition(position));
    }

    @Override
    public CommRvAdapter<T> setDataList(List<T> list) {
        adapter.setDataList(list);
        return this;
    }
}
