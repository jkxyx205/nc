<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://rick.xu.com/selecttag" prefix="r" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

pageContext.setAttribute("ctx", basePath);
%>
<html>
<head>
</head>
<body>
<link href="${ctx }resources/css/form.css" rel="stylesheet" />
<link href="${ctx }resources/css/fileButton.css" rel="stylesheet" />
<style>
	#freeHour td{
		text-align: center;
	}
</style>
<div>
	<!-- 添加教师基本信息 -->
	<form class="nc_form">
		<table>
			<thead>
				<tr>
					<th colspan="7">教师信息登记表</th>
				</tr>
			</thead>
			<tbody>
			<tr>
				<td class="tdLabel">姓名<span>*</span>:</td>
				<td class="tdContent"><input type="text" class="validate[required]" name="name" placeholder="姓名" /></td>
				<td class="tdLabel">性别:</td>
				<td class="tdContent"><r:radio name="sex" key="sex" value="0"/></td>
				<td class="tdLabel">出生日期:</td>
				<td class="tdContent"><input type="text" id="birthday" name="birthday" placeholder="出生日期" readonly></td>
				<td rowspan="5" style="width:128px; cursor: pointer;">
					<span class="input-file" style="width:128px; height:128px;cursor: pointer;"><img id="headImg" src="${ctx }resources/css/images/head.png" style="width:128px;height:128px;cursor: pointer;"/><input type="file" name="head" id="fileupload"></span>
				</td>
			</tr>
			<tr>
				<td class="tdLabel">身份证:</td>
				<td class="tdContent"><input type="text"  name="personId" placeholder="身份证" /></td>
				<td class="tdLabel">民族:</td>
				<td class="tdContent"><r:select name="nation" key="nation"/></td>
				<td class="tdLabel">籍贯:</td>
				<td class="tdContent"><input name="hometown" type="text"/></td>
			</tr>
			<tr>
				<td class="tdLabel">最高学历:</td>
				<td class="tdContent"><r:select key="education" name="education"/></td>
				<td class="tdLabel">毕业院校:</td>
				<td class="tdContent"><input type="text" name="school" placeholder="毕业院校"></td>
				<td class="tdLabel">毕业日期:</td>
				<td class="tdContent"><input type="text" id="educationDate" name="educationDate" placeholder="毕业日期" readonly></td>
			</tr>
			<tr>
				<td class="tdLabel">手机号码<span>*</span>:</td>
				<td class="tdContent"><input type="text" class="validate[required] validate[custom[phone]]" name="tel" placeholder="手机号码" /></td>
				<td class="tdLabel">邮箱:</td>
				<td class="tdContent"><input type="text" id="email" name="email" class="validate[custom[email]]" placeholder="邮箱"></td>
				<td class="tdLabel">通讯地址:</td>
				<td class="tdContent"><input type="text" id="address" name="address" placeholder="通讯地址"></td>
			</tr>
			<tr>
				<td class="tdLabel">工作方式:</td>
				<td class="tdContent"><r:radio name="jobType" key="jobType" value="0"/></td>
				<td class="tdLabel">类型:</td>
				<td class="tdContent"><r:radio name="workWay" key="workWay" value="0"/></td>
				<td class="tdLabel">状态:</td>
				<td class="tdContent"><r:radio name="status" key="status" value="0"/></td>
			</tr>
			<tr>
				<td colspan="7" align="center">
					<span style="color:red;">授课教师往下填写*</span>
				</td>
			</tr>
			<tr>
				<td class="tdLabel">
					授课类型:
				</td>
				<td colspan="6" class="tdContent">
				<r:checkbox name="course" key="course" value="0;1;"/>
				</td>
			</tr>
			<tr>
				<td class="tdLabel">
					空闲时间:
				</td>
				<td colspan="6" style="padding: 0;">
					<table id="freeHour" style="border:none;">
						<tr>
							<td style="width:100px;border-left: none;border-top: none;"></td>
							<td style="border-top: none;">星期一</td>
							<td style="border-top: none;">星期二</td>
							<td style="border-top: none;">星期三</td>
							<td style="border-top: none;">星期四</td>
							<td style="border-top: none;">星期五</td>
							<td style="border-top: none;">星期六</td>
							<td style="border-right: none;border-top: none;">星期日</td>
						</tr>
						<tr>
							<td style="border-left: none;">09:00~11:30</td>
							<td><input type="checkbox" value="0" name="free" checked/></td>
							<td><input type="checkbox" value="1" name="free" checked/></td>
							<td><input type="checkbox" value="2" name="free" checked/></td>
							<td><input type="checkbox" value="3" name="free" checked/></td>
							<td><input type="checkbox" value="4" name="free" checked/></td>
							<td><input type="checkbox" value="5" name="free" checked/></td>
							<td><input type="checkbox" value="6" name="free" checked/></td>
						</tr>
						<tr>
							<td style="border-left: none; border-bottom: none;">13:20~16:00</td>
							<td style="border-bottom: none;"><input type="checkbox" value="7" name="free" checked/></td>
							<td style="border-bottom: none;"><input type="checkbox" value="8" name="free" checked/></td>
							<td style="border-bottom: none;"><input type="checkbox" value="9" name="free" checked/></td>
							<td style="border-bottom: none;"><input type="checkbox" value="10" name="free" checked/></td>
							<td style="border-bottom: none;"><input type="checkbox" value="11" name="free" checked/></td>
							<td style="border-bottom: none;"><input type="checkbox" value="12" name="free" checked/></td>
							<td style="border-bottom: none;"><input type="checkbox" value="13" name="free" checked/></td>
						</tr>
					</table>
				</td>
			</tr>
			</tbody>
		</table>
		<div style="text-align: center; margin-top: 10px; height: 20px;">
					<button type="submit" class="btn btn-primary btn-sm" name="submitType" value="0">保存返回</button>          
			    	<button type="submit" class="btn btn-success btn-sm" name="submitType" value="1">保存继续</button>          
			    	<button type="button" class="btn btn-sm" onclick="back();">取消</button>
		</div>
	</form>
</div>
<script type="text/javascript" src="${ctx }resources/js/common/form.js"></script>
<script type="text/javascript" src="${ctx}resources/plugin/jquery-layout/js/jquery-ui.js"></script> 
<script type="text/javascript" src="${ctx }resources/plugin/jQuery-File-Upload/js/jquery.fileupload.js"></script>
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
		
		$("#educationDate").datetimepicker({
			 weekStart: 1,
		     todayBtn:  1,
		     autoclose: 1,
			 todayHighlight: 1,
			 startView: 2,
			 minView: 2,
			 forceParse: 0,
			 format: 'yyyy-mm-dd'
		 });
		
		$('#fileupload').fileupload({
 	        url: "${ctx}teacher/uploadHead",
 	        dataType: 'json',
 	        formData:{"pid":1},
 	        done: function (e, data) {
 	        	$("#headImg").attr("src",data.result.visitUrl);
 	        },
 	        progressall: function (e, data) {
 	        }
 	    }).prop('disabled', !$.support.fileInput)
 	        .parent().addClass($.support.fileInput ? undefined : 'disabled');
	});
	
	function back() {
		location.href = '${ctx}teacher/list';
	}
</script>
</body>
</html>