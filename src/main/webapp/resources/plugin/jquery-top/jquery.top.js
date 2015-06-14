;(function($) {
	$.top = function (options) {
		options = $.extend({},$.top.defaults,options);
		
		$('body').append('<div id="updown"><span class="up"></span></div>');
		if(options.down)
			$("#updown").append('<span class="down"></span>');
		
		
		$("#updown").css({"top":options.top+"px","right":options.right+"px"});
		
		$(window).scroll(function() {		
			if($(window).scrollTop() >= 100){
				$('#updown').fadeIn(300); 
			}else{    
				$('#updown').fadeOut(300);    
			}  
		});
		$('#updown .up').click(function(){$('html,body').animate({scrollTop: '0px'}, options.speed);});
		$('#updown .down').click(function(){$('html,body').animate({scrollTop: document.body.clientHeight+'px'},  options.speed);});
	};
	
	$.top.defaults = {
		speed:100,	
		top:100,
		right:30,
		donw:false
	};
	
})(jQuery);
 