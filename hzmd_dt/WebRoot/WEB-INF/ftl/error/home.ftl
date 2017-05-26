<#include "/error/home_tatle.ftl" />
<@home>
<div class="ic-bread">
<div class="kk" style="display:none" name='1'></div>
			<div class="ic-search clear">
				<input type="text" id="input" class="ic-input fl" placeholder="请输入关键词">
				<button class="ic-search-btn fl" id="but" type="0">搜索</button>
			</div>
		</div>
		<!--bread 开始-->
		<!--main 开始-->
		<div class="ic-main dd">		
			<div class="ic-total clear">
				<p class="ic-infor fl" id="size">Size：${Size!} Millon</p>
				<#--Subcorpus-selected：10 Million-->
				<p class="ic-infor fl" id="size_a">Subcorpus-selected：${Size}Million</p>
			</div>
			<div class="ic-parts-list">
				<#--<!--学校				
				<div class="ic-group" id="1">
					<div class="ic-group-header clear">
						<span class="ic-gTitle fl">学校</span>
						<span class="ic-gCounts fr" id="sum">0</span>
					</div>
					<ul class="ic-group-list" id="school">
						<li title="全部" condition="-1"  class="active">全部</li>
						<#list schoolList as school>
						<#--<li class="active" title="武夷学院">武夷学院</li>-->
						<#--<li class="active"  title="南京师范大学">南京师范大学</li>
						<li condition="0" typeid="${school.id!}" >${school.name!}</li>
						</#list>
					</ul>
				</div>
				-->
				<!--时间-->
				<div class="ic-group">
					<div class="ic-group-header clear">
						<span class="ic-gTitle fl">时间</span>
						<span class="ic-gCounts fr" id="sum">0</span>
					</div>
					<ul class="ic-group-list" id="year">
						<li title="全部" condition="-1"  class="active">全部</li>
						<#list yearList as year>
						<#--<li class="active" title="2017">2017</li>-->
						<li condition="0" typeid="${year.id!}" >${year.name!}</li>
						</#list>
					</ul>
				</div>
				<!--使用场景-->
				<div class="ic-group" style="display:none">
					<div class="ic-group-header clear">
						<span class="ic-gTitle fl">使用场景</span>
						<span class="ic-gCounts fr" id="sum">0</span>
					</div>
					<ul class="ic-group-list" id="examType">				
						<li title="全部" condition="1"  class="active">全部</li>
						<#list examTypeList as examType>
						<#--<li class="active" title="课下写作">课下写作</li>-->
						<li condition="0" typeid="${examType.id!}" >${examType.name!}</li>
					</#list>
					</ul>
				</div>
				<!--作文分数-->
				<div class="ic-group">
					<div class="ic-group-header clear">
						<span class="ic-gTitle fl">作文分数</span>
						<span class="ic-gCounts fr" id="sum">0</span>
					</div>
					<ul class="ic-group-list" id="score">
						<li title="全部" condition="1"  class="active">全部</li>
						<li condition="0" title="100-90">100-90</li>
						<li condition="0" title="89-80">89-80</li>
						<li condition="0">79-70</li>
						<li condition="0">69-60</li>
						<li condition="0">59-50</li>
						<li condition="0">49-0</li>
					</ul>
				</div>
				<!--教材-->
				<div class="ic-group" style="display:none">
					<div class="ic-group-header clear">
						<span class="ic-gTitle fl">教材</span>
						<span class="ic-gCounts fr" id="sum">0</span>
					</div>
					<ul class="ic-group-list" id="book">
					<#list bookList as book>				
						<li title="全部" condition="1" class="active">全部</li>					
						<#--<li class="active" title="外研社《国际交流英语视听说》">外研社《国际交流英语视听说》</li>-->
						<li condition="${book.id!}">${book.name!}</li>
					</#list>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		<!--main 结束-->
		<!--footer 开始-->
		<div class="footer">
			<p>Powered by CorpusClould</p>
		</div>
		<!--footer 结束-->		
		<script>
				$(function() {
					document.onkeydown = function(e) {
						var ev = document.all ? window.event : e;
						if (ev.keyCode == 13) {
							error.sousuo(10);
						}
					}
				});
		</script>	
</@home>