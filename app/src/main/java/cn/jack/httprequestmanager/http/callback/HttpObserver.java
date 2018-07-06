package cn.jack.httprequestmanager.http.callback;

import android.app.ProgressDialog;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by jack on 2018/7/4
 */
public class HttpObserver<DATA> implements Observer<DATA> {

    private WeakReference<BaseImpl> mBaseImpl;
    private ProgressDialog mDialog;

    private HttpDataListenerImpl<DATA> mDataListener;

    public HttpObserver(HttpDataListenerImpl<DATA> dataListener, BaseImpl baseImpl) {
        mBaseImpl = new WeakReference<>(baseImpl);
        mDataListener = dataListener;
    }

    public HttpObserver(HttpDataListenerImpl<DATA> dataListener, BaseImpl baseImpl, String message) {
        this(dataListener, baseImpl);
        initProgressDialog(message);
    }

    public HttpObserver(HttpDataListenerImpl<DATA> dataListener, BaseImpl baseImpl, ProgressDialog dialog) {
        this(dataListener, baseImpl);
        mDialog = dialog;
    }

    private void initProgressDialog(String message) {
        if (null == mDialog && null != mBaseImpl.get().getActivity()) {
            mDialog = new ProgressDialog(mBaseImpl.get().getActivity());
            mDialog.setMessage(message);
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        mBaseImpl.get().addRxDestroy(d);
        if (null != mDialog && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Override
    public void onNext(DATA data) {
        if (null != mDataListener) {
            mDataListener.onSuccess(data);
        }
    }

    @Override
    public void onError(Throwable t) {
        dismissDialog();
        if (null != mDataListener) {
            mDataListener.onFailure(t);
        }
    }

    private void dismissDialog(){
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onComplete() {
        dismissDialog();
    }
}
