package com.hua.rvhelper_core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 给给定的Rv赋予加载更多的功能。
 * 注意：
 * 1、由于实现机制的问题，{@link LoadMoreHelper}会代理替换掉Rv的Adapter，
 * 以便在底部增加一个Loading item。因此，如果Rv的Adapter更新了，需要重新实例化此类；
 * <p>
 * 2、因为要操作Adapter的数据集，因此请让你的Rv Adapter实现{@link IAdapter}接口
 *
 * @author zhangsh
 * @version V1.0
 * @date 2020-02-15 10:45
 */

@SuppressWarnings("unchecked")
public class LoadMoreHelper {
    private RecyclerView recyclerView;
    private int loadMoreThreshold = 1;
    private ILoadMoreApi loadMoreApi;
    private boolean requesting = false;
    private LoadMoreAdapter loadMoreAdapter;

    public static void wrapWithLoadMore(RecyclerView recyclerView, ILoadMoreApi api){
        new LoadMoreHelper(recyclerView, api);
    }

    /**
     * @param threshold 倒数第几个item可见时触发加载更多，0表示最后一个item，依此类推
     */
    public static void wrapWithLoadMore(RecyclerView recyclerView,
                                        ILoadMoreApi api,
                                        int threshold){
        new LoadMoreHelper(recyclerView, threshold, api);
    }

    private LoadMoreHelper(RecyclerView recyclerView,
                           int loadMoreThreshold,
                           ILoadMoreApi loadMoreApi
    ) {
        this.recyclerView = recyclerView;
        this.loadMoreThreshold = loadMoreThreshold;
        this.loadMoreApi = loadMoreApi;
        setup();
    }

    private LoadMoreHelper(RecyclerView recyclerView,
                           ILoadMoreApi loadMoreApi) {
        this.recyclerView = recyclerView;
        this.loadMoreApi = loadMoreApi;
        setup();
    }

    private void setup() {
        replaceRvAdapter();
        addRvScrollListener();
    }

    private void addRvScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (isRvAdapterChanged()) {
                    return;
                }
                if (Math.abs(dy) > 0) {
                    loadMoreAdapter.enSureLoadMoreItem();
                }
                LoadMoreHelper.this.loadMoreIfNeed();
            }
        });
    }


    private void replaceRvAdapter() {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            return;
        }
        if (!(adapter instanceof IAdapter)) {
            throw new IllegalArgumentException("adapter务必实现IAdapter接口");
        }
        loadMoreAdapter = new LoadMoreAdapter(adapter);
        recyclerView.setAdapter(loadMoreAdapter);
    }

    private boolean isRvAdapterChanged() {
        return !(recyclerView.getAdapter() instanceof LoadMoreAdapter);
    }

    private void loadMoreIfNeed() {
        final LoadMoreAdapter adapter = (LoadMoreAdapter) recyclerView.getAdapter();
        if (adapter != null && isRvReachBottom() && !requesting) {
            if (adapter.hasMoreItem()) {
                int offset = adapter.getItemCount() - 1;
                final List<Object> list = new ArrayList<>();
                LoadMoreCallback callback = new LoadMoreCallback() {
                    @Override
                    public void onSuccess(boolean hasMore) {
                        requesting = false;
                        adapter.appendItemList(list);
                        if (!hasMore) {
                            adapter.updateLoadMoreItem(false, false);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        requesting = false;
                        adapter.updateLoadMoreItem(true, true);
                    }
                };
                requesting = true;
                loadMoreApi.loadMore(list, offset, callback);
            }
        }
    }

    private boolean isRvReachBottom() {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            return false;
        }

        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int lastAdapterPos = linearLayoutManager.findLastVisibleItemPosition();
            int totalCount = linearLayoutManager.getItemCount();
            return totalCount - lastAdapterPos - 1 <= loadMoreThreshold;
        } else {
            return recyclerView.canScrollHorizontally(1) || recyclerView.canScrollVertically(1);
        }
    }

    @SuppressWarnings({"unchecked", "BooleanMethodIsAlwaysInverted"})
    private class LoadMoreAdapter extends
            RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private RecyclerView.Adapter<RecyclerView.ViewHolder> originAdapter;
        private static final int LOAD_MORE_VIEW_TYPE = Integer.MAX_VALUE;
        private List<Object> itemList;
        private boolean moreItemAdded = false;

        private LoadMoreAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> originAdapter) {
            this.originAdapter = originAdapter;
            originAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    onDataSetChanged();
                    LoadMoreAdapter.this.notifyDataSetChanged();
                    moreItemAdded = false;
                }
            });
            onDataSetChanged();
        }

        private void onDataSetChanged() {
            IAdapter iAdapter = (IAdapter) originAdapter;
            itemList = iAdapter.getItemList();
            if (itemList == null) {
                itemList = Collections.emptyList();
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == LOAD_MORE_VIEW_TYPE) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateLoadMoreItem(true, false);
                        LoadMoreHelper.this.loadMoreIfNeed();
                    }
                });
                return new RecyclerView.ViewHolder(view) {
                };
            }
            return originAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (!isLoadMoreItem(position)) {
                originAdapter.onBindViewHolder(holder, position);
            } else {
                LoadMoreItemBean loadMore = (LoadMoreItemBean) itemList.get(itemList.size() - 1);
                if (loadMore.isFailed) {
                    ((TextView) holder.itemView.findViewById(R.id.text)).setText("加载失败，点击重试");
                } else {
                    ((TextView) holder.itemView.findViewById(R.id.text)).setText("正在加载更多...");
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (!isLoadMoreItem(position)) {
                return originAdapter.getItemViewType(position);
            }
            return LOAD_MORE_VIEW_TYPE;
        }

        private boolean isLoadMoreItem(int position) {
            Object o = itemList.get(itemList.size() - 1);
            return (o instanceof LoadMoreItemBean) &&
                    position == getItemCount() - 1;
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        private void appendItemList(List<Object> list) {
            if (!itemList.isEmpty() && !list.isEmpty()) {
                int totalCount = getItemCount();
                Object last = itemList.remove(itemList.size() - 1);
                itemList.addAll(list);
                itemList.add(last);
                ((IAdapter) originAdapter).setItemList(itemList);
                notifyItemRangeInserted(totalCount - 1, list.size());
            }
        }

        private void updateLoadMoreItem(boolean show, boolean failed) {
            int lastIndex = itemList.size() - 1;
            int totalCount = getItemCount();
            LoadMoreItemBean cur = (LoadMoreItemBean) itemList.get(lastIndex);
            if (cur != null) {
                if (cur.show && !show) {
                    itemList.remove(lastIndex);
                    notifyItemRemoved(totalCount - 1);
                } else if (cur.isFailed != failed) {
                    cur.isFailed = failed;
                    notifyItemChanged(totalCount - 1);
                }
            }
        }

        private boolean hasMoreItem() {
            return itemList.get(itemList.size() - 1) instanceof LoadMoreItemBean;
        }


        private void enSureLoadMoreItem() {
            if (!moreItemAdded && itemList.size() > 0) {
                int index = itemList.size();
                itemList.add(new LoadMoreItemBean(true, false));
                notifyItemInserted(index);
                moreItemAdded = true;
            }
        }
    }

    private static class LoadMoreItemBean {
        private boolean show;
        private boolean isFailed;

        private LoadMoreItemBean(boolean show, boolean failed) {
            this.show = show;
            this.isFailed = failed;
        }
    }

    public interface ILoadMoreApi {
        /**
         * 获取更多数据
         *
         * @param list     接收请求到的数据
         * @param offset   这个值等于列表当前的item总数
         * @param callback 通知LoadMoreHelper本次请求的情况，请确保一定要调用某一个方法。
         */
        void loadMore(List<Object> list, int offset, LoadMoreCallback callback);
    }

    public interface LoadMoreCallback {
        void onSuccess(boolean hasMore);

        void onFailed(Throwable e);
    }

    public interface IAdapter<T> {
        @Nullable
        List<T> getItemList();

        void setItemList(List<T> list);
    }
}
