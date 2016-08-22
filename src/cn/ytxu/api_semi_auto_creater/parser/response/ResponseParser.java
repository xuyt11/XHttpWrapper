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
        //2.1 子孙节点或兄弟节点中类型(DataType)相同，判断与处理；
        new HandleSameDataTypeOutputUtil(response).start();
        //3 获取到相同版本上该Section上的所有的响应体entity，进行遍历判断，
        // 若为一致的（子字段名称与属性都一样的话），则使用相同的entity，不需要重新输出一个新的entity文件


    }
}
