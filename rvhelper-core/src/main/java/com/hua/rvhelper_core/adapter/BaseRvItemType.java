package com.hua.rvhelper_core.adapter;

import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/9 15:39
 */

public abstract class BaseRvItemType<T> implements IRvItemType<T> {

    @Nullable
    @Override
    public View getItemView() {
        return null;
    }

}
