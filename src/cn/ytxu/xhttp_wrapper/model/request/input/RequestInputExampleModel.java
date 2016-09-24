package cn.ytxu.xhttp_wrapper.model.request.input;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ExampleBean;
import cn.ytxu.xhttp_wrapper.model.field.FieldExampleModel;

/**
 * Created by ytxu on 2016/9/23.
 * request : header, parameter<br>
 * title: param`s name<br>
 * content: param format text<br>
 * type: param`s type<br>
 * e.g.<br>
 * 1、type:json; content:{"name":"isAuthed"}<br>
 * 2、type:array; content:1,2,3,5-->以逗号分隔数组，并以string传输<br>
 */
public class RequestInputExampleModel extends FieldExampleModel<RequestInputModel> {

    public RequestInputExampleModel(RequestInputModel higherLevel, ExampleBean element) {
        super(higherLevel, element);
    }

}
