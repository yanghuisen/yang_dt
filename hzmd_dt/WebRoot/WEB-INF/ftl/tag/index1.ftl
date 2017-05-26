<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="${_cp}/static/psy.css" />
		<script src="${_cp}/static/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_cp}/static/js/index1.js" type="text/javascript" charset="utf-8"></script>
		<script src="${_cp}/static/layer/layer.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			window.onbeforeunload = function() 
			{ 
				var isChange = 0;
				$("div[isChange='1']").each(function(i,n){
					isChange = 1;
					return;
				})
				if(isChange == 1){ 
					return "The page is not saved, leaving the page data will be lost, you are sure to leave the page?"; 
				} 
			} 
			$(function() {
				var height = $(window).height();
				var h=$(".comment h2").height();
				var tbody = height - 20 - h- 84 - 95;
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
			<div class="main">
				<div class="comment">
					<h2>Please estimate how dialectical or analytical are these words from 1 to 7,1=Extremely analytical,7=Extremely dialectical. You can look up the definitions of the word by clicking on it.</h2>
					<div class="data">
						<div class="thead clear">
							<p class="p2"></p>
							<p class="p3">Extremely<br />analytical<br/><span>1</span></p>
							<p class="p3">Moderately<br />analytical<br/><span>2</span></p>
							<p class="p3">Slightly<br />analytical<br/><span>3</span></p>
							<p class="p4">Neither analytical<br/>nor dialectical<br/><span>4</span></p>
							<p class="p3">Slightly<br />dialectical<br/><span>5</span></p>
							<p class="p3">Moderately<br />dialectical<br/><span>6</span></p>
							<p class="p3">Extremely<br />dialectical<br/><span>7</span></p>

						</div>
						
						<div class="tbody">
						  <#if _words??>
						  <#list _words as word>
						  	<#if word.dt_type ?? >
								<div class="tr" id="tr_${word.id!}" isTag = "1" isChange="0">
							<#else>
							    <div class="tr" id="tr_${word.id!}" isTag = "0" isChange="0">
							</#if>
								<p class="p2">
									<span class="word" id="${word.id!}" atl="${word.word!}">${word.word!}</span>
								</p>
								<p class="p3">
									<#if word.dt_type ?? && word.dt_type == 1>
										<input type="radio" name="${word.id!}" checked="checked" value='1' autocomplete=""/>
									<#else>
										<input type="radio" name="${word.id!}" value='1' autocomplete=""/>
									</#if>
								 </p>
								<p class="p3">
									 <#if word.dt_type ?? && word.dt_type == 2>
										<input type="radio" name="${word.id!}" checked="checked" value='2' autocomplete="" />
									<#else>
										<input type="radio" name="${word.id!}" value="2" autocomplete=""/>
									</#if>
								</p>
								<p class="p3">
									 <#if word.dt_type ?? && word.dt_type == 3>
										<input type="radio" name="${word.id!}" checked="checked" value='3' autocomplete="" />
									<#else>
										<input type="radio" name="${word.id!}" value="3" autocomplete=""/>
									</#if>
								</p>
								<p class="p3">
									 <#if word.dt_type ?? && word.dt_type == 4>
										<input type="radio" name="${word.id!}" checked="checked" value='4' autocomplete="" />
									<#else>
										<input type="radio" name="${word.id!}" value="4" autocomplete=""/>
									</#if>
								</p>
								<p class="p3">
									 <#if word.dt_type ?? && word.dt_type == 5>
										<input type="radio" name="${word.id!}" checked="checked" value='5' autocomplete="" />
									<#else>
										<input type="radio" name="${word.id!}" value="5" autocomplete=""/>
									</#if>
								</p>
								<p class="p3">
									 <#if word.dt_type ?? && word.dt_type == 6>
										<input type="radio" name="${word.id!}" checked="checked" value='6' autocomplete="" />
									<#else>
										<input type="radio" name="${word.id!}" value="6" autocomplete=""/>
									</#if>
								</p>
								<p class="p3">
									 <#if word.dt_type ?? && word.dt_type == 7>
										<input type="radio" name="${word.id!}" checked="checked" value='7' autocomplete="" />
									<#else>
										<input type="radio" name="${word.id!}" value="7" autocomplete=""/>
									</#if>
								</p>
								<div class="clear"></div>
								<div class="explain"style="display:none;" id="div_def_${word.id}">
									<img src="${_cp}/static/tri.png" />
									<div class="jieshi" id="div_jieshi_${word.id}" isload='0'>加载中.....</div>
								</div>
							</div>
						 </#list>
						 </#if>
						</div>
					</div>

				</div>
				
				<span class="submit-btn jieshi-btn" id="spansub">SAVE</span>
			</div>
		</div>
		</div>
		<div id="wrap" style="display:none;" >
			<div class="main">
				<div class="code">
					<h2 class="code-title">温馨提示：</h2>
					<h2 class="code-title code-title-error" >数据正在保存，请勿刷新......</h2>
					<ul class="test-code">	
					</ul>
				</div>
			</div>
		</div>
	</body>
	<script type='text/javascript'>
	$(function(){
	    _cp = '${_cp}';
   		_code = '${_code}';
   		_gd = '${_gd!}'
		word_tag.init();
	})
	</script>
</html>