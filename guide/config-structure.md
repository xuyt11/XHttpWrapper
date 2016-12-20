# config structure
共有8个分类：
 **api_data**，**template_file_infos**，**base_config**，**filter**，
 **request**，**response**，**status_code**，**param_types**
 
##api_data
   1. api_data_source：api的数据源
   现有的数据源只有一个apidocjs，在ApiDataSourceType中定义的。
   若有其他数据源，可以在ApiDataSourceType中自己添加定义分类。
   且需要添加该分类的数据解析器，将其解析转换为x-http-wrapper内部model结构。

   2. file_path_infos：数据源文件地址的多操作系统配置
   可以根据不同的系统，有不同的路径。
   OSName：系统名称；
   address：api数据源的绝对路径；

   3. file_charset:数据源文件内部的字符编码集，现在还没有用到


##template_file_infos
##base_config
##filter
##request
##response
##status_code
##param_types
 