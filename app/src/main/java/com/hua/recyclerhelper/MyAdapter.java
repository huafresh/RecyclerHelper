package com.hua.recyclerhelper;

import com.hua.recyclerhelper_core.adapter.BaseMultiTypeRvAdapter;
import com.hua.recyclerhelper_core.adapter.BaseViewHolder;
import com.hua.recyclerhelper_core.adapter.IItemViewType;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/8 10:52
 */

public class MyAdapter extends BaseMultiTypeRvAdapter {

    public MyAdapter(IItemViewType itemType) {
        super(itemType);
    }
}
