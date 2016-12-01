package cn.cfanr.mvparchi.widget.ptr;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author xifan
 * @time 2016/6/22
 * @desc 下拉刷新组件，适用外层是ScrollView，FrameLayout等
 */
public class PtrClassicLayout extends PtrFrameLayout {
    private PtrClassicHeader mPtrClassicHeader;
    private PtrListener ptrListener;
    /**
     * 下拉刷新
     * @param ptrListener
     */
    public void setPtrListener( PtrListener ptrListener){
        this.ptrListener=ptrListener;
    }

    public PtrClassicLayout(Context context) {
        super(context);
        initViews();
    }

    public PtrClassicLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrClassicLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        this.setLoadingMinTime(1000);
        this.setDurationToCloseHeader(500);

        mPtrClassicHeader = new PtrClassicHeader(getContext());
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);

        this.setPtrHandler(new PtrDefaultHandler() {  //直接使用PtrDefaultHandler，可以不重写checkCanDoRefresh方法
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if(ptrListener!=null){
                    ptrListener.onRefresh();
                }
            }
        });
    }

    public PtrClassicHeader getHeader() {
        return mPtrClassicHeader;
    }

}