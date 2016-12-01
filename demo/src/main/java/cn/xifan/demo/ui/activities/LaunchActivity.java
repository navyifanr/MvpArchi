package cn.xifan.demo.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import cn.cfanr.mvparchi.core.BaseActivity;
import cn.xifan.demo.R;
import cn.xifan.demo.app.AppController;
import cn.xifan.demo.model.Launch;
import cn.xifan.demo.presenter.LaunchPresenter;
import cn.xifan.demo.ui.view.LaunchView;

/**
 * @author xifan
 * @time 2016/5/4
 * @desc 启动页
 */
public class LaunchActivity extends BaseActivity implements LaunchView {
    private LaunchPresenter launchPresenter;

    private NetworkImageView mImageView;
    private TextView tvAuthor;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initPresenter() {
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        launchPresenter=new LaunchPresenter(this);
        launchPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        mImageView=$(R.id.net_iv_launch_bg);
        tvAuthor = $(R.id.tv_author);
    }

    @Override
    protected void initEvent() {
        imageLoader = AppController.getInstance().getImageLoader();
        mImageView.setErrorImageResId(R.mipmap.bg_launch);
        mImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getActivity(), HomeActivity.class));
                finish();
            }
        }, 3000);
        launchPresenter.loadLaunchData();
    }

    @Override
    public void showLaunchPage(Launch launch) {
        mImageView.setImageUrl(launch.getImg(), imageLoader);
        String author=launch.getText();
        if(!TextUtils.isEmpty(author)) {
            tvAuthor.setText("By " + author);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        launchPresenter.detachView();
    }
}
