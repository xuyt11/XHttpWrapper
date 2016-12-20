x-http-wrapper model
====================

# BaseModel
   * 所有的model都需要继承BaseModel
   * BaseModel中有一个泛型用于存储更高一级的BaseModel
   * 在template engine中，反射只认BaseModel，不是BaseModel的model不能反射
   * template engine在反射调用时，若没有在反射的对象中找到方法，会从higherLevel中去找，直到没有higherLevel为止；

# model的结构
   * VersionModel: 内部最顶级的model，用于保存API的版本名称
       * sub: statusCodeGroups, requestGroups, subsOfErrors 
       * statusCodeGroups: 该版本下的所有状态码组
       * requestGroups: 该版本下的所有请求组
       * subsOfErrors: 该版本下的所有error内的字段，用于生成BaseResponse中的Error类
       * tip: 若compile_model=non_version，则sub是所有目标API中最新的，即没有版本的概念了

   * StatusCodeGroupModel: 状态码的分类model
       * title: 状态码组的中文名称
       * name: 状态码组的英文名称
       * sub: statusCodes-->该状态码组下的所有状态码
   * StatusCodeModel: 状态码model
       * name: 状态码标识符
       * number: 状态码在response中的值
       * desc: 状态码的描述信息

   * RequestGroupModel: 请求组model
       * name: 该请求组的名称，en;
       * sub: requests-->该请求组下的所有请求
   * RequestModel: 请求model
       * methodType: 请求方法类型，例如：post,get,delete...
       * url: 请求的相对路径;
       * title: 该请求的名称，zh;
       * name: 该请求的名称，en;
       * version: 该请求的版本;
       * group: 该请求的请求组的名称;
       * description: 该请求的简介;
       * sub: 
           * restfulUrl-->url经解析转换后的model；
           * headerGroups-->请求头组；
           * inputGroups-->请求参数组;
           * responseContainer-->请求响应；

   * RESTfulUrlModel: url经解析转换后的model
       * url: RequestModel中的url; 
       * isRESTfulUrl: 是否为RESTful的url,  
        简单来说就是，是否有需要动态输入的参数，来拼凑出真正请求的URL；  
       * multiUrl: url经转换变为的多选类型url, 使用了config文件中request.multi_replace功能之后的URL；
       * hasMultiParam: 是否有多选类型的参数，若有的话，则使用url时需要使用multiUrl
       * params: url或multiUrl中需要替换的参数model；
   * RESTfulParamModel: url或multiUrl中需要替换的参数model；
       * param: 在url或multiUrl中的字符串
       * realParam: 在代码中实际的字符串
       * paramIndex: 在url或multiUrl中所有param的index
       * start, end: param 在url或multiUrl中的范围(range), 在转换请求url时，替换的范围

   * HeaderGroupModel: 请求头组model
       * name: 该请求头组的名称，en;
       * sub: headers-->该请求头组的所有请求头；
   * InputGroupModel: 请求参数组model
       * name: 该请求参数组的名称，en;
       * sub: inputs-->该请求参数组的所有请求参数；
   * HeaderModel与InputModel: 请求头与请求参数的model，都继承于FieldModel
   * FieldModel: 
       * name: 字段的名称
       * group: 字段所属分类
       * type: 参数的类型
       * optional: 该字段是否为可选参数：主要应用于request(header与parameter)
       * defaultValue: 默认值
       * description: 字段的描述
       * size: 参数的范围，例如
           * 1, 1..20-->int类型的参数，范围在1到20
           * -->String类型的参数，字符串的长度
           * 2, ..20-->int最大值为20
           * -->String类型的参数，字符串的长度
           * 3, "a","b","bc"-->String类型的参数，枚举参数值
       * filterTag: 是否为可过滤掉的参数
       * paramTypeBean: 字段的类型，依赖于type解析出来的
       * dataType: 字段的数据类型
           * tip:在描述字段(description)解析出来的该字段的类型名称；可以用于response 中数组、对象的起名
           * 例如：results中有children字段，但两个都是Area属性
           * results	Array 地区信息结果{DataType:Area}
           * children	Array 地区信息子结果{DataType:Area}

   * ResponseContainerModel: 响应的容器，包含响应字段与响应体(成功响应与失败响应)
       * sub:
           * successFieldGroups: 成功响应的所有字段组；
           * errorFieldGroups: 失败响应的所有字段组；
           * successResponses: 成功响应的所有响应体；
           * errorResponses: 失败响应的所有响应体；
   * ResponseFieldGroupModel: 响应字段组model
       * name: 响应字段组的名称
       * sub: fields--> 响应组中所有的字段
   * ResponseFieldModel: 响应字段model，继承于FieldModel
   * ResponseModel: 响应体的model
       * header: 响应报文中的头部
       * body: 响应报文中的响应体
       * statusCode: 解析响应报文后，响应体中的statusCode的值
       * outputs: 解析响应报文后的，该响应中的所有输出字段
   * OutputParamModel: 响应输出字段model
       * parent: 该响应输出字段是否有父响应输出字段；
           * response下的一级output则parent=null;
       * type: ParamTypeEnum枚举，该响应输出字段中的参数类型;
       * fieldName: 该字段的名称；
       * fieldValue: 该字段在json格式response中的值；
       * values: 该字段在json格式response中的所有值；只有object与array，才会有
       * subType: 子类型， 只有array才有，如：List<Integer>,List<Long>,List<String>...
       * subs: 子outpout, 只有object与array，才会有的
       * defined: 该输出字段，在response field中有相同name的，  
       若有该值，则直接使用该值的type;
       * nonGenerationResponseEntityFileTag: 是否需要生成响应实体文件的标记，默认为需要  
       防止重复生成response entity文件；
        
        
        
