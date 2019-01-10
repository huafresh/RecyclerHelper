package com.hua.recyclerhelper_core.adapter2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.hua.recyclerhelper_core.adapter2.listeners.OnItemClickListener;
import com.hua.recyclerhelper_core.adapter2.listeners.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/10 15:32
 */

@SuppressWarnings("unchecked")
public class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context context;
    protected List<T> dataList;
    private IBindHolder<T> bindHolder;
    protected RecyclerView recyclerView;
    protected ListenerInfo listenerInfo;
    private ItemViewTypeManager itemViewTypeManager;
    private HeaderFooterHelper headerFooterHelper;

    public BaseRvAdapter(Context context) {
        this.context = context;
        this.listenerInfo = new ListenerInfo();
        itemViewTypeManager = new ItemViewTypeManager();
        headerFooterHelper = new HeaderFooterHelper(this);
        registerAdapterDataObserver(new HeaderFooterHelper.AdapterObserver(headerFooterHelper));
    }

    public final BaseRvAdapter<T> setDataList(List<T> list) {
        if (list == null) {
            return this;
        }
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged();
        return this;
    }

    public final @Nullable
    List<T> getDataList() {
        return Collections.unmodifiableList(dataList);
    }

    public final BaseRvAdapter<T> insert(int position, T data) {
        if (dataList != null) {
            dataList.add(position, data);
            notifyItemInserted(position + headerCount());
        }
        return this;
    }

    private int headerCount() {
        return headerFooterHelper.headerCount();
    }

    public final BaseRvAdapter<T> append(T data) {
        if (dataList != null) {
            int position = dataList.size();
            dataList.add(position, data);
            notifyItemInserted(position + headerCount());
        }
        return this;
    }


    public final BaseRvAdapter<T> remove(int position) {
        if (dataList != null) {
            dataList.remove(position);
            notifyItemRemoved(position + headerCount());
        }
        return this;
    }


    public BaseRvAdapter<T> setBinder(IBindHolder<T> bindHolder) {
        this.bindHolder = bindHolder;
        return this;
    }

    private void bindListeners(final BaseViewHolder holder) {
        View itemView = holder.itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listenerInfo.mOnItemClickListener != null) {
                    int position = holder.getLayoutPosition();
                    listenerInfo.mOnItemClickListener.onClick(holder, getItemData(position));
                }
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listenerInfo.mOnItemLongClickListener != null) {
                    int position = holder.getLayoutPosition();
                    listenerInfo.mOnItemLongClickListener.onLongClick(holder, getItemData(position));
                    return true;
                }
                return false;
            }
        });
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IRvItemType itemType = itemViewTypeManager.getItemView(viewType);
        if (itemType == null) {
            throw new IllegalArgumentException("please add at least one \"IRvItemType\"");
        }

        View itemView = itemType.getItemView();
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(itemType.layoutId(), parent, false);
        } else {
            ViewGroup itemParent = (ViewGroup) itemView.getParent();
            if (itemParent != null) {
                itemParent.removeView(itemView);
            }
        }

        BaseViewHolder holder = new BaseViewHolder(itemView);
        bindListeners(holder);
        return holder;
    }

    @Override
    public final void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (bindHolder != null) {
            bindHolder.bind(holder, getItemData(position));
        } else {
            convert(holder, getItemData(position), position);
        }
    }

    protected void convert(BaseViewHolder holder, T data, int position) {
        if (!headerFooterHelper.isHeaderPos(position) &&
                !headerFooterHelper.isFooterPos(position)) {
            itemViewTypeManager.bind(holder, data, position);
        }
    }

    public T getItemData(int position) {
        if (headerFooterHelper.isHeaderPos(position) ||
                headerFooterHelper.isFooterPos(position)) {
            return null;
        }
        return dataList != null ? dataList.get(position - headerCount()) : null;
    }

    @Override
    public int getItemCount() {
        return getDataListCount() + headerFooterHelper.totalCount();
    }

    int getDataListCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = null;
    }

    public BaseRvAdapter<T> setOnItemClickListener(OnItemClickListener<T> listener) {
        listenerInfo.mOnItemClickListener = listener;
        return this;
    }

    public BaseRvAdapter<T> setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        listenerInfo.mOnItemLongClickListener = listener;
        return this;
    }

    public BaseRvAdapter<T> addRvItemType(IRvItemType... itemType) {
        for (IRvItemType anItemType : itemType) {
            itemViewTypeManager.addItemViewDelegate(anItemType);
        }
        return this;
    }

    @Override
    public int getItemViewType(int position) {
        IRvItemType headerFooterType = headerFooterHelper.getRvItemType(position);
        if (headerFooterType != null) {
            return itemViewTypeManager.getItemViewType(headerFooterType);
        }
        return itemViewTypeManager.getItemViewType(getItemData(position), position);
    }

    public BaseRvAdapter<T> addHeaderView(View header) {
        headerFooterHelper.addHeaderView(header, headerCount());
        return this;
    }

    public BaseRvAdapter<T> addFooterView(View footer) {
        headerFooterHelper.addFooterView(footer, headerCount() + getDataListCount());
        return this;
    }


//    @Override
//    public CommRvAdapter<T> setBinder(IBindHolder<BaseViewHolder, T> bindHolder) {
//        if (this.getClass() == MultiTypeRvAdapter.class) {
//            throw new IllegalStateException("多列表类型Adapter数据绑定由各个IRvItemType自行处理，因此不能调用setBinder()方法");
//        } else {
//            return super.setBinder(bindHolder);
//        }
//    }

}
