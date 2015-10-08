<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
    
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", basePath);
%>    
<html>
<head>
</head>
<body>
<link href="${ctx }/resources/plugin/bootstrap-paginator/css/bootstrap-combined.min.css" rel="stylesheet">
<style>
	 .pointer-cursor {
    	cursor: pointer;
	}
	
	#listContent {
		padding:0 10px 10px 10px;
	}
	
	#list li {
		border-bottom: 1px dashed #BCBCBC;
		padding: 2px;
		font-size:11px;
	}
	
	#list li a {
		color:#444;
	}
	 
	#list li span.datetime{
		color:#999999;
		float: right;
	} 
	
	.breadcrumb {
		margin:2px 0;
	}
	
	.pagination {
		margin: 0;
	}
</style>
<div class="row">
  <div class="col-md-4 col-md-offset-8">
    <div class="input-group">
      <input id="title" type="text" class="form-control" style='height:34px;' placeholder="标题" value="${title }">
      <span class="input-group-btn">
        <button name="search" class="btn btn-default" style='height:34px;' type="button" onclick="query();">查询</button>
      </span>
    </div> 
  </div> 
</div> 
<div id="listContent">
	<div id="list">
		<ol start="${start }">
			<c:forEach var="row" items="${jqgridJsonBO.rows }">
				<li><a target='_blank' href="${ctx }html/notice/${row.y}/${row.m }/${ row.id}.html">${row.title }</a><span class="datetime"><fmt:formatDate value="${row.publishTime }" pattern="yyyy年MM月dd日 "/></span></li>
<%-- 				<li><a target='_blank' href="${ctx }notice/gotoDetail/${ row.id}">${row.title }</a><span class="datetime"><fmt:formatDate value="${row.publishTime }" pattern="yyyy年MM月dd日 "/></span></li> --%>
			</c:forEach>
		</ol>
	</div>
	<div id="page"></div>
</div>
	
<script src="${ctx }resources/plugin/bootstrap-paginator/src/bootstrap-paginator.js"></script>
<script>
	$(function() {
		if ($("#title").val()) {
			   var input = document.getElementById("title");
			   var val = input.value;
			   input.focus();
			   input.value = '';
			   input.value = val;
		}
		
		
		common.breadcrumb([{"text":"通知公告"},
		                   {"text":"公告列表"}
		                  ]);
		
		if('${jqgridJsonBO.total}' == '0') {
			$("#list").html('没有相关记录');
			
		} else {
			var options = {
		            currentPage: '${jqgridJsonBO.page}',
		            totalPages: '${jqgridJsonBO.total}',
		            alignment:'left',
		            numberOfPages:'15',
		            itemContainerClass: function (type, page, current) {
		                return (page === current) ? "active" : "pointer-cursor";
		            },
		             pageUrl: function(type, page, current){
		                return queryUrl(page);
		            }, 
		            itemTexts: function (type, page, current) {
		                    switch (type) {
		                    case "first":
		                        return "首页";
		                    case "prev":
		                        return "上一页";
		                    case "next":
		                        return "下一页";
		                    case "last":
		                        return "末页";
		                    case "page":
		                        return page;
		                    }
		                },
		                tooltipTitles: function (type, page, current) {
		                    switch (type) {
		                    case "first":
		                        return "首页";
		                    case "prev":
		                        return "上一页";
		                    case "next":
		                        return "下一页";
		                    case "last":
		                        return "末页";
		                    case "page":
		                        return  + page;
		                    }
		                }
		            };

		        $('#page').bootstrapPaginator(options);
		}
		
		
	});
	
	function query() {
		location.href = queryUrl(1);
	}
	
	function queryUrl(page) {
		return "${ctx}notice/list/"+page+"?title="+$("#title").val()+"";
	}
</script>
</body>
</html>