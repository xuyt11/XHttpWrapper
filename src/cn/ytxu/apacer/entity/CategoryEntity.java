package cn.ytxu.apacer.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * API 分类的实体类
 * @author ytxu
 * @date 2016-3-18 17:35:11
 *
 */
public class CategoryEntity extends BaseEntity<ApiEnitity> {
    private String name;// 类别的名称
    private List<MethodEntity> methods;// 该分类中所有的方法

    // 所有被使用了的子孙Output:进行比对,
    // 1 若在其中有与当前的output equal的,则不能输出,否则需要输出,并添加近该数组中.
    // 2 若 equal不同,但是名称一样,则将按照规则生成一个从根output的名称
    private List<OutputParamEntity> subUsedOutputs = new ArrayList<>();

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<MethodEntity> getMethods() {
        return methods;
    }
    public void setMethods(List<MethodEntity> methods) {
        this.methods = methods;
    }

    public List<OutputParamEntity> getSubUsedOutputs() {
        return subUsedOutputs;
    }

    @Override
    public String toString() {
        return "CategoryEntity [name=" + name + ", methods=" + methods + "]";
    }


    public void setDoubleLinkedRefrence() {
        setHigherLevel(methods, this);

        if (methods != null && methods.size() > 0) {
            for (MethodEntity method : methods) {
                method.setDoubleLinkedRefrence();
            }
        }
    }
}