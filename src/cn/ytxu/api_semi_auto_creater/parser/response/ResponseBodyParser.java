package cn.ytxu.api_semi_auto_creater.parser.response;

/**
 * Created by ytxu on 2016/8/16.
 */
public class ResponseBodyParser {


    public ResponseBodyParser() {

    }

    public void start() {
        //1 解析出body中json格式数据的所有字段；
        //2 对request中defineds进行辨析，设置到response字段上
        //3 获取到相同版本上该Section上的所有的响应体entity，进行遍历判断，
        // 若为一致的（子字段名称与属性都一样的话），则使用相同的entity，不需要重新输出一个新的entity文件



    }

}
