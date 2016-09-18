package cn.ytxu.xhttp_wrapper.model;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;

import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016-9-18
 * 状态码的分类model；
 * tip: 若status_code category分类是与request的Section分类名称一致，则可以用于分类筛选
 */
public class StatusCodeGroupModel extends BaseModel<VersionModel, ApiDataBean> {
    /**
     * request title
     * 接口名称 zh
     * format：提交验证信息(投资人)
     */
    private String title;
    /**
     * request name
     * 接口名称 en
     * format：account_buyer_verify
     * 分组的名称，即在ApiDataBean中的title
     */
    private String name;

    private List<StatusCodeModel> statusCodes = Collections.EMPTY_LIST;

    public StatusCodeGroupModel(VersionModel higherLevel, ApiDataBean element, String title, String name) {
        super(higherLevel, element);
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public List<StatusCodeModel> getStatusCodes() {
        return statusCodes;
    }

    public void setStatusCodes(List<StatusCodeModel> statusCodes) {
        this.statusCodes = statusCodes;
    }


    //*************** reflect method area ***************
    public List<StatusCodeModel> status_codes() {
        return statusCodes;
    }

}
