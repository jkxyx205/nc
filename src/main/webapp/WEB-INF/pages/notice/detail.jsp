<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>    
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", basePath);
%>    
<html>
<head>
<link rel="Shortcut Icon" href="${ctx }resources/css/images/favicon.ico">
<link rel="stylesheet" href="${ctx}resources/plugin/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}resources/plugin/jquery-top/jquery.top.css">
<style type="text/css">
	.noticeContent {
		width:800px;
		margin: 0 auto;
	}
	h1 {
		font-size:18px;
	}
</style>
</head>
<body>
<div class="noticeContent">
<img src="http://j.xhd.cn/r/cms/suzhou/default/index/img/logo1.png">
<ol class="breadcrumb" style="margin: 0;">
<li><span class="glyphicon glyphicon-home" aria-hidden="true"></span></li>
<li>通知公告</li><li class="active">公告详情</li>
</ol>
	<div style="border: 1px solid #BCBCBC; border-top: 2px solid #0D3387">
		<div style="text-align: center;">
			<h1 class="title">${notice.title }</h1>
			<span style="color:#BCBCBC;font-size: 11px;">作者：${notice.userId } 发布时间：<fmt:formatDate value="${notice.publishTime }" pattern="yyyy年MM月dd日 "/>
			<a href="javascript:void()" style="font-size:13px;" onclick="window.close();">[关闭]</a></span>
		</div>
		<div style="margin: 10px 30px; border-top: 1px solid #BCBCBC; padding-top:20px;">${notice.content }</div>
	</div>
</div>


<script type="text/javascript" src="${ctx}resources/js/common/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${ctx}resources/js/common/common.js"></script>
<script type="text/javascript" src="${ctx}resources/plugin/jquery-top/jquery.top.js"></script>

<script>
$(function() {
	$.top({top:window.screen.availHeight-300,down:true});
});
</script>
</body>
</html>
