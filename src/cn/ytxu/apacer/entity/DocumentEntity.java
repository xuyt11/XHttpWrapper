package cn.ytxu.apacer.entity;

import java.util.List;

/**
 * API文档实体类<br>
 * 2016-04-08<br>
 * 只有一个状态码类: 状态码不分版本;也就是说,状态码是不能更改的,只能是新增;
 */
public class DocumentEntity extends BaseEntity {
    private List<String> versions;// API所有的版本号
    private List<StatusCodeEntity> statusCodes;// 所有的状态码
    private List<ApiEnitity> apis;// 根据版本,来分的不同的API

    public DocumentEntity(List<String> versions, List<StatusCodeEntity> statusCodes, List<ApiEnitity> apis) {
        this.versions = versions;
        this.statusCodes = statusCodes;
        this.apis = apis;
    }

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
    }

    public List<ApiEnitity> getApis() {
        return apis;
    }

    public void setApis(List<ApiEnitity> apis) {
        this.apis = apis;
    }

    public List<StatusCodeEntity> getStatusCodes() {
        return statusCodes;
    }

    public void setStatusCodes(List<StatusCodeEntity> statusCodes) {
        this.statusCodes = statusCodes;
    }


    /** 给整个文档对象数据链,设置higherLevel */
    public void setDoubleLinkedRefrence() {
        // 1 doc
        setHigherLevel(statusCodes, this);
        setHigherLevel(apis, this);

        // 2 api
        if (apis == null || apis.size() <= 0) {
            return;
        }

        for (ApiEnitity api : apis) {
            api.setDoubleLinkedRefrence();
        }

    }
}
