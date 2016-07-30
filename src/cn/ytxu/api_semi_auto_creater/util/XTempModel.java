package cn.ytxu.api_semi_auto_creater.util;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ytxu on 16/7/30.
 */
public class XTempModel {
    private static final String HeaderStartTag = "<header>";
    private static final String HeaderEndTag = "</header>";
    private static final String FirstLine = HeaderStartTag;

    private List<String> contents;

    public XTempModel(List<String> contents) {
        this.contents = contents;

        if (!FirstLine.equals(contents.get(0).trim())) {
            throw new IllegalStateException("the first line must header tag");
        }

        init();
    }

    private void init() {
        StringBuilder headerBuilder = getHeaderAndRemoveItInContent();
        getHeader(headerBuilder);
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
        new XmlParseUtil().parseInputStream(xml, new XmlParseUtil.Callback() {
            @Override
            public void startDocument(XmlPullParser pullParser, String nodeName) {

            }

            @Override
            public void startTag(XmlPullParser pullParser, String nodeName) {

            }

            @Override
            public void endTag(XmlPullParser pullParser, String nodeName) {

            }
        });
    }

    public List<String> getContents() {
        return contents;
    }

}
