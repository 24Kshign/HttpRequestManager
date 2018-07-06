package cn.jack.httprequestmanager.http;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import cn.jack.httprequestmanager.http.parser.DefaultUrlParser;
import cn.jack.httprequestmanager.http.parser.UrlParser;
import cn.jack.httprequestmanager.http.servlet.FRServletAddress;
import cn.jack.httprequestmanager.util.FRString;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jack on 2018/7/3
 */
public class HttpManager {

    private static final String TAG = "HttpManager";

    private static final String DOMAIN_NAME = "Domain-Name";
    public static final String DOMAIN_NAME_HEADER = DOMAIN_NAME + ": ";
    private final Interceptor mInterceptor;
    private UrlParser mUrlParser;

    /**
     * 如果在 Url 地址中加入此标识符, 框架将不会对此 Url 进行任何切换 BaseUrl 的操作
     */
    public static final String IDENTIFICATION_IGNORE = "#url_ignore";

    private HttpManager() {
        UrlParser urlParser = new DefaultUrlParser();
        urlParser.init(this);
        setUrlParser(urlParser);
        this.mInterceptor = new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                return chain.proceed(processRequest(chain.request()));
            }
        };
    }

    /**
     * 对 {@link Request} 进行一些必要的加工, 执行切换 BaseUrl 的相关逻辑
     *
     * @param request {@link Request}
     * @return {@link Request}
     */
    public Request processRequest(Request request) {
        if (request == null) return null;

        Request.Builder newBuilder = request.newBuilder();

        String url = request.url().toString();
        //如果 Url 地址中包含 IDENTIFICATION_IGNORE 标识符, 框架将不会对此 Url 进行任何切换 BaseUrl 的操作
        if (url.contains(IDENTIFICATION_IGNORE)) {
            return pruneIdentification(newBuilder, url);
        }

        String domainName = obtainDomainNameFromHeaders(request);

        HttpUrl httpUrl;

        // 如果有 header,获取 header 中 domainName 所映射的 url,若没有,则检查全局的 BaseUrl,未找到则为null
        if (FRString.isEmpty(domainName)) {
            throw new NullPointerException("Header should not be null");
        }

        httpUrl = FRServletAddress.getInstance().getServletAddress(domainName);
        newBuilder.removeHeader(DOMAIN_NAME);
        if (null != httpUrl) {
            HttpUrl newUrl = mUrlParser.parseUrl(httpUrl, request.url());
            Log.e(TAG, "The new url is { " + newUrl.toString() + " }, old url is { " + request.url().toString() + " }");
            return newBuilder
                    .url(newUrl)
                    .build();
        }
        return newBuilder.build();
    }

    /**
     * 将 {@code IDENTIFICATION_IGNORE} 从 Url 地址中修剪掉
     *
     * @param newBuilder {@link Request.Builder}
     * @param url        原始 Url 地址
     * @return 被修剪过 Url 地址的 {@link Request}
     */
    private Request pruneIdentification(Request.Builder newBuilder, String url) {
        String[] split = url.split(IDENTIFICATION_IGNORE);
        StringBuffer buffer = new StringBuffer();
        for (String s : split) {
            buffer.append(s);
        }
        return newBuilder
                .url(buffer.toString())
                .build();
    }

    public static HttpManager getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final HttpManager INSTANCE = new HttpManager();
    }

    public OkHttpClient.Builder with(OkHttpClient.Builder builder) {
        checkNotNull(builder, "builder cannot be null");
        return builder.addInterceptor(mInterceptor);
    }

    public void setUrlParser(UrlParser parser) {
        checkNotNull(parser, "parser cannot be null");
        this.mUrlParser = parser;
    }

    private String obtainDomainNameFromHeaders(Request request) {
        List<String> headers = request.headers(DOMAIN_NAME);
        if (headers == null || headers.size() == 0)
            return null;
        if (headers.size() > 1)
            throw new IllegalArgumentException("Only one Domain-Name in the headers");
        return request.header(DOMAIN_NAME);
    }

    private <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

}