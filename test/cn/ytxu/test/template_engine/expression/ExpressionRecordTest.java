package cn.ytxu.test.template_engine.expression;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.template.expression.Content2ExpressionRecordConverter;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.parser.statement.StatementRecord;
import cn.ytxu.test.StringBufferTest;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2016/12/21.
 */
public class ExpressionRecordTest {

//    public static final String ExpressionFilePath = "I:\\ytxuStudio\\ApiSemiAutoCreater\\test\\cn\\ytxu\\test\\template_engine\\expression\\expression_test.xhwt";
    public static final String ExpressionFilePath = "/Users/newchama/Documents/ytxu/workspace/ApiSemiAutoCreater/test/cn/ytxu/test/template_engine/expression/expression_test.xhwt";
    @Test
    public void test() {
        List<String> fileData = getContents();
        List<ExpressionRecord> records = new Content2ExpressionRecordConverter.Top(fileData).start();
        ExpressionRecord.parseRecords(records);
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


    @Test
    public void testExpressionAndStatementSpeed() {
        CountDownLatch mCountDownLatch = new CountDownLatch(2);
        int cycleCount = 40000;
        List<String> fileData = getContents();
        new Thread(new Runnable() {
            @Override
            public void run() {
                testStatement(fileData, cycleCount);
                mCountDownLatch.countDown();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                testExpression(fileData, cycleCount);
                mCountDownLatch.countDown();
            }
        }).start();

        try {
            mCountDownLatch.await();//等待所有子线程执行完
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testStatement(List<String> fileData, int cycleCount) {
//        LogUtil.i("testStatement:");
//        StringBuffer logBuffer = new StringBuffer(cycleCount * 4);
//        logBuffer.append("testStatement" + "\n");
        long startT = getCurrTime();
        long duration = 0;
        for (int i = 0; i < cycleCount; i++) {
            long middle1T = getCurrTime();

            List<StatementRecord> records2 = StatementRecord.getRecords(fileData);
//            StatementRecord.parseRecords(records2);

            long middle2T = getCurrTime();
            long currDuration = middle2T - middle1T;
//            logBuffer.append(currDuration + "\t");
            duration += currDuration;
        }
//        logBuffer.append("\n");
//        LogUtil.i(logBuffer.toString());
        long endT = getCurrTime();
        LogUtil.i("testStatement end:" + (endT - startT) + ", real duration:" + duration);
    }

    public void testExpression(List<String> fileData, int cycleCount) {
//        LogUtil.i("testExpression:");
//        StringBuffer logBuffer = new StringBuffer(cycleCount * 4);
//        logBuffer.append("testExpression" + "\n");
        long startT = getCurrTime();
        long duration = 0;
        for (int i = 0; i < cycleCount; i++) {
            long middle1T = getCurrTime();

            List<ExpressionRecord> records = new Content2ExpressionRecordConverter.Top(fileData).start();
//            ExpressionRecord.parseRecords(records);

            long middle2T = getCurrTime();
            long currDuration = middle2T - middle1T;
//            logBuffer.append(currDuration + "\t");
            duration += currDuration;
        }
//        logBuffer.append("\n");
//        LogUtil.i(logBuffer.toString());
        long endT = getCurrTime();
        LogUtil.i("testExpression end:" + (endT - startT) + ", real duration:" + duration);
    }

    private long getCurrTime() {
        return System.currentTimeMillis();
//        return System.nanoTime();
    }
}
