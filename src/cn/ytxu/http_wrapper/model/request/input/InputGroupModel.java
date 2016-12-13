package cn.ytxu.http_wrapper.model.request.input;

import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.model.request.RequestModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/9/24.
 */
public class InputGroupModel extends BaseModel<RequestModel> implements Comparable<InputGroupModel> {
    private final String name;
    private List<InputModel> inputs = Collections.EMPTY_LIST;

    public InputGroupModel(RequestModel higherLevel, String name) {
        super(higherLevel);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<InputModel> getInputs() {
        return inputs;
    }

    public void addInput(InputModel input) {
        if (inputs == Collections.EMPTY_LIST) {
            inputs = new ArrayList<>(10);
        }
        inputs.add(input);
    }

    @Override
    public int compareTo(InputGroupModel o) {
        return this.name.compareToIgnoreCase(o.name);
    }
}
