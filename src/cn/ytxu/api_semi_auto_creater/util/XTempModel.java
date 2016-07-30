package cn.ytxu.api_semi_auto_creater.util;

import cn.ytxu.api_semi_auto_creater.config.OSPlatform;
import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ytxu on 16/7/30.
 * contents 进入之后,会抽离其中的header部分,剩下temp文件的内容
 */
public class XTempModel {
    private static final String HeaderStartTag = "<header>";
    private static final String HeaderEndTag = "</header>";
    private static final String FirstLine = HeaderStartTag;

    private List<FileDir> fileDirs;
    private String fileName;
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
        new XmlParseUtil(xml, new XmlParseUtil.Callback() {
            @Override
            public void startDocument(XmlPullParser pullParser, String nodeName) {
            }

            @Override
            public void startTag(XmlPullParser pullParser, String nodeName) {
                if ("fileDir".equals(nodeName)) {
                    fileDirs = new ArrayList<>();
                } else if ("value".equals(nodeName)) {
                    FileDir fileDir = new FileDir();
                    fileDir.osName = pullParser.getAttributeValue(null, "osName");
                    fileDir.value = pullParser.getAttributeValue(null, "val");
                    fileDirs.add(fileDir);
                } else if ("fileName".equals(nodeName)) {
                    fileName = pullParser.getAttributeValue(null, "val");
                }
            }

            @Override
            public void endTag(XmlPullParser pullParser, String nodeName) {
            }
        }).start();
    }

    public String getFileDir() {
        String currOsName = OSPlatform.getCurrentOSPlatform().getOsName();
        for (FileDir fileDir : fileDirs) {
            if (currOsName.equals(fileDir.osName)) {
                return fileDir.value;
            }
        }

        throw new NullPointerException("not find target file dir");
    }

    public String getFileName() {
        return fileName;
    }

    public List<String> getContents() {
        return contents;
    }

    public static class FileDir {
        private String osName;// 对应系统的名称,必须要与OSPlatform中的一致
        private String value;// 文件夹的路径字符串
    }

}
