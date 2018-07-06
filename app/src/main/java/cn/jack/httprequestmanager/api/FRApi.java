package cn.jack.httprequestmanager.api;

import java.util.ArrayList;
import java.util.Map;

import cn.jack.httprequestmanager.bean.PersonItemInfoBean;
import cn.jack.httprequestmanager.http.BaseResponse;
import cn.jack.httprequestmanager.http.HttpManager;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by jack on 2018/7/3
 */
public interface FRApi {

    String KAMAO_DOMAIN_NAME="kamao";

    @Headers({HttpManager.DOMAIN_NAME_HEADER + KAMAO_DOMAIN_NAME})
    @FormUrlEncoded
    @POST("Config/getMineList")
    Observable<BaseResponse<ArrayList<PersonItemInfoBean>>> getPersonItemInfo(@FieldMap Map<String, Object> params);//获取我的界面order

}