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

   * 
   * 
   * 
   * 
   * 
   * 
   * 
   * 
   * 
   * 
   * 
   * 
   * 
   * 
   * 
        
        
        
        
        
        
        
        
        
        
        
        
