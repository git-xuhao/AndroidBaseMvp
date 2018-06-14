package com.hazz.example.ui.main.video;


import com.hazz.baselibs.mvp.BasePresenter;
import com.hazz.baselibs.net.BaseHttpResult;
import com.hazz.baselibs.net.BaseObserver;
import com.hazz.baselibs.rx.RxSchedulers;
import com.hazz.example.data.entity.TestNews;

import java.util.List;

/**
 * @author xuhao
 * @date 2018/6/12 22:57
 * @desc
 */
public class VideoPresenter extends BasePresenter<VideoContract.Model,VideoContract.View> {
    @Override
    protected VideoContract.Model createModel() {
        return new VideoModel();
    }


    public void requestData(){

    }
}
