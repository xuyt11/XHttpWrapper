package cn.ytxu.http_wrapper.apidocjs.parser.request.input;

import cn.ytxu.http_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.http_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.http_wrapper.apidocjs.bean.field_container.FieldContainerBean;
import cn.ytxu.http_wrapper.apidocjs.bean.field_container.field.FieldBean;
import cn.ytxu.http_wrapper.apidocjs.parser.field.FieldsParser;
import cn.ytxu.http_wrapper.model.request.RequestModel;
import cn.ytxu.http_wrapper.model.request.input.InputGroupModel;
import cn.ytxu.http_wrapper.model.request.input.InputModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RequestInputGroupParser {
    private final RequestModel request;
    private final FieldContainerBean input;

    public RequestInputGroupParser(RequestModel request) {
        this.request = request;
        ApiDataBean apiData = ApidocjsHelper.getApiData().getApiData(request);
        this.input = apiData.getParameter();
    }

    public void start() {
        if (Objects.isNull(input)) {// non header
            return;
        }

        if (input.getFields().isEmpty()) {// non header filed
            return;
        }

        List<InputGroupModel> inputGroups = createInputGroups();
        request.setInputGroups(inputGroups);
    }

    private List<InputGroupModel> createInputGroups() {
        List<InputGroupModel> inputGroups = new ArrayList<>(input.getFields().size());
        input.getFields().forEach((name, fields) -> {
            InputGroupModel inputGroup = new InputGroupModel(request, name);
            inputGroups.add(inputGroup);
            createInputByParseFields(fields, inputGroup);
        });
        return inputGroups;
    }

    private void createInputByParseFields(List<FieldBean> fields, final InputGroupModel inputGroup) {
        new FieldsParser<>(fields, new FieldsParser.Callback<InputModel>() {
            @Override
            public InputModel createFieldModel() {
                return new InputModel(inputGroup);
            }

            @Override
            public void parseFieldModelEnd(InputModel fieldModel) {
            }

            @Override
            public void parseEnd() {
            }
        }).start();
    }

}
