<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://rick.xu.com/selecttag" prefix="r" %>  
<html>
<head>
<title>教师信息</title>
</head>
<body>
<ol class="breadcrumb">
<!-- 	<li><a href="#">信息管理</a></li> -->
	<li><span class="glyphicon glyphicon-home" aria-hidden="true"></span></li>
	<li>信息管理</li>
	<li class="active">教师信息</li>
</ol>
 
<div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">编辑</h4>
      </div>
      <div class="modal-body" id="teacher_content">
     		 
      </div>
    </div>
  </div>
</div>
<!-- validate[required] -->

<form id="queryTeacher" class="form-horizontal" method="post">
    <div class="form-group">
	 		<label class="col-sm-1 control-label">姓名<span class="red">*</span></label>
            <div class="col-sm-3">
              <input type="text" class="form-control" name="name" placeholder="姓名" />
            </div>
            <label class="col-sm-1 control-label">性别</label>
            <div class="col-sm-3">
				    <r:select name="sex" key="sex" multiple="true"/>
            </div>
            
            <div class="col-sm-2">
				    <button name="search" type="button" class="btn btn-primary btn-sm">查询</button>
				    <button name="reset" type="button" class="btn btn-warning btn-sm">重置</button>
<!-- 				    <button name="export" type="button" class="btn btn-warning btn-sm">导出</button> -->
            </div>
            
    	 </div> 
</form>  


<!-- <div class="toolbar">
	<button type="button" class="btn btn-primary btn-xs" style="float:right;" onclick="gotoAdd();">添加</button>
</div> -->


<table id="gridTable"></table>	
<div id="gridPager"></div>
 

<script type="text/javascript">
var $gird;

$(function() {
	//必须在这个$(function() {//...});
	$gird = common.grid({
		id:"#gridTable",
		pager:"#gridPager",
		queryName:"notice.index",
		fileName:"teacherInfo",
		//data:{"name":"Rick.Xu"},
		colNames:['id','title','content','userId','notice_status','publishTime'], 
		colModel:[
	             {name:'id',index:'id', width:60,hidden:true},
	             {name:'title',index:'title', width:100},
	             {name:'content',index:'content', width:100}, 
	             {name:'userId',index:'userId', width:100}, 
	             {name:'notice_status',index:'notice_status', width:100}, 
	             {name:'publishTime',index:'publishTime', width:100}
	     ], 
	     sortname:"updateTime",
	     sortorder:'desc',
	     queryForm:"#queryNotice" //optional
	});
	
	
	$("#gridTable").jqGrid('navGrid', '#gridPager', {
		   /*  edit : false,
		    add : false,
		    del : false,
		    search: false */
	 });
	
	$("#gridTable").navButtonAdd('#gridPager',{title:"添加通知公告",caption:"",buttonicon:"ui-icon-plus", onClickButton: function(){ gotoAdd(); }, position:"first" });
	
});

function gotoEdit(id) {
	$("#myModal").modal('show');	
	$("#teacher_content" ).load("${ctx}gotoEdit/"+id+"",function() {
		$("#cancel").on("click",function() {
			$("#myModal").modal('hide');	
		});
		
		$("#teacherForm").submit(function() {
			 $("#teacherForm").ajaxSubmit(
			    {
//	 			 url:"login",
	 			 beforeSubmit:function() {
	 				return $("#teacherForm").validationEngine("validate");
	 			 },
				 resetForm:true,
				 success:function(data) {
					 $("#myModal").modal('hide');
					 $gird.trigger("reloadGrid", [{page:1}]);
					 common.message("保存成功");
				}});
			 
			 return false; //非常重要，如果是false，则表明是不跳转，在本页上处理，也就是ajax，如果是非false，则传统的form跳转。
		});
	});
}

function del(id) {
	common.confirm("确定要删除记录?",function() {
		 common.ajaxUpdate({
			queryName:"teacher.deleteById",
			data:{id:id},
			success:function(data) {
				$gird.trigger("reloadGrid", [{page:1}]);
				common.message("删除成功");
			}
		}); 
	})
}

function gotoAdd() {
	location.href = "${ctx}gotoAdd"; 
}
</script>
</body>
</html>

	