package com.hazz.baselibs.net.token;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Token失效的处理方案二，如果服务端没有遵循设计规范，可以尝试使用如下方法
 * 使用方法：addInterceptor(new TokenInterceptor());
 */
public class TokenInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // try the request
        Response originalResponse = chain.proceed(request);

        /**
         * 通过如下方法提前获取到请求完成的数据
         */
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }

        String bodyString = buffer.clone().readString(charset);

        Log.e("body---------->", bodyString);

//        if ("判断是否过期"){// 根据和服务端的约定判断Token是否过期
//
//            // 通过一个特定的接口获取新的Token，此处要用到同步的Retrofit请求
//
//            // 获取到新的Token
//            String token = "call.execute().body()";
//
//            // create a new request and modify it accordingly using the new token
//            Request newRequest = request.newBuilder().header("token", token).build();
//
//            originalResponse.body().close();
//            // retry the request
//            return chain.proceed(newRequest);
//        }

        // otherwise just pass the original response on
        return originalResponse;
    }
}