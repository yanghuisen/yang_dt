	<#include "/error/home_tatle.ftl" />
<@home>
<div class="kk" style="display:none" name='2'></div>
<div class="ic-bread">
			<div class="ic-school" id=${type!0}>
				<#--${type!0}-->
				${_com.convertToName(type!0)}
			</div>
			<#--${_com.convertToName(book.textBook!)}-->
		</div>
		<!--bread 开始-->
		<!--main 开始-->
		<div class="ic-main">
			<div class="ic-parts-list ic-sentence-list">
				<ul class="ic-example">
					<#--<li><span class="font-size">1、</span> Will The Democrats Be Able To <span class="font-color">Reverse</span> The Online Gambling Ban</li>-->
					<#list errorList as error>
						<li><span style="font-weight:bold;" id="number"></span> &nbsp;&nbsp;&nbsp;${error.error_sentence}</li>
					</#list>
				</ul>
			</div>
			<div class="ic-page">
				<ul class="ic-pagination">
					<li>
						<p class="page-total">共<span class="page-count">${page!}</span> 页</p>
					</li>
				</ul>

			</div>
		</div>
		<!--main 结束-->
		<!--footer 开始-->
		<div class="footer">
			<p>Powered by CorpusClould</p>
		</div>
		<script type='text/javascript'>
		var hh = $('.ic-example').children('li');
			hh.each(function(){
				var index = $(this).index();
				index = index*1+1;
				$(this).children("#number").text(index);				
			})
			pageCon = $('.page-count').html();
		liTab = 10;
		var str ="";
		str +="<li><a href=\"javascript:;\"><i class=\"ic-fa-left\"></i></a></li>";
		num = error.GetQueryString("page");
		 i=1;
		if(num>liTab){
			i += num-liTab;
		}
		if(liTab<=pageCon){
			for(var j=1;j<=liTab;j++){
				str+="<li><a href=\"javascript:;\" class=\"a\"> "+i+" </a></li>"
				i++;
			}
			}else{
				for(var j=1;j<=pageCon;j++){
				str+="<li><a href=\"javascript:;\" class=\"a\"> "+i+" </a></li>"
				i++;
				}
		}
		str += "<li class=\"angle-btn\"><a href=\"javascript:;\"><i class=\"ic-fa-right\"></i></a></li>"
		str += "<li><input id=\"input\" type=\"text\" placeholder=\"\" onkeyup=\"value=value.replace(/[^\\d]/g,'')\"><i class=\"goto\"></i><div class=\"clear\"></div></li>"
		str += "<li><p class=\"page-total\">共<span class=\"page-count\"> ${page!}</span> 页</p></li>"
		$('.ic-pagination').html(str);
	</script>
		</@home>