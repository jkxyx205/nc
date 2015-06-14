$(function() {
	$(".nc_form table td.tdContent input").each(function() {
		var c = $(this).attr("checked");
		
		if(c != undefined) {
			$(this).next().css("color","blue");
		}
		
	});
 
	
	$(".nc_form table td.tdContent input[type='radio']").on("click",function() {
		var name = $(this).attr("name");
		
		$(".nc_form table td.tdContent").find("input[name="+name+"]").each(function() {
			$(this).next().css("color","black");
		});
		
		$(this).next().css("color","blue");
	});
	
	$(".nc_form table td.tdContent input[type='checkbox']").on("change",function() {
		if (this.checked) {
			$(this).next().css("color","blue");
		} else {
			$(this).next().css("color","black");
		}
	});
	
});