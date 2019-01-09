package com.hua.recyclerhelper_core.adapter2;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/9 8:59
 */

@SuppressWarnings("unchecked")
public class CommRvAdapter<T> extends AbstractRvAdapter<BaseViewHolder, T> {

    CommRvAdapter(Context context) {
        super(context);
    }

    public static <T> CommRvAdapter<T> withModel(Context context, Class<T> model) {
        return new CommRvAdapter<>(context);
    }

    public SingleRvAdapter<T> single(@LayoutRes int layoutId) {
        return new SingleRvAdapter(context, layoutId) {

            @Override
            protected void convert(BaseViewHolder holder, Object data) {
                throw new IllegalStateException("链式调用构造Adapter，必须调用AbstractRvAdapter#setBinder()方法进行item数据绑定");
            }
        };
    }

    public SingleRvAdapter<T> single(View itemView) {
        return new SingleRvAdapter(itemView) {

            @Override
            protected void convert(BaseViewHolder holder, Object data) {
                throw new IllegalStateException("链式调用构造Adapter，必须调用AbstractRvAdapter#setBinder()方法进行item数据绑定");
            }
        };
    }

    public MultiTypeRvAdapter<T> multiType() {
        return new MultiTypeRvAdapter(context);
    }

//    public void bind(IBindHolder bindHolder) {
//        this.bindHolder = bindHolder;
//    }


//    @Override
//    public CommRvAdapter<T> setDataList(List<T> list) {
//        super.setDataList(list);
//        return this;
//    }
//
//    @Override
//    public CommRvAdapter<T> setBinder(IBindHolder<BaseViewHolder, T> bindHolder) {
//        super.setBinder(bindHolder);
//        return this;
//    }

    @Override
    BaseViewHolder createHolder(@NonNull ViewGroup parent, int viewType) {
        throw new IllegalStateException("you must override this method");
    }
}
