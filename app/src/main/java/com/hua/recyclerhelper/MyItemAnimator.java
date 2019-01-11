package com.hua.recyclerhelper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/10 17:46
 */

public class MyItemAnimator extends DefaultItemAnimator {

    @Override
    public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder,
                                        @NonNull ItemHolderInfo preLayoutInfo,
                                        @Nullable ItemHolderInfo postLayoutInfo) {
        Log.e("@@@hua","animateDisappearance");
        return super.animateDisappearance(viewHolder, preLayoutInfo, postLayoutInfo);
    }

    @Override
    public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder,
                                     @Nullable ItemHolderInfo preLayoutInfo,
                                     @NonNull ItemHolderInfo postLayoutInfo) {
        Log.e("@@@hua","animateAppearance");
        return super.animateAppearance(viewHolder, preLayoutInfo, postLayoutInfo);
    }

    @Override
    public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder,
                                      @NonNull ItemHolderInfo preLayoutInfo,
                                      @NonNull ItemHolderInfo postLayoutInfo) {
        Log.e("@@@hua","persistence");
        return super.animatePersistence(viewHolder, preLayoutInfo, postLayoutInfo);
    }

    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder,
                                 @NonNull RecyclerView.ViewHolder newHolder,
                                 @NonNull ItemHolderInfo preLayoutInfo,
                                 @NonNull ItemHolderInfo postLayoutInfo) {
        Log.e("@@@hua","animateChange");
        return super.animateChange(oldHolder, newHolder, preLayoutInfo, postLayoutInfo);
    }

    @Override
    public void runPendingAnimations() {
        Log.e("@@@hua","runPendingAnimations");
        super.runPendingAnimations();
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {
        Log.e("@@@hua","endAnimation");
        super.endAnimation(item);
    }

    @Override
    public void endAnimations() {
        Log.e("@@@hua","endAnimations");
        super.endAnimations();
    }

    @Override
    public boolean isRunning() {
        //Log.e("@@@hua","isRunning");
        return super.isRunning();
    }
}
