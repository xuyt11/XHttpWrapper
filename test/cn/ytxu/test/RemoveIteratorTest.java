package cn.ytxu.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ytxu on 16/7/30.
 */
public class RemoveIteratorTest {


    public static void main(String... args) {
        List<String> datas = new ArrayList<>(10);

        for (int i=0, size=10; i<size; i++) {
            datas.add("data:" + i);
        }

        Iterator<String> iterator = datas.iterator();
        while (iterator.hasNext()) {
            String data = iterator.next();
            System.out.println("remove " + data);
            iterator.remove();
        }
    }

}
