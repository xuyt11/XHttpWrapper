package cn.ytxu.test;

import com.google.gson.Gson;

/**
 * Created by newchama on 16/4/11.
 */
public class GsonTest {

    private static final String JsonStr = "{\n" +
            "    \"status_code\": 1230,\n" +
            "    \"message\": \"xuyt_message\",\n" +
            "    \"data\": {\n" +
            "        \"first_name\": \"test\",\n" +
            "        \"weibo_url\": \"weibo\",\n" +
            "        \"weixin_number\": \"wechat\",\n" +
            "        \"summary\": \"test\",\n" +
            "        \"member_tradehistory\": [\n" +
            "            {\n" +
            "                \"trade_date\": \"2015-11-12\",\n" +
            "                \"project_type\": 5,\n" +
            "                \"project_stage\": 2,\n" +
            "                \"project_name\": \"test\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "}";

    public static void main(String[] args) {

        Gson gson = new Gson();

        AccountSellerVerify aa = gson.fromJson(JsonStr, AccountSellerVerify.class);
        System.out.println(aa.toString());
    }


}
