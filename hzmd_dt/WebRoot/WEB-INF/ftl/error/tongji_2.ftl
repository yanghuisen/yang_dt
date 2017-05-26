	<#include "/error/home_tatle.ftl" />
<@home>
<!--bread 开始-->
<div class="kk" style="display:none" name='2'></div>
		<div class="ic-bread">
			<p class="ic-notice fl">统计结果</p>
			<div class="clear"></div>
		</div>
		<!--bread 开始-->
		<!--main 开始-->

		<div class="ic-main">
			<div class="ic-parts-list">
				<!--句法类-->
				<#list errorTypeList as errorType>
				<div class="ic-type-group">
					<div class="ic-group-header clear">
						<span class="ic-gTitle fl">${_com.convertToName(errorType.error_type!0)}</span>
						<span class="ic-type-gCounts fr">${errorType.error_count}</span>
					</div>
					<ul class="ic-group-list ic-error-list">
					<#list errorType.errorList as error>
						<li style="cursor: pointer;">
							<span class="ic-error-name fl"  title="" typeid='${error.error!0}'>${_com.convertToName(error.error!0)}</span>
							<span class="fr">${error.errorsum}</span>
							<div class="clear"></div>
						</li>
					</#list>
					</ul>
				</div>
				</#list>
				<!--词法类-->
				<div class="clear"></div>
			</div>
		</div>
		
		<!--main 结束-->
		<!--footer 开始-->
		<div class="footer">
			<p>Powered by CorpusClould</p>
		</div>
		<!--footer 结束-->
</@home>