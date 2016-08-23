package cn.ytxu.api_semi_auto_creater.parser.response;

import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import cn.ytxu.api_semi_auto_creater.parser.response.output.defined.HandleSameDataTypeOutputUtil;
import cn.ytxu.api_semi_auto_creater.parser.response.output.defined.SetupDefined2OutputUtil;

/**
 * Created by ytxu on 2016/8/16.
 */
public class ResponseParser {

    private ResponseModel response;

    public ResponseParser(ResponseModel response) {
        this.response = response;
    }

    public void start() {
        //1 解析出body中json格式数据的所有字段；
        new ResponseBodyParser(response).start();
        //2 对request中defineds进行辨析，设置到response字段上
        new SetupDefined2OutputUtil(response).start();
        //3 子孙节点或兄弟节点中类型(DataType)相同，判断与处理；
        new HandleSameDataTypeOutputUtil(response).start();
    }
}
