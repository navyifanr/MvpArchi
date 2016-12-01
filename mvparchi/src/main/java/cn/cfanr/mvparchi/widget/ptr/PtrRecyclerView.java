package cn.cfanr.mvparchi.widget.ptr;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import cn.cfanr.mvparchi.R;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author xifan
 *         date: 2016/8/30
 *         desc:
 */
public class PtrRecyclerView extends FrameLayout {
    private PtrFrameLayout mPtrFrame;
    private RecyclerView mRecyclerView;
    private PtrClassicHeader ptrHeader;
    private PtrClassicFooter ptrFooter;

    private PtrListener ptrListener;
    private PtrLoadMoreListener ptrLoadMoreListener;

    /**
     *只要下拉刷新时使用，同时记得调用PtrListView.setMode(PtrFrameLayout.Mode.REFRESH); 方法
     * @param ptrListener
     */
    public void setPtrListener(PtrListener ptrListener){
        this.ptrListener=ptrListener;
    }

    /**
     * 用于下拉刷新和加载更多
     * @param ptrLoadMoreListener
     */
    public void setPtrLoadMoreListener(PtrLoadMoreListener ptrLoadMoreListener){
        this.ptrLoadMoreListener=ptrLoadMoreListener;
    }

    /**
     * 滚动监听器
     * @param onScrollListener
     */
    public void addOnScrollListener(RecyclerView.OnScrollListener onScrollListener){
        this.mRecyclerView.addOnScrollListener(onScrollListener);
    }

    public PtrRecyclerView(Context context) {
        super(context);
        initViews();
    }

    public PtrRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews(){
        View view= LayoutInflater.from(getContext()).inflate(R.layout.ptr_recycler_view, null);  //not 'this'
        mRecyclerView = (RecyclerView) view.findViewById(R.id.ptr_recycler_view_base);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mPtrFrame= (PtrFrameLayout) view.findViewById(R.id.ptr_frame_recycler_view);
        mPtrFrame.setLoadingMinTime(1000);
        mPtrFrame.setDurationToCloseHeader(500);
        mPtrFrame.setDurationToCloseFooter(500);

        ptrHeader=new PtrClassicHeader(getContext());
        mPtrFrame.addPtrUIHandler(ptrHeader);
        mPtrFrame.setHeaderView(ptrHeader);

        ptrFooter=new PtrClassicFooter(getContext());
        mPtrFrame.addPtrUIHandler(ptrFooter);
        mPtrFrame.setFooterView(ptrFooter);

        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return super.checkCanDoLoadMore(frame, mRecyclerView, footer);
            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                if(ptrLoadMoreListener!=null) {
                    ptrLoadMoreListener.onLoadMore();
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, mRecyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if(ptrListener!=null){
                    ptrListener.onRefresh();
                    return;
                }
                if(ptrLoadMoreListener!=null) {
                    ptrLoadMoreListener.onRefresh();
                }
            }
        });

        this.addView(view);
    }

    public void refreshComplete(){
        mPtrFrame.refreshComplete();
    }

    public void autoRefresh(){
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh(true);
            }
        }, 100);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        if(adapter!=null){
            mRecyclerView.setAdapter(adapter);
        }
    }

    /**
     * 选择使用下拉刷新和加载更多
     * @param mode
     */
    public void setMode(PtrFrameLayout.Mode mode){
        mPtrFrame.setMode(mode);
    }
}
