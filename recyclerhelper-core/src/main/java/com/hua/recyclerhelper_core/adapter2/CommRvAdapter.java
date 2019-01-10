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
 * 为支持链式构造引入的一层Adapter。
 *
 * @author hua
 * @version V1.0
 * @date 2019/1/9 8:59
 */

@SuppressWarnings("unchecked")
public final class CommRvAdapter<T> extends BaseRvAdapter<T> {
    private boolean isSingle = false;

    CommRvAdapter(Context context) {
        super(context);
    }

    public static <T> CommRvAdapter<T> newAdapter(Context context, Class<T> model) {
        return new CommRvAdapter<>(context);
    }

    public SingleRvAdapter<T> single(@LayoutRes int layoutId) {
        isSingle = true;
        return new SingleRvAdapter(context, layoutId) {

            @Override
            protected void convert(BaseViewHolder holder, Object data) {
                throw new IllegalStateException("链式调用必须调用setBinder()方法进行item数据绑定");
            }
        };
    }

    public SingleRvAdapter<T> single(View itemView) {
        isSingle = true;
        return new SingleRvAdapter(itemView) {

            @Override
            protected void convert(BaseViewHolder holder, Object data) {
                throw new IllegalStateException("链式调用必须调用setBinder()方法进行item数据绑定");
            }
        };
    }

    @Override
    public BaseRvAdapter<T> setBinder(IBindHolder<T> bindHolder) {
        if(!isSingle){
            throw new RuntimeException("对于多类型列表，不同类型的绑定是由各自的\"IRvItemType\"实现的，因此不能设置\"IBindHolder\"");
        }
        return super.setBinder(bindHolder);
    }

}
