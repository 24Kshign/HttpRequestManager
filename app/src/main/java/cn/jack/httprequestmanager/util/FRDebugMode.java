package cn.jack.httprequestmanager.util;


import cn.jack.httprequestmanager.BuildConfig;

/**
 * 调试的封装
 */
public final class FRDebugMode {

    private FRDebugMode() {
    }

    private static boolean isDebugMode = false;     //是否是测试
    private static boolean isPreMode = false;     //是否是预发
    private static boolean isUseCloudAPiHost = false;   //是否使用网关

    public static boolean isDebugMode() {
        return isDebugMode;
    }

    public static void setDebugMode(boolean debugMode) {
        isDebugMode = debugMode;
    }

    public static boolean isPreMode() {
        return isPreMode;
    }

    public static void setPreMode(boolean preMode) {
        isPreMode = preMode;
    }

    //是否显示日志（测试和预发环境下显示）
    public static boolean isShowLog() {
        return isDebugMode || isPreMode || BuildConfig.DEBUG;
    }

    //是否是正式环境
    public static boolean isFormalMode() {
        return !isPreMode && !isDebugMode;
    }

    public static boolean isIsUseCloudAPiHost() {
        return isUseCloudAPiHost;
    }

    public static void setIsUseCloudAPiHost(boolean useCloudAPiHost) {
        isUseCloudAPiHost = useCloudAPiHost;
    }
}