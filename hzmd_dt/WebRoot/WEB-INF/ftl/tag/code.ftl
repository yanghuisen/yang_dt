<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>输入测评编码</title>
		<script src="${_cp!}/static/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_cp!}/static/js/index1.js" type="text/javascript" charset="utf-8"></script>
		<link rel="stylesheet" type="text/css" href="${_cp!}/static/psy.css"/>
	</head>
	<body>
		<div id="wrap">
			<div class="main">
				<div class="code">
					<h2 class="code-title">Please input your ID number.</h2>
					<!--测评码无效，请重新输入-->
					<h2 class="code-title code-title-error" style="display: none;">ID Number is invalid, please enter again.</h2>
					<!--测评码输入正确-->
					<h2 class="code-title code-title-error" style="display: none;">Thank you for your participant!</h2>
					<ul class="test-code" >
					    <li><input type="text" name="code" id="code" placeholder="" autocomplete="off"/></li>
					    <li style="display:none" id="lides" class="result-notice">
					    	You have already finished <span class="green" id="span_finished">12</span> group(s)<br /><br />
					    	There are <span class="red" id="span_group_t">22</span> group(s) left.</li>
						<li id="liErr">
							<span class="red" style="display:none" id="span_Err"></span>
						</li>
					</ul>
					<button class="sure-btn sure-btn-click" id="OK" >OK</button>
				</div>
			</div>
		</div>
	</body>
<script type='text/javascript'>
$(function(){
	_cp = '${_cp}';
	word_tag.codeInit();
})
</script>
</html>
