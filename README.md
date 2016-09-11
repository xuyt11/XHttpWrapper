1 这是一个解析器,解析由apidoc.js生成的API接口的HTML页面;<br>
2 解析后,生成API接口中请求方法,请求路径,请求参数,请求头与响应实体等等的接口数据.<br>
3 再由这些接口数据,生成Android中的请求方法,与响应实体类.<br>
4 由于这些只是自己在开发公司项目中做的一个东东,所以,很多东西都是只符合当前项目,若要拿去使用,可以自己更改.

release version 0.5 future
==========================
重构xtemp解析器，
使得foreach标签能够多层嵌套，去除掉list等标签

release version 0.4 future
==========================
添加响应实体类类型的解析功能；
* 1 添加Map类型的解析---在defined parameter的type中需要为Map---SubType参数需要考虑为List；
* 2 添加Object类型的参数设置，在.json配置文件中；
* 3 对Array类型的参数，替换SubType的参数，不能再用${object}了，要用${sub_type}替换，因为SubType有可能为Array，Map，Object...

release version 0.3 future
=========================
不再解析apidocjs的和HTML页面中的数据了，直接解析api_data.json；

release version 0.2
===================
实现了转换文件的模板化；
* xha
    http api:请求的对外文件，所有的请求都通过该文件进行访问；
* xreq
    http request:请求的类别文件，包含同一分类中的所有的请求；
* xres
    http response entity:每一个请求中的响应实体类；
* xbres
    http base response:请求响应的base文件，包含了各个response中所有的error；
* xsc
   http response status code:请求响应的内部的状态码文件；

release version 0.1
===================
实现了最基本的，将RESTful url转换成请求方法与响应实体类，与响应状态码的功能；
