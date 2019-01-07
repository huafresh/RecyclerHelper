package com.hua.recyclerhelper_core.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.hua.recyclerhelper_core.adapter.BaseMultiItemRvAdapter;
import com.hua.recyclerhelper_core.adapter.MyViewHolder;

/**
 * 对现有的recyclerView adapter进行装饰，使其能够添加头部和尾部布局。
 * 适用于增加需求的情况，如果是要设计新的adapter，可以直接继承{@link BaseMultiItemRvAdapter}
 * 然后把头部和尾部当成特殊的item即可
 *
 * @author hua
 * @date 2017/8/14
 */
public class HeaderFootWrapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerView.Adapter mAdapter;

    private SparseArray<View> mHeadViews = new SparseArray<>();
    private SparseArray<View> mFootViews = new SparseArray<>();
    private static final int REAL_ITEM_TYPE = 0;
    private static final int BASE_ITEM_TYPE_HEAD = 100;
    private static final int BASE_ITEM_TYPE_FOOT = 200;

    public HeaderFootWrapAdapter(RecyclerView.Adapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mHeadViews.get(viewType);
        if (itemView == null) {
            itemView = mFootViews.get(viewType);
        }

        RecyclerView.ViewHolder viewHolder = null;
        if (itemView != null) {
            parent.removeView(itemView);
            viewHolder = new MyViewHolder(itemView);
        } else {
            viewHolder = mAdapter.onCreateViewHolder(parent, viewType);
        }
        return viewHolder;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (position >= headerCount() &&
                getItemCount() - position >= footerCount() + 1) {
            final int realPos = position - headerCount();
            mAdapter.onBindViewHolder(holder, realPos);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < headerCount()) {
            return mHeadViews.keyAt(position);
        } else if (getItemCount() - position < footerCount() + 1) {
            return mFootViews.keyAt(footerCount() - getItemCount() + position);
        }
        return mAdapter.getItemViewType(position - headerCount());
    }

    @Override
    public int getItemCount() {
        return realCount() + headerCount() + footerCount();
    }

    private int realCount() {
        return mAdapter != null ? mAdapter.getItemCount() : 0;
    }

    private int headerCount() {
        return mHeadViews != null ? mHeadViews.size() : 0;
    }

    private int footerCount() {
        return mFootViews != null ? mFootViews.size() : 0;
    }

    /**
     * 添加一个头部布局
     *
     * @param view View
     */
    public void addHeadView(View view) {
        if (mHeadViews != null) {
            mHeadViews.put(mHeadViews.size() + BASE_ITEM_TYPE_HEAD, view);
        } else {
            mHeadViews = new SparseArray<>();
            mHeadViews.put(mHeadViews.size() + BASE_ITEM_TYPE_HEAD, view);
        }
    }

    public void removeHeadView(View view) {
        if (mHeadViews != null) {
            int index = mHeadViews.indexOfValue(view);
            if (index != -1) {
                mHeadViews.removeAt(index);
            }
        }
    }

    /**
     * 添加一个底部布局
     *
     * @param view View
     */
    public void addFootView(View view) {
        if (mFootViews != null) {
            mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOT, view);
        } else {
            mFootViews = new SparseArray<>();
            mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOT, view);
        }
    }

}
