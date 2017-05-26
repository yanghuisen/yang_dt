<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>结果页面</title>
		<link rel="stylesheet" type="text/css" href="${_cp!}/static/css/psy.css"/>
		<script src="${_cp!}/static/js/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_cp!}/static/js/word.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<div id="wrap">
			
			<div class="main">
				<div class="result">
					<h2 class="result-title">Thank you for participating in our study!</h2>
					<p class="like-next">You will need to estimate another <span>${group!}</span> groups of words. Would you like to</p>
					<div class="result-btn">
						<span class="rest">have a rest</span>
						<span class="or">or</span>
						<span class="next">Go to the next group</span>
					</div>
					
				</div>
			</div>
		</div>
		<div id="user" style="display: none;">${user!}</div>
	</body>
	<script type="text/javascript">
		$(function(){
		_cp = '${_cp}';
			word.init();			
		})
	</script>
</html>
