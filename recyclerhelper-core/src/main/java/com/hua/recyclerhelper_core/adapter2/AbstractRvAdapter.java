package com.hua.recyclerhelper_core.adapter2;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hua.recyclerhelper_core.adapter2.listeners.OnItemClickListener;
import com.hua.recyclerhelper_core.adapter2.listeners.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 不要继承这个实现Adapter。
 * 单类型列表可以使用{@link SingleRvAdapter};
 * 多类型列表可以使用{@link MultiTypeRvAdapter}
 *
 * @author hua
 * @version V1.0
 * @date 2019/1/9 8:59
 */

@SuppressWarnings("unchecked")
abstract class AbstractRvAdapter<VH extends BaseViewHolder, T> extends RecyclerView.Adapter<VH> {
    protected Context context;
    protected List<T> dataList;
    private IBindHolder<VH, T> bindHolder;
    protected RecyclerView recyclerView;
    protected ListenerInfo listenerInfo;

    AbstractRvAdapter(Context context) {
        this.context = context;
        this.listenerInfo = new ListenerInfo();
    }

    AbstractRvAdapter<VH, T> setDataList(List<T> list) {
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
        return dataList;
    }

    AbstractRvAdapter<VH, T> setBinder(IBindHolder<VH, T> bindHolder) {
        this.bindHolder = bindHolder;
        return this;
    }

    @NonNull
    @Override
    public final VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VH holder = createHolder(parent, viewType);
        bindListeners(holder);
        return holder;
    }

    abstract VH createHolder(@NonNull ViewGroup parent, int viewType);

    private void bindListeners(final VH holder) {
        View itemView = holder.itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHolderClick(holder, getItemData(holder.getLayoutPosition()));
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onHolderLongClick(holder, getItemData(holder.getLayoutPosition()));
                return true;
            }
        });
    }

    private void onHolderClick(VH holder, T data) {
        if (listenerInfo.mOnItemClickListener != null) {
            listenerInfo.mOnItemClickListener.onClick(holder, data);
        }
    }

    private void onHolderLongClick(VH holder, T data) {
        if (listenerInfo.mOnItemLongClickListener != null) {
            listenerInfo.mOnItemLongClickListener.onLongClick(holder, data);
        }
    }

    @Override
    public final void onBindViewHolder(@NonNull VH holder, int position) {
        if (bindHolder != null) {
            bindHolder.bind(holder, getItemData(position));
        } else {
            onBind(holder, getItemData(position), position);
        }
    }

    void onBind(VH holder, T data, int position) {

    }

    T getItemData(int position) {
        return dataList != null ? dataList.get(position) : null;
    }

    @Override
    public int getItemCount() {
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

    AbstractRvAdapter<VH, T> setOnItemClickListener(OnItemClickListener<T> listener) {
        listenerInfo.mOnItemClickListener = listener;
        return this;
    }

    AbstractRvAdapter<VH, T> setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        listenerInfo.mOnItemLongClickListener = listener;
        return this;
    }

}
