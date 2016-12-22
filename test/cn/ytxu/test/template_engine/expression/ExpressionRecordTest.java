package cn.ytxu.test.template_engine.expression;

import cn.ytxu.http_wrapper.template.expression.Content2ExpressionRecordConverter;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/21.
 */
public class ExpressionRecordTest {

    public static final String ExpressionFilePath = "I:\\ytxuStudio\\ApiSemiAutoCreater\\test\\cn\\ytxu\\test\\template_engine\\expression\\expression_test.xhwt";

    @Test
    public void test() {
        List<String> fileData = getContents();
        Content2ExpressionRecordConverter.getTop(fileData, new Content2ExpressionRecordConverter.Callback() {
            @Override
            public void middleTagLine(String content, List<ExpressionRecord> records) {
            }

            @Override
            public void endTagLine(List<ExpressionRecord> records) {
                ExpressionRecord.parseRecords(records);
            }
        }).start();
    }

    private List<String> getContents() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(ExpressionFilePath));
            return getContents(reader);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<String> getContents(BufferedReader reader) throws IOException {
        List<String> contents = new ArrayList<>();
        String strLine;
        while (null != (strLine = reader.readLine())) {
            contents.add(strLine);
        }
        return contents;
    }

}
