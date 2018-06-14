package com.hazz.baselibs.mvp;

import android.app.Activity;

import com.hazz.baselibs.utils.Preconditions;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.HashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author xuhao
 * @date 2018/6/9 17:14
 * @desc
 */
public abstract class BasePresenter<M extends IModel,V extends IView> implements IPresenter<V> {

    private  V mView;
    private  M mModel;

    private CompositeDisposable compositeDisposable;

    public V getView() {
        Preconditions.checkNotNull(mView, "%s cannot be null", IView.class.getName());
        return mView;
    }

    public BasePresenter() {
        this.mModel =createModel();
    }

    public M getModel() {
        Preconditions.checkNotNull(mModel, "%s cannot be null", IModel.class.getName());
        return mModel;
    }

    /**
     * 获取 Model
     * @return
     */
    protected abstract M createModel();

    /**
     * 绑定 View
     * @param mView
     */
    @Override
    public void attachView(V mView) {
       this.mView = mView;
    }

    /**
     * 解除绑定
     */
    @Override
    public void detachView() {
        unDispose();
        mView = null;
    }

    /**
     * 检查View是否存在
     */
    protected boolean isViewAttached() {
        return mView != null;
    }

    /**
     * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
     * 可在 {@link Activity#onDestroy()} 中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务，避免内存泄漏(框架已自行处理)
     *
     * @param disposable
     */
    public void addDispose(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);// 将所有 Disposable 放入集中处理
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    public void unDispose() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();// 保证 Activity 结束时取消所有正在执行的订阅
        }
    }


    protected <T> LifecycleProvider<T> getLifecycleProvider() {
        LifecycleProvider<T> provider = null;
        if (null != mView && mView instanceof LifecycleProvider) {
            provider = (LifecycleProvider<T>) mView;
        }
        return provider;
    }





}
