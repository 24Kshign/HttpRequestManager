package cn.jack.httprequestmanager.http.callback;

/**
 * Created by jack on 2018/7/4
 */

interface HttpDataListener<DATA> {

    void onSuccess(DATA data);

    void onFailure(Throwable t);
}