package cn.ytxu.http_wrapper.model.field;

import cn.ytxu.http_wrapper.config.property.param_type.ParamTypeBean;
import cn.ytxu.http_wrapper.model.BaseModel;

/**
 * Created by ytxu on 2016/9/21.
 */
public class FieldModel<H extends BaseModel> extends BaseModel<H> implements Comparable<FieldModel> {
    // 字段的名称
    private String name;
    /**
     * 字段所属分类
     * e.g.
     * TODO 1 在response中，可以将其作为object，array，map类型中的类名
     * TODO 2 在request中，可以将其作为缩减参数数量的分类
     */
    private String group;
    // 参数的类型
    private String type;
    // 该字段是否为可选参数：主要应用于request(header与parameter)
    private boolean optional;
    /**
     * default value:默认值
     * TODO 未来需要能装换该值，如将双引号转换为单引号或者删除掉等等
     */
    private String defaultValue;
    /**
     * desc:接口描述，支持html语法，且在两侧会有<p></p>标签
     * format：<p>用户名<font color='red'>red</font></p>
     */
    private String description;
    /**
     * 参数的范围
     * e.g.
     * 1, 1..20-->int类型的参数，范围在1到20
     * -->String类型的参数，字符串的长度
     * 2, ..20-->int最大值为20
     * -->String类型的参数，字符串的长度
     * 3, "a","b","bc"-->String类型的参数，枚举参数值
     */
    private String size;

    // 是否为可过滤掉的参数
    private boolean filterTag = false;
    // 字段的类型，依赖于type解析出来的
    private ParamTypeBean paramTypeBean;
    /**
     * tip:在描述字段(description)解析出来的该字段的类型名称；可以用于response 中数组、对象的起名
     * 例如：results中有children字段，但两个都是Area属性
     * results	Array 地区信息结果{DataType:Area}
     * children	Array 地区信息子结果{DataType:Area}
     */
    private String dataType;

    public FieldModel(H higherLevel) {
        super(higherLevel);
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFilterTag() {
        return filterTag;
    }

    public void setFilterTag(boolean filterTag) {
        this.filterTag = filterTag;
    }

    public ParamTypeBean getParamTypeBean() {
        return paramTypeBean;
    }

    public void setParamTypeBean(ParamTypeBean paramTypeBean) {
        this.paramTypeBean = paramTypeBean;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public int compareTo(FieldModel o) {
        return this.name.compareToIgnoreCase(o.name);
    }

    //*************** reflect method area ***************
    public String field_read_type() {
        return type;
    }

    public String type() {
        return paramTypeBean.getRequestParamType();
    }

    public String requestParamType() {
        if (optional) {
            return paramTypeBean.getRequestOptionalParamType();
        }
        return paramTypeBean.getRequestParamType();
    }

    public boolean isFilterParam() {
        return filterTag;
    }

}
