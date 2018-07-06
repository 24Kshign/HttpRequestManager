package cn.jack.httprequestmanager.http.callback;

import android.accounts.NetworkErrorException;
import android.net.ParseException;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import cn.jack.httprequestmanager.APP;

/**
 * Created by jack on 2018/7/4
 */
public abstract class HttpDataListenerImpl<DATA> implements HttpDataListener<DATA> {

    @Override
    public void onFailure(Throwable t) {
        StringBuilder sb = new StringBuilder();
        if (t instanceof NetworkErrorException
                || t instanceof UnknownHostException
                || t instanceof ConnectException) {
            sb.append("网络异常");
        } else if (t instanceof SocketTimeoutException
                || t instanceof InterruptedIOException
                || t instanceof TimeoutException) {
            sb.append("请求超时");
        } else if (t instanceof JsonSyntaxException) {
            sb.append("服务端数据解析失败");
        } else if (t instanceof JsonParseException
                || t instanceof JSONException
                || t instanceof ParseException) {   //  解析错误
            sb.append("解析错误");
        } else {
            sb.append("未知错误");
        }

        Toast.makeText(APP.getInstance(), sb.toString(), Toast.LENGTH_SHORT).show();
    }
}
