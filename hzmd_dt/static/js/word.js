var word={
	currentTrId:"",
	init:function(){
		$("#sub").click(this.saveTg);
		$(":radio").click(this.change);
		$('.rest').click(this.rest);
		$('.next').click(this.next);
		$('.word').click(this.jishi);
	},
	jishi:function(){
		var gg = $(this).attr("id");
		alert(gg);
		var word = $(this).html();
		var sh = $("#jie_"+$(this).attr("id")).attr("isload");
		if(sh==0){
			$("#jie_"+$(this).attr("id")).show();
			$("#jie_"+$(this).attr("id")).attr("isload",1);
			$.ajax({
			type:"POST",			
			url:_cp +"/tg/jieShi",
			dataType: "json",
			data:{"word":word},
			success: function(rlt){	
					$("#shi_"+gg).html(rlt.msg);
			}			
		})		
		}else{
			$("#jie_"+$(this).attr("id")).hide();
			$("#jie_"+$(this).attr("id")).attr("isload",0);
		}
		//$().show();
		
	},
	next:function(){
		var user = $("#user").html();
		location.href=_cp + "/tg/getWords?user=" + user;
	},
	rest:function(){		
		window.open("about:blank","_self").close()   		
	}		
	,
	change:function(){
		$(this).parent().parent().attr("isChange",1);
	},
	saveTg:function(){
		var tgList = word.getInfo();
		var list = JSON.stringify(tgList);
		var user = $("#user").html();
		var g_id = $("#g_id").html();
		$.ajax({
			type:"POST",
			data:{"list":list,"user":user,"g_id":g_id},
			url:_cp +"/tg/saveTg",
			dataType: "json",
			success: function(rlt){
				if(rlt.code==2){
						location.href=_cp+"/tg/tiShi?user=" + user
				}else {
					location.href=_cp + "/tg/getWords?user=" + user;					
				}
			}
		})
	},
	 getInfo: function(){
	 	var tg = [];
	 	$("div[isChange='1']").each(function(i,n){
				var vd = $(n).find(":input:radio:checked");
				var tag={};
				tag.dt_type = vd.val();
				tag.w_id = vd.attr("name");
				tg.push(tag);			
	 	})
	 	return tg;	
	 }
}