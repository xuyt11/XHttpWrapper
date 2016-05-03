package cn.ytxu.apacer.entity;

import java.util.List;

/**
 * 方法描述的实体类
 * @author ytxu
 * @date 2016-3-18 17:35:11
 *
 */
public class MethodEntity extends BaseEntity<CategoryEntity> {
	private String descrption;// 方法的中文名称（描述）
	private String versionCode;// 该方法的版本号
	private String methodName;// 该方法的方法名称：驼峰法命名
	private String methodType;// 请求类型:post、get、patch...
	private List<FieldEntity> headers;// 请求头字段
	private List<FieldEntity> inputParameters;// 输入字段
	private List<FieldEntity> outParameters;// 输出字段

	private String url;// 方法的相对路径，起始位置必须为/
	private List<RESTfulApiEntity> restfulApis;// url RESTful风格的解析对象

    private List<ResponseEntity> responses;// 请求响应列表
	
	// TODO 2016-3-18 18:57:06 未来可以添加输出数据的分类，用于直接生成输出数据的判断逻辑 
	
	/**
	 * purpose：用于不同版本的请求的分辨<br>
	 * 同一个方法，但是是不同版本中的:应该是输出参数不同<br>
	 * 两个方法只是版本号不同：方法、参数(header，inputparam)，都一样<br>
	 * 现在先不考虑：描述、url、request type请求类型、输出字段
	 * @return
	 */
	public static boolean isSameMethodButOnlyVersionCode(MethodEntity preMethod, MethodEntity currMethod) {
		if (null == preMethod) {
			return false;
		}
		
		if (null == preMethod.methodName || !preMethod.methodName.equals(currMethod.methodName)) {
			return false;
		}
		
//		if (null == preMethod.url || !preMethod.url.equals(currMethod.url)) {
//			return false;
//		}
		
		if (preMethod.headers == null && currMethod.headers == null) {
			// do nothing
		} else {			
			if ((preMethod.headers == null && currMethod.headers != null)
					|| (preMethod.headers != null && currMethod.headers == null)
					|| (preMethod.headers.size() != currMethod.headers.size())) {
				return false;
			}
			for(int i=0, size=preMethod.headers.size(); i<size; i++) {
				String preType = preMethod.headers.get(i).getType();
				String currType = currMethod.headers.get(i).getType();
				if (preType.equalsIgnoreCase(currType)) {// 现在还不考虑List\Array的区别，因为现在方法应该都是一个人写的，或者以后可以规范他们，只能有其中一个写法
					continue;
				}
				return false;
			}
		}
		
		if (preMethod.inputParameters == null && currMethod.inputParameters == null) {
			// do nothing
		} else {
			if ((preMethod.inputParameters == null && currMethod.inputParameters != null)
					|| (preMethod.inputParameters != null && currMethod.inputParameters == null)
					|| (preMethod.inputParameters.size() != currMethod.inputParameters.size())) {
				return false;
			}
			for(int i=0, size=preMethod.inputParameters.size(); i<size; i++) {
				String preType = preMethod.inputParameters.get(i).getType();
				String currType = currMethod.inputParameters.get(i).getType();
				if (preType.equalsIgnoreCase(currType)) {// 现在还不考虑List\Array的区别，因为现在方法应该都是一个人写的，或者以后可以规范他们，只能有其中一个写法
					continue;
				}
				return false;
			}
		}

		if ((preMethod.versionCode == currMethod.versionCode)
				|| (null != preMethod.versionCode && preMethod.versionCode.equals(currMethod.versionCode))) {
			return false;
		}
		return true;
	}

	/**
	 * 不同版本的请求
	 * @date 2016-04-07
     */
	public static boolean isNotSameVersionCodeRequest(MethodEntity preMethod, MethodEntity currMethod) {
		if (null == preMethod) {
			return false;
		}

		if (null == preMethod.methodName || !preMethod.methodName.equals(currMethod.methodName)) {
			return false;
		}

		if ((preMethod.versionCode == currMethod.versionCode)
				|| (null != preMethod.versionCode && preMethod.versionCode.equals(currMethod.versionCode))) {
			return false;
		}
		return true;
	}
	
	public MethodEntity() {
		super();
	}
	
	public String getDescrption() {
		return descrption;
	}
	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethodType() {
		return methodType;
	}
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
	public List<FieldEntity> getHeaders() {
		return headers;
	}
	public void setHeaders(List<FieldEntity> headers) {
		this.headers = headers;
	}
	public List<FieldEntity> getInputParameters() {
		return inputParameters;
	}
	public void setInputParameters(List<FieldEntity> inputParameters) {
		this.inputParameters = inputParameters;
	}
	public List<FieldEntity> getOutParameters() {
		return outParameters;
	}
	public void setOutParameters(List<FieldEntity> outParameters) {
		this.outParameters = outParameters;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	
	public List<RESTfulApiEntity> getRESTfulApis() {
		return restfulApis;
	}
	
	public void setRESTfulApis(List<RESTfulApiEntity> restfulApis) {
		this.restfulApis = restfulApis;
	}

    public List<ResponseEntity> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseEntity> responses) {
        this.responses = responses;
    }

    public List<RESTfulApiEntity> getRestfulApis() {
        return restfulApis;
    }

    public void setRestfulApis(List<RESTfulApiEntity> restfulApis) {
        this.restfulApis = restfulApis;
    }

	@Override
	public String toString() {
		return "MethodEntity [descrption=" + descrption + ", versionCode="
				+ versionCode + ", methodName=" + methodName + ", methodType="
				+ methodType + ", headers=" + headers + ", inputParameters="
				+ inputParameters + ", outParameters=" + outParameters
				+ ", url=" + url + ", restfulApis=" + restfulApis + "]";
	}


	public void setDoubleLinkedRefrence() {
        setHigherLevel(headers, this);
        setHigherLevel(inputParameters, this);
        setHigherLevel(outParameters, this);
        setHigherLevel(restfulApis, this);
        setHigherLevel(responses, this);

        if (responses != null && responses.size() > 0) {
            for (ResponseEntity response : responses) {
                response.setDoubleLinkedRefrence();
            }
        }
	}
}