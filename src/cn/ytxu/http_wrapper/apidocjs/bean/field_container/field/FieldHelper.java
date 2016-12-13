package cn.ytxu.http_wrapper.apidocjs.bean.field_container.field;

import cn.ytxu.http_wrapper.model.status_code.StatusCodeGroupModel;
import cn.ytxu.http_wrapper.model.status_code.StatusCodeModel;

/**
 * Created by ytxu on 2016/12/3.
 */
public class FieldHelper {

    private static FieldHelper instance;

    public static void reload() {
        if (instance != null) {
        }
        instance = new FieldHelper();
    }

    private FieldHelper() {
    }

    public static FieldHelper getInstance() {
        return instance;
    }

    public StatusCodeModel createStatusCode(StatusCodeGroupModel statusCodeGroup,
                                            String group, String name, int number, String desc) {
        return new StatusCodeModel(statusCodeGroup, group, name, number, desc);
    }
}
