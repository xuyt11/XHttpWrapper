package cn.ytxu.apacer.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * API文档的实体类
 * @author ytxu
 * @date 2016-3-18 17:35:11
 *
 */
public class ApiEnitity extends BaseEntity<DocumentEntity> {
	
	private List<CategoryEntity> categorys;// 所有的API分类
	private String currVersionCode;
	
	public ApiEnitity(String version) {
		super();
        currVersionCode = version;
		categorys = new ArrayList<>();
	}
	
	public List<CategoryEntity> getCategorys() {
		return categorys;
	}
	
	public void addACategory(CategoryEntity category) {
		this.categorys.add(category);
	}

    /** 提供版本号:例如:1.3.1 */
    public String getCurrVersionCode() {
        return currVersionCode;
    }

    /** 提供格式化后的版本号,例如:version_1_3_1 */
    public String getFormatVersion() {
        return "V" + currVersionCode.replace(".", "_");
    }

}