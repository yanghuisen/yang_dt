var login = {
	init : function() {
		
		$("#sub").click(this.ok);
		$("#mit").click(this.mit);
	},
	mit :function(){
		var user = $("#code").val();
		location.href=_cp + "/tg/getWords?user=" + user;
	},
	ok : function() {	
		var user = $("#code").val();
		$.ajax({
			type:"POST",
			data:{"user":user},
			url:_cp +"/tg/userLogin",
			dataType: "json",
			success: function(rlt){
				if (rlt.code == 1) {
							$("#sub").hide();
							$("#mit").show();
							$("#ok").show();
							$("#group").show();
							$("#no").hide();
							var wz = rlt.data.wz;
							var wc = rlt.data.wc;
								$("#wz").html(wz);
								$("#wc").html(wc);
						} else {
							$("#no").show();
						}			
			}
		})
		
	}
}