package cn.ytxu.api_semi_auto_creater.model.response;

import cn.ytxu.xhttp_wrapper.config.property.element_type.ElementTypeProperty;
import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import cn.ytxu.api_semi_auto_creater.model.request.DefinedParamModel;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.response.output.OutputParamType;
import cn.ytxu.util.FileUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/8/17.
 */
public class OutputParamModel extends BaseModel<ResponseModel> {

    private final OutputParamModel parent;
    private final OutputParamType type;
    private OutputParamType subType;// 只有array才有，如：List<Integer>,List<Long>,List<String>...
    private DefinedParamModel defined;
    private boolean dontRequireGenerationResponseEntityFileTag = false;// 是否需要生成响应实体文件标记，默认为需要

    private final String fieldName;
    private final Object fieldValue;
    private List<Object> values = Collections.EMPTY_LIST;// 只有object与array，才会有的
    private List<OutputParamModel> subs = Collections.EMPTY_LIST;

    public OutputParamModel(ResponseModel higherLevel, OutputParamModel parent, OutputParamType type,
                            String fieldName, Object fieldValue) {
        super(higherLevel, null);
        this.parent = parent;
        this.type = type;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public OutputParamType getType() {
        return type;
    }

    public void setSubType(OutputParamType subType) {
        this.subType = subType;
    }

    public OutputParamType getSubType() {
        return subType;
    }

    public void setDefined(DefinedParamModel defined) {
        this.defined = defined;
    }

    public DefinedParamModel getDefined() {
        return defined;
    }

    public String getName() {
        return fieldName;
    }

    public Object getValue() {
        return fieldValue;
    }

    public List<Object> getValues() {
        return values;
    }

    public void addValue(Object value) {
        if (values == Collections.EMPTY_LIST) {
            values = new ArrayList<>();
        }
        values.add(value);
    }

    public void setSubs(List<OutputParamModel> subs) {
        this.subs = subs;
    }

    public List<OutputParamModel> getSubs() {
        return subs;
    }

    public void setDontRequireGenerationResponseEntityFileTag() {
        dontRequireGenerationResponseEntityFileTag = true;
    }

    public boolean isDontRequireGenerationResponseEntityFileTag() {
        return dontRequireGenerationResponseEntityFileTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OutputParamModel that = (OutputParamModel) o;

        if (type != that.type) return false;
        if (subType != that.subType) return false;
        return fieldName != null ? fieldName.equals(that.fieldName) : that.fieldName == null;

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (subType != null ? subType.hashCode() : 0);
        result = 31 * result + (fieldName != null ? fieldName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OutputParamModel{" +
                "parent=" + parent +
                ", type=" + type +
                ", subType=" + subType +
                ", defined=" + defined +
                ", dontRequireGenerationResponseEntityFileTag=" + dontRequireGenerationResponseEntityFileTag +
                ", fieldName='" + fieldName + '\'' +
                ", fieldValue=" + fieldValue +
                ", values=" + values +
                ", subs=" + subs +
                '}';
    }

//*************** reflect method area ***************

    /**
     * 是否为响应下面的根参数，还是根参数下面的参数
     */
    public boolean isResponseRootEntity() {
        return Objects.isNull(parent);
    }

    public String entity_class_name() {
        if (needUseDefinedDataName()) {
            return defined.getDataType();
        }

        // TODO future：未来不只是解析status_code==OK的分组，还要解析其他的异常情况的分组，
        // TODO future: 所以需要使用到status_code_name进行output的类名拼接
        if (isResponseRootEntity()) {// 根output则使用request的类名作为该output的类名
            return getHigherLevel().getHigherLevel().request_class_name();
        }

        return FileUtil.getClassFileName(fieldName) + "4" + parent.entity_class_name();
    }

    private boolean needUseDefinedDataName() {
        return Objects.nonNull(defined) && Objects.nonNull(defined.getDataType());
    }

    public List<OutputParamModel> outputs() {
        return subs;
    }

    public String output_type() {
        return ElementTypeProperty.getInstance().getElementTypeByOutput(this);
    }

    public String output_name() {
        return fieldName;
    }

    public String output_original_type() {
        return type.name();
    }

    public String output_getter() {
        if (type == OutputParamType.BOOLEAN) {
            String classFileName = FileUtil.getClassFileName(fieldName);
            classFileName = classFileName.substring(0, 1).toLowerCase() + classFileName.substring(1);
            return classFileName;
        }
        return "get" + FileUtil.getClassFileName(fieldName);
    }

    public String output_setter() {
        return "set" + FileUtil.getClassFileName(fieldName);
    }

}
