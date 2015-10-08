<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Quartz Config</title>
</head>
<body>
<div class="modal fade" id="exampleModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Cron Expression Config</h4>
      </div>
      <div class="modal-body">
      <form action="quartz/confJob" id="conf" method="post">
       	<input type="hidden" name="id" id="id"/>
      	<table>
      		<tr>
      			<td><span>Job Name</span></td>
      			<td><span id="jobName"></span></td>
      		</tr>
      		<tr>
      			<td><span>Cron Expression</span>&nbsp;</td>
      			<td><input name="cronExpression" id="cronExpression"/></td>
      		</tr>
      	</table>
      </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="saveChanges()">Save changes</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div>
<table id="gridTable"></table>	
<div id="gridPager"></div>
</div>
<script type="text/javascript">
var $gird;
$(function() {
		common.breadcrumb([{"text":"管理管理"},
	                   {"text":"任务管理"}
	                  ]);
	
	
		 $gird = common.grid({
					id:"#gridTable",
					pager:"#gridPager",
					queryName:"scheduleJobList",
					colNames:['ID','JOB_NAME','JOB_GROUP','CRON_EXPRESSION','JOB_STATUS','JOB_STATUS_VALUE','Operation'], //i18n
					colModel:[
							 {name:'id',index:'id', hidden:true,key:true},
		                     {name:'job_name',index:'job_name', width:60},
		                     {name:'job_group',index:'job_group', width:100},
		                     {name:'cron_expression',index:'cron_expression', width:50}, 
		                     {name:'job_status',index:'job_status', width:50},
		                     {name:'job_status_value',index:'job_status_value',hidden:true},
		                     {name:'operation',index:'operation', width:100,formatter:function(cellvalue,options, rowObject) {
		                    	 var id = rowObject["id"];
		                    	 var btn = ["<button onclick='changeExpression("+id+")'>设置参数</button>"];
		                    	 if(rowObject['job_status_value'] == "0") {
		                    		 btn.push("<button onclick='changJobStatus("+id+",1,this)'>开启</button>");
		                    	 } else if(rowObject['job_status_value'] == "1") {
		                    		 btn.push("<button onclick='changJobStatus("+id+",0,this)'>暂停</button>");
		                    	 }
		                    	 
		                    	 btn.push("<button onclick='executeNow("+id+")'>立即执行</button>");
		                    	 return btn.join("");
		                     	}
		                     }
		             ], 
		             sortname:"ID",
		             sortorder:'desc',
		             multiselect:false
		});
	 
  }); 

	function changJobStatus(id,status,obj) {
		var url = "";
		if("1" == status) {
			url = "quartz/resumeJob/" + id;
			btnText = "开启";
		} else if ("0" == status){
			url = "quartz/pauseJob/" + id;
			btnText = "暂停";
		}
			
		if(url) {
			$.ajax({
				url:url,
				success:function(data) {
					if(data=="success") {
						$gird.trigger("reloadGrid", [{page:1}]);
						common.message("操作成功");
					}
				},
				error:function(data) {
					
				}
			});
		}
	}
	
	function changeExpression(id) {
		var rowDatas = $gird.jqGrid('getRowData', id);
		$("#id").val(id);
	 	$("#jobName").text(rowDatas["job_name"]);
	  	$("#cronExpression").val(rowDatas["cron_expression"]);
	  	
		$("#exampleModal").modal('show');
	}
	
	function executeNow(id) {
	 $.ajax({
			url:"quartz/runJobNow/" + id,
			success:function(data) {
				common.message("操作成功");
			},
			error:function(data) {
				
			}
		});
	}
	
	function saveChanges() {
		 $("#conf").ajaxSubmit(
		    {
			 beforeSubmit:function() {
				//return $("#login").valid();
			 },
			 resetForm:true,
			 success:function(data) {
				$("#exampleModal").modal('hide');
				common.message("操作成功");
 				$gird.trigger("reloadGrid", [{page:1}]);
			}});
	}
</script>
</body>
</html>