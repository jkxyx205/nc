<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://rick.xu.com/selecttag" prefix="r" %>  
<html>
<head>
<title>添加教师信息</title>
</head>
<body>
<div>
	<!-- 添加教师基本信息 -->
	<form  class="form-horizontal" id="teacherForm" method="post" action="${ctx}save">
		<div class="form-group">
	 		<label class="col-sm-1 control-label">姓名<span class="red">*</span></label>
            <div class="col-sm-3">
              <input type="text" class="form-control validate[required]" name="name" placeholder="姓名" />
            </div>
            <label class="col-sm-1 control-label">性别</label>
            <div class="col-sm-2">
				    <r:select name="sex" key="sex"/>
            </div>
            <label class="col-sm-2 control-label">出生日期</label>
             <div class="col-sm-3">
				    <input type="text" class="form-control" id="birthday" name="birthday" placeholder="出生日期" readonly>	
            </div>  
    	 </div>  
    	 <div class="form-group">
	 		 
            <label class="col-sm-1 control-label">状态</label>
            <div class="col-sm-2">
				    <r:select name="status" key="status"/>
            </div>
    	 </div>  
    	 
    	 
    	 <div class="form-group">
    	 <div class="text-center">
	    	 <button type="submit" class="btn btn-primary btn-sm" name="submitType" value="0">保存返回</button>          
	    	 <button type="submit" class="btn btn-success btn-sm" name="submitType" value="1">保存继续</button>          
	    	 <button type="button" class="btn btn-sm" onclick="back();">取消</button>
    	 </div>  
    	 </div>   
	</form>
</div>
<script>
	$(function() {
		common.breadcrumb([{"text":"信息管理"},
		                   {"text":"教师信息","href":"list"},
		                   {"text":"添加"}
		                  ]);
		
		
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
	
	function back() {
		location.href = '${ctx}list';
	}
</script>
</body>
</html>