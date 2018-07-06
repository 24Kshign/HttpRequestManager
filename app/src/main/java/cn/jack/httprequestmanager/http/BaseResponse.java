package cn.jack.httprequestmanager.http;

/**
 * Created by jack on 2018/7/4
 */
public class BaseResponse<DATA> {

    private int status;

    private String message;

    private DATA data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DATA getData() {
        return data;
    }

    public void setData(DATA data) {
        this.data = data;
    }
}
