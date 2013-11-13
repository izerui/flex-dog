<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网狗盘</title>
<style type="text/css" media="screen">
html,body,#content {
	height: 100%;
}

body {
	margin: 0;
	padding: 0;
	overflow: hidden;
}

#altContent { /* style alt content */
	
}
</style>
<script type="text/javascript" src="swfobject.js"></script>
<script type="text/javascript">
	swfobject.registerObject("myId", "10.0.0", "expressInstall.swf");
</script>
<script type="text/javascript">
	function settitle(title){
		document.title = title;
	}
</script>
</head>
<body>
	<div id="content">
		<object id="myId" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			width="100%" height="100%">
			<param name="movie" value="Index.swf" />
			<!--[if !IE]>-->
			<object type="application/x-shockwave-flash" data="Index.swf"
				width="100%" height="100%">
				<!--<![endif]-->
				<div id="altContent">
					<h1>Alternative content</h1>
					<p>
						<a href="http://www.adobe.com/go/getflashplayer"><img
							src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif"
							alt="Get Adobe Flash player" /></a>
					</p>
				</div>
				<!--[if !IE]>-->
			</object>
			<!--<![endif]-->
		</object>
	</div>
</body>
</html>