config structure
================

##x-http-wrapper.json
   1. 该文件保存有所有的配置信息；
   2. 共有8个分类：  
 **api_data**，**template_file_infos**，**base_config**，**filter**，
 **request**，**response**，**status_code**，**param_types**
 
##api_data: API数据信息的配置
   1. api_data_source：api的数据源
        * 现有的数据源只有一个apidocjs，在ApiDataSourceType中定义的。
        * 若有其他数据源，可以在ApiDataSourceType中自己添加定义分类。
        且需要添加该分类的数据解析器，将其解析转换为x-http-wrapper内部model结构。
   2. file_path_infos：数据源文件地址的多操作系统配置
        * 可以根据不同的系统，有不同的路径。
        * OSName：系统名称；
        * address：api数据源的绝对路径；
   3. file_charset:数据源文件内部的字符编码集，现在还没有用到

##template_file_infos：模板文件列表：根据模板文件，生成目标文件
   1. 对于item：
       * key: 该模板文件的类别名称；
            * 所有的类别都在XHWTFileType枚举中，现阶段共有6个类别；
            * HttpApi, Request, RequestParam, Response, StatusCode, BaseResponse
       * value: 该模板文件的配置信息(template_file_info)；

   2. template_file_info:
        * need_generate: 是否需要生成该类型的文件
        * path: 该模板文件的路径；
            * 可以为绝对路径，也可以为相对于x-http-wrapper.json的相对路径；
        * is_polymerization: 是否需要聚合数据
           * 例如：在多版本的编译模式中，BaseResponse只想有一个，这是就需要将所有的数据聚合。
           目前，只有BaseResponse使用了该功能，其他分类需要自己去实现。

##base_config: 一些基础的配置
   1. create_file_charset: 生成文件的字符编码集，默认为: UTF-8；
   2. compile_model: 编译模式--转换数据时的模式；
   在CompileModel枚举中, 现阶段有两种：multi_version,non_version。
        * multi_version: 在转换数据为内部model的过程中，请求按照版本的不同，映射到不同的更高级model中；
        * non_version: 在转换数据为内部model的过程中，只会保留该请求最新的版本；
            * tip: 版本大小依赖于配置文件中之后的order_versions属性顺序
   3. order_versions: 目标版本的顺序枚举:顺序为升序
        * 顺序的枚举出所有目标的版本号：
        * 若没有出现在其中，就会过自动过滤掉，不会出现在生成文件中；
        * 若一个目标request需要生成，则至少需要支持该request的最高版本；

##filter: 过滤功能的配置
   1. header: 过滤请求头中的字段
        * use_headers: 是否使用过滤请求头字段的功能，默认：不使用；
        * headers: 需要过滤的请求头字段的数组；
            * 例如：过滤掉Authorization字段，因为可以在全局client中添加该字段，不需要每次都在request中添加；
   2. version: 过滤API版本数据，使得只有相关的版本API才能输入到文件中；
        * use_output_versions：是否使用版本输出过滤，默认：不使用；
        * output_versions: 过滤的版本与过滤该版本下的分类；
            * 是一个数组，可以添加多个过滤规则，但是，每一个版本号只能在同一个规则中，否则只会使用第一个匹配的版本号规则进行过滤；
            * 若use_output_versions=false，则该字段失效；
   3. output_versions: 输出规则(过滤规则)
        * output_version_name: 输出的版本名称，且该版本号需要在base_config的order_versions中存在，否则无效；
        * use_output_request_group: 是否对分类进行输出过滤，
        默认不使用，即全部都输出，不需要过滤，
        否则需要在output_request_groups中枚举出所有的输出请求分类；
        * output_request_groups: 输出的版本下,需要输出的分类名称数组; 
    
##request
   1. RESTful: 替换URL上的替代符的配置
        * replaceString: 在url上替换的字符串，主要是使用在string replace上；
            * 例如：若replaceString="%s"，则URL:api/project_id/{project_id}/，
            会替换为api/project_id/%s/
        * multi_replace: 多分类选择的参数：根据顺序查找替换;
            * 例如：若multi_replace = android，在URL：api/project/[android|ios|html]/中，
            则会替换为：api/project/android/
            * 若有多个替换规则可以匹配到，只会替换第一个匹配；
        * date_replace: RESTful风格url中，date参数的格式与在输出文件中在url上请求参数的名称的替换配置
            * date_format: date参数在url上的格式
            * date_request_param: 在输出文件中，在url上请求参数的名称
   2. optional_request_method: 
        * need_generate：是否需要在XHWTFileType的Request文件中生成可选的请求方法(或叫缩略请求方法)；
        * min_number_of_input_params_in_one_input_group: 
        请求组中参数的个数，默认若少于3个则不需要生成;
        * tip: 若开启了生成缩略请求方法的功能，则该请求组中，所有作用的请求，都需要生成对应的RequestParam文件；

##response: 基础响应实体类必须的字段的字段名称和类型

##status_code: 获取状态码
   1. request_group_name: 状态码在apidocjs中的分类的名称，用于找到状态码所在的分类；
   2. parse_model: 解析状态码field所使用的解析模式的名称；
        * 所有的类别都在StatusCodeParseModel枚举中；目前有：x_custom_model与default_value_model
        * x_custom_model: 我定义的一套规范解析
        * default_value_model: 使用apidocjs中，参数的默认值，作为状态码的值
   3. use_version_filter: 是否使用版本过滤功能；
   4. filted_versions: 过滤之后的版本号
   5. ok_number: success response中status_code的值，默认为0；

##param_types: 请求参数与响应字段的对应类型设置
   1. 类型的枚举为ParamTypeEnum类：
        * 现阶段有integer,long,float,double,nubmer,boolean,string,object,array,map,date,file,unknown这些类型类别
        * number类型，现阶段支持，但不推荐用户使用，因为不能明确到底是何种类型(integer,long,float,double等类型中的哪一个)
        * date与file两个类型的字段，在响应实体类中不支持；
        * unknown类型，是没有匹配到其他类型时的通用匹配类型；
            * 例如：在响应的json格式中，若字段值为null，则在获取对应类型时，该字段的类型即为unknown类型；
            所以，若是根据json格式的response去解析响应实体类，则需要注意两点：
                * json格式的response中需要包含所有的字段，不能有遗漏；
                * 字段的value，尽量不要为null值；
        * number类型的response field：(包括了integer,long,float,double)
        所有的字段是何种类型的最好写出该类型与其他number类型区分的value；
        例如：id字段为long类型，则id的值最好为大于int的最大值的数据；
        * 对于array与map两个类型：
            * 其sub type 都必须为object类型；
            * map的key必须为String类型；
   2. param_type
        * match_type_names: 在api文档中写入的类型名称，用于匹配类型，且忽略大小写
        * request_param_type: 生成的文件中，请求参数的类型
        * request_optional_param_type: 生成的文件中，请求中可选参数的类型，或者是数组、对象、集合等类型参数在请求中的类型
        * response_param_type: 生成的文件中，响应中的参数类型
        * 在三个类型中${object}字符串为替换type的匹配字符串；
            * 其中，Object类型为替换该对象的type；
            * 而array与map两个为替换其sub type；