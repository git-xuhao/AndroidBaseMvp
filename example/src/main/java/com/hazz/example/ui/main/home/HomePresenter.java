package com.hazz.example.ui.main.home;


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
public class HomePresenter extends BasePresenter<HomeContract.Model,HomeContract.View> {
    @Override
    protected HomeContract.Model createModel() {
        return new HomeModel();
    }


    public void requestData(){
        getModel().getGankData()
                .compose(RxSchedulers.applySchedulers(getLifecycleProvider()))
                .subscribe(new BaseObserver<List<TestNews>>(getView()) {
                    @Override
                    public void onSuccess(BaseHttpResult<List<TestNews>> result) {
                        if (result != null) {
                            getView().showData(result.getData());
                        }
                    }

                    @Override
                    public void onFailure(String errMsg, boolean isNetError) {
                        getView().showError(errMsg);
                    }
                });
    }
}
