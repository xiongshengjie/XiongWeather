package cn.xiong.xiongweather.util;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/8/3.
 */

public class HttpUtil {

    private final static OkHttpClient client = new OkHttpClient();

    public static void HttpGet(String url, Callback callback){
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
