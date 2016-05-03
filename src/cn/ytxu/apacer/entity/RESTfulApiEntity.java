package cn.ytxu.apacer.entity;

/**
 * RESTfulApi的实体类：url RESTful风格的解析对象
 *
 * 解析转换请求路径(url)，生成一个List？<br>
 * 例如：<br>
 * 1、id型的：{id}、{feedback_id}、{recommend_id}...<br>
 * 2、多选型的：[ios|android|web]<br>
 * 3、时间型的：{YYYY-MM-DD}<br>
 * @author ytxu 2016-3-22
 *
 */
public class RESTfulApiEntity extends BaseEntity<MethodEntity> {
	
	private String restfulParam;
	private String staticStr;
	private Type type;
	
	
	public enum Type {
		staticStr, // RESTful url中固定不变的字符串
		restfulParam,// RESTful url中需要替换的字符串
	}
	
	public static RESTfulApiEntity createStaticStr(String staticStr) {
		RESTfulApiEntity entity = new RESTfulApiEntity();
		entity.type = Type.staticStr;
		entity.staticStr = staticStr;
		return entity;
	}

	public static RESTfulApiEntity createRESTfulParam(String restfulParam) {
		RESTfulApiEntity entity = new RESTfulApiEntity();
		entity.type = Type.restfulParam;
		entity.restfulParam = restfulParam;
		return entity;
	}
	
	public String getRestfulParam() {
		return restfulParam;
	}

	public String getStaticStr() {
		return staticStr;
	}

	public Type getType() {
		return type;
	}
	
	
}