package cn.ytxu.api_semi_auto_creater.xtemp_parser;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/8/28.
 * contents 进入之后,会抽离其中的header部分,剩下temp文件的内容，最后返回XTempModel
 */
public class XTempParser {
    private static final String HeaderStartTag = "<header>";
    private static final String HeaderEndTag = "</header>";
    private static final String FirstLine = HeaderStartTag;

    private List<String> contents;
    private XTempModel model = new XTempModel();

    public XTempParser(List<String> contents) {
        this.contents = contents;
    }

    public XTempModel start() {
        if (!FirstLine.equals(contents.get(0).trim())) {
            throw new IllegalStateException("the first line must header tag");
        }

        StringBuilder headerBuilder = getHeaderAndRemoveItInContent();
        model.setContents(contents);
        getHeader(headerBuilder);
        return model;
    }

    private StringBuilder getHeaderAndRemoveItInContent() {
        StringBuilder headerBuilder = new StringBuilder();
        Iterator<String> iterator = contents.iterator();

        while (iterator.hasNext()) {
            String content = iterator.next();
            headerBuilder.append(content);
            iterator.remove();

            if (HeaderEndTag.equals(content)) {
                break;
            }
        }
        return headerBuilder;
    }

    private void getHeader(StringBuilder headerBuilder) {
        InputStream xml = new ByteArrayInputStream(headerBuilder.toString().getBytes());
        new XmlParseUtil(xml, new XmlParseUtil.Callback() {
            @Override
            public void startDocument(XmlPullParser pullParser, String nodeName) {
            }

            @Override
            public void startTag(XmlPullParser pullParser, String nodeName) {
                if ("fileDir".equals(nodeName)) {
                    model.setFileDirs(new ArrayList<>());
                } else if ("value".equals(nodeName)) {
                    String osName = pullParser.getAttributeValue(null, "osName");
                    String value = pullParser.getAttributeValue(null, "val");
                    model.addFileDir(new XTempModel.FileDir(osName, value));
                } else if ("fileName".equals(nodeName)) {
                    model.setFileName(pullParser.getAttributeValue(null, "val"));
                }
            }

            @Override
            public void endTag(XmlPullParser pullParser, String nodeName) {
            }
        }).start();
    }

}
