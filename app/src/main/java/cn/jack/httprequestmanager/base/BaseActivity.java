package cn.jack.httprequestmanager.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import cn.jack.httprequestmanager.http.callback.BaseImpl;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by jack on 2018/7/4
 */
public abstract class BaseActivity extends FragmentActivity implements BaseImpl {

    private CompositeDisposable mCompositeDisposable;// 管理Destroy取消订阅者者

    protected abstract int layoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (0 != layoutRes()) {
            setContentView(layoutRes());
        } else {
            throw new IllegalArgumentException("layout res should not be null");
        }
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        initView();
    }

    protected void initView(){

    }

    @Override
    public void addRxDestroy(Disposable disposable) {
        if (null != mCompositeDisposable) {
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        if (null != mCompositeDisposable) {
            mCompositeDisposable.dispose();
            mCompositeDisposable = null;
        }
        super.onDestroy();
    }
}
