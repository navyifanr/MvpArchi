package cn.xifan.demo.ui.view;

import java.util.List;

import cn.cfanr.mvparchi.core.mvp.MvpView;
import cn.xifan.demo.model.News;

/**
 * @author xifan
 *         date: 2016/8/29
 *         desc:
 */
public interface HomeView extends MvpView {

    void setNewsList(String title, List<News>newsList);

    /**
     * 根据应用需求取舍这个网络加载的状态，若需要，则每个（间接）实现了接口MvpView的Activity/Fragment，
     * 都要实现这两个方法，但有些Activity/Fragment并不需要网络加载状态，或者另外写一个有网络加载状态的MvpView接口
     */
    void showProgress();
    void hideProgress();
}
