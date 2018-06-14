package com.hazz.baselibs.rx;

import android.app.Dialog;
import android.content.DialogInterface;

import com.hazz.baselibs.net.function.RetryWithDelay;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Rx线程相关的切换
 */
public class RxSchedulers {


    public static <T> ObservableTransformer<T, T> applySchedulers(final LifecycleProvider provider) {
        return new ObservableTransformer<T, T>() {
            @Override public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .retryWhen(new RetryWithDelay())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxSchedulers.<T>bindToLifecycle(provider));

            }
        };
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final LifecycleProvider provider, final ActivityEvent event) {
        return new ObservableTransformer<T, T>() {
            @Override public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .retryWhen(new RetryWithDelay())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxSchedulers.<T>bindToLifecycle(provider, event));

            }
        };
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final LifecycleProvider provider, final FragmentEvent event) {
        return new ObservableTransformer<T, T>() {
            @Override public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .retryWhen(new RetryWithDelay())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxSchedulers.<T>bindToLifecycle(provider, event));

            }
        };
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final LifecycleProvider provider, @NonNull final Dialog dialog) {
        return new ObservableTransformer<T, T>() {
            @Override public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .delay(1, TimeUnit.SECONDS)
                        .retryWhen(new RetryWithDelay())
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull final Disposable disposable) throws Exception {
                                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override public void onCancel(DialogInterface dialog) {
                                        disposable.dispose();
                                    }
                                });
                                dialog.show();
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnTerminate(new Action() {
                            @Override public void run() throws Exception {
                                dialog.dismiss();
                            }
                        })
                        .compose(RxSchedulers.<T>bindToLifecycle(provider));
            }
        };
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final LifecycleProvider provider, final ActivityEvent event, @NonNull final Dialog dialog) {
        return new ObservableTransformer<T, T>() {
            @Override public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .delay(1, TimeUnit.SECONDS)
                        .retryWhen(new RetryWithDelay())
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull final Disposable disposable) throws Exception {
                                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override public void onCancel(DialogInterface dialog) {
                                        disposable.dispose();
                                    }
                                });
                                dialog.show();
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnTerminate(new Action() {
                            @Override public void run() throws Exception {
                                dialog.dismiss();
                            }
                        })
                        .compose(RxSchedulers.<T>bindToLifecycle(provider, event));
            }
        };
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final LifecycleProvider provider, final FragmentEvent event, @NonNull final Dialog dialog) {
        return new ObservableTransformer<T, T>() {
            @Override public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .delay(1, TimeUnit.SECONDS)
                        .retryWhen(new RetryWithDelay(2,5000))
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull final Disposable disposable) throws Exception {
                                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override public void onCancel(DialogInterface dialog) {
                                        disposable.dispose();
                                    }
                                });
                                dialog.show();
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnTerminate(new Action() {
                            @Override public void run() throws Exception {
                                dialog.dismiss();
                            }
                        })
                        .compose(RxSchedulers.<T>bindToLifecycle(provider, event));
            }
        };
    }


    private static <T> LifecycleTransformer<T> bindToLifecycle(LifecycleProvider provider) {
        if (provider instanceof RxAppCompatActivity) {
            return ((RxAppCompatActivity) provider).bindToLifecycle();
        } else if (provider instanceof RxFragment) {
            return ((RxFragment) provider).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("class must extents RxAppCompatActivity or RxFragment");
        }
    }

    private static <T> LifecycleTransformer<T> bindToLifecycle(LifecycleProvider provider, ActivityEvent event) {
        if (provider instanceof RxAppCompatActivity) {
            return ((RxAppCompatActivity) provider).bindUntilEvent(event);
        } else {
            throw new IllegalArgumentException("class must extents RxAppCompatActivity");
        }
    }

    private static <T> LifecycleTransformer<T> bindToLifecycle(LifecycleProvider provider, FragmentEvent event) {
        if (provider instanceof RxFragment) {
            return ((RxFragment) provider).bindUntilEvent(event);
        } else {
            throw new IllegalArgumentException("class must extents RxFragment");
        }
    }

}