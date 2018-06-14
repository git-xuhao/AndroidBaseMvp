package com.hazz.example.data.repository;

import com.hazz.baselibs.net.BaseRetrofit;
import com.hazz.example.data.api.ApiService;

/**
 * @author xuhao
 * @date 2018/6/11 23:02
 * @desc 网络请求管理类
 */
public class RetrofitUtils extends BaseRetrofit {
    private static ApiService httpService;

    /**
     * @return retrofit的底层利用反射的方式, 获取所有的api接口的类
     */
    public static ApiService getHttpService() {
        if (httpService == null) {
            httpService = getRetrofit().create(ApiService.class);
        }
        return httpService;
    }


}
