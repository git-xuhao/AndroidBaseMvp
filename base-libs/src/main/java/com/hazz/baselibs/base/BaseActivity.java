package com.hazz.baselibs.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author xuhao
 * @date 2018/6/9 17:12
 * @desc 基类 BaseMvpActivity
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    private Unbinder unBinder;

    private ProgressDialog loadingDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        Intent intent = getIntent();
        if (intent != null)
            getIntent(intent);
        unBinder = ButterKnife.bind(this);
        if (useEventBus()) {
            EventBus.getDefault().register(this);//注册eventBus
        }
        initView();
        initData();
        initListener();
    }



    /**
     * 是否使用eventBus
     *
     * @return
     */
    protected boolean useEventBus() {
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unBinder != null) {
            unBinder.unbind();
        }
        if (useEventBus()) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);//注销eventBus
            }
        }

    }

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

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
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
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 显示带消息的进度框
     *
     * @param title 提示
     */
    protected void showLoadingDialog(String title) {
        createLoadingDialog();
        loadingDialog.setMessage(title);
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    /**
     * 显示进度框
     */
    protected void showLoadingDialog() {
        createLoadingDialog();
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    /**
     * 创建LoadingDialog
     */
    private void createLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setCancelable(true);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
    }

    /**
     * 隐藏进度框
     */
    protected void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }




    /**
     * 获取布局 Id
     * @return
     */
    protected abstract @LayoutRes int getLayoutId();
    /** 获取 Intent 数据 **/
    protected abstract void getIntent(Intent intent);

    /** 初始化View的代码写在这个方法中 */
    protected abstract void initView();

    /** 初始化监听器的代码写在这个方法中 */
    protected abstract void initListener();

    /** 初始数据的代码写在这个方法中，用于从服务器获取数据 */
    protected abstract void initData();



}
