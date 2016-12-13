package cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json;

import cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.OutputParamParser;
import cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.defined.HandleSameDataTypeOutputUtil;
import cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.defined.SetupDefined2OutputUtil;
import cn.ytxu.http_wrapper.model.response.ResponseModel;

/**
 * Created by ytxu on 2016/10/11.
 * 响应报文解析器
 */
public class JsonResponseMessageParser {
    private ResponseModel response;

    public JsonResponseMessageParser(ResponseModel response) {
        this.response = response;
    }

    public void start() {
        boolean notNeedParseAgain = new ResponseBodyParser(response).start();
        if (notNeedParseAgain) {
            return;
        }

        //1 解析出body中json格式数据的所有字段；
        new OutputParamParser(response).start();
        //2 对request中defineds进行辨析，设置到response字段上
        new SetupDefined2OutputUtil(response).start();
        //3 子孙节点或兄弟节点中类型(DataType)相同，判断与处理；
        new HandleSameDataTypeOutputUtil(response).start();
    }

}
