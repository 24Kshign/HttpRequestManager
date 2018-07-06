package cn.jack.httprequestmanager;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import cn.jack.httprequestmanager.base.BaseActivity;
import cn.jack.httprequestmanager.bean.PersonItemInfoBean;
import cn.jack.httprequestmanager.http.ResultMap;
import cn.jack.httprequestmanager.http.request.HttpRequestManager;
import cn.jack.httprequestmanager.http.RetrofitManager;
import cn.jack.httprequestmanager.http.callback.HttpDataListenerImpl;

public class MainActivity extends BaseActivity {

    private TextView tvTest;

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        tvTest = findViewById(R.id.am_tv_test);
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });
    }

    private void request() {
        HttpRequestManager
                .with(this)
                .addParams("list_code", "kamao_mine_list")
                .addParams("plat_type", "1")
                .addParams("plat_version", "4.2.0")
                .addParams("package_name", "cn.rongdao.jrkabao")
                .setProgressDialogMessage("加载中...")
                .transFormData(new ResultMap<List<PersonItemInfoBean>>())
                .setObservable(RetrofitManager.getInstance().getFRApi().getPersonItemInfo(HttpRequestManager.with(this).getParams()))
                .enqueue(new HttpDataListenerImpl<List<PersonItemInfoBean>>() {
                    @Override
                    public void onSuccess(List<PersonItemInfoBean> data) {
                        Log.e("TAG", "onSuccess------->" + data.size());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        super.onFailure(t);
                        Log.e("TAG", "onFailure------->" + t.getMessage());
                    }
                });
    }
}