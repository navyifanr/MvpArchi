package cn.cfanr.mvparchi.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;


/**
 * @author xifan
 * @time 2016/8/30
 * @desc
 */
public class ToastUtil {
    private static Toast mToast;

    public static void show(Context context, CharSequence text) {
        if(TextUtils.isEmpty(text)){
            return;
        }
        if(mToast!=null){
            mToast.cancel();
        }
        if(text.length()>10) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        }else{
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void show(Context context, @StringRes int resId) {
        show(context, context.getResources().getString(resId));
    }

    /**
     * 完全自定义布局Toast
     * @param context
     * @param view
     */
    public ToastUtil indefinite(Context context, View view, CharSequence message, int duration){
        if(mToast!=null){
            mToast.cancel();
        }
        mToast = new Toast(context);
        mToast.setView(view);
        mToast.setDuration(duration);
        mToast.setText(message);
        return this;
    }

    public ToastUtil show(){
        mToast.show();
        return this;
    }
}
