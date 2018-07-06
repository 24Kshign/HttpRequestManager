package cn.jack.httprequestmanager.http.callback;

import android.app.Activity;

import io.reactivex.disposables.Disposable;

/**
 *
 */

public interface BaseImpl {

    void addRxDestroy(Disposable disposable);

    Activity getActivity();

}
