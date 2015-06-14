( function() {
	CKEDITOR.dialog.add( 'file', function( editor )  
			{  
			    return {   
			        title : '附件上传',  
			        minWidth : 600,  
			        minHeight : 200,  
			        contents : [  
			            {  
			                elements :  
			                [  
			                    {  
			                        type : 'html',
			                        html : '<input type="file" id="upload" name="upload" multiple/><div id="fileList" style="padding:2px;height:200px; border:1px solid #BCBCBC; margin-top:5px; overflow:auto;">\
			                        	 </div>'
			                    }  
			                ]  
			            }  
			        ],
			        onLoad:function() {
			        	$('#upload').fileupload({
			     	        url: ""+ctx+"ckeditor/uploadFile",
			     	        dataType: 'json',
			     	        done: function (e, data) {
			     	            $.each(data.result, function (index, file) {
			     	            	var a = "<span><img src=\""+ctx+"resources/css/images/"+common.getImage(1,file.name)+"\"/><a href=\"javascript:void(0)\" onclick=\"common.postSubmit('"+(file.downloadUrl+"&fileName="+file.name+"")+"');\">"+file.name+"</a>&nbsp;<span style='font-weight:normal;color:#BCBCBC;font-size:11px;'>"+common.getFileSize(file.size)+"</span>" +
			     	            			"\<a class='delClass' style='color:#3D5E86;margin-left:5px;' href='javascript:void(0);' onclick=\"$(this).parent().remove();\">删除</a></span><br/>";
			     	            	$("#fileList").append(a);
			     	            }); 
			     	        } 
			     	    }).prop('disabled', !$.support.fileInput)
			     	        .parent().addClass($.support.fileInput ? undefined : 'disabled');
			        	
			        	$("#fileList").niceScroll({cursorcolor:"#BCBCBC"}); // First scrollable DIV
			        },
			        onShow:function() {
			        	 $('#upload').val('');  
			        	 $("#fileList").html('');
			        },
			        onOk: function() {
			        	$("#fileList a.delClass").remove();
			        	$("#fileList a.delClass").next().remove();
			        	editor.insertHtml($("#fileList").html());
			     	}
			    };  
			} );  
} )();