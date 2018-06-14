package com.hazz.baselibs.net.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.hazz.baselibs.utils.NetworkUtils;
import com.orhanobut.logger.Logger;

/**
 * 网络状态变化接收器
 */
public class NetworkStatusReceiver extends BroadcastReceiver {

    /**
     * 网络类型
     */
    public enum Type {
        NONE(1),
        MOBILE(2),
        WIFI(4);

        public int value;

        Type(int value) {
            this.value = value;
        }
    }

    /**
     * 已连接网络下的网络类型
     */
    public enum NetType {
        UN_KNOWN(-1),
        WIFI(1),
        NET2G(2),
        NET3G(3),
        NET4G(4);

        public int value;

        NetType(int value) {
            this.value = value;
        }
    }

    private static NetworkStatusReceiver.Type type = Type.NONE;

    public NetworkStatusReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            if (NetworkUtils.isWifiConnected(context)) {
                type = Type.WIFI;
            } else if (NetworkUtils.isMobileConnected(context)) {
                type = Type.MOBILE;
            } else {
                type = Type.NONE;
            }

            Logger.e("The network has changed, code = " + type.value);
        }
    }

    public static NetworkStatusReceiver.Type getType(Context context) {
        if (type == Type.NONE) {
            if (NetworkUtils.isWifiConnected(context)) {
                type = Type.WIFI;
            } else if (NetworkUtils.isMobileConnected(context)) {
                type = Type.MOBILE;
            } else {
                type = Type.NONE;
            }
        }
        return type;
    }
}