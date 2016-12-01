package cn.xifan.demo.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.VolleyError;

import cn.cfanr.mvparchi.core.http.ResponseListener;
import cn.cfanr.mvparchi.core.http.VolleyRequest;
import cn.cfanr.mvparchi.core.mvp.BasePresenter;
import cn.cfanr.mvparchi.utils.JsonTool;
import cn.cfanr.mvparchi.utils.ScreenUtil;
import cn.xifan.demo.app.Api;
import cn.xifan.demo.model.Launch;
import cn.xifan.demo.ui.view.LaunchView;

/**
 * author xifan
 *date: 2016/8/29
 *desc:
 */
public class LaunchPresenter extends BasePresenter<LaunchView> {
    private Context mContext;
    private LaunchView launchView;

    public LaunchPresenter(@NonNull LaunchView launchView){
        this.launchView = launchView;
        this.mContext = launchView.getContext();
    }

    public void loadLaunchData(){
        VolleyRequest.StringRequestGet(mContext, Api.url_launch + getLaunchImgSize(), new ResponseListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(Object response) {
                Launch launch= JsonTool.jsonToObj(response.toString(), Launch.class);
                launchView.showLaunchPage(launch);
            }

            @Override
            public void onError(VolleyError error) {
            }
        });
    }

    private String getLaunchImgSize(){
        String imgSize="1080*1776";
        int screenWidth= ScreenUtil.getScreenWidth(mContext);
        if(screenWidth<=320){
            imgSize="320*432";
        }else if(screenWidth<=480){
            imgSize="480*728";
        }else if(screenWidth<=720){
            imgSize="720*1184";
        }else if(screenWidth<=1080){
            imgSize="1080*1776";
        }
        return imgSize;
    }
}
