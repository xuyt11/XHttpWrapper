package cn.ytxu.test;

import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by ytxu on 16/12/2.
 */
public class RxjavaTest {

    public static void main(String... args) {
        new RxjavaTest().test();
    }

    @Test
    public void test() {
//        Observable.just(1, 2, 3, 4, 5, 6, 7).subscribeOn().map(index -> index + "").map(new Func1<String, Boolean>() {
//            @Override
//            public Boolean call(String s) {
//                return "2".equals(s);
//            }
//        }).subscribe(new Action1<Boolean>() {
//            @Override
//            public void call(Boolean aBoolean) {
//                LogUtil.i(aBoolean + "");
//            }
//        });

    }
}
