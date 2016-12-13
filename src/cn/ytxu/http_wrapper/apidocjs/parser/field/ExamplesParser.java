package cn.ytxu.http_wrapper.apidocjs.parser.field;

import cn.ytxu.http_wrapper.apidocjs.bean.field_container.example.ExampleBean;
import cn.ytxu.http_wrapper.model.field.ExampleModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/12/5.
 */
public class ExamplesParser<E extends ExampleModel> {

    private final List<ExampleBean> exampleBeans;

    private final Callback<E> callback;

    public ExamplesParser(List<ExampleBean> beans, Callback<E> callback) {
        this.exampleBeans = beans;
        this.callback = callback;

        if (Objects.isNull(callback)) {
            throw new RuntimeException("u must setup callback...");
        }
    }

    public void start() {
        List<E> exampleModels = new ArrayList<>(exampleBeans.size());
        exampleBeans.forEach(fieldBean -> {
            E exampleModel = callback.createExampleModel();
            initExampleModel(exampleModel, fieldBean);

            exampleModels.add(exampleModel);

            callback.parseExampleModelEnd(exampleModel);
        });

        callback.setExampleModels(exampleModels);

        callback.parseEnd(exampleModels);
    }

    private void initExampleModel(E fieldExampleModel, ExampleBean bean) {
        fieldExampleModel.init(bean.getTitle(), bean.getContent(), bean.getType());
    }

    public interface Callback<E> {
        E createExampleModel();

        void parseExampleModelEnd(E exampleModel);

        void setExampleModels(List<E> exampleModel);

        void parseEnd(List<E> exampleModel);
    }

}
