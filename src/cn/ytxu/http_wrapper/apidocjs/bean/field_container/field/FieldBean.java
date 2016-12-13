package cn.ytxu.http_wrapper.apidocjs.bean.field_container.field;

public class FieldBean {
    /**
     * 字段所属分类
     * e.g.
     * TODO 1 在response中，可以将其作为object，array，map类型中的类名
     * TODO 2 在request中，可以将其作为缩减参数数量的分类
     */
    private String group;
    /**
     * 参数的类型
     */
    private String type;
    /**
     * field size
     * 参数的范围
     * e.g.
     * 1, 1..20-->int类型的参数，范围在1到20
     * -->String类型的参数，字符串的长度
     * 2, ..20-->int最大值为20
     * -->String类型的参数，字符串的长度
     * 3, "a","b","bc"-->String类型的参数，枚举参数值
     */
    private String size;
    /**
     * 该字段是否为可选参数：主要应用于request(header与parameter)
     */
    private boolean optional;
    /**
     * field name
     * 字段的名称
     */
    private String field;
    /**
     * field default value
     * 默认值
     * TODO 未来需要能装换该值，如将双引号转换为单引号或者删除掉等等
     */
    private String defaultValue;
    /**
     * field desc
     * 接口描述，支持html语法，且在两侧会有<p></p>标签
     * format：<p>用户名<font color='red'>red</font></p>
     */
    private String description;

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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
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

    @Override
    public String toString() {
        return "FieldBean{" +
                "group='" + group + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", optional=" + optional +
                ", field='" + field + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}