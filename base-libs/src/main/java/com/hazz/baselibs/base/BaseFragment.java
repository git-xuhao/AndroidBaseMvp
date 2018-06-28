package com.hazz.baselibs.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hazz.baselibs.app.BaseApplication;
import com.hazz.baselibs.mvp.BasePresenter;
import com.hazz.baselibs.mvp.IView;
import com.squareup.leakcanary.RefWatcher;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author xuhao
 * @date 2018/6/9 17:12
 * @desc fragment 基类
 */
public abstract class BaseFragment<T extends BasePresenter> extends RxFragment implements IView {

    /**
     * 将代理类通用行为抽出来
     */
    protected T mPresenter;

    private Unbinder unBinder;

    private BaseActivity mActivity;
    /**
     * 缓存Fragment view
     */
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView==null){
            mRootView=inflater.inflate(getLayoutId(),null);
            unBinder = ButterKnife.bind(this, mRootView);
            if (useEventBus()) {
                EventBus.getDefault().register(this);//注册eventBus
            }
        }
        ViewGroup parent= (ViewGroup) mRootView.getParent();
        if(parent!=null){
            parent.removeView(mRootView);
        }
        return mRootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter =createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initView();
        initListener();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.mActivity = (BaseActivity) this.getActivity();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (unBinder != null) {
            unBinder.unbind();
        }
        if (useEventBus()) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);//注销eventBus
            }
        }
        initLeakCanary();
    }


    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    @Override
    public void showLoading() {
        mActivity.showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        mActivity.hideLoadingDialog();
    }


    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getBaseActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getBaseActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 用来检测所有Fragment的内存泄漏
     */
    private void initLeakCanary() {
        RefWatcher refWatcher = BaseApplication.getRefWatcher(getBaseActivity());
        refWatcher.watch(this);
    }


    /**
     * 返回一个用于显示界面的布局id
     */
    protected abstract @LayoutRes int getLayoutId();

    protected abstract T createPresenter();
    /**
     * 初始化View的代码写在这个方法中
     */
    protected abstract void initView();

    /**
     * 初始化监听器的代码写在这个方法中
     */
    protected abstract void initListener();

    /**
     * 初始数据的代码写在这个方法中，用于从服务器获取数据
     */
    protected abstract void initData();

    protected abstract boolean useEventBus();
    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

}
