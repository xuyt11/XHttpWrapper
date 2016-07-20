package cn.ytxu.api_semi_auto_creater;

import cn.ytxu.api_semi_auto_creater.entity.DocumentEntity;
import cn.ytxu.util.LogUtil;

/**
 * Created by ytxu on 2016/6/16.
 */
public class NewEngine {

    public static void main(String... args) {
        long start = System.currentTimeMillis();

        Parser parser = new Parser();
        DocumentEntity document = parser.start();

        Creater creater = new Creater(document);
        creater.start();

        long end = System.currentTimeMillis();
        LogUtil.w("duration time is " + (end - start));
    }

}
