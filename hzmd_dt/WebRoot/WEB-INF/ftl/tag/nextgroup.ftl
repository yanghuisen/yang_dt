<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="${_cp}/static/psy.css" />
	<script src="${_cp}/static/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${_cp}/static/js/index1.js" type="text/javascript" charset="utf-8"></script>
	<script src="${_cp}/static/layer/layer.js" type="text/javascript" charset="utf-8"></script>
</head>
	<body>
		<div id="wrap">
			
			<div class="main">
				<div class="result">
					<h2 class="result-title">Thank you for participating in our study!</h2>
					<p class="like-next">You will need to estimate another <span>${_groupCount!}</span> groups of words. Would you like to</p>
					<div class="result-btn">
						<span class="rest">have a rest</span>
						<span class="or">or</span>
						<span class="next">Go to the next group</span>
					</div>
				</div>
			</div>
		</div>
	</body>
   <script type='text/javascript'>
	$(function(){
	    _cp = '${_cp!}';
	    _code = '${_code!}'
		word_tag.nextgroupInit();
	})
	</script>
</html>
