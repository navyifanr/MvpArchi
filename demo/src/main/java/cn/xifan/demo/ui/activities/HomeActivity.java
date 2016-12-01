package cn.xifan.demo.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import cn.cfanr.mvparchi.core.BaseActivity;
import cn.xifan.demo.R;
import cn.xifan.demo.ui.fragment.HomeFragment;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initPresenter() {
        
    }

    @Override
    protected void initView() {
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frg_content, HomeFragment.newInstance()).commit();
    }

    @Override
    protected void initEvent() {
//        this.setTitle("首页");
    }

}
