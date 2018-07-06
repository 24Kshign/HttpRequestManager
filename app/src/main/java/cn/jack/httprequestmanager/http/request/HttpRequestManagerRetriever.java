package cn.jack.httprequestmanager.http.request;

import android.accounts.NetworkErrorException;
import android.app.ProgressDialog;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import cn.jack.httprequestmanager.http.callback.BaseImpl;
import cn.jack.httprequestmanager.http.callback.HttpDataListenerImpl;
import cn.jack.httprequestmanager.http.callback.HttpObserver;
import cn.jack.httprequestmanager.util.FRString;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jack on 2018/7/5
 */
public class HttpRequestManagerRetriever {

    private WeakReference<BaseImpl> mBaseImpl;

    private Function mFunction;

    private Observable mObservable;
    private Map<String, Object> mParams;

    private String message;
    private ProgressDialog dialog;

    HttpRequestManagerRetriever(@Nullable BaseImpl baseImpl) {
        if (baseImpl == null) {
            throw new NullPointerException("the BaseImpl should not be null");
        }
        if (null == mBaseImpl) {
            mBaseImpl = new WeakReference<>(baseImpl);
        }
        if (null == mParams) {
            mParams = new HashMap<>();
        } else {
            mParams.clear();
        }
    }

    public HttpRequestManagerRetriever addParams(String key, Object object) {
        if (!FRString.isEmpty(key)) {
            mParams.put(key, object);
        }
        return this;
    }

    public HttpRequestManagerRetriever addParams(Map<String, Object> params) {
        if (null != params) {
            mParams.putAll(params);
        }
        return this;
    }

    public HttpRequestManagerRetriever setProgressDialogMessage(String message) {
        this.message = message;
        return this;
    }

    public HttpRequestManagerRetriever setProgressDialog(ProgressDialog progressDialog) {
        this.dialog = progressDialog;
        return this;
    }

    public <DATA, R> HttpRequestManagerRetriever transFormData(Function<? super DATA, ? extends R> mapper) {
        mFunction = mapper;
        return this;
    }

    public <DATA> HttpRequestManagerRetriever setObservable(Observable<DATA> observable) {
        mObservable = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    private int mRetryCount;

                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                                if ((throwable instanceof NetworkErrorException
                                        || throwable instanceof ConnectException
                                        || throwable instanceof SocketTimeoutException
                                        || throwable instanceof TimeoutException) && mRetryCount < 3) {
                                    mRetryCount++;
                                    return Observable.timer(2000, TimeUnit.MILLISECONDS);
                                }
                                return Observable.error(throwable);
                            }
                        });
                    }
                });
        return this;
    }

    public Map<String, Object> getParams() {
        return mParams;
    }

    //创建subscriber
    public <DATA> void enqueue(HttpDataListenerImpl<DATA> listener) {
        HttpObserver<DATA> observer;
        if (null != dialog) {
            observer = new HttpObserver<DATA>(listener, mBaseImpl.get(), dialog);
        } else if (!FRString.isEmpty(message)) {
            observer = new HttpObserver<DATA>(listener, mBaseImpl.get(), message);
        } else {
            observer = new HttpObserver<DATA>(listener, mBaseImpl.get());
        }
        if (null != mFunction) {
            mObservable = mObservable.map(mFunction);
        }
        mObservable.subscribe(observer);
    }

}