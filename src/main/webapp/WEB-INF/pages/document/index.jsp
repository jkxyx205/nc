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
<link href="${ctx }resources/css/zTreeStyle.css" rel="stylesheet" />
<link href="${ctx }resources/css/fileButton.css" rel="stylesheet" />  


<link rel="stylesheet" type="text/css" href="${ctx }/resources/plugin/jquery-fancybox/source/jquery.fancybox.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/resources/plugin/jquery-fancybox/source/helpers/jquery.fancybox-thumbs.css" />

<style type="text/css">
/* 	#verticalCenteringWrapper {
		background:	#777; /* for easier visualization */
		overflow:	visible !important; /* override Layout CSS added due to #container.min-height */
		width:		920px;
		height:		0;
		top:		100px;
		position:	absolute;
		margin-top: 35px;
	}
	#container {
		background:	#999;
		height:		410px;
		/*margin:		-250px auto 0;*/ /* top-margin = height/2 */
		width:		100%;
		max-width:	920px;
		min-width:	700px;
		_width:		700px; /* min-width for IE6 */
		position:	relative;
	} */
	.pane {
		display:	none; /* will appear when layout inits */
	}
	
	#titleBar,.fileList {
		width:100%;
		font-size: 11px;
	}
	
	#titleBar {
		background-color: yellow;
	}
	
	#container a {
		cursor: pointer;
	}
	
	.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	
	</style>
<!-- ui-search-toolbar -->

 
	<div id="container" style="height:400px;">
		<div id="navtree" style="float:left; width:220px; border:1px solid #BCBCBC;height:100%; border-right: none; padding:2px; overflow: auto;">
			<ul id="docTree" class="ztree" style="margin-top: 0; width: 170px; height:80px; margin:0;padding:0;">
			</ul>
		</div>
		<div style="float:left; width:700px;border:1px solid #BCBCBC;height:100%;padding:2px;">
			<span id="titleBar"></span>
			<div style="float:right;margin-right: 20px;">
			<span class="input-file" onclick="query();">查询</span>	
			<span class="input-file">上传<input type="file" multiple id="fileupload"></span>
			</div>
			<table id="gridTable"></table>	
			<div id="gridPager"></div>
		</div>
	</div>
 
 
<%-- <script type="text/javascript" src="${ctx}resources/plugin/jquery-layout/js/jquery.layout.js"></script>--%>


<script type="text/javascript" src="${ctx }resources/plugin/zTree/js/jquery.ztree.core-3.5.min.js"></script> 
<script type="text/javascript" src="${ctx }resources/plugin/zTree/js/jquery.ztree.excheck-3.5.min.js"></script> 
<script type="text/javascript" src="${ctx }resources/plugin/zTree/js/jquery.ztree.exedit-3.5.js"></script> 
<script type="text/javascript" src="${ctx }resources/plugin/zTree/custom/tree.js"></script>
 
<script type="text/javascript" src="${ctx}resources/plugin/jquery-layout/js/jquery-ui.js"></script> 
<script type="text/javascript" src="${ctx }resources/plugin/jQuery-File-Upload/js/jquery.fileupload.js"></script>
 
<script type="text/javascript" src="${ctx }resources/plugin/jquery.nicescroll/js/jquery.nicescroll.min.js"></script> 

<script type="text/javascript" src="${ctx }resources/plugin/jquery-fancybox/source/jquery.fancybox.pack.js"></script>	
<script type="text/javascript" src="${ctx }resources/plugin/jquery-fancybox/source/helpers/jquery.fancybox-thumbs.js"></script>	

 
<script type="text/javascript">
   var pid;
   var $grid;
   var treeObject;
	$(function() {
		common.breadcrumb([ {"text":"知识库"}]);
		//$('#container').layout({ applyDefaultStyles: true });
	 	var setting = {
	         data: {         
	             simpleData: {
	                 enable: true
	             }
	         },
	         view: {
					addHoverDom: addHoverDom,
					removeHoverDom: removeHoverDom,
					selectedMulti: false
				},
	         edit: {
	        	enable: true,
				editNameSelectAll: true,
				showRemoveBtn: function(treeId, treeNode) {
					return treeNode.id=="1" ? false : true;
				},
				showRenameBtn: function(treeId, treeNode) {
					return true;
				}
				},
	         callback: {
	        	 onClick:function(event,treeId,treeNode) {
	        		 id = treeNode.id;
	        		 setLabel(id);
	        		 upload(id);
	        		 search(id);
	        	 },beforeRename :function(reeId, treeNode, newName, isCancel) {
	        		 renameNode(treeNode,newName);
	        	 },beforeRemove :function(treeId,treeNode) {
	        		common.confirm("确认要删除此文件夹吗？",function() {
	        			treeObject.removeNode(treeNode,false);
	        		 	deleteNode(treeNode.id);
	        		});
	        		return false;
	        	 }
	         }
	 		};
 		$("#docTree").tree({
 				//url:"getAbc",
 				data: {name:"张三"},
 				//queryName:"tree.document",        //从后台获取节点
 				values:"1",
 				setting:setting,
 				//zNodes:zNodes,                 //设置zNodes简单json，不从后台获取
 				//async:true,
 				initQueryName:"tree.document.init" //异步下默认展开节点集合
 		});
 		
 		treeObject = $("#docTree").tree("getTreeObject");
 		var rootNode = treeObject.getNodeByParam("id", 1, null);
 		$("#titleBar").text(rootNode.name);
 		
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='add node' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			 if (btn) btn.bind("click", function(){
				//添加文件夹节点
				var nid = addNode(treeNode);
				treeObject.addNodes(treeNode, {id:nid, pId:treeNode.id, name:"新建文件夹"});
				
				//var ntreeNode = treeObject.getNodeByParam("id", nid, null);
				//treeObject.selectNode(ntreeNode);
				return false;
			}); 
		};
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
		};
 		
		/******************************************文件操作***********************************************/
 		$('#fileupload').fileupload({
 	        url: "${ctx}upload/file",
 	        dataType: 'json',
 	        formData:{"pid":1},
 	        done: function (e, data) {
 	            $.each(data.result, function (index, file) {
 	            	var row = {
 	            			'id':file.id,
 	            			'title':file.name,
 	            			'doc_type':file.contentType,
 	            			'file_type':'1',
 	            			'doc_size':file.size,
 	            			'updateTime':file.updateTime
 	            			
 	            	};
 	       	
 	            	$grid.jqGrid("addRowData",file.id,row,"last");
 	            }); 
 	        },
 	        progressall: function (e, data) {
 	        	//console.log("................processing");
 	            //var progress = parseInt(data.loaded / data.total * 100, 10);
 	            //$('#process_span').text(progress + '%');
 	            
 	           /*  $('#process_span').css(
 	                'width',
 	                progress + '%'
 	            );  */
 	        }
 	    }).prop('disabled', !$.support.fileInput)
 	        .parent().addClass($.support.fileInput ? undefined : 'disabled');
 		
 		$grid = common.grid({
 			datatype:'local',
 			width:680,
 			height:330,
 			id:"#gridTable",
 			//data:{pid:'1'},
 			queryName:"tree.document.getfiles",
 			colNames:['id','名称','修改日期','类型','大小','文件类型'], 
 			colModel:[
 		             {name:'id',index:'id',hidden:true},
 		             {name:'title',index:'title', width:200,searchoptions:{sopt:['cn']},formatter:function(cellvalue, options, rowObject) {
 		            	var title =  "<img src=\"${ctx}resources/css/images/"+getImage(rowObject.file_type,rowObject.title)+"\"/>&nbsp;<span style='cursor:pointer;' ondblclick=\"operator("+rowObject.id+","+rowObject.file_type+");\">"+cellvalue+"</span>";
 		            	
 		            	if(rowObject.file_type == "1") {
 		            		title += "<img style='float:right; cursor:pointer' onclick=\"deleteFile('"+rowObject.id+"')\" src='${ctx}resources/css/images/remove.jpg'/>";
 		            		
 		            		if(rowObject.doc_type.indexOf("image") == 0) {
 	 		            		title += '<a style="float:right; cursor:pointer" class="fancybox-thumbs" data-fancybox-group="thumb" href="${ctx}'+rowObject.filePath+'"><img src="${ctx}/resources/css/images/preview.png"/></a>';
 	 		            	}
 		            	}
 		            	
 		            	
 		            	
 		            	return title;
 		             
 		             }},
 		             {name:'updateTime',index:'doc_type', width:70,search:false,align:'center'}, 
 		             {name:'doc_type',index:'doc_type', width:50,search:false}, 
 		             {name:'doc_size',index:'doc_size',sorttype:'int', width:50,search:false,formatter:function(cellvalue, options, rowObject) {
 		            	 if(!cellvalue)
 		            		return "";
 		            	 return parseInt(cellvalue/1024) + "KB";
 		             }}, 
 		             {name:'file_type',index:'file_type',hidden:true} 
 		     ], 
 		     viewrecords:false,
             rownumbers:false,
             //reloadAll:true,
             //loadonce:true,
             rowNum:10000000,
 		     gridComplete: function () {
 		    	 //隐藏表头
                //$(this).closest('.ui-jqgrid-view').find('div.ui-jqgrid-hdiv').hide();
            }
 		});
 		
 		$grid.jqGrid('filterToolbar',{searchOperators : true});
    	$('.ui-search-toolbar').hide();
		$(".ui-search-clear a").attr("title","关闭查询").on("click",function() {
			$('.ui-search-toolbar').hide();
		});
		
		search(1);
 		$(".ui-jqgrid-bdiv").niceScroll({cursorcolor:"#BCBCBC"}); // First scrollable DIV
 		$("#navtree").niceScroll({cursorcolor:"#BCBCBC"}); // First scrollable DIV
 		
 		//init box
 		$('.fancybox-thumbs').fancybox({
			prevEffect : 'none',
			nextEffect : 'none',

			closeBtn  : true,
			arrows    : true,
			nextClick : true/* ,

			helpers : {
				thumbs : {
					width  : 50,
					height : 50
				}
			} */
		});
 		
	});
	
	function getImage(type,title) {
		if(type=="0") {
			return "folder.jpg";
		} else {
			if(endWith(title,"xls") || endWith(title,"xlsx"))
				return "excel.png";
			if(endWith(title,"doc") || endWith(title,"docx"))
				return "word.png";
			if(endWith(title,"jpg") || endWith(title,"png")|| endWith(title,"bmp")|| endWith(title,"jpeg")|| endWith(title,"gif"))
				return "image.png";
			if(endWith(title,"mp3") || endWith(title,"wma"))
				return "mp3.png";
			if(endWith(title,"rar") || endWith(title,"zip") || endWith(title,"jar") || endWith(title,"tar"))
				return "rar.png";
			if(endWith(title,"txt"))
				return "txt.png";
			if(endWith(title,"avi")||endWith(title,"mp4") ||endWith(title,"rmvb"))
				return "vedio.png";
		}
		
		return "else.png";
		
		function endWith(title,ext){     
			  var reg=new RegExp(ext+"$","i");     
			  return reg.test(title);        
		};
	}
	
	function upload(pid) {
		//动态改变参数
		$('#fileupload').fileupload(
			    'option',
			    'formData',
			    {"pid":pid}
			);
	}
	
	function search(pid) {
		 /* 
		 $grid.jqGrid("setGridParam", {postData:null});
		 $grid.jqGrid("setGridParam", {postData:{"pid":pid}}).trigger("reloadGrid", [{page:1}]); */
		
		 $.post("${ctx}jqrid",{"pid":pid,"reloadAll":true,"queryName":"tree.document.getfiles"},function(data) {
			
			 
			 $grid.clearGridData();
			 $grid.setGridParam({data: data.rows}); //数据格式就是json的rows的数据
			 $grid.trigger("reloadGrid", [{page:1}]);
					
		 
			 $("#gs_title").val("");
			 $grid.trigger("triggerToolbar");
		 
     		},"json");
 		}
	
	function operator(id,type) {
		if(type == "0") {
			search(id);
			setLabel(id);
		} else {
			donwload(id);			
		}
	}
	
	function setLabel(id) {
		 var treeNode = treeObject.getNodeByParam("id",id,null);
		 var path = treeNode.name;
		 var parentNode = treeNode.getParentNode();
		 while(parentNode != null) {
			 path = "<a onclick=\"operator('"+parentNode.id+"',0)\">" + parentNode.name + "</a>\\" + path;
			 parentNode =parentNode.getParentNode();
		 }
		 
		 $("#titleBar").html(path);
		 
		 $(".ui-search-toolbar").hide();
	}
	
	function donwload(id) {
		common.postSubmit("${ctx}download/"+ id);
	}
	
	/******************************************节点操作***********************************************/
	function addNode(treeNode) {
		var id = "";
		$.ajax({
			url:"${ctx}doc/addDoc",
			type:'post',
			data:{pid:treeNode.id,title:"新建文件夹"},
			async:false,
			success:function(data) {
				id = data;
			}
		});
		
		return id;
	}
	
	function deleteNode(id) {
		$.ajax({
			url:"${ctx}doc/delDoc/" + id,
			type:'post'
		});		
	}
	
	function deleteFile(id) {
		common.confirm("确认要删除此文件吗？",function() {
			$.ajax({
				url:"${ctx}doc/delDoc/" + id,
				type:'post',
				success:function(data) {
					if("success" == data) {
						$grid.jqGrid("delRowData",id);
						common.message("删除成功");
					} else {
						common.message("删除失败");
					}
					
				}
			});	
		});
	}
	
	function renameNode(treeNode,newName) {
		$.ajax({
			url:"${ctx}doc/renameDoc",
			data:{id:treeNode.id,title:newName},
			type:'post',
			success:function() {
				treeObject.selectNode(treeNode);
			}
		});
		
	}
	
	function query() {
		$('.ui-search-toolbar').show();
	}
</script>
</body>
</html>