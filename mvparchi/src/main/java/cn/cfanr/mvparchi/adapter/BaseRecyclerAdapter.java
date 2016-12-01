package cn.cfanr.mvparchi.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.cfanr.mvparchi.ui.viewholder.RecyclerHolder;

/**
 * author: xifan
 * date: 2016/9/2
 * desc:
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerHolder> {
    protected List<T> mDataList;
    protected final int mItemLayoutId;
    protected boolean isScrolling;
    protected Context mContext;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Object data, int position);
    }

    public BaseRecyclerAdapter(RecyclerView v, Collection<T> datas, int itemLayoutId) {
        if (datas == null) {
            mDataList = new ArrayList<T>();
        } else if (datas instanceof List) {
            mDataList = (List<T>) datas;
        } else {
            mDataList = new ArrayList<T>(datas);
        }
        mItemLayoutId = itemLayoutId;
        mContext = v.getContext();

        v.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling = !(newState == RecyclerView.SCROLL_STATE_IDLE);
                if (!isScrolling) {
                    notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Recycler适配器填充方法
     * @param holder      viewholder
     * @param item        javabean
     * @param isScrolling RecyclerView是否正在滚动
     */
    public abstract void convert(RecyclerHolder holder, T item, int position, boolean isScrolling);

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View root = inflater.inflate(mItemLayoutId, parent, false);
        return new RecyclerHolder(root);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        convert(holder, mDataList.get(position), position, isScrolling);
        holder.itemView.setOnClickListener(OnItemClickListener(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public View.OnClickListener OnItemClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(@Nullable View v) {
                if (onItemClickListener != null && v != null) {
                    onItemClickListener.onItemClick(v, mDataList.get(position), position);
                }
            }
        };
    }

    public BaseRecyclerAdapter<T> refresh(Collection<T> datas) {
        if (datas == null) {
            mDataList = new ArrayList<T>();
        } else if (datas instanceof List) {
            mDataList = (List<T>) datas;
        } else {
            mDataList = new ArrayList<T>(datas);
        }
        return this;
    }
}