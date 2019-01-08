package com.hua.recyclerhelper_core.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    }

    public void addItemViewType(IItemViewType<?> itemViewDelegate) {
        itemViewTypeManager.addItemViewDelegate(itemViewDelegate);
    }

    @Override
    public int getItemViewType(int position) {
        return itemViewTypeManager.getItemViewType(getItemData(position), position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IItemViewType itemViewDelegate = itemViewTypeManager.getItemViewDelegate(viewType);
        if (itemViewDelegate == null) {
            throw new IllegalArgumentException("please add at least one \"IItemViewType\"");
        }
        BaseViewHolder viewHolder = BaseViewHolder.createViewHolder(
                getContext(), itemViewDelegate.layoutId(), parent);
        setListeners(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        itemViewTypeManager.convert(holder, getItemData(position), position);
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    /**
     * 获取item绑定的bean
     *
     * @param position 位置
     * @return Object 绑定的bean
     */
    protected Object getItemData(int position) {
        return mDataList != null ? mDataList.get(position) : null;
    }

    private void setListeners(final BaseViewHolder viewHolder) {
        View itemView = viewHolder.itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listenerInfo.mOnItemClickListener != null) {
                    int pos = viewHolder.getLayoutPosition();
                    listenerInfo.mOnItemClickListener.onClick(v, getItemData(pos), pos);
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listenerInfo.mOnItemLongClickListener != null) {
                    int pos = viewHolder.getLayoutPosition();
                    listenerInfo.mOnItemLongClickListener.onLongClick(v, getItemData(pos), pos);
                }
                return true;
            }
        });

        itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return listenerInfo.mOnTouchListener != null &&
                        listenerInfo.mOnTouchListener.onTouch(v, event, viewHolder.getLayoutPosition());
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener<?> listener) {
        listenerInfo.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<?> listener) {
        listenerInfo.mOnItemLongClickListener = listener;
    }

    public void setOnTouchListener(OnTouchListener listener) {
        listenerInfo.mOnTouchListener = listener;
    }

    protected Context getContext() {
        if (recyclerView == null) {
            throw new IllegalStateException("adapter already detach from RecyclerView");
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
}
