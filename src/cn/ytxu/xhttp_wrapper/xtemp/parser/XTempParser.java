package cn.ytxu.xhttp_wrapper.xtemp.parser;

import cn.ytxu.xhttp_wrapper.xtemp.parser.model.XTempFileModel;
import cn.ytxu.xhttp_wrapper.xtemp.parser.model.XTempModel;
import com.alibaba.fastjson.JSON;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/8/28.
 * contents 进入之后,会抽离其中的header部分,剩下temp文件的内容，最后返回XTempModel
 */
public class XTempParser {

    private final List<String> contents;
    private final XTempModel model = new XTempModel();

    public XTempParser(List<String> contents) {
        this.contents = contents;
    }

    public XTempModel start() {
        judgeFirstLine();

        StringBuilder headerBuilder = getHeaderAndRemoveItInContent();
        XTempFileModel xTempFile = JSON.parseObject(headerBuilder.toString(), XTempFileModel.class);
        model.setFile(xTempFile);

        model.setContents(contents);
        return model;
    }

    private void judgeFirstLine() {
        if (!XTempModel.HeaderStartTag.equals(contents.get(0).trim())) {
            throw new TheFirstLineMustBeHeaderStartTagLineException();
        }
    }

    private static final class TheFirstLineMustBeHeaderStartTagLineException extends RuntimeException {
    }

    private StringBuilder getHeaderAndRemoveItInContent() {
        contents.remove(0);
        StringBuilder headerBuilder = new StringBuilder();
        Iterator<String> iterator = contents.iterator();
        while (iterator.hasNext()) {
            String content = iterator.next();

            if (XTempModel.HeaderEndTag.equals(content.trim())) {
                iterator.remove();
                break;
            }

            headerBuilder.append(content);
            iterator.remove();
        }
        return headerBuilder;
    }

}
