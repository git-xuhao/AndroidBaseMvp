package com.hazz.baselibs.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.hazz.baselibs.glide.okhttp3.OkHttpUrlLoader;

import java.io.InputStream;

@GlideModule
public final class MyAppGlideModule extends AppGlideModule {

    /**
     * 通过GlideBuilder设置默认的结构(Engine,BitmapPool ,ArrayPool,MemoryCache等等).
     *
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDefaultRequestOptions(
                new RequestOptions()
                        .format(DecodeFormat.PREFER_RGB_565)
                        .disallowHardwareConfig());//禁止硬件位图
        //重新设置内存限制
        builder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));

    }

    /**
     * 为App注册一个自定义的String类型的BaseGlideUrlLoader
     *
     * @param context
     * @param registry
     */
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }



    /**
     * 清单解析的开启
     * <p>
     * 这里不开启，避免添加相同的modules两次
     *
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}