package cn.jack.httprequestmanager.http;

import java.util.concurrent.TimeUnit;

import cn.jack.httprequestmanager.util.FRDebugMode;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jack on 2018/7/5
 */
public class BaseRetrofit {

    Retrofit mRetrofit;

    BaseRetrofit() {

        OkHttpClient mOkHttpClient = HttpManager.getInstance().with(new OkHttpClient.Builder()) //RetrofitUrlManager 初始化
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        OkHttpClient.Builder builder = mOkHttpClient.newBuilder();

        if (FRDebugMode.isShowLog()) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用rxjava
                .addConverterFactory(GsonConverterFactory.create())//使用Gson
                .client(mOkHttpClient)
                .build();
    }
}