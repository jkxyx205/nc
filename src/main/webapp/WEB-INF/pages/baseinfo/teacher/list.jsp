<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://rick.xu.com/selecttag" prefix="r" %>  
<html>
<head>
</head>
<body>
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
<form id="queryTeacher" class="form-horizontal" method="post">
    <div class="form-group">
	 		<label class="col-sm-1 control-label">姓名<span class="red">*</span></label>
            <div class="col-sm-3">
              <input type="text" class="form-control" name="name" placeholder="姓名" />
            </div>
<!--             <label class="col-sm-1 control-label">性别</label> -->
<!--             <div class="col-sm-3"> -->
<%-- 				    <r:select name="sex" key="sex" multiple="true"/> --%>
<!--             </div> -->
            
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
	common.breadcrumb([{"text":"信息管理"},
	                   {"text":"教师信息"}
	                  ]);
	//必须在这个$(function() {//...});
	$gird = common.grid({
		id:"#gridTable",
		pager:"#gridPager",
		queryName:"teacher.list",
		fileName:"teacherInfo",
		//data:{"name":"Rick.Xu"},
		colNames:['id','name','sex','education','tel','email','jobType','workWay','operator'], 
		colModel:[
	             {name:'id',index:'id', width:60,hidden:true},
	             {name:'name',index:'name', width:100}, 
	             {name:'sex',index:'sex', width:100,align:'center'}, 
	             {name:'education',index:'education', width:100}, 
	             {name:'tel',index:'tel', width:100}, 
	             {name:'email',index:'email', width:150}, 
	             {name:'jobType',index:'jobType', width:100,align:'center'}, 
	             {name:'workWay',index:'workWay', width:100}, 
	             {name:'operator',index:'operator', width:80,exp:false,align:'center',formatter:function(cellvalue, options, rowObject) {
	            	 return "<a href='javascript:void(0)' onclick='gotoEdit("+rowObject.id+");'>编辑</a>&nbsp;<a href='javascript:void(0)' onclick='del("+rowObject.id+");'>删除</a>";
	             }} 
	     ], 
/* 	     sortname:"number",
	     sortorder:'asc', */
	     queryForm:"#queryTeacher" //optional
	});
	
	
	$("#gridTable").jqGrid('navGrid', '#gridPager', {
		   /*  edit : false,
		    add : false,
		    del : false,
		    search: false */
	 });
	
	$("#gridTable").navButtonAdd('#gridPager',{title:"添加记录",caption:"",buttonicon:"ui-icon-plus", onClickButton: function(){ gotoAdd(); }, position:"first" });
	
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