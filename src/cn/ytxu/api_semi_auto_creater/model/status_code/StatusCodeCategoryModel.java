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

    private List<StatusCodeModel> statusCodes = Collections.EMPTY_LIST;

    public StatusCodeCategoryModel(VersionModel higherLevel, Element element) {
        super(higherLevel, element);
    }

}
