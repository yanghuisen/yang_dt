var error = {
	init:function(){
		$('.dd li').click(this.select);
		$("#but").click(this.sousuo);
		$("#search").click(this.search);
		$('.sentence').click(this.sentence);
		$('.yearCount').click(this.yearCount);
		$('.exam').click(this.exam);
		$('.a').click(this.page);
		$('.ic-fa-right').click(this.right);
		var num = error.GetQueryString("page");
		if(num==null){
			num=1;
		}
		$('.ic-pagination li a').each(function(){
			if($(this).html().trim() == num+""){
				$(this).parent().addClass("active");
			}
		})
		$('.ic-error-name').click(this.errorDetails);
		$('.ic-fa-left').click(this.left);
		$('.goto').click(this.go);
		$('.error').click(this.tatle);
		//$('.tongji').click(this.tongji);
		if($('.kk').attr("name")*1==1){
			$("#one").addClass("active");
		}else{
			$("#two").addClass("active");
		}
		var groupList = $('.ic-group-list');
		groupList.each(function(){
			var sum = $(this).children('li').size();
			if(sum>1){
				sum = sum-1;
			}
			$(this).prev(".ic-group-header").children(".ic-gCounts").text(sum);
		})
	},
	errorDetails:function(){
		var errorType = $(this).html();
		var page = 1;
		var school = error.GetQueryString("schoolName");
		var yearList = error.GetQueryString("yearList");
		var book = error.GetQueryString("book");
		var exam = error.GetQueryString("examType");
		var score = error.GetQueryString("score");
		var pageCount = $('.page-count').html();
		location.href=_cp+"/error_statistics/errorDetails?schoolName="+school+
               		"&"+"examType="+exam+"&"+"yearList="+yearList+"&"+"book="+book +"&"+"type="+errorType+
               		"&"+"page="+page+"&"+"score="+score+"&"+"pageCount="+pageCount
	},
	tatle:function(){
		$(this).addClass("active").siblings().removeClass("active");
		if($(this).attr("id")=="two"){
			location.href=_cp+"/error_statistics"	
		}else{
		location.href=_cp+"/error"	}	
	},
	go:function(){
		var page = $("#input").val();
		var type = 0;
		$('.ic-header ul li').each(function(){
			if($(this).attr("class")=="error active"){
				if($(this).attr("id")=="two"){
					type=1;
				}
			}
		})
		if(page*1>$('.page-count').html().trim()*1){
		}else{
			var word = error.GetQueryString("errorWord");
			var school = error.GetQueryString("schoolName");
			var yearList = error.GetQueryString("yearList");
			var book = error.GetQueryString("book");
			var exam = error.GetQueryString("examType");
			var score = error.GetQueryString("score");
			var pageCount = $('.page-count').html();
			var error_1 = $('.ic-school').html();
			if(type==0){
			location.href=_cp+"/error/errorDetails?errorWord="+word+"&"+"schoolName="+school+
               		"&"+"examType="+exam+"&"+"yearList="+yearList+"&"+"book="+book+"&"+"type="+exam+"&"+"page="+page+"&"+"score="+score+"&"+"pageCount="+pageCount
		}else{
			location.href=_cp+"/error_statistics/errorDetails?errorWord="+word+"&"+"schoolName="+school+
               		"&"+"examType="+exam+"&"+"yearList="+yearList+"&"+"book="+book+"&"+"type="+error_1+"&"+"page="+page+"&"+"score="+score+"&"+"pageCount="+pageCount
		}}
	},
	right:function(){
		var type = 0;
		$('.ic-header ul li').each(function(){
			if($(this).attr("class")=="error active"){
				if($(this).attr("id")=="two"){
					type=1;
				}
			}
		})
		var max = $('.page-count').html().trim()*1;
		var page = error.GetQueryString("page")*1;
		var word = error.GetQueryString("errorWord");
		var school = error.GetQueryString("schoolName");
		var yearList = error.GetQueryString("yearList");
		var book = error.GetQueryString("book");
		var exam = error.GetQueryString("examType");
		var score = error.GetQueryString("score");
		var pageCount = $('.page-count').html();
		var error_1 = $('.ic-school').html();
		if(page==max){
		}else{
			page = page+1;
			$('.ic-pagination li a').each(function(){
				il =  $(this).html().trim()*1;
				if(il==page){
					$(this).parent().addClass("active").siblings().removeClass("active");
				}
			})
			//.addClass("active").siblings().removeClass("active");
			if(type==0){
			location.href=_cp+"/error/errorDetails?errorWord="+word+"&"+"schoolName="+school+
               		"&"+"examType="+exam+"&"+"yearList="+yearList+"&"+"book="+book +"&"+"type="+exam+"&"+"page="+page+"&"+"score="+score+"&"+"pageCount="+pageCount
		}else{
			location.href=_cp+"/error_statistics/errorDetails?errorWord="+word+"&"+"schoolName="+school+
               		"&"+"examType="+exam+"&"+"yearList="+yearList+"&"+"book="+book+"&"+"type="+error_1+"&"+"page="+page+"&"+"score="+score+"&"+"pageCount="+pageCount
		}}
	},
	left:function(){
		var type = 0;
		$('.ic-header ul li').each(function(){
			if($(this).attr("class")=="error active"){
				if($(this).attr("id")=="two"){
					type=1;
				}
			}
		})
		var num=0;		
		var word = error.GetQueryString("errorWord");
		var school = error.GetQueryString("schoolName");
		var yearList = error.GetQueryString("yearList");
		var book = error.GetQueryString("book");
		var exam = error.GetQueryString("examType");
		var score = error.GetQueryString("score");
		var pageCount = $('.page-count').html();
		var error_1 = $('.ic-school').html();
		$('.ic-pagination li').each(function(){
			if($(this).attr("class")=="active"){
				if($(this).html().trim() == 1+""){				
				}else{
				num = $(this).children('a').html().trim()*1-1;				
				}
			}
		})
		$('.ic-pagination li a').each(function(){
			if($(this).html().trim()==num+""){
				$(this).parent().addClass("active").siblings().removeClass("active");
				if(type==0){
				location.href=_cp+"/error/errorDetails?errorWord="+word+"&"+"schoolName="+school+
               		"&"+"examType="+exam+"&"+"yearList="+yearList+"&"+"book="+book +"&"+"type="+exam+"&"+"page="+num+"&"+"score="+score+"&"+"pageCount="+pageCount
			}else{
				location.href=_cp+"/error_statistics/errorDetails?errorWord="+word+"&"+"schoolName="+school+
               		"&"+"examType="+exam+"&"+"yearList="+yearList+"&"+"book="+book+"&"+"type="+error_1+"&"+"page="+num+"&"+"score="+score+"&"+"pageCount="+pageCount
			}}
		})		
	},
	page:function(){
		var type = 0;
		$('.ic-header ul li').each(function(){
			if($(this).attr("class")=="error active"){
				if($(this).attr("id")=="two"){
					type=1;
				}
			}
		})
		$(this).parent().addClass("active").siblings().removeClass("active");
		var page = $(this).html().trim();
		var word = error.GetQueryString("errorWord");
		var school = error.GetQueryString("schoolName");
		var yearList = error.GetQueryString("yearList");
		var book = error.GetQueryString("book");
		var exam = error.GetQueryString("examType");
		var score = error.GetQueryString("score");
		var pageCount = $('.page-count').html();
		var error_1 = $('.ic-school').html();
		if(type==0){
	location.href=_cp+"/error/errorDetails?errorWord="+word+"&"+"schoolName="+school+"&"+"examType="+exam+"&"+"yearList="+yearList+"&"+"book="+book +"&"+"type="+exam+"&"+"page="+page+"&"+"score="+score+"&"+"pageCount="+pageCount
	}else{
		location.href=_cp+"/error_statistics/errorDetails?errorWord="+word+"&"+"schoolName="+school+
               		"&"+"examType="+exam+"&"+"yearList="+yearList+"&"+"book="+book+"&"+"type="+error_1+"&"+"page="+page+"&"+"score="+score+"&"+"pageCount="+pageCount
	}},
	GetQueryString:function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");		
		     var r = window.location.search.substr(1).match(reg);		
		     if(r!=null)return  decodeURI(r[2]); return null;
	},
	exam:function(){
		var index = $(this).index();
		var exam = $("#exam").children('th').eq(index).html();
		var word = error.GetQueryString("errorWord");
		var school = error.GetQueryString("schoolName");
		var yearList = error.GetQueryString("yearList");
		var book = error.GetQueryString("book");
           location.href=_cp+"/error/errorDetails?errorWord="+word+"&"+"schoolName="+school+
               		"&"+"examType="+exam+"&"+"yearList="+yearList+"&"+"book="+book +"&"+"type="+exam+"&"+"score="+score      
	},
	yearCount:function(){
		var index = $(this).index();
		var yearList = $("#years").children('th').eq(index).html();
		var word = error.GetQueryString("errorWord");
		var school = error.GetQueryString("schoolName");
		var exam = error.GetQueryString("examType");
		var book = error.GetQueryString("book");
		location.href=_cp+"/error/errorDetails?errorWord="+word+"&"+"schoolName="+school+
               		"&"+"examType="+exam+"&"+"yearList="+yearList+"&"+"book="+book +"&"+"type="+yearList+"&"+"score="+score
	},
	sentence:function(){
		var index = $(this).index();
		var school = $("#school").children('th').eq(index).html();
		var word = error.GetQueryString("errorWord");
		var exam = error.GetQueryString("examType");
		var yearList = error.GetQueryString("yearList");
		var book = error.GetQueryString("book");
		var score = error.GetQueryString("score");
		location.href=_cp+"/error/errorDetails?errorWord="+word+"&"+"schoolName="+school+
               		"&"+"examType="+exam+"&"+"yearList="+yearList+"&"+"book="+book +"&"+"type="+school +"&"+"score="+score
	},
	search:function(){
		var word = $('.ic-input').val();
		location.href=_cp+"/error/queryResult?errorWord="+word
	},
	select:function(){		
			var sum = 0;
		if($(this).attr("condition")==0){
			$(this).addClass("active");
			$(this).attr("condition","1");		
			var li_List = $(this).parent().children('li');
			if($(this).html()=="全部"){
				$(this).siblings().attr("condition",0).removeClass("active")
				sum = li_List.size()-1;
			}else{				
				li_List.each(function(){
					//.attr("condition",0).removeClass("active")
				if($(this).html()=="全部"){
					$(this).attr("condition",0).removeClass("active")
				}
				if($(this).attr("condition")==1){
					sum++;
				}
			})
			}			
			error.sousuo();
		}else{
			var jj = 0;
			$(this).parent().children('li').each(function(){
				if($(this).attr("class")=="active"){
					jj++;
				}
			})
			if(jj > 1){
			$(this).removeClass("active");
			$(this).attr("condition","0");}			
	var li_List = $(this).parent().children('li');
			li_List.each(function(){
				if($(this).attr("condition")==1 && $(this).html() != "全部"){
					sum++;
				}else{
					if($(this).attr("condition")==1 && $(this).html()=="全部")
					sum = li_List.size()-1;
				}
			})				
			error.sousuo();
		}
		$(this).parent().prev(".ic-group-header").children(".ic-gCounts").text(sum);
	},
	sousuo:function(){
		var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
		var nameType = $(this).attr("type")*1
		var type = 0;
		if($(this).attr("id")=="but"){
			type = 1;
		}
		var errorWord = $("#input").val();
		var school = $("#school").children("li");
		var year = $("#year").children("li");
		var examTypeList = $("#examType").children("li");
		var examType = new Array();
		var schoolName = new Array();
		var yearList = new Array();
		
		school.each(function(){
			if($(this).attr("condition")==1){
				if($(this).html()=="全部"){
					 schoolName = new Array;
					return false ;
				}else{
				schoolName.push($(this).html());
				}
			}
		})
		schoolName = schoolName.toString();
		year.each(function(){
			if($(this).attr("condition")==1){
				if($(this).html()=="全部"){
					 yearList = new Array();
					return false ;
				}else{
				yearList.push($(this).html());}
			}
		})
			yearList = yearList.toString();
		examTypeList.each(function(){
			if($(this).attr("condition")==1){
				if($(this).html()=="全部"){
					examType = new Array();
					return false ;
				}else{
				 examType.push($(this).html());
				}
			}
		})
		examType = examType.toString();
		var scoreList = $("#score").children("li");
		var score = new Array();
		scoreList.each(function(){
			if($(this).attr("condition")==1){
				if($(this).html()=="全部"){
					score = new Array();;
					return false ;
				}else{
				score.push($(this).html());}
			}
		})
		score = score.toString();
		var bookList = $("#book").children("li");
		var book = new Array();
		bookList.each(function(){
			if($(this).attr("condition")==1){
				if($(this).html()=="全部"){
					book = new Array();;
					return false ;
				}else{
				book.push($(this).html());}
			}
		})
		book = book.toString();
		$.ajax({
             type: "POST",
             url: _cp+"/error/size",
             data: {"errorWord":errorWord,"schoolName":schoolName,"examType":examType,"yearList":yearList,"score":score,"book":book,"type":type},
             dataType: "json",
             success: function(rlt){
               	if(rlt.code == -1){
               		if(nameType == 0){
	               		location.href=_cp+"/error/queryResult?errorWord="+errorWord+"&"+"schoolName="+schoolName+
	               		"&"+"examType="+examType+"&"+"yearList="+yearList+"&"+"score="+score+"&"+"book="+book
               		}else{              		
               			 location.href=_cp+"/error_statistics/errorStatistics?schoolName="+schoolName+
	               		"&"+"examType="+examType+"&"+"yearList="+yearList+"&"+"score="+score+"&"+"book="+book             		
               		}
               	} else{
               		var size = rlt.code;
               		$("#size_a").text("Subcorpus-selected："+size+" Million");
               		//$('.ic-group ul li');
               		layer.closeAll();
               	}    
                      }
         });

	}
}