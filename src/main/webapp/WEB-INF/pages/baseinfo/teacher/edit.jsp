<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>     
<%@ taglib uri="http://rick.xu.com/selecttag" prefix="r" %>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<title>添加教师信息</title>
</head>
<body>
<div>
	<!-- 添加教师基本信息 -->
	<form:form  class="form-horizontal" id="teacherForm" method="post" action="${ctx}edit" modelAttribute="teacher">
		<form:input path="id" type="hidden" name="id"/>
		<div class="form-group">
	 		<label class="col-sm-1 control-label">姓名<span class="red">*</span></label>
            <div class="col-sm-2">
              <form:input path="name" type="text" class="form-control validate[required]" name="name" placeholder="姓名" />
            </div>
            <label class="col-sm-1 control-label">性别</label>
            <div class="col-sm-3">
				    <r:select name="sex" key="sex"  value="${teacher.sex }"/> 
            </div>
            <label class="col-sm-2 control-label">出生日期</label>
            <div class="col-sm-2">
 				 <input path="birthday" class="form-control" id="birthday" name="birthday" placeholder="出生日期" readonly="true" value="<fmt:formatDate value="${teacher.birthday}" type="both" pattern="yyyy-MM-dd"/> "/>	 
            </div>  
    	 </div>  
    	 <div class="form-group">
    	 </div>   
    	 
    	 <div class="text-center">
	    	 <button type="submit" class="btn btn-primary btn-sm">保存</button>          
	    	 <button type="button" class="btn btn-sm" id="cancel">取消</button>
    	 </div>  
	</form:form>
</div>

<script>
	$(function() {
		//必须手动执行
		$('.selectpicker').selectpicker();
		
		$("#birthday").datetimepicker({
			 weekStart: 1,
		     todayBtn:  1,
		     autoclose: 1,
			 todayHighlight: 1,
			 startView: 2,
			 minView: 2,
			 forceParse: 0,
			 format: 'yyyy-mm-dd'
		 });
	});
</script>
</body>
</html>