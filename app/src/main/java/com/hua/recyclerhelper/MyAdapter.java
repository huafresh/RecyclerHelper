package com.hua.recyclerhelper;

import com.hua.recyclerhelper_core.adapter.BaseMultiTypeAdapter;
import com.hua.recyclerhelper_core.adapter.BaseViewHolder;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/8 10:52
 */

public class MyAdapter extends BaseMultiTypeAdapter<String> {
    public MyAdapter(int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(BaseViewHolder holder, String data, int position) {

    }
}
