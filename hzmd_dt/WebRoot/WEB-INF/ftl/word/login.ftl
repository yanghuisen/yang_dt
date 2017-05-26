<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>输入测评编码</title>
		<link rel="stylesheet" type="text/css" href="${_cp!}/static/css/psy.css"/>
		<script src="${_cp!}/static/js/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_cp!}/static/js/login.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<div id="wrap">
			
			<div class="main">
				<div class="code">
					<h2 class="code-title">Please input your ID number.</h2>
					<!--测评码无效，请重新输入-->
					<h2 class="code-title code-title-error" style="display: none;" id="no">ID Number is invalid, please enter again.</h2>
					<!--测评码输入正确-->
					<h2 class="code-title code-title-error" style="display: none;" id="ok">Thank you for your participant!</h2>
					<ul class="test-code">
					    <li><input type="text" name="code" id="code" placeholder="" autocomplete="off" maxlength="5" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/></li>
					    <li class="result-notice" style="display: none;" id="group">You have already finished <span class="green" id="wc"></span> group(s)<br /><br />There are <span class="red" id="wz"></span> group(s) left.</li>
					</ul>
					<button class="sure-btn sure-btn-click" id="sub">OK</button>
					<!--disabled="disabled"禁止点击-->
					<!--可以点击-->
					<button class="sure-btn sure-btn-click" style="display: none;" id="mit">确定</button><!---->
				</div>
			</div>
		</div>
		
	</body>
	<script type="text/javascript">
		$(function(){
		_cp = '${_cp}';
			login.init();			
		})
	</script>
</html>
