package cn.xifan.demo.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.VolleyError;

import java.util.List;
import java.util.Map;

import cn.cfanr.mvparchi.core.http.ResponseListener;
import cn.cfanr.mvparchi.core.http.VolleyRequest;
import cn.cfanr.mvparchi.core.mvp.BasePresenter;
import cn.cfanr.mvparchi.utils.JsonTool;
import cn.xifan.demo.app.Api;
import cn.xifan.demo.model.News;
import cn.xifan.demo.ui.view.HomeView;
import cn.xifan.demo.utils.DateUtils;

/**
 * @author xifan
 *         date: 2016/8/29
 *         desc:
 */
public class HomePresenter extends BasePresenter<HomeView> {
    private HomeView mView;
    private Context mContext;

    public HomePresenter(@NonNull HomeView homeView){
        this.mView = homeView;
        this.mContext = homeView.getContext();
    }

    public void loadHomeData(final int dayNum){
        String url;
        if(dayNum==0){
            url= Api.url_latest_news;
        }else{
            url=String.format(Api.url_before_news, DateUtils.getNDaysAgo(dayNum));
        }
        VolleyRequest.StringRequestGet(mContext, url, new ResponseListener() {
            @Override
            public void onStart() {
                mView.showProgress();
            }

            @Override
            public void onSuccess(Object response) {
                Map<String, Object> resultMap= JsonTool.parseJson2Map(response.toString());
                if(resultMap!=null) {
                    String date = JsonTool.mapObjVal2Str(resultMap, "date");
                    String title = DateUtils.convertDateTxt(date);
                    if (dayNum == 0) {
                        title = "首页";
                    }
                    String contentStr = JsonTool.mapObjVal2Str(resultMap, "stories");
                    List<News> list = JsonTool.jsonToObjList(contentStr, News[].class);
                    mView.setNewsList(title, list);
                }
                mView.hideProgress();
            }

            @Override
            public void onError(VolleyError error) {
                mView.hideProgress();
            }
        });
    }
}
