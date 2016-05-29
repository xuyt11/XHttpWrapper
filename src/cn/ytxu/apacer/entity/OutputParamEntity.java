package cn.ytxu.apacer.entity;

import cn.ytxu.apacer.fileCreater.newchama.resultEntity.v6.ResponseCategoryCreater;
import cn.ytxu.util.CamelCaseUtils;

import java.util.List;

/**
 * 输出数据中的参数<br>
 * 1 先判断是否为对象isObject;<br>
 * 2 在判断是否为数组isArray,并且若是数组类型的话,还要判断是什么类型(type?)的数组(Number,Boolean,String,Object);<br>
 *     2.1 若是对象类型的数组,则是一个类;<br>
 * 3 那就是一些基本类型与String了;
 */
public class OutputParamEntity extends BaseEntity<ResponseEntity> {
    private String name;
    private String type;// TODO replace with OutputType
    private String desc;
    private boolean isObject = false;
    private boolean isArray = false;

    private OutputParamEntity parent;// 输出参数的父级
    private List<OutputParamEntity> subs;// 若是对象或数组类型,则有字段
    private String createdClassName;// 生成类文件的名称:name相同的output,但是subs不一样的,会生成从根节点开始的一个类名
    private String dataClassName;// 若该字段有值,代表为该字段是response的data字段,需要使用请求方法的名称来进行类命名

    private ResponseEntity response;

    public OutputParamEntity(String name, String type, String desc) {
        this.name = name;
        this.type = type;
        this.desc = desc;
    }


    public void setObject(boolean object) {
        isObject = object;
    }

    public void setArray(boolean array) {
        isArray = array;
    }

    public boolean isObject() {
        return isObject;
    }

    public boolean isArray() {
        return isArray;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<OutputParamEntity> getSubs() {
        return subs;
    }

    public void setSubs(List<OutputParamEntity> subs) {
        this.subs = subs;
        if (null != this.subs && subs.size() > 0) {
            for (OutputParamEntity sub : subs) {
                sub.parent = this;
            }
        }
    }

    public String getCreatedClassName() {
        return createdClassName;
    }

    public void setCreatedClassName(String createdClassName) {
        this.createdClassName = createdClassName;
    }

    public OutputParamEntity getParent() {
        return parent;
    }

    public void setDataClassName(String dataClassName) {
        this.dataClassName = dataClassName;
    }

    public String getDataClassName() {
        return dataClassName;
    }

    public ResponseEntity getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "OutputParamEntity{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", isObject=" + isObject +
                ", isArray=" + isArray +
                ", subs=" + subs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OutputParamEntity that = (OutputParamEntity) o;

        if (isObject != that.isObject) return false;
        if (isArray != that.isArray) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return subs != null ? subs.equals(that.subs) : that.subs == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (isObject ? 1 : 0);
        result = 31 * result + (isArray ? 1 : 0);
        result = 31 * result + (subs != null ? subs.hashCode() : 0);
        return result;
    }

    /** 在outputs中,是否有和target名字相同的成员 */
    public static boolean hasSameName(List<OutputParamEntity> outputs, OutputParamEntity target) {
        if (null == outputs || outputs.size() <= 0) {
            return false;
        }

        for (OutputParamEntity output : outputs) {
            if (target.getName().equals(output.getName())) {
                return true;
            }
        }
        return false;
    }

    /** 在outputs中,是否有和targetsubs相同的成员:<br>
     *  基于代码逻辑顺序,target的parent一定会在outputs中; 若target的name与其parent的name一样,则认为是相同的对象*/
    public static boolean isSameObject(List<OutputParamEntity> outputs, OutputParamEntity target) {
        if (null == outputs || outputs.size() <= 0) {
            return false;
        }

        for (OutputParamEntity output : outputs) {
            // 基于代码逻辑顺序,target的parent一定会在outputs中; 若target的name与其parent的name一样,则认为是相同的对象
            if (output == target.getParent() && target.getName().equals(output.getName())) {
                return true;
            }
        }

        return false;
    }


    public String rename4Class() {
        if (null == parent) {// data
            return dataClassName;
        }

        return CamelCaseUtils.toCapitalizeCamelCase(name) + "4" + parent.rename4Class();
    }

    // TODO 未来要用heighLevel替换
    @Deprecated
    public static void setResponse(List<OutputParamEntity> outputs, ResponseEntity response) {
        if (outputs == null || outputs.size() <= 0) {
            return;
        }

        for (OutputParamEntity output : outputs) {
            output.response = response;
        }
    }


}
