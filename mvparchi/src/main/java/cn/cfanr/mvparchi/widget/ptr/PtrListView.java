package cn.cfanr.mvparchi.widget.ptr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import cn.cfanr.mvparchi.R;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author xifan
 * @time 2016/6/2
 * @desc 下拉刷新和加载更多的ListView控件，默认具有下拉刷新和加载更多功能
 */
public class PtrListView extends FrameLayout {
    private PtrFrameLayout mPtrFrame;
    private ListView mListView;
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
     * 滚动事件监听
     * @param listener
     */
    public void setOnScrollListener(AbsListView.OnScrollListener listener) {
        mListView.setOnScrollListener(listener);
    }

    public PtrListView(Context context) {
        super(context);
        initViews();
    }

    public PtrListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews(){
        View view= LayoutInflater.from(getContext()).inflate(R.layout.ptr_list_view, null);  //not 'this'
        mListView= (ListView) view.findViewById(R.id.ptr_list_view_base);

        mPtrFrame= (PtrFrameLayout) view.findViewById(R.id.ptr_frame_list_view);
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
                return super.checkCanDoLoadMore(frame, mListView, footer);
            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                if(ptrLoadMoreListener!=null) {
                    ptrLoadMoreListener.onLoadMore();
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, mListView, header);
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

    public void setAdapter(BaseAdapter adapter){
        if(adapter!=null){
            mListView.setAdapter(adapter);
        }
    }

    /**
     * 选择使用下拉刷新和加载更多
     * @param mode
     */
    public void setMode(PtrFrameLayout.Mode mode){
        mPtrFrame.setMode(mode);
    }

    public void addListHeaderView(View headerView){
        if(headerView != null){
            mListView.addHeaderView(headerView);
        }
    }

    /**
     * 添加底部View，注意在setAdapter之前调用，避免部分手机addFooterView不成功
     * @param footerView
     */
    public void addListFooterView(View footerView){
        if(footerView != null){
            mListView.addFooterView(footerView);
        }
    }

    public void setSelection(int index){
        if(mListView != null){
            mListView.setSelection(index);
        }
    }

}
