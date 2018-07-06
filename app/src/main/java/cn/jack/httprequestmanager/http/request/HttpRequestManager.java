package cn.jack.httprequestmanager.http.request;

import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import cn.jack.httprequestmanager.http.callback.BaseImpl;

/**
 * Created by jack on 2018/7/3
 */
public class HttpRequestManager {

    private WeakReference<BaseImpl> mBaseImpl;

    private HttpRequestManagerRetriever mHttpRequestManagerRetriever;
    private static volatile HttpRequestManager httpRequestManager;
    private static volatile boolean isInitializing;

    HttpRequestManager(@Nullable BaseImpl baseImpl, @Nullable HttpRequestManagerRetriever httpRequestManagerRetriever) {
        mHttpRequestManagerRetriever = httpRequestManagerRetriever;
        if (null == mBaseImpl) {
            mBaseImpl = new WeakReference<>(baseImpl);
        }
    }

    public static HttpRequestManagerRetriever with(@Nullable BaseImpl baseImpl) {
        if (baseImpl == null) {
            throw new NullPointerException("the BaseImpl should not be null");
        }
        return get(baseImpl).getHttpRequestManagerRetriever();
    }

    private HttpRequestManagerRetriever getHttpRequestManagerRetriever() {
        return mHttpRequestManagerRetriever;
    }

    private static HttpRequestManager get(@Nullable BaseImpl baseImpl) {
        if (httpRequestManager == null) {
            synchronized (HttpRequestManager.class) {
                if (httpRequestManager == null) {
                    checkAndInitializeHttpRequestManager(baseImpl);
                }
            }
        }
        return httpRequestManager;
    }

    private static void checkAndInitializeHttpRequestManager(@Nullable BaseImpl baseImpl) {
        if (isInitializing) {
            throw new IllegalStateException("You cannot call HttpRequestManager.get() in registerComponents(),"
                    + " use the provided HttpRequestManager instance instead");
        }
        isInitializing = true;
        initializeHttpRequestManager(baseImpl);
        isInitializing = false;
    }

    private static void initializeHttpRequestManager(@Nullable BaseImpl baseImpl) {
        initializeHttpRequestManager(baseImpl, new HttpRequestBuilder());
    }

    private static void initializeHttpRequestManager(@Nullable BaseImpl baseImpl, @Nullable HttpRequestBuilder builder) {
        if (builder != null) {
            HttpRequestManager.httpRequestManager = builder.build(baseImpl);
        }
    }
}
