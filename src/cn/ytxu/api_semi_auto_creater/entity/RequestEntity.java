package cn.ytxu.api_semi_auto_creater.entity;

import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by ytxu on 2016/6/16.
 * 方法描述的实体类
 */
public class RequestEntity extends BaseEntity<SectionEntity> {
    private String descrption;// 方法的中文名称（描述）
    private String versionCode;// 该方法的版本号
    private String methodName;// 该方法的方法名称：驼峰法命名
    private String methodType;// 请求类型:post、get、patch...

    // TODO 是否需要将url放到RESTfulUrlEntity中，作为一个参数？
    private String url;// 方法的相对路径，起始位置必须为/
    private RESTfulUrlEntity restfulUrl;// url RESTful风格的解析对象

    private List<DefinedParameterEntity> definedParams;// 已定义了的参数

    private List<InputParamEntity> headers;// 请求头字段
    private List<InputParamEntity> inputParams;// 输入字段

    private List<ResponseEntity> responses;// 请求响应列表

    public RequestEntity(SectionEntity higherLevel, Element element) {
        super(higherLevel, element);
    }
}
