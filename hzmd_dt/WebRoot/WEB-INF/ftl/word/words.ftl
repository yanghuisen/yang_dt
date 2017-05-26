<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>答题页</title>
		<link rel="stylesheet" type="text/css" href="${_cp!}/static/css/psy.css" />
		<script src="${_cp!}/static/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_cp!}/static/js/word.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_cp!}/static/layer/layer.js" type="text/javascript" charset="utf-8"></script>

		<script type="text/javascript">
			$(function() {
				var height = $(window).height();
				var tbody = height - 59 - 39 - 81 - 67;
				$(".tbody").height(tbody);
				var length = $(".tbody .tr").length;
				if(length > 6) {
					$(".tbody .tr").each(function(i, v) {
						if(i > length - 6) {
							$(this).children(".explain").css({
								"top": "-242px"
							}).children("img").css({
								"top": "262px"
							})
						}
					})
				}
				$(window).resize(function() {
					var height = $(window).height();
					var tbody = height - 59 - 39 - 81 - 65;
					$(".tbody").height(tbody);
				})

			})
		</script>
	</head>

	<body>
		<div id="wrap">
			<!--<div class="header">
				<a href="#" class="goback fl">返回</a>
				<span class="title">心理测评</span>
				<a href="#" class="quit fr">退出</a>
			</div>-->
			<div class="main">
				<div class="comment">
					<h2>请您评价下列词语在多大程度上体现了辩证思维或线性思维？</h2>
					<div class="data">
						<div class="thead clear">

							<p class="p2"></p>
							<p class="p3">Extremely<br />analytical</p>
							<p class="p3">Moderately<br />analytical</p>
							<p class="p3">Slightly<br />analytical</p>
							<p class="p4">Neither analytical<br />nor dialectical</p>
							<p class="p3">Slightly<br />dialectical</p>
							<p class="p3">Moderately<br />dialectical</p>
							<p class="p3">Extremely<br />dialectical</p>

						</div>
						<div class="tbody">	
						<#list wordList as word>	
							<div class="tr" isChange="0" id=${word.w_id!}>
								<p class="p2">
									<span class="word" id=${word.w_id!}>${word.word!}</span>
								</p>
								<p class="p3">
									<#if word.dt_type ?? && word.dt_type==1>
									<input type="radio" value="1" name="${word.w_id!}" checked="checked" autocomplete="" />
									<#else>
									<input type="radio" value="1" name="${word.w_id!}" autocomplete="" />
									</#if>
								</p>
								<p class="p3">
									<#if word.dt_type ?? && word.dt_type==2>
									<input type="radio" value="2" name="${word.w_id!}" checked="checked" autocomplete="" />
									<#else>
									<input type="radio" value="2" name="${word.w_id!}" autocomplete="" />
									</#if>
								</p>
								<p class="p3">
									<#if word.dt_type ?? && word.dt_type==3>
									<input type="radio" value="3" name="${word.w_id!}" checked="checked" autocomplete="" />
									<#else>
									<input type="radio" value="3" name="${word.w_id!}" autocomplete="" />
									</#if>
								</p>
								<p class="p4">
									<#if word.dt_type ?? && word.dt_type==4>
									<input type="radio" value="4" name="${word.w_id!}" checked="checked" autocomplete="" />
									<#else>
									<input type="radio" value="4" name="${word.w_id!}" autocomplete="" />
									</#if>
								</p>
								<p class="p3">
									<#if word.dt_type ?? && word.dt_type==5>
									<input type="radio" value="5" name="${word.w_id!}" checked="checked" autocomplete="" />
									<#else>
									<input type="radio" value="5" name="${word.w_id!}" autocomplete="" />
									</#if>
								</p>
								<p class="p3">
									<#if word.dt_type ?? && word.dt_type==6>
									<input type="radio" value="6" name="${word.w_id!}" checked="checked" autocomplete="" />
									<#else>
									<input type="radio" value="6" name="${word.w_id!}" autocomplete="" />
									</#if>
								</p>
								<p class="p3">
									 <#if word.dt_type ?? && word.dt_type == 7>
										<input type="radio" name="${word.w_id!}" checked="checked" value='7' autocomplete="" />
									<#else>
										<input type="radio" name="${word.w_id!}" value="7" autocomplete=""/>
									</#if>
								</p>
								<div class="clear"></div>
								<div class="explain" id="jie_${word.w_id}" style="display: none;" isload='0'>
									<img src="${_cp!}/static/img/tri.png" />
									<div class="jieshi" id="shi_${word.w_id}" >
									</div>
								</div>
							
							</div>
						</#list>
						</div>
					</div>
					<!--很单纯的获取user-->
						<div id= "user" style="display: none;">${user!}</div>
						<!--很单纯的获取g_id-->
						<div id ="g_id" style="display: none;">${g_id!}</div>
				</div>
				<span class="submit-btn jieshi-btn" id="sub">提交测试</span>
			</div>
		</div>
		</div>
	</body>
<script type='text/javascript'>
	$(function(){
	_cp = '${_cp}';
	    word.init();
	})
	</script>
</html>