$(function(){
	initInputCode();
})
var str="";
var m=0;
function initInputCode(){
	$(".test-code li").on("keyup",function(e){
		if(e.keyCode<=105&&e.keyCode>=48){     
    	     $(this).children("input").blur().parent().next().children("input").removeAttr("disabled").focus();
    	     
        }else{
         	$(this).children("input").val("");
        }
        if(e.keyCode==46||e.keyCode==8){
         	$(this).children("input").removeClass("error").blur().parent().prev().children("input").focus();
         	$(".code-title").removeClass('code-title-error');
         	str="";
        }
        var length=$(".test-code li").length;
        var seven=$(".test-code li").eq(length-1).children("input").val();
	    if(seven!=""){
	    	initSureBtn();
	    }
	});
}
function initSureBtn(){
	$(".test-code li").each(function(i,v){
		var value=$(this).children("input").val();
		str += value;
})
	if(str!="1234"){
		$(".code-title").addClass('code-title-error');
		$(".test-code li input").addClass("error");
		str="";
	}else{
		$(".code-title").removeClass('code-title-error');
		$(".test-code li input").removeClass("error");
		$(".sure-btn").removeAttr("disabled").addClass("sure-btn-click");
	}
}

