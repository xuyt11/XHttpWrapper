package cn.ytxu.http_wrapper.model.status_code;

import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.model.version.VersionModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016-9-18
 * 状态码的分类model；
 * tip: 若status_code category分类是与request的Section分类名称一致，则可以用于分类筛选
 */
public class StatusCodeGroupModel extends BaseModel<VersionModel> implements Comparable<StatusCodeGroupModel> {
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

    private String version;

    private List<StatusCodeModel> statusCodes = Collections.EMPTY_LIST;

    public StatusCodeGroupModel(VersionModel higherLevel) {
        super(higherLevel);
        higherLevel.addStatusCodeGroup(this);
    }

    public void init(String title, String name, String version) {
        this.title = title;
        this.name = name;
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public List<StatusCodeModel> getStatusCodes() {
        return statusCodes;
    }

    public void addStatusCode(StatusCodeModel statusCode) {
        if (this.statusCodes == Collections.EMPTY_LIST) {
            this.statusCodes = new ArrayList<>();
        }
        this.statusCodes.add(statusCode);
    }

    @Override
    public int compareTo(StatusCodeGroupModel o) {
        return this.name.compareToIgnoreCase(o.name);
    }


    //*************** reflect method area ***************
    public List<StatusCodeModel> status_codes() {
        return statusCodes;
    }
}
