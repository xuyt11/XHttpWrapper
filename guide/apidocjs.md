apidocjs guide
==============


# apidocjs安装 
[apidocjs安装与简介](http://www.jianshu.com/p/bb5a4f5e588a 'apidocjs安装与简介')

# apidocjs tags
@api：请求的方法，中文名称，url
@apiVersion：请求的版本号
@apiName：请求的英文名称
@apiGroup：该请求的分组
@apiPermission:权限，现阶段没有用到
@apiUse: 引入一个@apiDefine的注释块

@apiHeader：请求头
@apiParam：请求参数

@apiHeaderExample：请求头的模板，现阶段没有用到
@apiParamExample：请求参数的模板，现阶段没有用到

@apiSuccess：成功响应的字段
@apiError：失败响应的字段

@apiSuccessExample：成功响应的响应体、描述，现阶段没有用到
@apiErrorExample：失败响应的响应体、描述，现阶段没有用到

# apidocjs tags detail
   * @api {method} path [title]
      只有使用@api标注的注释块才会在解析之后生成文档，title会被解析为导航菜单(@apiGroup)下的小菜单
      method可以有空格，如{POST GET}
   * @apiGroup name
      分组名称，被解析为导航栏菜单
   * @apiName name
      接口名称，在同一个@apiGroup下，名称相同的@api通过@apiVersion区分，否者后面@api会覆盖前面定义的@api
   * @apiDescription text
      接口描述，支持html语法
   * @apiVersion verison
      接口版本，major.minor.patch的形式
    
   * @apiIgnore [hint]
      apidoc会忽略使用@apiIgnore标注的接口，hint为描述
   * @apiSampleRequest url
      接口测试地址以供测试，发送请求时，@api method必须为POST/GET等其中一种
    
   * @apiDefine name [title] [description]
      定义一个注释块(不包含@api)，配合@apiUse使用可以引入注释块
      在@apiDefine内部不可以使用@apiUse
   * @apiUse name
      引入一个@apiDefine的注释块
    
   * @apiParam [(group)] [{type}] [field=defaultValue] [description]
   * @apiHeader [(group)] [{type}] [field=defaultValue] [description]
   * @apiError [(group)] [{type}] field [description]
   * @apiSuccess [(group)] [{type}] field [description]
      用法基本类似，分别描述请求参数、头部，响应错误和成功
      group表示参数的分组，type表示类型(不能有空格)，入参可以定义默认值(不能有空格)
   * @apiParamExample [{type}] [title] example
   * @apiHeaderExample [{type}] [title] example
   * @apiErrorExample [{type}] [title] example
   * @apiSuccessExample [{type}] [title] example
      用法完全一致，但是type表示的是example的语言类型
      example书写成什么样就会解析成什么样，所以最好是书写的时候注意格式化，(许多编辑器都有列模式，可以使用列模式快速对代码添加*号)
    
   * @apiPermission name
      name必须独一无二，描述@api的访问权限，如admin/anyone



