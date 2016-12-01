package cn.ytxu.api_semi_auto_creater.model.request;

import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import org.jsoup.nodes.Element;

import java.util.Objects;

/**
 * Created by ytxu on 2016/6/16.
 */
public class InputParamModel extends BaseModel<RequestModel> {
    private String name;// 字段名称
    private String type;// 字段的类型
    private DefinedParamModel defind;// 已定义的字段描述对象
    private boolean isFilterTag;// 是否为可过滤掉的参数

    public InputParamModel(RequestModel higherLevel, Element element) {
        super(higherLevel, element);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDefind(DefinedParamModel defind) {
        this.defind = defind;
    }

    public void setFilterTag(boolean isFilterTag) {
        this.isFilterTag = isFilterTag;
    }

    public boolean isFilterTag() {
        return isFilterTag;
    }

    @Override
    public String toString() {
        return "InputParamModel{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", defind=" + defind +
                ", isFilterTag=" + isFilterTag +
                '}';
    }

    //*************** reflect method area ***************
    public String header_type() {
        return type();
    }

    public String input_type() {
        return type();
    }

    private String type() {
        return ConfigWrapper.getFieldType().getElementType(this);
    }

    public String header_request_param_type() {
        return requestParamType();
    }

    public String input_request_param_type() {
        return requestParamType();
    }

    private String requestParamType() {
        return ConfigWrapper.getFieldType().getElementRequestType(this);
    }

    public String header_name() {
        return name;
    }

    public String input_name() {
        return name;
    }

    public String header_desc() {
        return getDesc();
    }

    public String input_desc() {
        return getDesc();
    }

    private String getDesc() {
        if (Objects.isNull(defind)) {
            return "";
        }

        String desc = defind.getDescription();
        if (Objects.isNull(desc)) {
            return "";
        }

        return desc;
    }

    public boolean input_isOptional() {
        if (Objects.isNull(defind)) {
            return false;
        }
        return defind.isOptional();
    }

    public boolean isFilterParam() {
        return isFilterTag;
    }
}
