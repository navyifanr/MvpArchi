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
 * @time 2016/6/2
 * @desc 自定义的下拉刷新的头部
 */
public class PtrCustomHeader extends FrameLayout implements PtrUIHandler {
    private int mRotateAniTime = 150;
    private RotateAnimation mFlipAnimation;
    private RotateAnimation mReverseFlipAnimation;
    private ImageView mRotateView;
    private ImageView mProgressView;
    private TextView mStateTxt;
    private ImageView mPtrImg;

    public PtrCustomHeader(Context context) {
        super(context);
        initViews();
    }

    public PtrCustomHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrCustomHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews(){
        buildAnimation();
        View header = LayoutInflater.from(getContext()).inflate(R.layout.ptr_custom_header, this);

        mRotateView= (ImageView) header.findViewById(R.id.ptr_header_refresh_arrows);
        mProgressView= (ImageView) header.findViewById(R.id.ptr_header_progress_anim);
        mStateTxt= (TextView) header.findViewById(R.id.ptr_header_state_text);

//        //设置下拉时的头部图片
//        mPtrImg= (ImageView) header.findViewById(R.id.ptr_header_ptr_icon);
//        String ptrIconUrl = Config.getInstance(getContext()).getCacheFile().getString("ptr_loading_icon");
//        String fileName = FileUtil.urlToName(ptrIconUrl);
//        String filepath = StorageUtil.getFilePath(getContext(), fileName);
//        if(!TextUtils.isEmpty(ptrIconUrl)){
//            File file = new File(filepath);
//            if(file.exists()){
//                mPtrImg.setImageBitmap(getLocalBitmap(filepath));
//            } else {
//                mPtrImg.setVisibility(GONE);
//            }
//        } else {
//            mPtrImg.setVisibility(GONE);
//        }
//
//        mProgressView.setImageResource(R.drawable.ptr_header_loading);
//        ((AnimationDrawable) mProgressView.getDrawable()).start();

        resetView();
    }

    private void buildAnimation() {
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
        mProgressView.setVisibility(INVISIBLE);
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
        mProgressView.setVisibility(INVISIBLE);

        mRotateView.setVisibility(VISIBLE);
        mStateTxt.setVisibility(VISIBLE);
        if (frame.isPullToRefresh()) {
            mStateTxt.setText(getResources().getString(R.string.ptr_classic_pull_down_to_refresh));
        } else {
            mStateTxt.setText(getResources().getString(R.string.ptr_classic_pull_down));
        }
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        hideRotateView();
        mProgressView.setVisibility(VISIBLE);
        mStateTxt.setVisibility(INVISIBLE);
        mStateTxt.setText(R.string.ptr_classic_refreshing);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        hideRotateView();
        mProgressView.setVisibility(INVISIBLE);

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
        if (frame.isPullToRefresh()) {
            mStateTxt.setText(getResources().getString(R.string.ptr_classic_pull_down_to_refresh));
        } else {
            mStateTxt.setText(getResources().getString(R.string.ptr_classic_pull_down));
        }
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
