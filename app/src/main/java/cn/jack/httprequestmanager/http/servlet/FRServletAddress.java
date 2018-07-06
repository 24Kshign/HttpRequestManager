package cn.jack.httprequestmanager.http.servlet;


import java.util.HashMap;
import java.util.Map;

import cn.jack.httprequestmanager.util.FRDebugMode;
import cn.jack.httprequestmanager.util.FRString;
import okhttp3.HttpUrl;

/**
 *
 */
public class FRServletAddress {

    private Map<String, HttpUrl> onlineAddressArray = new HashMap<>();
    private Map<String, HttpUrl> offlineAddressArray = new HashMap<>();
    private Map<String, HttpUrl> releaseAddressArray = new HashMap<>();


    private static final class SingletonHolder {
        private static final FRServletAddress INSTANCE = new FRServletAddress();
    }

    public static FRServletAddress getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private FRServletAddress() {
    }

    public FRServletAddress setOnlineServletAddress(String key, String onlineServletAddress) {
        if (!FRString.isEmpty(key, onlineServletAddress)) {
            onlineAddressArray.put(key, checkUrl(onlineServletAddress));
        }
        return this;
    }

    public FRServletAddress setOfflineServletAddress(String key, String offlineServletAddress) {
        if (!FRString.isEmpty(key, offlineServletAddress)) {
            offlineAddressArray.put(key, checkUrl(offlineServletAddress));
        }
        return this;
    }

    public FRServletAddress setReleaseServletAddress(String key, String releaseServletAddress) {
        if (!FRString.isEmpty(key, releaseServletAddress)) {
            releaseAddressArray.put(key, checkUrl(releaseServletAddress));
        }
        return this;
    }

    public HttpUrl getServletAddress(String key) {
        if (FRDebugMode.isDebugMode()) {
            return offlineAddressArray.get(key);   //测试
        } else if (FRDebugMode.isPreMode()) {
            return releaseAddressArray.get(key);  //预发
        }
        return onlineAddressArray.get(key);   //正式
    }

    private HttpUrl checkUrl(String url) {
        HttpUrl parseUrl = HttpUrl.parse(url);
        if (null == parseUrl) {
            throw new RuntimeException(url);
        } else {
            return parseUrl;
        }
    }
}