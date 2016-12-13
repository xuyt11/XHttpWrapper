package cn.ytxu.http_wrapper.apidocjs.parser.field;

import cn.ytxu.http_wrapper.apidocjs.bean.field_container.field.FieldBean;
import cn.ytxu.http_wrapper.model.field.FieldModel;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/9/21.
 */
public class FieldsParser<F extends FieldModel> {

    private final List<FieldBean> fieldBeans;

    private final Callback<F> callback;

    public FieldsParser(List<FieldBean> beans, Callback<F> callback) {
        this.fieldBeans = beans;
        this.callback = callback;

        if (Objects.isNull(callback)) {
            throw new RuntimeException("u must setup callback...");
        }
    }

    public void start() {
        if (fieldBeans.isEmpty()) {
            return;
        }

        fieldBeans.forEach(fieldBean -> {
            F fieldModel = callback.createFieldModel();

            new FieldParser<>(fieldModel, fieldBean).start();

            callback.parseFieldModelEnd(fieldModel);
        });

        callback.parseEnd();
    }

    public interface Callback<F> {
        F createFieldModel();

        void parseFieldModelEnd(F fieldModel);

        void parseEnd();
    }

}
