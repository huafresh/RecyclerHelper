package com.hua.recyclerhelper_core.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.hua.recyclerhelper_core.adapter.listeners.OnItemClickListener;
import com.hua.recyclerhelper_core.adapter.listeners.OnItemLongClickListener;
import com.hua.recyclerhelper_core.adapter.listeners.OnTouchListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * recyclerView通用多类型适配器
 * 调用{@link #addItemViewType}方法添加不同类型的item
 *
 * @author hua
 * @date 2017/8/14
 */

@SuppressWarnings("unchecked")
public abstract class BaseMultiTypeRvAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "BaseMultiTypeRvAdapter";
    private List<Object> mDataList;
    private ListenerInfo listenerInfo;
    private RecyclerView recyclerView;
    private ItemViewTypeManager itemViewTypeManager;
    private List<HeaderFooterItem> headerFooterItems;

    public BaseMultiTypeRvAdapter() {
        this.itemViewTypeManager = new ItemViewTypeManager();
        this.listenerInfo = new ListenerInfo();
    }

    /**
     * 获取item数据列表
     */
    public List<?> getDataList() {
        return Collections.unmodifiableList(mDataList);
    }

    /**
     * 设置item数据列表
     */
    public void setDataList(List<?> list) {
        if (list == null) {
            Log.e(TAG, "setDataList: data list is null");
            return;
        }
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataList.clear();
        mDataList.addAll(list);
        appendHeaderFooterData();
        notifyDataSetChanged();
    }

    private void appendHeaderFooterData() {
        int realCount = mDataList.size();
        for (HeaderFooterItem item : headerFooterItems) {
            if (item.isHeader) {
                mDataList.add(item.index, item.itemData);
            } else {
                mDataList.add(realCount + item.index + 1, item.itemData);
            }
        }
    }

    public void addItemViewType(IItemViewType<?, ? extends BaseViewHolder> itemViewType) {
        itemViewTypeManager.addItemViewDelegate(itemViewType);
    }

    @Override
    public int getItemViewType(int position) {
        return itemViewTypeManager.getItemViewType(getItemData(position), position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IItemViewType itemViewType = itemViewTypeManager.getItemView(viewType);
        if (itemViewType == null) {
            throw new IllegalArgumentException("please add at least one \"IItemViewType\"");
        }

        View itemView = itemViewType.getItemView();
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(itemViewType.layoutId(), parent, false);
        }

        BaseViewHolder viewHolder = itemViewType.createViewHolder(parent, itemView);
        setListeners(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        itemViewTypeManager.bind(holder, getItemData(position), position);
    }

    @Override
    public int getItemCount() {
        int realCount = mDataList != null ? mDataList.size() : 0;
        return realCount + headerCount() + footerCount();
    }

    private Object getItemData(int position) {
        return mDataList != null ? mDataList.get(position) : null;
    }

    private void setListeners(final BaseViewHolder viewHolder) {
        View itemView = viewHolder.itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listenerInfo.mOnItemClickListener != null) {
                    int pos = viewHolder.getLayoutPosition();
                    listenerInfo.mOnItemClickListener.onClick(viewHolder, getItemData(pos));
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listenerInfo.mOnItemLongClickListener != null) {
                    int pos = viewHolder.getLayoutPosition();
                    listenerInfo.mOnItemLongClickListener.onLongClick(viewHolder, getItemData(pos));
                }
                return true;
            }
        });

//        itemView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return listenerInfo.mOnTouchListener != null &&
//                        listenerInfo.mOnTouchListener.onTouch(v, event, viewHolder.getLayoutPosition());
//            }
//        });
    }

    public void setOnItemClickListener(OnItemClickListener<?> listener) {
        listenerInfo.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<?> listener) {
        listenerInfo.mOnItemLongClickListener = listener;
    }

    /**
     * @deprecated unSupport now
     */
    @Deprecated
    public void setOnTouchListener(OnTouchListener listener) {
        listenerInfo.mOnTouchListener = listener;
    }

    protected Context getContext() {
        if (recyclerView == null) {
            throw new IllegalStateException("adapter already detached from RecyclerView");
        }
        return recyclerView.getContext();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = null;
    }

    public int headerCount() {
        int count = 0;
        if (headerFooterItems != null) {
            for (HeaderFooterItem item : headerFooterItems) {
                if (item.isHeader) {
                    count++;
                }
            }
        }

        return count;
    }

    public int footerCount() {
        int count = 0;
        if (headerFooterItems != null) {
            for (HeaderFooterItem item : headerFooterItems) {
                if (!item.isHeader) {
                    count++;
                }
            }
        }

        return count;
    }

    public void addHeader(final View headerView) {
        HeaderFooterItem item = new HeaderFooterItem(headerView, true, headerCount());
        addItemViewType(item);
        addHeaderFooterItem(item);
    }

    private void addHeaderFooterItem(HeaderFooterItem item) {
        if (headerFooterItems == null) {
            headerFooterItems = new ArrayList<>();
        }
        headerFooterItems.add(item);
    }

    public void addFooter(final View footer) {
        HeaderFooterItem item = new HeaderFooterItem(footer, false, footerCount());
        addItemViewType(item);
        addHeaderFooterItem(item);
    }

}
