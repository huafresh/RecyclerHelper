package com.hua.recyclerhelper_core.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

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
public abstract class BaseMultiItemRvAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = "MultiItemRvAdapter";
    protected final Context mContext;
    private List<Object> mDataList;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnTouchListener mOnTouchListener;

    private ItemViewDelegateManager itemViewDelegateManager;

    public BaseMultiItemRvAdapter(Context context) {
        this.mContext = context;
        this.itemViewDelegateManager = new ItemViewDelegateManager();
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
        itemViewDelegateManager.addItemViewDelegate(itemViewDelegate);
    }

    @Override
    public int getItemViewType(int position) {
        return itemViewDelegateManager.getItemViewType(mDataList.get(position), position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IItemViewType itemViewDelegate = itemViewDelegateManager.getItemViewDelegate(viewType);
        if (itemViewDelegate == null) {
            throw new IllegalArgumentException("please add at least one \"IItemViewType\"");
        }
        MyViewHolder viewHolder = MyViewHolder.createViewHolder(mContext, itemViewDelegate.layoutId(), parent);
        setListeners(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        itemViewDelegateManager.convert(holder, getItemDataInternal(position), position);
    }

    @Nullable
    private Object getItemDataInternal(int position) {
        Object itemData = getItemData(position);
        if (itemData == null) {
            itemData = mDataList != null ? mDataList.get(position) : null;
        }
        return itemData;
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
        return null;
    }

    private void setListeners(final MyViewHolder viewHolder) {
        View itemView = viewHolder.itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int pos = viewHolder.getLayoutPosition();
                    mOnItemClickListener.onClick(v, getItemDataInternal(pos), pos);
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    int pos = viewHolder.getLayoutPosition();
                    mOnItemLongClickListener.onLongClick(v, getItemDataInternal(pos), pos);
                }
                return true;
            }
        });

        itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mOnTouchListener != null &&
                        mOnTouchListener.onTouch(v, event, viewHolder.getLayoutPosition());
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener<?> listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<?> listener) {
        mOnItemLongClickListener = listener;
    }

    public void setOnTouchListener(OnTouchListener listener) {
        mOnTouchListener = listener;
    }


    public interface OnItemClickListener<D> {
        /**
         * item被点击时调用
         *
         * @param view     被点击的item
         * @param position item的位置
         */
        void onClick(View view, D data, int position);
    }

    public interface OnItemLongClickListener<D> {
        /**
         * item被长按时调用
         *
         * @param view     被长按的item
         * @param position item的位置
         */
        void onLongClick(View view, D data, int position);
    }

    public interface OnTouchListener {
        /**
         * item被触摸时调用
         *
         * @param v        被触摸的item
         * @param event    触摸事件
         * @param position item的位置
         * @return 是否消费
         */
        boolean onTouch(View v, MotionEvent event, int position);
    }


}
