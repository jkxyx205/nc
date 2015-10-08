<%@ page language="java" contentType="text/html; utf-8"
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
<link rel="stylesheet" href="${ctx}resources/plugin/jquery-top/jquery.top.css">
	<div id="content" style="margin-top: 20px;">
	  	  ${weather.name }
		  ${weather.weatherDesc }
		  <img src="${ctx }resources/css/images/weather/${weather.icon1 }">
		  <img src="${ctx }resources/css/images/weather/${weather.icon2 }">
		<h1>Infinite Scroll Testing</h1>
		<p>One for all and all for one, Muskehounds are always ready. One for all and all for one, helping everybody. One for all and all for one, it's a pretty story. Sharing everything with fun, that's the way to be. One for all and all for one, Muskehounds are always ready. One for all and all for one, helping everybody. One for all and all for one, can sound pretty corny. If you've got a problem chum, think how it could be.</p>

		<p>Barnaby The Bear's my name, never call me Jack or James, I will sing my way to fame, Barnaby the Bear's my name. Birds taught me to sing, when they took me to their king, first I had to fly, in the sky so high so high, so high so high so high, so - if you want to sing this way, think of what you'd like to say, add a tune and you will see, just how easy it can be. Treacle pudding, fish and chips, fizzy drinks and liquorice, flowers, rivers, sand and sea, snowflakes and the stars are free. La la la la la, la la la la la la la, la la la la la la la, la la la la la la la la la la la la la, so - Barnaby The Bear's my name, never call me Jack or James, I will sing my way to fame, Barnaby the Bear's my name.</p>

		<p>There's a voice that keeps on calling me. Down the road, that's where I'll always be. Every stop I make, I make a new friend. Can't stay for long, just turn around and I'm gone again. Maybe tomorrow, I'll want to settle down, Until tomorrow, I'll just keep moving on.</p>

		<p>Top Cat! The most effectual Top Cat! Who's intellectual close friends get to call him T.C., providing it's with dignity. Top Cat! The indisputable leader of the gang. He's the boss, he's a pip, he's the championship. He's the most tip top, Top Cat.</p>

		<p>Hong Kong Phooey, number one super guy. Hong Kong Phooey, quicker than the human eye. He's got style, a groovy style, and a car that just won't stop. When the going gets tough, he's really rough, with a Hong Kong Phooey chop (Hi-Ya!). Hong Kong Phooey, number one super guy. Hong Kong Phooey, quicker than the human eye. Hong Kong Phooey, he's fan-riffic!</p>

		<p>Barnaby The Bear's my name, never call me Jack or James, I will sing my way to fame, Barnaby the Bear's my name. Birds taught me to sing, when they took me to their king, first I had to fly, in the sky so high so high, so high so high so high, so - if you want to sing this way, think of what you'd like to say, add a tune and you will see, just how easy it can be. Treacle pudding, fish and chips, fizzy drinks and liquorice, flowers, rivers, sand and sea, snowflakes and the stars are free. La la la la la, la la la la la la la, la la la la la la la, la la la la la la la la la la la la la, so - Barnaby The Bear's my name, never call me Jack or James, I will sing my way to fame, Barnaby the Bear's my name.</p>

		<p>There's a voice that keeps on calling me. Down the road, that's where I'll always be. Every stop I make, I make a new friend. Can't stay for long, just turn around and I'm gone again. Maybe tomorrow, I'll want to settle down, Until tomorrow, I'll just keep moving on.</p>

		<p>Top Cat! The most effectual Top Cat! Who's intellectual close friends get to call him T.C., providing it's with dignity. Top Cat! The indisputable leader of the gang. He's the boss, he's a pip, he's the championship. He's the most tip top, Top Cat.</p>

		<p>Hong Kong Phooey, number one super guy. Hong Kong Phooey, quicker than the human eye. He's got style, a groovy style, and a car that just won't stop. When the going gets tough, he's really rough, with a Hong Kong Phooey chop (Hi-Ya!). Hong Kong Phooey, number one super guy. Hong Kong Phooey, quicker than the human eye. Hong Kong Phooey, he's fan-riffic!</p>
	</div>
	
	<a id="next" href="#">next page?</a>

 
 
<script type="text/javascript" src="${ctx }resources/plugin/infinite-scroll/jquery.infinitescroll.min.js"></script>
<script type="text/javascript" src="${ctx}resources/plugin/jquery-top/jquery.top.js"></script>

<script>
	$(function() {
		$.top({top:window.screen.availHeight-200});
		common.breadcrumb([{"text":"首页"}]);
		
		$("#nav_height").css("height","125px");
		$("#nav_div").addClass("fixed-top");
	});
	
	$('#content').infinitescroll({
		navSelector  	: "#next:last",
		nextSelector 	: "a#next:last",
		itemSelector 	: "#content p",
		debug		 	: true,
		dataType	 	: 'html',
        maxPage         : 10,
		path: function(index) {
			return "${ctx }/index/" + index;
		}
    }, function(newElements, data, url){
    	
    });
	</script>
	
</body>

</html>