package cn.ytxu.api_semi_auto_creater.model;

import cn.ytxu.api_semi_auto_creater.parser.base.DocEntity;
import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by ytxu on 2016/7/20.
 */
public class DocModel extends BaseModel {
    private List<VersionModel> versions;
    private DocEntity.SectionEntity statusCode;// TODO 未来要直接转换为状态码list，进行保存

    public DocModel(Element element, DocEntity.SectionEntity statusCode) {
        super(null, element);
        this.statusCode = statusCode;
    }

    public List<VersionModel> getVersions() {
        return versions;
    }

    public void setVersions(List<VersionModel> versions) {
        this.versions = versions;
    }

    public DocEntity.SectionEntity getStatusCode() {
        return statusCode;
    }

}
