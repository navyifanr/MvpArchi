package cn.cfanr.mvparchi.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cn.cfanr.mvparchi.R;


/**
 * 网络请求时的加载对话框
 */
public class LoadingProgressDialog extends Dialog {
    private TextView tvState;

    public LoadingProgressDialog(Context context) {
        super(context);
        initProgress(context);
    }

    public LoadingProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        initProgress(context);
    }

    private void initProgress(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_loading_progress, null);
        tvState = (TextView) view.findViewById(R.id.tv_loading_progress);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(view);
    }

    /**
     * 显示加载框
     */
    public void showProgress(CharSequence tipText) {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        if (tvState != null) {
            tvState.setText(tipText);
        }
        show();
    }

    /**
     * 显示加载框
     * @param cancelAble    是否可以返回取消
     * @param touchOutside 是否可以点击其他区域取消
     */
    public void showProgress(String tipText, boolean cancelAble, boolean touchOutside) {
        setCancelable(cancelAble);
        setCanceledOnTouchOutside(touchOutside);
        if (tvState != null) {
            tvState.setText(tipText);
        }
        show();
    }

    /**
     * 显示加载框
     */
    public void showProgress() {
        setCancelable(false);
        if (tvState != null) {
            tvState.setText(R.string.loading_progress);
        }
        show();
    }


    /**
     * 取消加载框，只是隐藏，未销毁
     */
    public void hideProgress() {
        if (isShowing()) {
            hide();
        }
    }

    /**
     * 取消加载框
     */
    public void dismissProgress() {
        if (isShowing()) {
            dismiss();
        }
    }
}
