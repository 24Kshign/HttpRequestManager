package cn.jack.httprequestmanager;

import android.app.Application;

import cn.jack.httprequestmanager.api.FRApi;
import cn.jack.httprequestmanager.http.servlet.FRServletAddress;

/**
 * Created by jack on 2018/7/4
 */
public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        FRServletAddress.getInstance()
                .setOfflineServletAddress(FRApi.KAMAO_DOMAIN_NAME, getString(R.string.kamao_offline_address))
                .setReleaseServletAddress(FRApi.KAMAO_DOMAIN_NAME, getString(R.string.kamao_release_address))
                .setOnlineServletAddress(FRApi.KAMAO_DOMAIN_NAME, getString(R.string.kamao_online_address));
    }

    private static APP sInstance;

    public static APP getInstance() {
        return sInstance;
    }

}
