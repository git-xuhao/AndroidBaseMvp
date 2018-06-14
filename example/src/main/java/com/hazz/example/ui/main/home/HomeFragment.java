package com.hazz.example.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hazz.baselibs.base.BaseFragment;
import com.hazz.baselibs.rx.RxBus;
import com.hazz.baselibs.utils.ToastUtils;
import com.hazz.example.R;
import com.hazz.example.data.entity.TestNews;
import com.hazz.example.event.TestEvent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * @author xuhao
 * @date 2018/6/12 22:57
 * @desc 首页
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;


    private String mTitle;

    public static HomeFragment getInstance(String title) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        fragment.mTitle = title;
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        button.setOnClickListener(v -> mPresenter.requestData());
        button2.setOnClickListener(v -> {
            RxBus.getDefault().post(new TestEvent(1,"RxBus post test data from homeFragment"));});
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean useEventBus() {
        return false;
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showShort(msg);
    }


    @Override
    public void showData(List<TestNews> testNews) {
        ToastUtils.showShort(testNews.get(0).toString());
        textView.setText(testNews.get(0).toString());
    }

}
