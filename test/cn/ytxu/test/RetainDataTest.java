package cn.ytxu.test;

import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.util.LogUtil;

/**
 * Created by newchama on 16/4/18.
 */
public class RetainDataTest {

    public static void main(String... args) {


        RetainEntity retain = RetainEntity.getRetainEntity("RetainDataTest.txt", "/Users/newchama/Desktop/NewChama-Data/");
        if (null == retain) {
            // print retain data is null
            return;
        }

        LogUtil.e("import:" + retain.getImportData().toString());
        LogUtil.e("field:" + retain.getFieldData().toString());
        LogUtil.e("method:" + retain.getMethodData().toString());
        LogUtil.e("other:" + retain.getOtherData().toString());

    }


}
