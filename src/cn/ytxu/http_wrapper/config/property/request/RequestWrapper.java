package cn.ytxu.http_wrapper.config.property.request;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.common.util.TextUtil;
import cn.ytxu.http_wrapper.config.property.request.optional_request_method.OptionalRequestMethodBean;
import cn.ytxu.http_wrapper.model.request.input.InputGroupModel;

import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/9/5.
 */
public class RequestWrapper {

    private static RequestWrapper instance;

    private RequestBean requestBean;

    public static RequestWrapper getInstance() {
        return instance;
    }

    public static void load(RequestBean request) {
        LogUtil.i(RequestWrapper.class, "load request property start...");
        instance = new RequestWrapper(request);
        LogUtil.i(RequestWrapper.class, "load request property success...");
    }

    private RequestWrapper(RequestBean request) {
        this.requestBean = request;

        if (Objects.isNull(request.getRESTful())) {
            throw new IllegalArgumentException("u must setup RESTful property...");
        }
        if (TextUtil.isBlank(request.getRESTful().getReplaceString())) {
            throw new IllegalArgumentException("the RESTful-->replaceString property is null, u must setup...");
        }

        if (Objects.isNull(request.getOptional_request_method())) {
            throw new IllegalArgumentException("u don`t setup optional request method property with null...");
        }
    }

    public String getReplaceString() {
        return requestBean.getRESTful().getReplaceString();
    }

    public List<String> getMultis() {
        return requestBean.getRESTful().getMultiReplaces();
    }

    public List<DateReplaceBean> getDateReplaces() {
        return requestBean.getRESTful().getDateReplaces();
    }


    /**
     * 是否需要生成可选的请求方法(或叫缩略请求方法)；
     * 即：请求参数分组归类；
     * detail:
     * 1、若没有请求参数，不需要生成；
     * 2、若有超过两个请求参数组，则需要生成；
     * 3、只有一个请求组，判断组中参数的个数，若少于3个则不需要生成
     */
    public boolean needGenerateOptionalRequestMethod(List<InputGroupModel> inputGroups) {
        OptionalRequestMethodBean optionalRequestMethod = requestBean.getOptional_request_method();

        if (!optionalRequestMethod.isNeed_generate()) {
            return false;
        }

        switch (inputGroups.size()) {
            case 0: {
                return false;
            }
            case 1: {
                int min = optionalRequestMethod.getMin_number_of_input_params_in_one_input_group();
                return inputGroups.get(0).getInputs().size() > min;
            }
            default: {
                return true;
            }
        }
    }
}
