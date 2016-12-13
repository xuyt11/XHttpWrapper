package cn.ytxu.test;

import cn.ytxu.http_wrapper.apidocjs.bean.api_data.ApiDataBean;
import com.alibaba.fastjson.JSONArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/11.
 */
public class ApidocDataTest {

    public static void main(String... args) throws IOException {
        String jsonArrayText = readerJson("E:\\NewChama\\apidoc_xuyt\\api_data.json");
        List<ApiDataBean> list = JSONArray.parseArray(jsonArrayText, ApiDataBean.class);
        System.out.println(list.size());
    }

    //将file转化成string
    private static String readerJson(String filePath) throws IOException {
        //对一串字符进行操作
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        //缓冲区使用完必须关掉
        reader.close();
        return fileData.toString();
    }

}
