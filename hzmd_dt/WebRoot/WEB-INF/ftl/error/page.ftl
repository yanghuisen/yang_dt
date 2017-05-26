<#macro pageReander pageObj pagePrevURL>
	<@pageArguments pagePrevURL=pagePrevURL totalCount=pageObj.totalRow currPage=pageObj.pageNumber _totalPage=pageObj.totalPage numPerPage=pageObj.pageSize/>
</#macro>

<#macro pageArguments pagePrevURL='' totalCount=1 currPage=1 _totalPage=1 numPerPage=20>
	<#if _totalPage <= 0><#assign totalPage = 1/><#else><#assign totalPage = _totalPage/></#if>
	<ul class="pager" style="margin: 0px">
      	<li><span href="#" class="disabled">共 ${totalCount!} 条 (${currPage}/${totalPage}页)</span></li>
    </ul>
    <script>
    var totalPN = ${totalPage};
    $(function(){
    	$("button._page-gotobutton").click(function(){
    		var pn = parseInt($("input._page-gotobutton").val());
    		pn = pn > totalPN ? totalPN : pn;
    		if(pn > 0) gopage(pn);
    	});
    });
    </script>
    <!-- <ul class="pager" style="margin: 0px">
      	<li><span href="#" class="disabled">${currPage}/${totalPage}页</span></li>
    </ul> -->
	<ul class="pager" style="margin: 0px">
        <#if 1 < currPage>
			<li><a href="javascript:void(0);" onclick="gopage(1)" title="跳转到第一页">首页</a></li>
		   	<li class="previous"><a href="javascript:void(0);" onclick="gopage(${(currPage - 1)!})" title="上一页">上一页</a></li>
		<#else>
			<li class="disabled"><span title="跳转到第一页">首页</span></li>
        	<li class="previous disabled"><span title="上一页">上一页</span></li>
		</#if>
		
		<#if (currPage-3>=1) >
	   		<#list (currPage-3)..(currPage+3) as p>
	   		  <@showPageNum currPage=currPage index=p totalPage=totalPage pagePrevURL=pagePrevURL/>
	   		</#list>
	   	<#else>
	   		<#list 1..7 as p>
	   		  <@showPageNum currPage=currPage index=p totalPage=totalPage pagePrevURL=pagePrevURL/>
	   		</#list>
	   	</#if>
		
		<#if currPage < totalPage >
	   		<li class="next"><a href="javascript:void(0);" onclick="gopage(${(currPage + 1)!});" title="下一页">下一页</a></li>
			<li><a href="javascript:void(0);" onclick="gopage(${totalPage!});" title="跳转到最后一页">末页</a> </li>
		<#else>
			<li class="next disabled"><span title="下一页">下一页</span></li> 
			<li class="disabled"><span title="跳转到最后一页">末页</span></li> 
		</#if>
	</ul>
	<ul class="pager" style="margin: 0px;float:right;margin-left: 5px;margin-right: 3px;">
      	<li><button type="button" class="btn btn-primary _page-gotobutton">跳页</button></li>
    </ul>
	<ul class="pager" style="margin: 0px;float:right;margin-left: 5px;">
      	<li><input type="text" class="form-control _page-gotobutton" value="${currPage!}" style="width:50px;text-align:center;"/></li>
    </ul>
</#macro>

<#macro showPageNum currPage=1 index=1 totalPage=1 pagePrevURL=''>
	<#if index==currPage>
		<li class="active"><span>${index!}</span></li> 
    <#elseif index <= totalPage >
	  	<li><a href="javascript:void(0);" onclick="gopage(${index!})">${index!}</a></li>
    </#if>
</#macro>