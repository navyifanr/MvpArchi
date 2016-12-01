package cn.xifan.demo.ui.view;

import cn.cfanr.mvparchi.core.mvp.MvpView;
import cn.xifan.demo.model.Launch;

/**
 * @author xifan
 *         date: 2016/8/8
 *         desc:
 */
public interface LaunchView extends MvpView {
    void showLaunchPage(Launch launch);
}
