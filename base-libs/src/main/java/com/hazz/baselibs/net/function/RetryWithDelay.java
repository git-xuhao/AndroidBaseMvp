package com.hazz.baselibs.net.function;

import com.orhanobut.logger.Logger;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import retrofit2.HttpException;

/**
 * 请求重连
 */
public class RetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>> {
    private int maxRetries = 3;
    private long retryDelayMillis = 5000;
    private long increaseDelay = 5000;

    public RetryWithDelay() {
    }

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    public RetryWithDelay(int maxRetries, long retryDelayMillis, long increaseDelay) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
        this.increaseDelay = increaseDelay;
    }

    @Override
    public Observable<?> apply(@NonNull Observable<? extends Throwable> observable) throws Exception {
        return observable
                .zipWith(Observable.range(1, maxRetries + 1), new BiFunction<Throwable, Integer, Wrapper>() {
                    @Override
                    public Wrapper apply(Throwable throwable, Integer integer) {
                        return new Wrapper(throwable, integer);
                    }
                }).flatMap(new Function<Wrapper, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Wrapper wrapper) throws Exception {
                        if ((wrapper.throwable instanceof ConnectException
                                || wrapper.throwable instanceof SocketTimeoutException
                                || wrapper.throwable instanceof TimeoutException
                                || wrapper.throwable instanceof HttpException)
                                && wrapper.index < maxRetries + 1) { //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                            Logger.d("request retry at " + wrapper.index);
                            return Observable.timer(retryDelayMillis + (wrapper.index - 1) * increaseDelay, TimeUnit.MILLISECONDS);
                        }
                        return Observable.error(wrapper.throwable);
                    }
                });
    }

    private class Wrapper {
        private int index;
        private Throwable throwable;

        Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }
}
