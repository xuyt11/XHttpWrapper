package cn.ytxu.api_semi_auto_creater.model.status_code;

import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;
import org.jsoup.nodes.Element;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/9/4.
 * 状态码的分类model；
 * tip: 若status_code category分类是与request的Section分类名称一致，则可以用于分类筛选
 */
public class StatusCodeCategoryModel extends BaseModel<VersionModel> {
    private String name;// 分类的名称
    private String version;// 版本号

    private List<StatusCodeModel> statusCodes = Collections.EMPTY_LIST;

    public StatusCodeCategoryModel(VersionModel higherLevel, Element element, String name, String version) {
        super(higherLevel, element);
        this.name = name;
        this.version = version;
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

    public void setStatusCodes(List<StatusCodeModel> statusCodes) {
        this.statusCodes = statusCodes;
    }


    //*************** reflect method area ***************
    public List<StatusCodeModel> status_codes() {
        return statusCodes;
    }

}
