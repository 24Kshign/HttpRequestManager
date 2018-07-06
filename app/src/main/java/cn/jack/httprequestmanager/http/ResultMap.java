package cn.jack.httprequestmanager.http;


import io.reactivex.functions.Function;

/**
 * Created by jack on 2018/7/4
 */
public class ResultMap<DATA> implements Function<BaseResponse<DATA>, DATA> {
    @Override
    public DATA apply(BaseResponse<DATA> data) {
        if (data.getStatus() == 1) {
            return data.getData();
        } else {
            throw new RuntimeException("请求失败(code=" + data.getStatus() + ",message=" + data.getMessage() + ")");
        }
    }
}