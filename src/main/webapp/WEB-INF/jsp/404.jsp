<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>

<script>
	setTimeout(function(){
		window.location.href="http://www.baidu.com";
	},5000);
	
	setInterval(function(){
		var v=parseInt(document.getElementById("second").innerText);
		document.getElementById("second").innerText=v-1;
	},1000);
</script>

</head>
<body>
	您要找的页面去火星啦，要不然去首页看看？
	倒计时：<span id="second">5</span>秒
</body>
</html>