template file and tags
================================


# tempalte file structure
   1. template file name
       * 模板文件我取了x-http-wrapper template的首字母，以.xhwt作为后缀；
       * 各个模板文件的名称，都是用他的功能名称作为区分，即XHWTFileType枚举中的name；
       例如：HttpApi分类的模板文件名称为xxx-httpapi.xhwt
   2. template header: 模板头部
       * header tag: 
            * start tag: \<t:header>
            * end tag: \</t:header>
       * header内部的数据为json格式：
            * fileName: 生成的文件名称；
            * fileDirs: 生成的文件路径，且可以配置多操作系统；
   3. template content:模板的内容，生成文件内容的数据，
       * 生成的文件内容由该文件类型获取到的API数据与标签两者来驱动
   4. 现阶段只有7个标签类型
       * text, foreach, retain, list, list_single_line, if_else, list_replace
       * 标签内部的匹配都为反射的方法名称；
            * 例如：在foreach标签中，<t:foreach each="request_groups">，
            匹配的request_groups即为反射后去request_groups方法的数据，然后利用该数据去遍历；

# template tags
   1. text: 普通的文本
   2. foreach: 循环标签
       * 标签起始符：<t:foreach each=\")\\w+(\">；
            * 其中获取到的数据是能进行遍历的，如：array,list等collection;
       * 标签结束符：</t:foreach>
       * 作用：遍历该方法获取到的数据，并将item data作为反射数据，作用于foreach标签内部的其他标签上；
       * tip: foreach标签是能包含其他遍历标签的，而其他标签是不能的；最起码现在还没有实现；
   3. retain: 保留代码区域标签
       * 标签起始符：<t:retain type=\")\\w+(\"/>；
            * 其中获取到的数据是能与RetainType枚举中的name字段匹配的；
       * 标签结束符：没有
       * 作用：用于保留程序员在该文件中编写的代码；防止再次运行工具时，将这些代码清除了；
       工具在生成文件之前，会寻找该文件是否已存在，
       若有，则会获取到这些保留数据，并会在生成文件时，将保留数据添加上去；
       * 保留区域在生成的文件中的格式为：  
            //** ytxu.retain-start *///** ytxu.{RetainType.name} */  
            {retain content}  
            //** ytxu.retain-end */
       * RetainType枚举现在有四个类型：import，field，method，other；
            * import类型是在class外部，可以添加其他类型的import与该类的注释等消息；
            * field类型是在class field之后的区域；
            * method类型是在class method之后的区域；
            * other类型是在不确定到底是何种类型的数据放置的区域；
   4. list: 在foreach中的循环，防止foreach循环嵌套
       * 标签起始符：<t:list each=\")\\w+(\">；
       * 标签结束符：</t:list>
       * 作用：除了不能嵌套，其他作用于foreach一致
   5. list_single_line: 单行循环，防止foreach循环嵌套
       * 标签起始符：<t:list each=\")\\w+(\")( singleLine>；
       * 标签结束符：</t:list>
       * 作用：作用于list一致，只是会将内部的数据合并为一行；
   6. if_else: if else 条件判断
       * 标签起始符：<t:if xxxxx>
            * 有多个匹配表达式：现有：isTrue,isNotEmpty两个；
       * 标签结束符：</t:if_end>
       * 中间还有</t:if_else>标签
       * 作用：同if表达式一致；
   7. list_replace: 替换数组的文本
       * 标签起始符：<t:list_replace each=\")\\w+(\" replace_key=\")\\w+(\" list_value=\")[\\p{Print}\\p{Space}]+(\">
       * 标签结束符：</t:list_replace>
       * 作用：用each与list_value中的格式，遍历获取到的数据，替换内部${replace_key}字符串;






