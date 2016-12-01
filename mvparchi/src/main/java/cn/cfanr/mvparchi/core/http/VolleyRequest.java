package cn.cfanr.mvparchi.core.http;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xifan
 *         date: 2016/8/30
 *         desc:
 */
public class VolleyRequest {
    public static final String TAG = VolleyRequest.class.getSimpleName();
    private static RequestQueue mRequestQueue;
    private static final int TIME_OUT = 25 * 1000;         //请求超时时间
    private static final int DEFAULT_MAX_RETRIES = 0; //最大请求重试次数

    private static RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            VolleyLog.DEBUG = false;
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    private static <T> void addToRequestQueue(Context context, Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue(context).add(req);
    }

    /**
     * 退出Activity或Fragment时，销毁所有请求引用，避免内存泄漏
     */
    public static void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag == null ? TAG : tag);
        }
    }

    public static void StringRequestGet(final Context context, String url, final ResponseListener responseListener) {
        responseListener.onStart();
        url = processRequestGetUrl(url);
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseListener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseListener.onError(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return createRequestHeader();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    if(TextUtils.isEmpty(response.headers.get("Set-Cookie"))){
                        return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                    getCookieSession(context, response);
                    return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (Exception e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
        getRequest.setShouldCache(true);
        getRequest.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        addToRequestQueue(context, getRequest, context.getClass().getSimpleName());
    }

    public static void StringRequestPost(final Context context, final Map<String, String> params, String url, final ResponseListener responoseListener){
        responoseListener.onStart();
        processRequestPostUrl(params);
        StringRequest postRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                responoseListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responoseListener.onError(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return createRequestHeader();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    if(TextUtils.isEmpty(response.headers.get("Set-Cookie"))){
                        return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                    getCookieSession(context, response);
                    return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (Exception e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,
                DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        addToRequestQueue(context, postRequest, context.getClass().getSimpleName());
    }

    /**
     * 统一处理get请求的url，如在url后增加用户身份验证的信息，userKey、终端来源（Android or iOS）、
     * 设备id、渠道来源、网络类型等
     * @param url
     * @return
     */
    private static String processRequestGetUrl(String url) {
        // TODO:
        return url;
    }

    /**
     * 统一处理post请求的url，如在url后增加用户身份验证的信息，userKey、终端来源（Android or iOS）、
     * 设备id、渠道来源、网络类型等
     * @param params
     * @return
     */
    private static void processRequestPostUrl(Map<String, String> params) {
        //TODO
    }

    /**
     *
     * @return
     */
    private static Map<String,String> createRequestHeader() {
        Map<String, String> headerMap=new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        headerMap.put("apiKey", "");  //TODO
        return headerMap;
    }

    /**
     * 获取Header cookie 保存本地
     * @param context
     * @param response
     */
    private static void getCookieSession(Context context, NetworkResponse response){
        if(response == null){
            return;
        }
        try{
            String headerStr = response.headers.toString();
            String cookie = "";
            //使用正则表达式从response的头中提取cookie内容的子串
            Pattern pattern=Pattern.compile("Set-Cookie.*?;");
            Matcher m=pattern.matcher(headerStr);
            if(m.find()){
                cookie =m.group();
            }
            //去掉cookie末尾的分号
            if(TextUtils.isEmpty(cookie)){
                return;
            }
            cookie = cookie.substring(11,cookie.length()-1);
            //TODO
        }catch (Exception e){
        }
    }

}
