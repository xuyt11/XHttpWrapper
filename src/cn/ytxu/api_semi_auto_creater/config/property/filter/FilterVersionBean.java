package cn.ytxu.api_semi_auto_creater.config.property.filter;

import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 16/9/8.
 * 过滤版本与过滤该版本下的分类<br>
 * use_output_sections:是否对分类进行输出过滤；若为false，则下面的output_sections参数就失效<br>
 * 应用于API接口以及实体类<br>
 */
public class FilterVersionBean {
    /**
     * 输出的版本名称
     */
    private String output_version_name = "";
    /**
     * 是否对分类进行输出过滤
     */
    private boolean use_output_sections = false;
    /**
     * 输出的版本下,需要输出的分类名称的数组
     */
    private List<String> output_sections = Collections.EMPTY_LIST;

    public String getOutput_version_name() {
        return output_version_name;
    }

    public void setOutput_version_name(String output_version_name) {
        this.output_version_name = output_version_name;
    }

    public boolean isUse_output_sections() {
        return use_output_sections;
    }

    public void setUse_output_sections(boolean use_output_sections) {
        this.use_output_sections = use_output_sections;
    }

    public List<String> getOutput_sections() {
        return output_sections;
    }

    public void setOutput_sections(List<String> output_sections) {
        this.output_sections = output_sections;
    }
}
