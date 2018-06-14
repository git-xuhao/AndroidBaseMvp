package com.hazz.example.ui.main.home;

import com.hazz.baselibs.mvp.BaseModel;
import com.hazz.baselibs.net.BaseHttpResult;
import com.hazz.example.data.entity.TestNews;
import com.hazz.example.data.repository.RetrofitUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author xuhao
 * @date 2018/6/13 15:35
 * @desc
 */
public class HomeModel extends BaseModel implements HomeContract.Model {
    @Override
    public Observable<BaseHttpResult<List<TestNews>>> getGankData() {
        return RetrofitUtils.getHttpService().getGankData();
    }
}
