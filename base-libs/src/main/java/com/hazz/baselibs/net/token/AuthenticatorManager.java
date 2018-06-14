package com.hazz.baselibs.net.token;



import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Token失效的处理方案一，当HTTP的状态码为401回调接口Authenticator.authenticate()方法，如果服务端遵循设计规范，可以尝试使用如下方法
 * 使用方法：OkHttpClient.setAuthenticator(new AuthenticatorManager());
 */
public class AuthenticatorManager implements Authenticator {

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        // 通过一个特定的接口获取新的Token，此处要用到同步的Retrofit请求

        // 获取到新的Token
        String token = "";

        return response.request().newBuilder().header("token", token).build();
    }
}