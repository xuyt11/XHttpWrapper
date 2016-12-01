package cn.ytxu.test;

import cn.ytxu.util.LogUtil;
import cn.ytxu.xhttp_wrapper.config.ConfigBean;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/12/1.
 */
public class ConfigBeanTest {

    @Test
    public void test() {
        InputStream in = ConfigBeanTest.class.getClassLoader().getResourceAsStream("NewChama-android.json");
        try {
            ConfigBean object = JSON.parseObject(in, ConfigBean.class);
            LogUtil.i("config content:" + object.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
