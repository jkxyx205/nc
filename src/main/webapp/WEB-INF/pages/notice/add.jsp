<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

pageContext.setAttribute("ctx", basePath);
%>    
<html>
<head>
</head>
<body>
<style>
	.notice_content table {
		width:100%;

	}

	.notice_content td.tdLabel {
		width:80px;
		text-align: center;
		height: 50px;
	}
	
	.notice_content td.tdLabel span{
		color:red;
	}
 
</style>

<div class="notice_content">
<form method="POST" action="${ctx }notice/saveNotice" onsubmit="return checkContent();">
	<input type="hidden" name="id" value="${notice.id }"/> 
	<table>
		<tr>
			<td class="tdLabel">标题<span>*</span>：</td>
			<td><input type="text" class="form-control validate[required]" name="title" onkeypress="change=true;" value="${notice.title }"/></td>
		</tr>
		<tr>
			<td class="tdLabel" valign="top">正文<span>*</span>：</td>
			<td>
				<textarea cols="60" id="content" name="content" rows="10" name="content">
			 	${notice.content }
				</textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center" style="padding-top: 10px;">
			 <button type="submit" class="btn btn-primary btn-sm" name="noticeStatus" value="DRAFT">保存到草稿</button>          
	    	 <button type="submit" class="btn btn-success btn-sm" name="noticeStatus" value="PUBLISHED">保存并发布</button>          
	    	 <button type="button" class="btn btn-sm" onclick="back();">取消</button>
			</td>
		</tr>
	</table>
</form>	
</div>
<script type="text/javascript" src="${ctx }resources/plugin/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${ctx }resources/plugin/ckeditor/plugins/file/dialogs/file.js"></script>
<script type="text/javascript" src="${ctx }resources/plugin/ckeditor/plugins/file/plugin.js"></script>

<script type="text/javascript" src="${ctx}resources/plugin/jquery-layout/js/jquery-ui.js"></script> 
<script type="text/javascript" src="${ctx }resources/plugin/jQuery-File-Upload/js/jquery.fileupload.js"></script>

<script type="text/javascript">
	var change = false;
	$(function() {
		var id = '${notice.id }';
		
		var title = "添加公告";
		if(id)
			title = "修改公告";
		
		common.breadcrumb([{"text":"通知公告"},{"text":"我的公告","href":"${ctx}notice/gotoIndexPage"},{"text":title}]);
		CKEDITOR.replace( 'content');
		
		CKEDITOR.instances["content"].on("instanceReady", function () {  
	        //set keyup event  
	        this.document.on("keyup", function() {
	        	change = true;
	        });  
	        //and click event  
	        //this.document.on("click", AutoSave);  
	        //and select event  
	        //this.document.on("select", AutoSave);  
	    });  
		
		
		$(window).bind('beforeunload',function(){
			if(change)
				return '您输入的内容尚未保存，确定离开此页面吗？';
		});
	});
	
	function back() {
		location.href= '${ctx}notice/gotoIndexPage';
	}
	
	function checkContent() {
		var content = CKEDITOR.instances['content'].document.getBody().getText();
		if(!content || !content.trim()) {
			common.alert("正文不能为空！");
			return false;
		}
 
		$(window).unbind('beforeunload');
		
	}
	//$(window).unbind('beforeunload'); alert(CKEDITOR.instances['content'].getData());
	//.document.getBody().getText()
</script>
</body>
</html>