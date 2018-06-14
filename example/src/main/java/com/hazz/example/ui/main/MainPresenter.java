package com.hazz.example.ui.main;

import com.hazz.baselibs.mvp.BasePresenter;
import com.hazz.baselibs.net.BaseHttpResult;
import com.hazz.baselibs.net.BaseObserver;
import com.hazz.baselibs.rx.RxSchedulers;
import com.hazz.example.data.entity.TestNews;

import java.util.List;


/**
 * @author xuhao
 * @date 2018/6/12 16:18
 * @desc
 */
public class MainPresenter extends BasePresenter<MainContract.Model,MainContract.View>  {

    @Override
    protected MainContract.Model createModel() {
        return new MainModel();
    }

    public void requestData() {

    }



}
