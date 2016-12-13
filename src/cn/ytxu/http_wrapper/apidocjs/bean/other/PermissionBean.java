package cn.ytxu.http_wrapper.apidocjs.bean.other;

/**
 * name必须独一无二，描述@api的访问权限，如admin/anyone
 */
public class PermissionBean {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}