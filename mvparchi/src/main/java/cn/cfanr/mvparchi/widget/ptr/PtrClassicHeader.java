package cn.cfanr.mvparchi.widget.ptr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import cn.cfanr.mvparchi.R;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * @author xifan
 * @time 2016/6/22
 * @desc 下拉刷新经典头部，支持下发图片
 */
public class PtrClassicHeader extends FrameLayout implements PtrUIHandler {
    private int mRotateAniTime = 150;
    protected RotateAnimation mFlipAnimation;
    protected RotateAnimation mReverseFlipAnimation;
    protected TextView mStateTxt;
    private View mRotateView;
    private View mProgressBar;
    private ImageView mPtrImg;

    public PtrClassicHeader(Context context) {
        super(context);
        initViews();
    }

    public PtrClassicHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrClassicHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews(){
        buildAnimation();
        View header = LayoutInflater.from(getContext()).inflate(R.layout.ptr_classic_header, this);

        mStateTxt= (TextView) header.findViewById(R.id.ptr_classic_header_state_txt);
        mRotateView= header.findViewById(R.id.ptr_classic_header_rotate_img);
        mProgressBar=header.findViewById(R.id.ptr_classic_header_progressbar);
        mPtrImg= (ImageView) header.findViewById(R.id.ptr_classic_header_ptr_icon);

        setPtrIcon();

        resetView();
    }

    /**
     * 设置下拉时的头部图片
     */
    protected void setPtrIcon() {
//        String ptrIconUrl = Config.getInstance(getContext()).getCacheFile().getString("ptr_loading_icon");
//        String fileName = FileUtil.urlToName(ptrIconUrl);
//        final String filepath = StorageUtil.getFilePath(getContext(), fileName);
//        if(!TextUtils.isEmpty(ptrIconUrl)){
//            File file = new File(filepath);
//            if(file.exists()){
//                mPtrImg.setVisibility(VISIBLE);
//                mPtrImg.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        int height=60;             //DisplayUtil.getScreenHeight(getContext())/10;
//                        int width=360 * height / 130;
//                        ViewGroup.LayoutParams params=mPtrImg.getLayoutParams();
//                        params.width=DisplayUtil.dip2px(getContext(),width);
//                        params.height=DisplayUtil.dip2px(getContext(),height);
//                        mPtrImg.setLayoutParams(params);
//                    }
//                });
//                mPtrImg.setImageBitmap(getLocalBitmap(filepath));
//            } else {
//                mPtrImg.setVisibility(GONE);
//            }
//        } else {
//            mPtrImg.setVisibility(GONE);
//        }
    }

    protected void buildAnimation() {
        mFlipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(mRotateAniTime);
        mFlipAnimation.setFillAfter(true);

        mReverseFlipAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(mRotateAniTime);
        mReverseFlipAnimation.setFillAfter(true);
    }

    private void resetView() {
        hideRotateView();
        mProgressBar.setVisibility(INVISIBLE);
    }

    private void hideRotateView() {
        mRotateView.clearAnimation();
        mRotateView.setVisibility(INVISIBLE);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mProgressBar.setVisibility(INVISIBLE);

        mRotateView.setVisibility(VISIBLE);
        mStateTxt.setVisibility(VISIBLE);
        mStateTxt.setText(getResources().getString(R.string.ptr_classic_pull_down_to_refresh));
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        hideRotateView();
        mProgressBar.setVisibility(VISIBLE);
        mStateTxt.setVisibility(VISIBLE);
        mStateTxt.setText(R.string.ptr_classic_refreshing);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        hideRotateView();
        mProgressBar.setVisibility(INVISIBLE);

        mStateTxt.setVisibility(VISIBLE);
        mStateTxt.setText(getResources().getString(R.string.ptr_classic_refresh_complete));
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch(frame);
                if (mRotateView != null) {
                    mRotateView.clearAnimation();
                    mRotateView.startAnimation(mReverseFlipAnimation);
                }
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch(frame);
                if (mRotateView != null) {
                    mRotateView.clearAnimation();
                    mRotateView.startAnimation(mFlipAnimation);
                }
            }
        }
    }

    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
            mStateTxt.setVisibility(VISIBLE);
            mStateTxt.setText(R.string.ptr_classic_release_to_refresh);
        }
    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        mStateTxt.setVisibility(VISIBLE);
        mStateTxt.setText(getResources().getString(R.string.ptr_classic_pull_down_to_refresh));
//        if (frame.isPullToRefresh()) {
//            mStateTxt.setText(getResources().getString(R.string.ptr_classic_pull_down_to_refresh));
//        } else {
//            mStateTxt.setText(getResources().getString(R.string.ptr_classic_pull_down));
//        }
    }

    /**
     * 加载本地图片
     */
    public static Bitmap getLocalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
