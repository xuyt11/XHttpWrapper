package cn.ytxu.http_wrapper.apidocjs.parser.response;

import cn.ytxu.http_wrapper.apidocjs.bean.field_container.FieldContainerBean;
import cn.ytxu.http_wrapper.apidocjs.bean.field_container.field.FieldBean;
import cn.ytxu.http_wrapper.apidocjs.parser.field.FieldsParser;
import cn.ytxu.http_wrapper.model.response.ResponseContainerModel;
import cn.ytxu.http_wrapper.model.response.field.ResponseFieldGroupModel;
import cn.ytxu.http_wrapper.model.response.field.ResponseFieldModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/12/5.
 * parse success and error response field
 */
public class ResponseFieldParser {
    private final ResponseContainerModel responseContainer;
    private final FieldContainerBean bean;

    public ResponseFieldParser(ResponseContainerModel responseContainer, FieldContainerBean bean) {
        this.responseContainer = responseContainer;
        this.bean = bean;
    }

    public List<ResponseFieldGroupModel> start() {
        return createFieldGroups();
    }

    private List<ResponseFieldGroupModel> createFieldGroups() {
        List<ResponseFieldGroupModel> responseFieldGroups = new ArrayList<>(bean.getFields().size());
        bean.getFields().forEach((name, fields) -> {
            ResponseFieldGroupModel responseFieldGroup = new ResponseFieldGroupModel(responseContainer, name);
            responseFieldGroups.add(responseFieldGroup);
            createFieldByParseFields(fields, responseFieldGroup);
        });
        return responseFieldGroups;
    }

    private void createFieldByParseFields(List<FieldBean> fields, final ResponseFieldGroupModel responseFieldGroup) {
        new FieldsParser<>(fields, new FieldsParser.Callback<ResponseFieldModel>() {
            @Override
            public ResponseFieldModel createFieldModel() {
                return new ResponseFieldModel(responseFieldGroup);
            }

            @Override
            public void parseFieldModelEnd(ResponseFieldModel fieldModel) {
            }

            @Override
            public void parseEnd() {
            }
        }).start();
    }

}
