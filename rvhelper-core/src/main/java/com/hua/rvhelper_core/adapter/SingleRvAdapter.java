package com.hua.rvhelper_core.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * 使用方式：
 * 1、继承此类，重写{@link #convert(BaseViewHolder, Object)}做数据绑定；
 * 2、使用{@link CommRvAdapter#single(int)}方法构造实例，例如：
 * <code>
 * CommRvAdapter.withModel(MainActivity.this, String.class)
 * .single(R.layout.recycler_item)
 * .setDataList()
 * .setBinder()
 * <code/>
 *
 * @author hua
 * @version V1.0
 * @date 2019/1/9 8:59
 */

@SuppressWarnings("unchecked")
public abstract class SingleRvAdapter<T> extends BaseRvAdapter<T> {
    private View itemView;
    private int layoutId = -1;

    public SingleRvAdapter(Context context, @LayoutRes int layoutId) {
        super(context);
        this.layoutId = layoutId;
        addItemType();
    }

    public SingleRvAdapter(View itemView) {
        super(itemView.getContext());
        this.itemView = itemView;
        addItemType();
    }

    private void addItemType() {
        addRvItemType(new IRvItemType<T>() {
            @Override
            public int layoutId() {
                return layoutId;
            }

            @Nullable
            @Override
            public View getItemView() {
                return itemView;
            }

            @Override
            public boolean isForViewType(Object data, int position) {
                return true;
            }

            @Override
            public void bind(BaseViewHolder viewHolder, T data) {
                SingleRvAdapter.this.convert(viewHolder, data);
            }
        });
    }

    protected abstract void convert(BaseViewHolder holder, T data);

}
