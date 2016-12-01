package cn.cfanr.mvparchi.core;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.cfanr.mvparchi.R;
import cn.cfanr.mvparchi.ui.dialog.LoadingProgressDialog;

/**
 * @author xifan
 * @time 2016/5/4
 * @desc
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected LoadingProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(getLayoutResId());
        initPresenter();
        initExtraBase();
        initView();
        initEvent();
    }

    protected void setBaseContentView(@LayoutRes int layoutResId){
        setContentView(layoutResId);
    }

    protected abstract int getLayoutResId();

    protected abstract void initPresenter();

    private void initExtraBase(){
        mProgressDialog = new LoadingProgressDialog(getActivity(), R.style.LoadingProgressDialogStyle);
    }

    protected abstract void initView();

    protected abstract void initEvent();

    protected <T extends View>T $(@IdRes int resId){
        return (T) super.findViewById(resId);
    }

    protected Activity getActivity(){
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.cancel();
        }
    }
}