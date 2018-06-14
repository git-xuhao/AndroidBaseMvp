package com.hazz.example.ui.main.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hazz.baselibs.base.BaseFragment;
import com.hazz.baselibs.rx.RxBus;
import com.hazz.example.R;
import com.hazz.example.event.TestEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

/**
 * @author xuhao
 * @date 2018/6/12 22:57
 * @desc 我的
 */
public class VideoFragment extends BaseFragment<VideoPresenter> {

    @BindView(R.id.textView)
    TextView textView;

    private String mTitle;

    public static VideoFragment getInstance(String title) {
        VideoFragment fragment = new VideoFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        fragment.mTitle = title;
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_second;
    }

    @Override
    protected VideoPresenter createPresenter() {
        return new VideoPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mPresenter.addDispose(RxBus.getDefault().toObservable(TestEvent.class)
                .subscribe(new Consumer<TestEvent>() {
                    @Override
                    public void accept(TestEvent testEvent) throws Exception {
                        if(testEvent!=null){
                            textView.setText(testEvent.getId()+":"+testEvent.getName());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }

    @Override
    public void showError(String msg) {

    }


}
