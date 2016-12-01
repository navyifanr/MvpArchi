package cn.cfanr.mvparchi.widget.ptr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.RotateAnimation;

import cn.cfanr.mvparchi.R;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author xifan
 * @time 2016/6/27
 * @desc 加载更多的底部样式，和下拉刷新类似
 */
public class PtrClassicFooter extends PtrClassicHeader {
    public PtrClassicFooter(Context context) {
        super(context);
    }

    public PtrClassicFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PtrClassicFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setPtrIcon(){
        return;  //不要刷新的icon
    }

    @Override
    protected void buildAnimation() {
        super.buildAnimation();
        RotateAnimation tmp = mFlipAnimation;
        mFlipAnimation = mReverseFlipAnimation;
        mReverseFlipAnimation = tmp;
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        super.onUIRefreshPrepare(frame);
        mStateTxt.setText(getResources().getString(R.string.ptr_load_more_load));
    }
}
