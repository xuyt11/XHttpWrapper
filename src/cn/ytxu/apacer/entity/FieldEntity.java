package cn.ytxu.apacer.entity;

import java.util.List;

/**
 * 字段描述的实体类
 * @author ytxu
 * @date 2016-3-18 17:35:11
 *
 */
public class FieldEntity extends BaseEntity<MethodEntity> {
	private String key;// 字段名称
	private String type;// 字段的类型
    private boolean isOptional = false;// 是否为可选字段
	private String description;// 字段描述
	private String paramCategory;// 该字段所属的分类:有可能是一个类别的名称,也有可能只是result\param等
	
	private boolean isList = false;// 是否为数组类型：默认为不是数组类型
	private List<FieldEntity> subs;// 子字段集合

	public FieldEntity() {
		super();
	}
	
	public FieldEntity(String key, String type, String description) {
		super();
		this.key = key;
		this.type = type;
		this.description = description;
	}

	public FieldEntity(String paramName, String paramType, String paramDesc, boolean isOptional, String paramCategory) {
		this(paramName, paramType, paramDesc);
        this.isOptional = isOptional;
		this.paramCategory = paramCategory;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    public boolean isOptional() {
        return isOptional;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParamCategory() {
		return paramCategory;
	}

	public boolean isList() {
		return isList;
	}
	
	public List<FieldEntity> getSubs() {
		return subs;
	}

	public void setSubs(List<FieldEntity> subs) {
		this.subs = subs;
		this.isList = null != subs && subs.size() > 0;// 子字段中最少要有一个字段
	}

    @Override
    public String toString() {
        return "FieldEntity{" +
                "key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", isOptional=" + isOptional +
                ", description='" + description + '\'' +
                ", paramCategory='" + paramCategory + '\'' +
                ", isList=" + isList +
                ", subs=" + subs +
                '}';
    }

    @Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FieldEntity that = (FieldEntity) o;

		if (key != null ? !key.equals(that.key) : that.key != null) return false;
		return (type != null) ? type.equals(that.type) : (that.type == null);

	}

	@Override
	public int hashCode() {
		int result = key != null ? key.hashCode() : 0;
		result = 31 * result + (type != null ? type.hashCode() : 0);
		return result;
	}
}