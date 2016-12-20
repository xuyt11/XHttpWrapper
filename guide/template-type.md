template file type
==================
   1. 所有的类别都在XHWTFileType枚举中，现阶段共有6个类别;
   2. 且在该枚举中也有该模板类别所需数据的获取过滤功能(getReflectiveDatas);

# HttpApi
## 所有API请求的统一调用入口，统合所有的请求类别的接口，防止API接口分散。
   
    public class HttpApi {
   
       private static Account account;
       private static Data data;
       private static Message message;
       private static Version version;
   
       public static Account account() {
           if (null == account) {
               account = Account.getInstance();
           }
           return account;
       }
   
       public static Data data() {
           if (null == data) {
               data = Data.getInstance();
           }
           return data;
       }
   
       public static Message message() {
           if (null == message) {
               message = Message.getInstance();
           }
           return message;
       }
   
       public static Version version() {
           if (null == version) {
               version = Version.getInstance();
           }
           return version;
       }
   
    }
   
# Request: 单个请求分组中，所有的请求方法。
> 若需要的话，可以添加对应的简化参数个数的方法(缩略请求方法)。
   
    public class Account extends BaseApi {
    
        public static Account getInstance() {
            return Helper.instance;
        }
    
        private static class Helper {
            public static final Account instance = new Account();
        }
    
        private Account() {
            super();
        }
    
        /**
         * @version 2.0.0
         * @requestUrl 
         * @title 初始化账号信息
         *
         */
        public RequestHandle init(Context cxt001,
            ResponseHandlerInterface response) {
            // hide implementation
        }
    
        /**
         * @version 2.0.0
         * @title 扫二维码到 web 端进行操作
         *
         * @param context String desc
         * @param project_id isOptional Integer desc
         * @param scene isOptional String desc
         * @param uuid_rand String desc
         */
        public RequestHandle qrcodeConfirm(Context cxt001,
            String context, Integer project_id, String scene, String uuid_rand, 
            ResponseHandlerInterface response) {
            // hide implementation
        }
    
        /**
         * 缩略请求方法
         */
        public RequestHandle qrcodeConfirm(Context cxt001,
            QrcodeConfirmRP.Parameter parameter, 
            ResponseHandlerInterface response) {
            return qrcodeConfirm(cxt001,
            parameter.context, parameter.project_id, parameter.scene, parameter.uuid_rand, 
            response);
        }
    
    }
   
# Requestparam: 请求参数分组归类，对应单个请求
   1. 在request-->optional_request_method中，
有参数need_generate与min_number_of_input_params_in_one_input_group；
   2. need_generate：是否需要在XHWTFileType的Request文件中生成可选的请求方法(或叫缩略请求方法)；
   3. min_number_of_input_params_in_one_input_group: 请求组中参数的个数，默认若少于3个则不需要生成;
   4. 每一个请求，若生成了缩略请求方法，都会有一个对应的请求参数类；
   
    /**
     * 缩略请求方法
     */
    public RequestHandle qrcodeConfirm(Context cxt001,
        QrcodeConfirmRP.Parameter parameter, 
        ResponseHandlerInterface response) {
        return qrcodeConfirm(cxt001,
        parameter.context, parameter.project_id, parameter.scene, parameter.uuid_rand, 
        response);
    }
   
    /**
     * 请求参数
     */
    public class QrcodeConfirmRP implements Serializable {
   
       public static final class Parameter implements Serializable {
           /**
            * type: String<br>
            * isOptional : false<br>
            * desc: <p>扫码场景，枚举值</p>
            */
           public String context;
           /**
            * type: Integer<br>
            * isOptional : true<br>
            * desc: <p>业务参数: 根据 context 的不同而不同</p>
            */
           public Integer project_id;
           /**
            * type: String<br>
            * isOptional : true<br>
            * desc: <p>身份信息: 服务端会优先使用客户端传入的身份信息，当为”投资人“的时候必传</p>
            */
           public String scene;
           /**
            * type: String<br>
            * isOptional : false<br>
            * desc: <p>从二维码扫描得到的唯一码</p>
            */
           public String uuid_rand;
       }
    
    }
   
# Response: 请求的相应数据model

    public class Init {
    
    	private long member_id;
    	private long member_role;
    	private long member_status;
    	private String ry_token;
    	private long step;
    
    	public long getMemberId() {return member_id;}
    	public long getMemberRole() {return member_role;}
    	public long getMemberStatus() {return member_status;}
    	public String getRyToken() {return ry_token;}
    	public long getStep() {return step;}
    	public void setMemberId(long member_id) {this.member_id = member_id;}
    	public void setMemberRole(long member_role) {this.member_role = member_role;}
    	public void setMemberStatus(long member_status) {this.member_status = member_status;}
    	public void setRyToken(String ry_token) {this.ry_token = ry_token;}
    	public void setStep(long step) {this.step = step;}
    
    }
   
# StatusCode: 响应中所有状态码的枚举类
    
    public class StatusCode {
    
        /** '') */
        public static final int OK = 0;
    
        /** '登录状态已过期，请重新登入') */
        public static final int UNAUTHORIZED = 101;
    
        /** '您没有权限查看') */
        public static final int FORBIDDEN = 102;
    
        /** '资源未找到') */
        public static final int NOT_FOUND = 103;
    
        /** '客户端请求错误') # 4XX客户端错误 */
        public static final int CLIENT_ERROR = 228;
    
        /** '服务器错误') # 5XX 服务器错误 */
        public static final int SERVER_ERROR = 229;
    
        /** '参数错误') */
        public static final int PARAM_ERROR = 230;
    
        /** '登录失败，请检查您的邮箱地址是否正确') */
        public static final int LOGIN_FAIL_EMAIL_NOT_EXIST = 332;
    
        /** '登录失败，请确认您的手机号是否正确') */
        public static final int LOGIN_FAIL_MOBILE_NOT_EXIST = 333;
    
        /** '登录失败，请检查密码是否正确') */
        public static final int LOGIN_FAIL_PASSWORD_ERROR = 334;
    
    }
   
# BaseResponse: 基础的响应实体类
> 所有的请求响应的数据，都是在该类的基础上的；且在其中还包含了错误异常提示的model

    public class ResponseEntity<T> {
    
        private int status_code;
        private String message;
        private Error error;
        private T data;
    
        public int getStatusCode() {return status_code;}
        public void setStatusCode(int status_code) {this.status_code = status_code;}
        public String getMessage() {return message;}
        public void setMessage(String message) {this.message = message;}
        public Error getError() {return error;}
        public void setError(Error error) {this.error = error;}
        public T getData() {return data;}
        public void setData(T data) {this.data = data;}
    
        public static class Error {
            private String detail;
            private List<String> device_token;
            private List<String> content;
            private List<String> followed_id;
    
            public String getDetail() {return detail;}
            public void setDetail(String detail) {this.detail = detail;}
            public List<String> getDeviceToken() {return device_token;}
            public void setDeviceToken(List<String> device_token) {this.device_token = device_token;}
            public List<String> getContent() {return content;}
            public void setContent(List<String> content) {this.content = content;}
            public List<String> getFollowedId() {return followed_id;}
            public void setFollowedId(List<String> followed_id) {this.followed_id = followed_id;}
        }
    
    }
   