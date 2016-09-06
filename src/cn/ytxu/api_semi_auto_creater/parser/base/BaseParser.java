package cn.ytxu.api_semi_auto_creater.parser.base;

import cn.ytxu.api_semi_auto_creater.parser.base.convert.Converter;
import cn.ytxu.api_semi_auto_creater.parser.base.entity.DocEntity;
import cn.ytxu.api_semi_auto_creater.parser.base.parser.DocParser;
import cn.ytxu.api_semi_auto_creater.model.base.DocModel;

/**
 * Created by ytxu on 2016/7/20.
 * 基础解析器：解析出直到request name,version为止
 */
public class BaseParser {

    public BaseParser() {
    }

    public DocModel start() {
        DocEntity doc = new DocParser().start();
        DocModel docModel = new Converter(doc).invoke();
        return docModel;
    }

}
