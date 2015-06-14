<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

pageContext.setAttribute("ctx", basePath);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>
        <%-- <sitemesh:write property="title" />  --%>
        新航道内部管理系统
</title>
<link rel="Shortcut Icon" href="${ctx }resources/css/images/favicon.ico">
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="${ctx}resources/plugin/bootstrap/css/bootstrap.min.css">
<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet" href="${ctx}resources/plugin/bootstrap/css/bootstrap-theme.min.css">

<link rel="stylesheet" href="${ctx}resources/plugin/bootstrap-select/css/bootstrap-select.min.css" type="text/css">

<link rel="stylesheet" href="${ctx}resources/plugin/bootstrap_datetimepicker/css/bootstrap-datetimepicker.min.css" media="screen">

<!-- css jqgrid start-->
<link rel="stylesheet"  href="${ctx}resources/plugin/jqueryUI/jquery-ui.min.css" />
<link rel="stylesheet"  href="${ctx}resources/plugin/jqGrid/css/ui.jqgrid.css" />

<%-- <link rel="stylesheet" href="${ctx}resources/plugin/bootstrap_validate/css/formValidation.min.css" media="screen"> --%>
<link rel="stylesheet" href="${ctx}resources/plugin/jQuery-Validation-Engine-2.6.2/css/validationEngine.jquery.css" media="screen">

<link href="${ctx}resources/plugin/jquery-loadmask-0.4/jquery.loadmask.css" rel="stylesheet" type="text/css" />

<link href="${ctx}resources/plugin/bootsrap_messenger/css/messenger.css" rel="stylesheet" type="text/css" />
<link href="${ctx}resources/plugin/bootsrap_messenger/css/messenger-theme-future.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" href="${ctx}resources/css/common/bootstrap_custom.css" media="screen">
<script>var ctx='${ctx}';</script>
<script type="text/javascript" src="${ctx}resources/js/common/jquery-1.11.0.min.js"></script>
</head>
<body>
<div class="container" id="w_container">
	<!-- <div class="page-header">
         
		  <div class="row">
		  <div class="col-md-4 col-md-offset-8">
		  	<table>
			<tr>
				<td colspan="2"><a href="?lang=en">English</a>&nbsp;&nbsp;<a href="?lang=zh">中文简体</a>&nbsp;&nbsp;<a href="?lang=hk">中文繁體</a>
				&nbsp;&nbsp;<a href="?lang=fr">Français</a></td>
			</tr>
			</table>
		  </div> 

		</div>
 		<hr/> -->
		<div id="nav_div" >	
			<nav class="navbar" style="border:0; width:920px; margin: 0 auto; ">
			 <div style="padding-bottom: 5px;">
		   	<img src="http://j.xhd.cn/r/cms/suzhou/default/index/img/logo1.png">
		  </div>
		 
		  <div class="container-fluid">
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		      <ul class="nav navbar-nav">
				<li><a href="${ctx}index">首页</a></li>
		        <li class="dropdown">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown">信息管理<span class="caret"></span></a>
		          <ul class="dropdown-menu">
		            <li><a href="${ctx}teacher/list">教师信息</a></li>
		            <li><a href="#">学生信息</a></li>
		            <li><a href="#">班级信息</a></li>
		            <li class="divider"></li>
		            <li><a href="#">教材信息</a></li>
		            <li><a href="#">课程信息</a></li>
		            <li><a href="#">考试成绩</a></li>
		            
		          </ul>
		        </li>
		         <li class="dropdown">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown">助教工作<span class="caret"></span></a>
		          <ul class="dropdown-menu" >
		            <li><a href="${ctx}teacher/list">助教排班</a></li>
		            <li><a href="${ctx}teacher/list">教学排课</a></li>
		            <li><a href="${ctx}teacher/list">学习跟踪</a></li>
		            <li><a href="${ctx}teacher/list">学生缺勤</a></li>
		          </ul>
		        </li>
		         <li class="dropdown">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown">教师工作<span class="caret"></span></a>
		          <ul class="dropdown-menu" >
		            <li><a href="${ctx}teacher/list">我的课表</a></li>
		            <li><a href="${ctx}teacher/list">我的缺勤</a></li>
		          </ul>
		        </li>
		         <li class="dropdown">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown">通知公告<span class="caret"></span></a>
		          <ul class="dropdown-menu" >
		            <li><a href="${ctx}notice/list/1">公告列表</a></li>
		            <li><a href="${ctx}notice/gotoIndexPage">发布公告</a></li>
		          </ul>
		        </li>
		        <li><a href="${ctx}doc/index">知识库</a></li>
		        <li><a href="${ctx}demo/list">我的空间</a></li>
		        <li><a href="${ctx}demo/list">报表管理</a></li>
		        <li class="dropdown">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown">系统管理<span class="caret"></span></a>
		          <ul class="dropdown-menu">
		            <li><a href="${ctx}teacher/list">密码维护</a></li>
		            <li><a href="#">用户管理</a></li>
		            <li><a href="#">角色管理</a></li>
		            <li><a href="#">授权管理</a></li>
		            <li><a href="#">日志管理</a></li>
		            <li><a href="${ctx }quartz">任务管理</a></li>
		          </ul>
		        </li>
		        
		      </ul>
		    </div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid -->
			<ol class="breadcrumb">
				<li><span class="glyphicon glyphicon-home" aria-hidden="true"></span></li>
			</ol>
		</nav>
		</div>
 		<div id="nav_height"></div>
		<sitemesh:write property='body' />
</div>
<script type="text/javascript" src="${ctx}resources/plugin/jquery-loadmask-0.4/jquery.loadmask.min.js"></script>
 

<!-- js jqgrid start-->
<script type="text/javascript" src="${ctx}resources/plugin/jqueryUI/jquery-ui.min.js"></script>
	<!-- i18n -->
<script type="text/javascript" src="${ctx}resources/plugin/jqGrid/js/i18n/grid.locale-zh.js"></script>
<script type="text/javascript" src="${ctx}resources/plugin/jqGrid/js/jquery.jqGrid.src.js"></script>
<!-- js jqgrid end-->

<script type="text/javascript" src="${ctx}resources/js/common/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}resources/js/common/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}resources/js/common/jquery.form2json.js"></script>
<script type="text/javascript" src="${ctx}resources/js/common/json2.js"></script>
<script type="text/javascript" src="${ctx}resources/js/common/jquery.placeholder.js"></script>


<script type="text/javascript" src="${ctx}resources/js/common/lang.js"></script>

<script src="${ctx}resources/plugin/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript" src="${ctx}resources/plugin/bootstrap-select/js/bootstrap-select.js"></script>
<script type="text/javascript" src="${ctx}resources/plugin/bootstrap-select/js/i18n/defaults-zh_CN.min.js"></script>

<script type="text/javascript" src="${ctx}resources/plugin/bootstrap_datetimepicker/js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}resources/plugin/bootstrap_datetimepicker/js/locales/bootstrap-datetimepicker.zh.js" charset="UTF-8"></script>

<%-- <script type="text/javascript" src="${ctx}resources/plugin/bootstrap_validate/js/formValidation.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}resources/plugin/bootstrap_validate/js/framework/bootstrap.min.js" charset="UTF-8"></script> 
<script type="text/javascript" src="${ctx}resources/plugin/bootstrap_validate/js/language/zh.js" charset="UTF-8"></script> --%>
<script type="text/javascript" src="${ctx}resources/plugin/jQuery-Validation-Engine-2.6.2/js/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}resources/plugin/jQuery-Validation-Engine-2.6.2/js/jquery.validationEngine.min.js"></script>
 
<script type="text/javascript" src="${ctx}resources/plugin/bootstrap_modal/jquery.bootstrap.teninedialog.v3.min.js"></script>
 
<script type="text/javascript" src="${ctx}resources/plugin/bootsrap_messenger/js/messenger.js"></script> 
<script type="text/javascript" src="${ctx}resources/plugin/bootsrap_messenger/js/messenger-theme-future.js"></script> 

<script type="text/javascript" src="${ctx }resources/plugin/jquery.nicescroll/js/jquery.nicescroll.min.js"></script> 

<script type="text/javascript" src="${ctx}resources/js/common/dictionary.js"></script>

<script type="text/javascript" src="${ctx}resources/js/common/common.js"></script>
<script>
	$(function() {
		$('input[placeholder]').placeholder();
		$("form").validationEngine(); 
		$("#w_container").unmask();
		Messenger.options = {
			    extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-center',
			    theme: 'future'
		};
		$(document).ajaxStart(function() {
			$("#w_container").mask("Waiting...");
		}).ajaxStop(function() {
			$("#w_container").unmask();
		});
		
		$(document).keydown(function(event) {
            if (event.keyCode == "13") {
          
            	var activeElement = document.activeElement.tagName;
            	if (!(activeElement === "INPUT" || activeElement === "SELECT")) {
                	return;
                }
            	var searchBtn = $("[name=search]");
            	if (searchBtn.length > 0) {
            		searchBtn.click();
                	event.preventDefault();
            	}
            	
            }
        });
	});
</script>
</body>
</html>