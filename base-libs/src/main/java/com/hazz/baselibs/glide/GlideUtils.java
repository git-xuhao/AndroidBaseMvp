package com.hazz.baselibs.glide;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
/**
 * @author xuhao
 * @date 2018/6/11 17:36
 * @desc 图片加载工具类
 */
public class GlideUtils {
  
  /**
     * 加载图片
     *
     * @param context  context
     * @param iv       imageView
     * @param url      图片地址
     * @param emptyImg 默认展位图
     */
    public static void loadImage(Context context, ImageView iv, String url, int emptyImg) {
        if (!TextUtils.isEmpty(url)) {
            GlideApp.with(context)
                    .load(url)
                    .error(emptyImg)
                    .placeholder(iv.getDrawable())
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(iv);
        } else {
            loadImage(context, iv, emptyImg, emptyImg);
        }
    }

    /**
     * 加载圆角图片
     *
     * @param context  context
     * @param iv       imageView
     * @param url      图片地址
     * @param emptyImg 默认展位图
     */
    public static void loadRoundImage(Context context, ImageView iv, String url, int emptyImg) {
        if (!TextUtils.isEmpty(url)) {
            GlideApp.with(context)
                    .load(url)
                    .error(emptyImg)
                    .placeholder(iv.getDrawable())
                    .transition(new DrawableTransitionOptions().crossFade())
                    .transform(new RoundedCorners(20)).into(iv);
        } else {
            loadRoundImage(context, iv, emptyImg, emptyImg);
        }
    }

    /**
     * 加载圆形图片
     *
     * @param context  context
     * @param iv       imageView
     * @param url      图片地址
     * @param emptyImg 默认展位图
     */
    public static void loadCircleImage(Context context, ImageView iv, String url, int emptyImg) {
        if (!TextUtils.isEmpty(url)) {
            GlideApp.with(context)
                    .load(url)
                    .error(emptyImg)
                    .placeholder(iv.getDrawable())
                    .transition(new DrawableTransitionOptions().crossFade())
                    .transform(new CircleCrop()).into(iv);
        } else {
            loadCircleImage(context, iv, emptyImg, emptyImg);
        }
    }

    /**
     * 加载drawable中的图片资源
     *
     * @param context  context
     * @param iv       imageView
     * @param resId    图片地址
     * @param emptyImg 默认展位图
     */
    public static void loadImage(Context context, ImageView iv, int resId, int emptyImg) {
        GlideApp.with(context).load(resId).placeholder(emptyImg).into(iv);
    }

    /**
     * 加载drawable中的图片资源 圆角
     *
     * @param context  context
     * @param iv       imageView
     * @param resId    图片地址
     * @param emptyImg 默认展位图
     */
    public static void loadRoundImage(Context context, ImageView iv, int resId, int emptyImg) {
        GlideApp.with(context).load(emptyImg).placeholder(emptyImg).transform(new RoundedCorners(20)).into(iv);
    }

    /**
     * 加载drawable中的图片资源 圆形
     *
     * @param context  context
     * @param iv       imageView
     * @param resId    图片地址
     * @param emptyImg 默认展位图
     */
    public static void loadCircleImage(Context context, ImageView iv, int resId, int emptyImg) {
        GlideApp.with(context).load(emptyImg).placeholder(emptyImg).transform(new CircleCrop()).into(iv);
    }


}
