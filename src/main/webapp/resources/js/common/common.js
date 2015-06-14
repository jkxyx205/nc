var common = function() {
	function colNamesI18n(colNames) {
		//i18n
		for(var i in colNames) {
			var name = colNames[i];
			var name2 = I18nUtil.getMessageByCode(name);
			if(name2)
				colNames[i] = name2;
		}
	}
	return {
			/**
			  * 模拟POST提交跳转
			  * @param url 跳转url	  ("xx.do")
			  * @param data 传递参数 ({"username":"xx"})
			  */
			postSubmit:function(url,data) {
				var form = jQuery('<form  action="'+url+'" method="POST"></form>');	
				if(data){
					for(var i in data) {
						var $hidden = $('<input type="hidden" name="' + i + '" />');
						if(Array.isArray(data[i]))
							$hidden.val(data[i].join(";"));
						else
							$hidden.val(data[i]);
						$hidden.appendTo(form);
					}			
				}
				
				jQuery(form).appendTo('body');
				form.submit();
			},
			grid : function(_settings) {
				var settings = {
						url:common.getBase() +"/jqrid",
		                datatype: "json",
		                mtype:'POST',
		                height: 250,
		                width: 920,
		                viewrecords:true,
		                rowNum:10,
		                //multiselect:true,
		                rownumbers:true,
		                rowList:[10,20,30],
		                serializeGridData:function(postData) {
		                	return postData;
		                }
		        };
				
				$.extend(settings,_settings);
				if(settings.loadonce)
					settings.reloadAll=true;
				
				//i18n
				colNamesI18n(settings.colNames);
				
				//get param
				var $grid =  $(settings.id) ;
				settings.postData = getParam();
				$grid.jqGrid(settings);
				
				//register event
				$(settings.queryForm + " button[name=search]").click(function() {
					if(!validate())
						return;
					var param = getParam();
					$grid.jqGrid("setGridParam", {postData:null});
					$grid.jqGrid("setGridParam", {postData:param}).trigger("reloadGrid", [{page:1}]);
				});
				
				$(settings.queryForm + " button[name=reset]").click(function() {
					$(settings.queryForm).resetForm();
					$(settings.queryForm).clearForm(true);
					//reset multi select
//					$(settings.queryForm + ' .selectpicker').selectpicker('deselectAll');
					$(settings.queryForm + ' .selectpicker').selectpicker('val',"");
		           
				});
				
				$(settings.queryForm + " button[name=export]").on("click",function() {
					//此处修改了不要忘记修改 resources/plugin/jqGrid/js/jquery.jqGrid.src.js line:8842
					if(!validate())
						return;
					
					var param = getParam();
					var json = {
							queryName:settings.queryName,
							fileName:settings.fileName,
							sidx:$grid.jqGrid("getGridParam","sortname"),
							sord:$grid.jqGrid("getGridParam","sortorder"),
							colNames:$grid.jqGrid("getGridParam","colNames"),
							colModel:$grid.jqGrid("getGridParam","colModel")
					};
					param.jqridJson = JSON.stringify(json); 
					common.postSubmit(common.getBase() +"/jqrid/export",param);
				});
				
				function validate() {
					if($(settings.queryForm).validationEngine("validate"))
						return true;
						
					return false;
				}
				
				
				function getParam() {
					var param = {};
					if(settings.queryForm != undefined) {
						param = $(settings.queryForm).form2json({allowEmptyMultiVal:true});
					}
					
					if(settings.data != undefined) {
						$.extend(param,settings.data);
					}
						
					return param;
				}
				return $grid;
			},
			exportExcel:function(setting, param) {
				var colModel = setting.colModel;
			
				var modelTemplate = {hidden:false,width:100};
				
				colNamesI18n(setting.colNames);
				
				var json = {
						queryName:setting.queryName,
						fileName:setting.fileName,
						sidx:"",
						sord:"",
						colNames:setting.colNames,
						colModel:(function() {
							var _colModel = [];
							for(var i in colModel) {
								var model = {};
								$.extend(model,modelTemplate,model);
								model['index'] = model['name'] = colModel[i];
								_colModel.push(model);
							}
							return _colModel;
						})()
				};
				param.jqridJson = JSON.stringify(json); 
				common.postSubmit(common.getBase() + "/jqrid/export",param);
			},
			ajaxQuery:function(_settings) {
				var settings = {
						url:common.getBase() +"/jqrid",
		                datatype: "json",
		                type:'POST',
		                data:{},
		                error:function(data) {
		                	common.alert(data.responseText);
		                }
				};
				$.extend(settings,_settings);
				settings.data.reloadAll = "true";
				settings.data.queryName = settings.queryName;
				//添加jqrid所需要的参数
				$.ajax(settings);
			},
			ajaxUpdate:function(_settings) {
				var settings = {
						url:common.getBase() +"/update",
		                datatype: "json",
		                type:'POST',
		                data:{},
		                error:function(data) {
		                	common.alert(data.responseText);
		                }
				};
				$.extend(settings,_settings);
				settings.data.queryName = settings.queryName;
				//添加jqrid所需要的参数
				$.ajax(settings);
			},
			getRootPath :function(){  
			    //获取当前网址，如： http://localhost:8083/proj/meun.jsp  
			    var curWwwPath = window.document.location.href;  
			    //获取主机地址之后的目录，如： proj/meun.jsp  
			    var pathName = window.document.location.pathname;  
			    var pos = curWwwPath.indexOf(pathName);  
			    //获取主机地址，如： http://localhost:8083  
			    var localhostPath = curWwwPath.substring(0, pos);  
			    //获取带"/"的项目名，如：/proj  
			    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/')+1);  
			    return(localhostPath + projectName);  
			},
			getBase :function(){  
			    var pathName = window.document.location.pathname;  
			    //获取带"/"的项目名，如：/proj  
			    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/')+1);  
			    return projectName;  
			},
			/**
			 * 获取字典键值对
			 * {"GB/T 2659-2000.0":{"004":"阿富汗","008":"阿尔巴尼亚共和国"}}
			 * @param {} key
			 * @return {}
			 */
			formatDic4Map:function(keyOrAligns) {
				var map;
				if((map =dicMap[keyOrAligns.toUpperCase()]) || 
					(map =dicMap[keyOrAligns.toLowerCase()]) || 
					(map =dicMap[aligns[keyOrAligns.toLowerCase()]]) || 
					(map =dicMap[aligns[keyOrAligns.toUpperCase()]])) {
				}
				return map;
			},
			formatDic4Text:function(keyOrAligns,value) {
				if(!value)
					return "";
				var trueValue = "";
				var map = formatDic4Map(keyOrAligns);
				
				if(map) {	
					 if(map[value])
					 	trueValue = map[value];
				}
				if(trueValue)
					return trueValue;
				return value;
			},
			alert:function(msg) {
				$.teninedialog({
					title: '系统提示',
					content: msg
				});
			},
			confirm:function(msg,callback) {
				$.teninedialog({
					title: '系统提示',
					otherButtonStyles:['btn-primary'],
					otherButtons:["确定"],
					content: msg,
					clickButton:function(sender,modal,index){
						if(index==0) {
							callback();
						}
						 $(this).closeDialog(modal);
				     }
				});
			},
			message:function(msg) {
				Messenger().post({
					  message: msg,
					  hideAfter: 2,
					  hideOnNavigate: true
					});
			},
			breadcrumb:function(texts) {
				var len = texts.length;
				for(var i in  texts) {
					var li = texts[i];
					var $li = $("<li>.*.</li>");
					
					if(li.href)
						$li.html("<a href="+li.href+">"+li.text+"</a>");
					else 
						$li.text(li.text);
					
					if(len-1 == i)
						$li.addClass("active");
					
					$(".breadcrumb").append($li);
				}
			}, getImage:function(type,title) {
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
     	   			if(endWith(title,"pdf"))
     	   				return "pdf.png";
     	   			if(endWith(title,"avi")||endWith(title,"mp4") ||endWith(title,"rmvb"))
     	   				return "vedio.png";
     	   		}
     	   		
     	   		return "else.png";
     	   		
     	   		function endWith(title,ext){     
     	   			  var reg=new RegExp(ext+"$","i");     
     	   			  return reg.test(title);        
     	   		};
			}, getFileSize :function(size) {
				if(size >  1024 *1024) {
					return (size/(1024 *1024)).toFixed(2) + "M";
				} else {
					return (size/1024).toFixed(2) + "K";
				}
				
			}
	};
}(); 

 


 