<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://rick.xu.com/selecttag" prefix="r" %>  
<html>
<head>
<title>教师信息</title>
</head>
<body>
<form id="query" class="form-horizontal" method="post">
    <div class="form-group">
	 		<label class="col-sm-1 control-label">标题</label>
            <div class="col-sm-3">
              <input type="text" class="form-control" name="title" placeholder="标题" />
            </div>
            <div class="col-sm-2">
				    <button name="search" type="button" class="btn btn-primary btn-sm">查询</button>
            </div>
    	 </div> 
</form>  

<table id="gridTable"></table>	
<div id="gridPager"></div>
 

<script type="text/javascript">
var $gird;

$(function() {
	common.breadcrumb([{"text":"通知公告"},{"text":"我的公告"}]);
	//必须在这个$(function() {//...});
	$gird = common.grid({
		id:"#gridTable",
		pager:"#gridPager",
		queryName:"notice.index",
		fileName:"teacherInfo",
		//data:{"name":"Rick.Xu"},
		colNames:['id','title','userId','notice_status','publishTime','operator'], 
		colModel:[
	             {name:'id',index:'id', width:60,hidden:true},
	             {name:'title',index:'title', width:250},
	             {name:'userId',index:'userId', width:100}, 
	             {name:'notice_status',index:'notice_status', align:'center',width:100}, 
	             {name:'publishTime',index:'publishTime',align:'center', width:100},
	             {name:'operator',index:'operator', width:100,align:'center',formatter:function(cellvalue, options, rowObject) {
	            		 return '<a target="_blank" href="'+ctx+'notice/gotoDetail/'+rowObject.id+'">查看</a>&nbsp;&nbsp;<a href="${ctx}gotoAdd/'+rowObject.id+'">编辑</a>&nbsp;&nbsp;<a href="javascript:void(0)" onclick="del(\''+rowObject.id+'\')">删除</a>';
	            
	             }}
	     ], 
	     sortname:"updateTime",
	     sortorder:'desc',
	     queryForm:"#query" //optional
	});
	
	
	$("#gridTable").jqGrid('navGrid', '#gridPager', {
	 });
	
	$("#gridTable").navButtonAdd('#gridPager',{title:"添加通知公告",caption:"",buttonicon:"ui-icon-plus", onClickButton: function(){ gotoAdd(); }, position:"first" });
	
});

function del(id) {
	common.confirm("确定要删除记录?",function() {
		 common.ajaxUpdate({
			queryName:"notice.deleteById",
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

	