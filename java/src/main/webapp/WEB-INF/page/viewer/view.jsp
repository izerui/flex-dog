<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//String efilePathName = request.getParameter("PathName");
//if(StringUtils.isNotEmpty(efilePathName)){//这里用这个是为了解决efile表中path出现中文的问题
//	efilePathName = new String(efilePathName.getBytes("ISO-8859-1") ,"UTF-8");
//}

String filepath1 = basePath+"getVideoFile?flvfile="+request.getAttribute("filepath").toString()+request.getAttribute("filename").toString();
//System.out.println(filepath1);
%>
<html>
  <head>
    <title>电子文件显示页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script language="javascript">
	
		function document.oncontextmenu() { 
		    return false; 
		} 
		
		function nocontextmenu(){ 
		    if(document.all) {
		        event.cancelBubble=true;
		        event.returnvalue=false; 
		        return false; 
		    }
		}
		
		function onKeyDown(){
		    if ((event.altKey)||((event.keyCode==8)&&(event.srcElement.type!="text"&&event.srcElement.type!="textarea"&&event.srcElement.type!="password"))||((event.ctrlKey)&&((event.keyCode==78)||(event.keyCode==82)))||(event.keyCode==116)){
		        event.keyCode=0;
		        event.returnValue=false;
		    }
		}
</script>
  </head>
  
<body onkeydown="onKeyDown()" bgColor=#f2f6f9 bottomMargin=0 leftMargin=0 rightMargin=0 topMargin=0 marginheight="0" marginwidth="0">
<c:choose>

<c:when test="${ext=='flv'||ext=='f4v'||ext=='m4v'||ext=='mov'||ext=='mp3'||ext=='mp4'||ext=='ogv'||ext=='aac'||ext=='m4a'||ext=='f4a'||ext=='ogg'||ext=='oga'}"><!-- flv -->

<!-- 
	<script type='text/javascript' src='${pageContext.request.contextPath}/resource/efile/player/jwplayer.js'></script>
	<div id='mediaspace'>This text will be replaced</div>
	<script type='text/javascript'>
	  jwplayer('mediaspace').setup({
	    'flashplayer': '${pageContext.request.contextPath}/resource/efile/player/player.swf',
	    'file': '${path}${PathName}',
	    'controlbar': 'bottom',
	    'width': '100%',
	    'height': '100%'
	  });
	</script>
	
-->
	<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' width='100%' height='100%' id='single1' name='single1'>
		<param name='movie' value='${pageContext.request.contextPath}/assets/player/ffff.swf'>
		<param name='allowfullscreen' value='true'>
		<param name='allowscriptaccess' value='always'>
		<param name='wmode' value='transparent'>
		<param name='flashvars' value='file=<%= filepath1 %>&provider=http&stretching=fill&title=${filename}&backcolor=000000&frontcolor=ffffff&lightcolor=cc9900&screencolor=ffffff&skin=${pageContext.request.contextPath}/assets/player/skins/beelden.zip'>
		<embed
		type='application/x-shockwave-flash'
		id='single2'
		name='single2'
		src='${pageContext.request.contextPath}/assets/player/ffff.swf'
		width='100%'
		height='100%'
		bgcolor='undefined'
		allowscriptaccess='always'
		allowfullscreen='true'
		wmode='transparent'
		flashvars='file=<%= filepath1 %>&provider=http&stretching=fill&title=${filename}&backcolor=000000&frontcolor=ffffff&lightcolor=cc9900&screencolor=ffffff&skin=${pageContext.request.contextPath}/assets/player/skins/beelden.zip'
		/>
	</object>



</c:when>

<c:when test="${ext=='wma'||ext=='avi'||ext=='asf'||ext=='mpg'||ext=='mpeg'||ext=='wmv'}"><!-- media播放 -->
<input id="path" type="hidden" value="${path}${PathName}">
<div style="height:40px; width:100%; padding:10px 20px 10px 20px;">
<input name="custom" class="custom" type="button" onClick="customservice();" value="普通模式播放">&nbsp;
<input name="mms" class="mms" type="button" onClick="mmsservice();" value="MMS 流媒体播放"> 
&nbsp;&nbsp;&lt;-- 如果您安装了mms服务，请点击该按钮,以便更好的浏览大数据量的媒体文件</div>
<object id="movie" height="90%" width="90%" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6">
          <param name="AutoStart" value="-1" />
          <!--是否自动播放-->
          <param name="Balance" value="0" />
          <!--调整左右声道平衡,同上面旧播放器代码-->
          <param name="enabled" value="-1" />
          <!--播放器是否可人为控制-->
          <param name="EnableContextMenu" value="0" /><!--控制快捷菜单: x=1，允许使用右键菜单; x=0，禁用右键菜单。--> 
          <!--是否启用上下文菜单-->
          <param name="url" value="" />
          <!--播放的文件地址-->
          <param name="PlayCount" value="1" />
          <!--播放次数控制,为整数-->
          <param name="rate" value="1" />
          <!--播放速率控制,1为正常,允许小数,1.0-2.0-->
          <param name="currentPosition" value="-1" />
          <!--控件设置:当前位置-->
          <param name="currentMarker" value="0" />
          <!--控件设置:当前标记-->
          <param name="defaultFrame" value="-1" />
          <!--显示默认框架-->
          <param name="invokeURLs" value="-1" />
          <!--脚本命令设置:是否调用URL-->
          <param name="baseURL" value="" />
          <!--脚本命令设置:被调用的URL-->
          <param name="stretchToFit" value="0" />
          <!--是否按比例伸展-->
          <param name="volume" value="50" />
          <!--默认声音大小0%-100%,50则为50%-->
          <param name="mute" value="0" />
          <!--是否静音-->
          <param name="uiMode" value="Full" />
          <!--播放器显示模式:Full显示全部;mini最简化;None不显示播放控制,只显示视频窗口;invisible全部不显示-->
          <param name="windowlessVideo" value="0" />
          <!--如果是0可以允许全屏,否则只能在窗口中查看-->
          <param name="fullScreen" value="0" />
          <!--开始播放是否自动全屏-->
          <param name="enableErrorDialogs" value="-1" />
          <!--是否启用错误提示报告-->
          <param name="SAMIStyle" value="value" />
          <!--SAMI样式-->
          <param name="SAMILang" value="value" />
          <!--SAMI语言-->
          <param name="SAMIFilename" value="value" />
          <!--字幕ID-->
    </object>
<script language="javascript">
function mmsservice(){
    document.getElementById("custom").className = "mms";
	document.getElementById("mms").className = "custom";
    var path = document.getElementById("path").value;
	var mms = "mms"+path.substring(3,path.length);
	movie.url = mms;
}

function customservice(){
    document.getElementById("custom").className = "custom";
	document.getElementById("mms").className = "mms";
    var path = document.getElementById("path").value;
	movie.url = path;
}

function init(){
    var path = document.getElementById("path").value;
    var newPath = path.replace("/./","/");
	movie.url = newPath;
}
init();
</script>
</c:when>

<c:when test="${ext=='mht'||ext=='MHT'||ext=='htm'||ext=='HTM'||ext=='html'||ext=='HTML'}">
<script language="javascript">
function redirect(){
	self.location="${path}${PathName}";
}
redirect();
</script>
</c:when>

<c:otherwise>
	暂无格式浏览！
</c:otherwise>
</c:choose>
</body>
</html>
