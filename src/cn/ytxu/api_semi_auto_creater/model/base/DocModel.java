package cn.ytxu.api_semi_auto_creater.model.base;

import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by ytxu on 2016/7/20.
 */
public class DocModel extends BaseModel {
    private List<VersionModel> versions;

    public DocModel(Element element) {
        super(null, element);
    }

    public List<VersionModel> getVersions() {
        return versions;
    }

    public void setVersions(List<VersionModel> versions) {
        this.versions = versions;
    }

}
