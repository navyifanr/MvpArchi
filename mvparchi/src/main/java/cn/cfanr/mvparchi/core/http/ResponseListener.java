package cn.cfanr.mvparchi.core.http;

import com.android.volley.VolleyError;

/**
 * @author xifan
 *         date: 2016/8/30
 *         desc:
 */
public interface ResponseListener<T> {
    void onStart();
    void onSuccess(T response);
    void onError(VolleyError error);
}
