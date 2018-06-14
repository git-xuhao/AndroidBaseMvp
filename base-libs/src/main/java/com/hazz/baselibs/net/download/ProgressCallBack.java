package com.hazz.baselibs.net.download;

import android.os.Handler;
import android.util.Log;

import com.hazz.baselibs.rx.RxBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * 下载进度的 CallBack
 * @param <T>
 */
public abstract class ProgressCallBack<T> {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private String destFileDir; // 本地文件存放路径
    private String destFileName; // 文件名
    private Disposable mSubscription;
    private final Handler handler;

    /**
     *
     * @param destFileDir 本地文件存放路径
     * @param destFileName 文件名
     */
    public ProgressCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        handler = new Handler();
        subscribeLoadProgress();
    }

    public abstract void onSuccess(T t);

    public abstract void progress(long progress, long total);

    public void onStart() {
    }

    public void onCompleted() {
    }

    public abstract void onError(Throwable e);

    public void saveFile(ResponseBody body) {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            unsubscribe();
            //onCompleted();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                Log.e("saveFile", e.getMessage());
            }
        }
    }

    /**
     * 订阅加载的进度条
     */
    public void subscribeLoadProgress() {
        mSubscription = RxBus.getDefault().toObservable(DownLoadStateBean.class)
                .subscribe(new Consumer<DownLoadStateBean>() {
                    @Override
                    public void accept(final DownLoadStateBean downLoadStateBean) {
                        // 回调到主线程更新UI
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progress(downLoadStateBean.getBytesLoaded(), downLoadStateBean.getTotal());
                            }
                        });
                    }
                });
        //将订阅者加入管理站
        addDispose(mSubscription);
    }

    /**
     * 取消订阅，防止内存泄漏
     */
    public void unsubscribe() {
        compositeDisposable.remove(mSubscription);
    }


    /**
     * @param disposable
     */
    public void addDispose(Disposable disposable) {
        if (disposable != null) {
            compositeDisposable.add(disposable);// 将所有 Disposable 放入集中处理
        }
    }

}
