package com.hua.recyclerhelper_core.adapter2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 使用方式：
 * 1、继承此类，调用{@link #addRvItemType(IRvItemType[])}方法添加列表item的类型。
 * PS：如果数据集不为空，则最少需要设置一个{@link IRvItemType}。
 * 2、使用{@link CommRvAdapter#single(int)}方法构造实例，例如：
 * <code>
 * CommRvAdapter.withModel(MainActivity.this, String.class)
 * .multiType()
 * .addRvItemType()
 * .setDataList()
 * .setBinder()
 * <code/>
 *
 * @author hua
 * @version V1.0
 * @date 2019/1/9 9:05
 */

@SuppressWarnings("unchecked")
public class MultiTypeRvAdapter<T> extends CommRvAdapter<T> {
    private ItemViewTypeManager itemViewTypeManager;

    public MultiTypeRvAdapter(Context context) {
        super(context);
        this.itemViewTypeManager = new ItemViewTypeManager();
    }

    public MultiTypeRvAdapter<T> addRvItemType(IRvItemType... itemType) {
        for (IRvItemType anItemType : itemType) {
            itemViewTypeManager.addItemViewDelegate(anItemType);
        }
        return this;
    }

    @Override
    public int getItemViewType(int position) {
        return itemViewTypeManager.getItemViewType(getItemData(position), position);
    }

    @Override
    BaseViewHolder createHolder(@NonNull ViewGroup parent, int viewType) {
        IRvItemType itemType = itemViewTypeManager.getItemView(viewType);
        if (itemType == null) {
            throw new IllegalArgumentException("please add at least one \"IRvItemType\"");
        }

        View itemView = itemType.getItemView();
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(itemType.layoutId(), parent, false);
        }

        return new BaseViewHolder(itemView);
    }

    @Override
    void onBind(BaseViewHolder holder, Object data, int position) {
        itemViewTypeManager.bind(holder, data, position);
    }

    @Override
    public AbstractRvAdapter<BaseViewHolder, T> setBinder(IBindHolder<BaseViewHolder, T> bindHolder) {
        if (this.getClass() == MultiTypeRvAdapter.class) {
            throw new IllegalStateException("多列表类型Adapter数据绑定由各个IRvItemType自行处理，因此不能调用setBinder()方法");
        } else {
            return super.setBinder(bindHolder);
        }
    }
}
