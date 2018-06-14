package com.hazz.baselibs.mvp;

/**
 * @author xuhao
 * @date 2018/6/9 17:12
 * @desc
 */
public interface IView {


    //显示loading
    void showLoading();

    //隐藏loading
    void hideLoading();

    //显示吐司
    void showError(String msg);
}
