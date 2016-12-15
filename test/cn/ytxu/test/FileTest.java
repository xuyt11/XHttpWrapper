package cn.ytxu.test;

import cn.ytxu.http_wrapper.common.util.FileUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/13.
 */
public class FileTest {
    private static final String filePath = "I:/ytxuStudio/ApiSemiAutoCreater/xhwt/ncm_non_version/x-http-wrapper.json";
    private static final String filePath2 = "../ncm_a_n-httpapi.xhwt";
    private static final String filePath3 = "/ncm_a_n-httpapi.xhwt";
    private static final String filePath4 = "ncm_a_n-httpapi.xhwt";


    @Test
    public void testAbsolute() throws IOException {
        File file = new File(filePath);
        Assert.assertEquals(true, file.isAbsolute());
        File file2 = new File(filePath2);
        Assert.assertEquals(false, file2.isAbsolute());
        File file3 = new File(filePath3);
        Assert.assertEquals(false, file3.isAbsolute());
        File file4 = new File(filePath4);
        Assert.assertEquals(false, file4.isAbsolute());
    }

    @Test
    public void testParent() throws IOException {
        File file = new File(filePath);
        System.out.println("dir:" + file.getParent());
    }

    @Test
    public void testPathSynthesis() throws IOException {
        File file = new File(filePath);
        System.out.println("dir:" + file.getParent());
        File file2 = new File(file.getParentFile(), filePath2);
        System.out.println("file2:" + file2.getPath());
        System.out.println("file12:" + file2.getAbsolutePath());
        File file3 = new File(file.getParentFile(), filePath3);
        System.out.println("file3:" + file3.getPath());
        System.out.println("file12:" + file3.getAbsolutePath());
        File file4 = new File(file.getParentFile(), filePath4);
        System.out.println("file4:" + file4.getPath());
        System.out.println("file12:" + file4.getAbsolutePath());
    }

    @Test
    public void testPath() throws IOException {
        File file = new File(filePath);
        System.out.println("dir:" + file.getParent());
        File file2 = new File(filePath2);
        System.out.println("file12:" + file2.getPath());
        System.out.println("file12:" + file2.getAbsolutePath());
        File file3 = new File(filePath3);
        System.out.println("file13:" + file3.getPath());
        System.out.println("file13:" + file3.getAbsolutePath());
        File file4 = new File(filePath4);
        System.out.println("file14:" + file4.getPath());
        System.out.println("file14:" + file4.getAbsolutePath());
        File f = new File(".");
        System.out.println("file15:" + f.getPath());
        System.out.println("file15:" + f.getAbsolutePath());
    }
}

