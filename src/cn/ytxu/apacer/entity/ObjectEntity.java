package cn.ytxu.apacer.entity;

import java.util.List;

/**
 * 输入输出对象的实体类
 * 2016-03-31
 */
public class ObjectEntity {
    private String objName;// 对象名称
    private ObjType objType;// 对象类型
    private String objDesc;// 对象描述信息
    private List<FieldEntity> fields;// 对象包含的字段


    public static enum ObjType {
        Input,// 输入参数中的对象
        Output// 输出参数的对象
    }

}
