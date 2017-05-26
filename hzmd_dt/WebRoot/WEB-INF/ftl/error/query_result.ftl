<#include "/error/home_tatle.ftl" />
<@home>
<div class="kk" style="display:none" name='1'></div>
		<!--bread 开始-->
		<div class="ic-bread">
			<div class="ic-search clear">
				<input type="text" class="ic-input fl" placeholder=${word!} id="input">
				<button class="ic-search-btn fl" id="search">搜索</button>
			</div>
		</div>
		<!--bread 开始-->
		<!--main 开始-->
		<div class="ic-main">
			<div class="ic-total clear">
				<p class="ic-infor fl" id="errorsum"></p>
			</div>
			<div class="ic-parts-list ic-table-list">
				<!--学校-->
				<div class="ic-table ">
			<#--		<div class="ic-group-header clear">
						<span class="ic-gTitle fl">学校</span>
						<span class="ic-gCounts fr" id="school_count"></span>
					</div>
					<div class="ic-table-box">
					<table>
					
						<thead>
							<tr id="school">
							<#list school_count as school>
								<th>${_com.convertToName(school.school)}</th>
							</#list>
							</tr>
						</thead>
						<tbody>
							<tr>
							<#list school_count as school>
								<td class="sentence" style="cursor: pointer;" typeid='${school.school}'>${school.count}</td>
							</#list>
							</tr>
						</tbody>
						
					</table>
					</div>
				</div>-->
				<!--时间-->
				<div class="ic-table">
					<div class="ic-group-header clear">
						<span class="ic-gTitle fl">时间</span>
						<span class="ic-gCounts fr" id="year"></span>
					</div>
					<table>
						<thead>
							<tr id="years">
								<#list yearList as year>
									<th>
									${_com.convertToName(year.year)}
									</th>
								</#list>
							</tr>
						</thead>
						<tbody>
							<tr>
								<#list yearList as year>
									<td style="cursor: pointer;" class="yearCount" typeid='${year.year}'>${year.yearCount}</td>
								</#list>
							</tr>
						</tbody>
					</table>
				</div>
				<!--使用场景-->
				<div class="ic-table" style="display:none">
					<div class="ic-group-header clear">
						<span class="ic-gTitle fl">使用场景</span>
						<span class="ic-gCounts fr" id="examType"></span>
					</div>
					<table>
						<thead>						 
							<tr id="exam">
							 <#list examList as examtype>
								<th>
								${_com.convertToName(examtype.exam_type)}
							   </th>
							 </#list>
							</tr>					
						</thead>
						<tbody>
							<tr id="examCount">
								<#list examList as examtype>
									<td style="cursor: pointer;" class="exam" typeid='${examtype.exam_type}'>${examtype.exam}</td>
								</#list>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="ic-table">
					<div class="ic-group-header clear">
						<span class="ic-gTitle fl">作文分数</span>
						<span class="ic-gCounts fr" id="score"></span>
					</div>
					<table>
						<thead>						 
							<tr id="scoreType">
							 <#list scoreList as range>
								<th>${range.total_score}</th>
							 </#list>
							</tr>					
						</thead>
						<tbody>
							<tr id="scoreCount">
								<#list scoreList as range>
									<td style="cursor: pointer;" class="score">${range.scoreCount}</td>
								</#list>
							</tr>
						</tbody>
					</table>
				</div>				
				<div class="ic-table" style="display:none">
					<div class="ic-group-header clear">
						<span class="ic-gTitle fl">教材</span>
						<span class="ic-gCounts fr" id="book"></span>
					</div>
					<table>
						<thead>						 
							<tr id="bookType">
							
							 <#list bookList as book>
							 <#if book.textBook == -100>
								 <th>无教材名称</th>
							 <#else>
								<th>${_com.convertToName(book.textBook!)}</th>
							 </#if>
							 </#list>
							</tr>					
						</thead>
						<tbody>
							<tr id="bookCount">
								<#list bookList as book>
									<td style="cursor: pointer;" class="bookType" typeid='${book.textBook}'>${book.bookCount}</td>
								</#list>
							</tr>
						</tbody>
					</table>
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
	<script type='text/javascript'>
		$(function(){
			var bookType = $("#bookType").children('th');
			$("#book").text(bookType.size());
			var scoreType = $("#scoreType").children('th');
			$("#score").text(scoreType.size());
			var schoolList = $("#school").children('th');
			$("#school_count").text(schoolList.size());
			var examType = $("#exam").children('th');
			$("#examType").text(examType.size());
			var yearList = $("#years").children('th');
			$("#year").text(yearList.size());
			var count = 0;
			var tdList = $("#scoreCount").children('td');			
			tdList.each(function(){
				count += $(this).html()*1;
			})
			$("#errorsum").text("共 "+ count +"处错误， 分布如下：");
			_cp = '${_cp}';	
			error.init();
			document.onkeydown = function(e) {
						var ev = document.all ? window.event : e;
						if (ev.keyCode == 13) {
							error.sousuo(10);
						}
					}
		})
	</script>
</@home>