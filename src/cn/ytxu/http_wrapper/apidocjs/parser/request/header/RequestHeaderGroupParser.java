package cn.ytxu.http_wrapper.apidocjs.parser.request.header;

import cn.ytxu.http_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.http_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.http_wrapper.apidocjs.bean.field_container.FieldContainerBean;
import cn.ytxu.http_wrapper.apidocjs.bean.field_container.field.FieldBean;
import cn.ytxu.http_wrapper.apidocjs.parser.field.FieldsParser;
import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.model.request.RequestModel;
import cn.ytxu.http_wrapper.model.request.header.HeaderGroupModel;
import cn.ytxu.http_wrapper.model.request.header.HeaderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/12/04.
 */
public class RequestHeaderGroupParser {
    private final RequestModel request;
    private final FieldContainerBean header;

    public RequestHeaderGroupParser(RequestModel request) {
        this.request = request;
        ApiDataBean apiData = ApidocjsHelper.getApiData().getApiData(request);
        this.header = apiData.getHeader();
    }

    public void start() {
        if (Objects.isNull(header)) {// non header
            return;
        }

        if (header.getFields().isEmpty()) {// non header filed
            return;
        }

        List<HeaderGroupModel> headerGroups = createHeaderGroups();
        request.setHeaderGroups(headerGroups);
    }

    private List<HeaderGroupModel> createHeaderGroups() {
        List<HeaderGroupModel> headerGroups = new ArrayList<>(header.getFields().size());
        header.getFields().forEach((name, fields) -> {
            HeaderGroupModel headerGroup = new HeaderGroupModel(request, name);
            headerGroups.add(headerGroup);
            createHeaderByParseFields(fields, headerGroup);
        });
        return headerGroups;
    }

    private void createHeaderByParseFields(List<FieldBean> fields, final HeaderGroupModel headerGroup) {
        new FieldsParser<>(fields, new FieldsParser.Callback<HeaderModel>() {
            @Override
            public HeaderModel createFieldModel() {
                return new HeaderModel(headerGroup);
            }

            @Override
            public void parseFieldModelEnd(HeaderModel fieldModel) {
                setFilterTag2Header(fieldModel);
            }

            @Override
            public void parseEnd() {
            }
        }).start();
    }

    private void setFilterTag2Header(HeaderModel header) {
        boolean isFilterParam = ConfigWrapper.getFilter().hasThisHeaderInFilterHeaders(header.getName());
        if (isFilterParam) {
            header.setFilterTag(true);
        }
    }

}
