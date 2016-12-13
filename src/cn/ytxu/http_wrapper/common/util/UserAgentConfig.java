package cn.ytxu.http_wrapper.common.util;



/**
 * http请求的userAgent参数
 * 
 * @authorAdministrator
 * 
 */
public class UserAgentConfig {
	
	// PC端：

	// safari5.1–MAC
	public static final String Params4safari5MAC = "Mozilla/5.0(Macintosh;U;IntelMacOSX10_6_8;en-us)AppleWebKit/534.50(KHTML,likeGecko)Version/5.1Safari/534.50";

	// safari5.1–Windows
	public static final String Params4safari5Windows = "Mozilla/5.0(Windows;U;WindowsNT6.1;en-us)AppleWebKit/534.50(KHTML,likeGecko)Version/5.1Safari/534.50";

	// IE9.0
	public static final String Params4IE9 = "Mozilla/5.0(compatible;MSIE9.0;WindowsNT6.1;Trident/5.0;";

	// IE8.0
	public static final String Params4IE8 = "Mozilla/4.0(compatible;MSIE8.0;WindowsNT6.0;Trident/4.0)";

	// IE7.0
	public static final String Params4IE7 = "Mozilla/4.0(compatible;MSIE7.0;WindowsNT6.0)";

	// IE6.0
	public static final String Params4IE6 = "Mozilla/4.0(compatible;MSIE6.0;WindowsNT5.1)";

	// Firefox4.0.1–MAC
	public static final String Params4Firefox4MAC = "Mozilla/5.0(Macintosh;IntelMacOSX10.6;rv:2.0.1)Gecko/20100101Firefox/4.0.1";

	// Firefox4.0.1–Windows
	public static final String Params4Firefox4Windows = "Mozilla/5.0(WindowsNT6.1;rv:2.0.1)Gecko/20100101Firefox/4.0.1";

	// Opera11.11–MAC
	public static final String Params4Opera11MAC = "Opera/9.80(Macintosh;IntelMacOSX10.6.8;U;en)Presto/2.8.131Version/11.11";

	// Opera11.11–Windows
	public static final String Params4Opera11Windows = "Opera/9.80(WindowsNT6.1;U;en)Presto/2.8.131Version/11.11";

	// Chrome17.0–MAC
	public static final String Params4Chrome17MAC = "Mozilla/5.0(Macintosh;IntelMacOSX10_7_0)AppleWebKit/535.11(KHTML,likeGecko)Chrome/17.0.963.56Safari/535.11";

	// 傲游（Maxthon）
	public static final String Params4Maxthon = "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;Maxthon2.0)";

	// 腾讯TT
	public static final String Params4TT = "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;TencentTraveler4.0)";

	// 世界之窗（TheWorld）2.x
	public static final String Params4TheWorld2 = "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1)";

	// 世界之窗（TheWorld）3.x
	public static final String Params4TheWorld3 = "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;TheWorld)";

	// 搜狗浏览器1.x
	public static final String Params4Sogou = "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;Trident/4.0;SE2.XMetaSr1.0;SE2.XMetaSr1.0;.NETCLR2.0.50727;SE2.XMetaSr1.0)";

	// 360浏览器
	public static final String Params4360SE = "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;360SE)";

	// Avant
	public static final String Params4Avant = "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;AvantBrowser)";

	// GreenBrowser
	public static final String Params4GreenBrowser = "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1)";
	
	
	// 移动设备端：

	// safariiOS4.33–iPhone
	public static final String Params4safariiOS4iPhone = "Mozilla/5.0(iPhone;U;CPUiPhoneOS4_3_3likeMacOSX;en-us)AppleWebKit/533.17.9(KHTML,likeGecko)Version/5.0.2Mobile/8J2Safari/6533.18.5";

	// safariiOS4.33–iPodTouch
	public static final String Params4safariiOS4iPodTouch = "Mozilla/5.0(iPod;U;CPUiPhoneOS4_3_3likeMacOSX;en-us)AppleWebKit/533.17.9(KHTML,likeGecko)Version/5.0.2Mobile/8J2Safari/6533.18.5";

	// safariiOS4.33–iPad
	public static final String Params4safariiOS4iPad = "Mozilla/5.0(iPad;U;CPUOS4_3_3likeMacOSX;en-us)AppleWebKit/533.17.9(KHTML,likeGecko)Version/5.0.2Mobile/8J2Safari/6533.18.5";

	// AndroidN1
	public static final String Params4AndroidN1 = "Mozilla/5.0(Linux;U;Android2.3.7;en-us;NexusOneBuild/FRF91)AppleWebKit/533.1(KHTML,likeGecko)Version/4.0MobileSafari/533.1";

	// AndroidQQ浏览器Forandroid
	public static final String Params4AndroidQQBrowser = "MQQBrowser/26Mozilla/5.0(Linux;U;Android2.3.7;zh-cn;MB200Build/GRJ22;CyanogenMod-7)AppleWebKit/533.1(KHTML,likeGecko)Version/4.0MobileSafari/533.1";

	// AndroidOperaMobile
	public static final String Params4AndroidOperaMobile = "Opera/9.80(Android2.3.4;Linux;OperaMobiild-1107180945;U;en-GB)Presto/2.8.149Version/11.10";

	// AndroidPadMotoXoom
	public static final String Params4AndroidPadMotoXoom = "Mozilla/5.0(Linux;U;Android3.0;en-us;XoomBuild/HRI39)AppleWebKit/534.13(KHTML,likeGecko)Version/4.0Safari/534.13";

	// BlackBerry
	public static final String Params4BlackBerry = "Mozilla/5.0(BlackBerry;U;BlackBerry9800;en)AppleWebKit/534.1+(KHTML,likeGecko)Version/6.0.0.337MobileSafari/534.1+";
	
	// WebOSHPTouchpad
	public static final String Params4WebOSHPTouchpad = "Mozilla/5.0(hp-tablet;Linux;hpwOS/3.0.0;U;en-US)AppleWebKit/534.6(KHTML,likeGecko)wOSBrowser/233.70Safari/534.6TouchPad/1.0";

	// NokiaN97
	public static final String Params4NokiaN97 = "Mozilla/5.0(SymbianOS/9.4;Series60/5.0NokiaN97-1/20.0.019;Profile/MIDP-2.1Configuration/CLDC-1.1)AppleWebKit/525(KHTML,likeGecko)BrowserNG/7.1.18124";

	// WindowsPhoneMango
	public static final String Params4WindowsPhoneMango = "Mozilla/5.0(compatible;MSIE9.0;WindowsPhoneOS7.5;Trident/5.0;IEMobile/9.0;HTC;Titan)";

	// UC无
	public static final String Params4UC = "UCWEB7.0.2.37/28/999";

	// UC标准
	public static final String Params4UCStandard = "NOKIA5700/UCWEB7.0.2.37/28/999";

	// UCOpenwave
	public static final String Params4UCOpenwave = "Openwave/UCWEB7.0.2.37/28/999";

	// UCOpera
	public static final String Params4UCOpera = "Mozilla/4.0(compatible;MSIE6.0;)Opera/UCWEB7.0.2.37/28/999";


	public static final String[] PCParams = new String[]{Params4safari5MAC, Params4safari5Windows, Params4IE9, Params4IE8, Params4IE7, Params4IE6, 
		Params4Firefox4MAC, Params4Firefox4Windows, Params4Opera11MAC, Params4Opera11Windows, Params4Chrome17MAC, Params4Maxthon, Params4TT,
		Params4TheWorld2, Params4TheWorld3, Params4Sogou, Params4360SE, Params4Avant, Params4GreenBrowser};
	
	public static final String[] MobileParams = new String[]{Params4safariiOS4iPhone, Params4safariiOS4iPodTouch, Params4safariiOS4iPad,
		Params4AndroidN1, Params4AndroidQQBrowser, Params4AndroidOperaMobile, Params4AndroidPadMotoXoom, Params4BlackBerry, Params4WebOSHPTouchpad,
		Params4NokiaN97, Params4WindowsPhoneMango, Params4UC, Params4UCStandard, Params4UCOpenwave, Params4UCOpera};
	
	
	public static String getWithRandom() {
		int index = (int) (Math.random() * PCParams.length);
		return PCParams[index];
	}

	
	// 二、浏览器识别

	// 1、IE浏览器（以IE9.0为例）

	// PC端：public static final String Params4 =
	// "Mozilla/5.0(compatible;MSIE9.0;WindowsNT6.1;Trident/5.0;

	// 移动设备：public static final String Params4 =
	// "Mozilla/5.0(compatible;MSIE9.0;WindowsPhoneOS7.5;Trident/5.0;IEMobile/9.0;HTC;Titan)

	// 由于遨游、世界之窗、360浏览器、腾讯浏览器以及搜狗浏览器、Avant、GreenBrowser均采用IE的内核，因此IE浏览器判断的标准是”MSIE“字段，MSIE字段后面的数字为版本号，但同时还需要判断不包含”Maxthon“、”Theworld“、”360SE“、”TencentTraveler“、”SE“、”Avant“等字段（GreenBrowser没有明显标识）。移动设备还需要判断IEMobile+版本号。

	// 2、360浏览器

	// PC端：public static final String Params4 =
	// "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;Trident/4.0;InfoPath.2;.NET4.0C;.NET4.0E;.NETCLR2.0.50727;360SE)

	// 移动设备：暂无

	// 360浏览器的判断标准是”360SE”字段，没有版本表示。

	// 3、搜狗浏览器

	// PC端：public static final String Params4 =
	// "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;Trident/4.0;SE2.XMetaSr1.0;SE2.XMetaSr1.0;.NETCLR2.0.50727;SE2.XMetaSr1.0)

	// 移动设备：暂无

	// 搜狗浏览器的判断标准是”SE“、”MetaSr“字段，版本号为SE后面的数字。

	// 4、Chrome

	// PC端：Mozilla/5.0(Macintosh;IntelMacOSX10_7_0)AppleWebKit/535.11(KHTML,likeGecko)Chrome/17.0.963.56Safari/535.11

	// 移动设备：public static final String Params4 =
	// "Mozilla/5.0(Linux;U;Android2.2.1;zh-cn;HTC_Wildfire_A3333Build/FRG83D)AppleWebKit/533.1(KHTML,likeGecko)Version/4.0MobileSafari/533.1

	// PC端chrome浏览器的判断标准是chrome字段，chrome后面的数字为版本号；移动端的chrome浏览器判断”android“、”linux“、”mobilesafari“等字段，version后面的数字为版本号。

	// 5、Safari

	// PC端：public static final String Params4 =
	// "Mozilla/5.0(Macintosh;U;IntelMacOSX10_6_8;en-us)AppleWebKit/534.50(KHTML,likeGecko)Version/5.1Safari/534.50

	// 移动设备：public static final String Params4 =
	// "Mozilla/5.0(iPhone;U;CPUiPhoneOS4_3_3likeMacOSX;en-us)AppleWebKit/533.17.9(KHTML,likeGecko)Version/5.0.2Mobile/8J2Safari/6533.18.5

	// 由于Chrome及Nokia’sSeries60browser也使用WebKit内核，因此Safari浏览器的判断必须是：包含safari字段，同时不包含chrome等信息，确定后”version/“后面的数字即为版本号。在以上条件下包含Mobile字段的即为移动设备上的Safari浏览器。

	// 6、腾讯浏览器

	// PC端：public static final String Params4 =
	// "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;Trident/4.0;TencentTraveler4.0;.NETCLR2.0.50727)

	// 移动设备：public static final String Params4 =
	// "MQQBrowser/26Mozilla/5.0(Linux;U;Android2.3.7;zh-cn;MB200Build/GRJ22;CyanogenMod-7)AppleWebKit/533.1(KHTML,likeGecko)Version/4.0MobileSafari/533.1

	// 腾讯浏览器的判断标准是”TencentTraveler“或者”QQBrowser“，TencentTraveler或QQBrowser后面的数字为版本号。

	// 7、Firefox

	// PC端：public static final String Params4 =
	// "Mozilla/5.0(WindowsNT6.1;rv:2.0.1)Gecko/20100101Firefox/4.0.1

	// 移动设备：public static final String Params4 =
	// "Mozilla/5.0(Androdi;Linuxarmv7l;rv:5.0)Gecko/Firefox/5.0fennec/5.0

	// Firefox的判断标准是Firefox字段，firefox后面的数字为版本号。

	// 8、Theworld

	// PC端：public static final String Params4 =
	// "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;TheWorld)

	// 移动设备：暂无

	// Theworld浏览器的判断标准是”Theworld“字段，没有标示版本号。

	// 需要注意的是：Theworld2.x版本的User-Agent中没有”Theworld“的字段。

	// 9、遨游

	// PC端：public static final String Params4 =
	// "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;Maxthon2.0)

	// 移动设备：暂无

	// 遨游浏览器的判断标准是”Maxthon“，Maxthon后面的数字为版本号。

	// 10、Opera

	// PC端：public static final String Params4 =
	// "Opera/9.80(WindowsNT6.1;U;en)Presto/2.8.131Version/11.11

	// 移动设备：public static final String Params4 =
	// "Opera/9.80(Android2.3.4;Linux;Operamobi/adr-1107051709;U;zh-cn)Presto/2.8.149Version/11.10

	// opera浏览器的判断标准是opera字段，opera字段后面的数字为版本号。

	// 11、UC浏览器

	// UCWeb有多种模式浏览方式，对应的User-Agent为：

	// UC无

	// public static final String Params4 = "UCWEB7.0.2.37/28/999

	// UC标准

	// public static final String Params4 = "NOKIA5700/UCWEB7.0.2.37/28/999";

	// UCOpenwave

	// public static final String Params4 = "Openwave/UCWEB7.0.2.37/28/999";

	// UCOpera

	// public static final String Params4 =
	// "Mozilla/4.0(compatible;MSIE6.0;)Opera/UCWEB7.0.2.37/28/999";

	// UC浏览器的判断标准是”UCWEB“字段，UCWEB后面的数字为版本号。

}