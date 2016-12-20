package cn.ytxu.http_wrapper.config.property.request.optional_request_method;

/**
 * Created by ytxu on 16/12/14.
 * 是否需要生成可选的请求方法(或叫缩略请求方法)；
 * 即：请求参数分组归类；
 * detail:
 * 1、若没有请求参数，不需要生成；
 * 2、若有超过两个请求参数组，则需要生成；
 * 3、只有一个请求组，判断组中参数的个数，若少于3个则不需要生成
 * <p>
 * tip: 若开启了生成缩略请求方法的功能，则该请求组中，所有作用的请求，都需要生成对应的RequestParam文件；
 */
public class OptionalRequestMethodBean {
    public static final OptionalRequestMethodBean DEFAULT = new OptionalRequestMethodBean();

    private boolean need_generate = true;// 是否需要生成可选的请求方法(或叫缩略请求方法)；
    private int min_number_of_input_params_in_one_input_group = 3;// 若只有一个请求组，组中参数的个数，若少于3个则不需要生成


    public boolean isNeed_generate() {
        return need_generate;
    }

    public void setNeed_generate(boolean need_generate) {
        this.need_generate = need_generate;
    }

    public int getMin_number_of_input_params_in_one_input_group() {
        return min_number_of_input_params_in_one_input_group;
    }

    public void setMin_number_of_input_params_in_one_input_group(int min_number_of_input_params_in_one_input_group) {
        this.min_number_of_input_params_in_one_input_group = min_number_of_input_params_in_one_input_group;
    }
}
