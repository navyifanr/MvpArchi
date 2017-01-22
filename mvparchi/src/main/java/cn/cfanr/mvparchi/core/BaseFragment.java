package cn.cfanr.mvparchi.core;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.cfanr.mvparchi.R;
import cn.cfanr.mvparchi.ui.dialog.LoadingProgressDialog;

/**
 * @author xifan
 */
public abstract class BaseFragment extends Fragment {
    public Context mContext;
    private View layoutView;
    protected LoadingProgressDialog mProgressDialog;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutView = inflater.inflate(getLayoutResId(), container, false);
        initPresenter();
        initExtraBase();
        initViews(layoutView);
        return layoutView;
    }

    public abstract int getLayoutResId();

    protected abstract void initPresenter();

    private void initExtraBase() {
        mProgressDialog = new LoadingProgressDialog(getActivity(), R.style.LoadingProgressDialogStyle);
    }

    public abstract void initViews(View layoutView);

    @SuppressWarnings("unchecked")
    public <T extends View>T $(View layoutView, @IdRes int resId){
        return (T) layoutView.findViewById(resId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.cancel();
        }
    }
}
