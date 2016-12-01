package cn.cfanr.mvparchi.utils;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

import java.lang.reflect.Method;

/**
 * author xifan
 * time 2016/8/30
 * desc 屏幕工具类
 */
public class ScreenUtil {
    /**
     * 获取屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        return display.widthPixels;
    }

    /**
     * 获取屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        return display.heightPixels;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * @param context DisplayMetrics类中属性density）
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * @param context （DisplayMetrics类中属性density）
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param context （DisplayMetrics类中属性scaledDensity）
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param context DisplayMetrics类中属性scaledDensity）
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static boolean isSmallScreenPhone(Context context) {
        if (getScreenWidth(context) < 600) {
            return true;
        }
        return false;
    }

	public static int getAppBarHeight(Context context) {
		return dip2px(context, 56)+getStatusBarHeight(context);
	}

	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

    /**
     * 判断该手机是否有SmartBar(魅族手机)
     * @return
     */
    public  static boolean hasSmartBar(){
        try {
            // 新型号可用反射调用Build.hasSmartBar()
            Method method = Class.forName("android.os.Build").getMethod("hasSmartBar");
            return ((Boolean) method.invoke(null)).booleanValue();
        } catch (Exception e) {
        }
        // 反射不到Build.hasSmartBar()，则用Build.DEVICE判断
        if (Build.DEVICE.equals("mx2")) {
            return true;
        } else if (Build.DEVICE.equals("mx") || Build.DEVICE.equals("m9")) {
            return false;
        }
        return false;
    }
}
