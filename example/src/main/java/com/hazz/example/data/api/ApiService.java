package com.hazz.example.data.api;

import com.hazz.example.data.entity.TestNews;
import com.hazz.baselibs.net.BaseHttpResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author xuhao
 * @date 2018/6/11 23:04
 * @desc
 */
public interface ApiService {

    @GET("api/data/Android/10/1")
    Observable<BaseHttpResult<List<TestNews>>> getGankData();

}
