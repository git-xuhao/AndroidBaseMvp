package com.hazz.baselibs.app;

import java.io.File;

/**
 * @author xuhao
 * @date 2018/6/10 16:18
 * @desc app 常量
 */
public class AppConstants {

    /**
     * Path
     */
    public static final String PATH_DATA = BaseApplication.getContext().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    static final String BUGLY_ID = "16e54f8921";
}
