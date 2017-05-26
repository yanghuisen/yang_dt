<#macro home>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>错误检索</title>
		<link rel="stylesheet" type="text/css" href="${_cp!}/static/css/iwriteCloud.css"/>
		<script src="${_cp!}/static/js/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_cp!}/static/js/layer.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_cp!}/static/js/error.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<!--header 开始-->
		<div class="ic-header">		
			<span class="ic-logo fl" style="padding-left:60px;background:url(${_cp!}/static/img/logo.png) no-repeat left center;background-size:50px 50px;">iWrite Corpus</span>
			<ul class="ic-nav fr">
				<li class="error" id="one"><a href="#">错误检索</a></li>
				<li class="error" id="two"><a href="#">错误统计</a></li>
				<li><a href="#">错误搭配</a></li>
			</ul>
		</div>
			<#nested>
		<!--header 结束-->
		<!--bread 开始-->
	</body>
	<script type='text/javascript'>
		$(function(){
			_cp = '${_cp}';
			error.init();
		})
	</script>
</html>
</#macro>