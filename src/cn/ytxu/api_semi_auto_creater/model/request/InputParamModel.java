package cn.ytxu.api_semi_auto_creater.model.request;

import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;

import java.util.Objects;

/**
 * Created by ytxu on 2016/6/16.
 */
public class InputParamModel extends BaseModel<RequestModel> {
    private String name;// 字段名称
    private String type;// 字段的类型
    private DefinedParamModel defind;// 已定义的字段描述对象

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


    //*************** reflect method area ***************
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

}