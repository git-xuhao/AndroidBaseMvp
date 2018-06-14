package com.hazz.baselibs.net.interceptor;

import android.text.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求头拦截器
 */
public class HeaderInterceptor implements Interceptor {
    private HashMap<String, Object> headers;

    public HeaderInterceptor(HashMap<String, Object> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        //如果公共请求头不为空,则构建新的请求
        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        Request request = requestBuilder.build();
        Response.Builder responseBuilder = chain.proceed(request).newBuilder();
        if (!TextUtils.isEmpty(request.cacheControl().toString())) {
            responseBuilder
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + request.cacheControl().maxAgeSeconds());
        }
        return responseBuilder.build();
    }
}