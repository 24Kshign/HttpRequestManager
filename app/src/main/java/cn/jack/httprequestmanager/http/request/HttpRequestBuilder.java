package cn.jack.httprequestmanager.http.request;

import android.support.annotation.Nullable;

import cn.jack.httprequestmanager.http.callback.BaseImpl;

/**
 * Created by jack on 2018/7/5
 */

class HttpRequestBuilder {

    HttpRequestBuilder() {

    }

    HttpRequestManager build(@Nullable BaseImpl baseImpl) {
        HttpRequestManagerRetriever httpRequestManagerRetriever = new HttpRequestManagerRetriever(baseImpl);
        return new HttpRequestManager(baseImpl, httpRequestManagerRetriever);
    }
}