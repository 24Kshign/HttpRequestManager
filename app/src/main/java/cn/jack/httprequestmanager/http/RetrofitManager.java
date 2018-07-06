package cn.jack.httprequestmanager.http;

import cn.jack.httprequestmanager.api.FRApi;

/**
 * Created by jack on 2018/7/3
 */
public class RetrofitManager extends BaseRetrofit{

    private FRApi mFRApi;

    private static class NetWorkManagerHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return NetWorkManagerHolder.INSTANCE;
    }

    private RetrofitManager() {
        super();
        mFRApi = mRetrofit.create(FRApi.class);
    }

    public FRApi getFRApi() {
        return getInstance().mFRApi;
    }
}