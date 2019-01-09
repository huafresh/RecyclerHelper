package com.hua.recyclerhelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.hua.recyclerhelper_core.adapter2.BaseViewHolder;
import com.hua.recyclerhelper_core.adapter2.SingleRvAdapter;


/**
 * @author hua
 * @version V1.0
 * @date 2019/1/8 10:52
 */

public class MyAdapter extends SingleRvAdapter<String>{


    public MyAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void convert(BaseViewHolder holder, String data) {

    }
}
