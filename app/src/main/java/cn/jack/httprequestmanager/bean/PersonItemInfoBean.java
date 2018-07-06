package cn.jack.httprequestmanager.bean;

/**
 * Created by jack on 17/5/17
 */

public class PersonItemInfoBean {

    /**
     * id : 10
     * list_code : sudai_user_center
     * config_en : my_repayment
     * config_cn : 我的还款
     * block : 1
     * type : 0
     * logo : http://mifengkongdemo.oss-cn-shenzhen.aliyuncs.com/fr_public_6666/6666_aba73dbabaefdcce9d202e42f4b2cc97.jpg
     * url : http://img06.tooopen.com/images/20161123/tooopen_sy_187628854311.jpg
     * state : 1
     * sort : 1
     */

    private String config_en;
    private String config_cn;
    private String url;
    private String person_content;
    private boolean is_last_item;
    private String need_login;
    private String logo;

    public String getConfig_en() {
        return config_en;
    }

    public void setConfig_en(String config_en) {
        this.config_en = config_en;
    }

    public String getConfig_cn() {
        return config_cn;
    }

    public void setConfig_cn(String config_cn) {
        this.config_cn = config_cn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean is_last_item() {
        return is_last_item;
    }

    public void setIs_last_item(boolean is_last_item) {
        this.is_last_item = is_last_item;
    }

    public String getPerson_content() {
        return person_content;
    }

    public void setPerson_content(String person_content) {
        this.person_content = person_content;
    }

    public boolean isIs_last_item() {
        return is_last_item;
    }

    public String getNeed_login() {
        return need_login;
    }

    public void setNeed_login(String need_login) {
        this.need_login = need_login;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
