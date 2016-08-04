$(function(){//没有$.ready的写法，只有$()、$().ready和$(document).ready
	var indexUrl = 'http://1u5186s163.51mypc.cn/manage/html/';
	var $warning=$("#warning"),
		$dealerInfo=$(".dealer-info"),
		currentPage=1,
		pageNum=0,
		ID=0,
		Rlength=0,
		currentTerm;
		
	var defTableHtml='<tr class="table-title"><th class="dealer-name">名称</th><th class="dealer-business">经营范围</th>'
					+'<th class="dealer-location">地理位置</th><th class="dealer-createTime">创建时间</th><th class="dealer-updateTime">更新时间</th>'
					+'<th class="delete-dealer">操作</th></tr>';
		
	var PAGELENGTH=10;//单页表单长度
	var tableData=[];
	
	/*
	 * nva-bar-item init
	 */
	$('.bar-item.agentInfo').addClass('choosen');
    $('.bar-item').hover(function(){
	    var $this = $(this);
		if($this.hasClass('choosen')){}
		else {
		   $(this).addClass('hover');
		}
	},function(){
        $(this).removeClass('hover')
	})
	//bar-item click
	$('.bar-item').click(function(evt){
		var $this = $(this);
		if($this.hasClass('choosen')) {}
		else {
			$('.bar-item').removeClass('hover choosen');
			$this.addClass('choosen');
		}
		
		switch($this.text()){
		    case '普通用户' :
          			           window.location = indexUrl+'entUserManagement';
							   break;
			case '促销员' : 
                               window.location = indexUrl+'entSalerManagement';
							   break;
			case '经销商' :
			                   window.location = indexUrl+'entAgencyManagement';
							   break;
			case '企业' : 
			                   window.location = '/ent';
							   break;
			case '产品信息' : 
			                   window.location= indexUrl + 'entProductInfo';
							   break;
								//$('#main-content').load('fragInfoContent.html');
								//$.getScript('user/js/uploadPreview.min.js');
								//$.getScript('user/js/entInfo.js');	
								//break;
			case '经销点信息' : 
			                   window.location = indexUrl+'entAgencyInfo';
							   break;
		}	
    });
	
	/*显示首页*/
	currentTerm={page:0,name:'',range:'',areaCode:''};
	$.when(getPage(1)).done(function(){
		showTable();//直接写（异步）不行，需要写在function内
	});
	
	/*地区选择*/
	//$(".mask2").css("display","block");
	$(".chzn-select").chosen({no_results_text: "没有匹配结果", search_contains: true});
	$.post("http://1u5186s163.51mypc.cn/manage/area/getall","",function (data,status){
                if(0 == data.errorCode){
                    var length = getJsonObjLength(data.param);
                    for(i=0;i<length;i++){
						//if(i==3) $(".chzn-select").append("<option selected=true value='"+data.param[i].code+"'>"+data.param[i].name+"</option>");else
						$(".chzn-select").append("<option value='"+data.param[i].code+"'>"+data.param[i].name+"</option>");
                    }
					$(".chzn-select").trigger("chosen:updated");
					$("#add-dealer").css("display","none");
					$("#revise-dealer").css("display","none");
				//	$(".mask2").css("display","none");
					$(".add-revise-dealer").css("z-index","3");
                }
            },'json');

	function getJsonObjLength(jsonObj) {
		var Length = 0;
		for (var item in jsonObj) {
			Length++;
		}
		return Length;
	}
	
	
	/*表单验证*/
	var $addValidator=$("#add-dealer").validate({/*只能有一个元素*/
		onfocusout: function(element){//失焦时验证(默认是在表单提交时执行验证代码。 手册中提到使用 onfocusout:false 来解决失去焦点时验证的参数功能，但是经测试无效果)
				$(element).valid();
		},
		errorPlacement : function(error, element) { //element即当前验证的表单元素,error:<label id="name-error" 
													//class="error" for="name" style="font-size: 1.1em; font-weight:normal; color: red;">这是必填字段</label>
				error.css({"font-size":"1.1em","font-weight":"normal","color":"red"}).appendTo(element.prev());
				//console.log(error);
			},
		rules:{
			name:{
				required:true
			},
			id:{
				required:true
			},
			business:{
				required:true
			},
			/*location:{
				required:true
			}*/
		}
	});
	var $reviseValidator=$("#revise-dealer").validate({
		onfocusout: function(element){
				$(element).valid();
		},
		errorPlacement : function(error, element) { 
				error.css({"font-size":"0.8em","color":"red"}).appendTo(element.prev());
		},
		rules:{
			name:{
				required:true,
			},
			id:{
				required:true,
			},
			business:{
				required:true,
			},
			/*location:{
				required:true,
			}*/
		}
	});
	
	/*表单生成*/
	function showTable(){
		/*显示第一页表单*/
		$dealerInfo.html(defTableHtml);
		for(var i=0;i<(pageNum>1?PAGELENGTH:Rlength);i++){
			$(tableData[i]).appendTo($dealerInfo);
		}
		currentPage=1;
		addBgToPN();
		$("#lastPage").css("visibility","hidden");//保留位置的隐藏,切换时不会有位置移动			
	}
	//生成页码
	function createPageList(){
		var tmepHtml="",
			$Pages=$("#pages");
			
		pageNum=Math.ceil(Rlength/PAGELENGTH);
		$Pages.html('');
		//alert(pageNum);
		if(pageNum>1){
			$('<li id="lastPage"><</li>').appendTo($Pages);
			for(var i=0;i<pageNum;i++){
				tmepHtml='<li class="pageNumber">'+(i+1)+'</li>';
				$(tmepHtml).appendTo($Pages);//jq对象才能调用jq函数
			}
			$('<li id="nextPage">></li>').appendTo($Pages);
		}
	}

	/*查询*/
	function query(){
		var queryTerm={page:'',name:'',range:'',areaCode:''};
		queryTerm.name=$("#query-name").val();
		queryTerm.range=$("#query-business").val();
		queryTerm.areaCode=$("#query-location").val();
		queryTerm.page=0;
		//console.log(queryTerm);
		getDealerInfo(queryTerm);
	}
	$(".get-dealer-button").click(query);
	/*document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];          
             if(e && e.keyCode==13){ // enter 键
                query();
            }
        }; */
	//获取首页
	function getDealerInfo(queryTerm){
		$.post("http://1u5186s163.51mypc.cn/manage/store/query.do",queryTerm,function(data){
            $(".dealer-info").html("");
            $("#pages").html("");
			console.log(data);
			if(data.errorCode)
				alert(data.errorMsg);
			else{
				tableData=[];//每次查询清空tableData
				for(var i=0;i<data.param.length;i++){
					/*data.param[i].createDate=data.param[i].createDate.toString();
					data.param[i].updateDate=data.param[i].updateDate.toString();*/
					tableData[i]='<tr class="table-contents" id="'+data.param[i].id+'"><td><a class="td-dealer-name">'+data.param[i].name+'</a></td><td>'
									+data.param[i].range+'</td>'+'<td>'+data.param[i].areaCode+'</td>+<td>'+data.param[i].createDate+'</td><td>'+data.param[i].createDate
									+'</td><td><a class="revise-button">修改</a>&nbsp<a class="delete-button">删除</a></td></tr>';;
				}
				Rlength=data.errorMsg;
				createPageList();
				showTable();
				currentTerm=queryTerm;
			}
		});
	}
	//获取指定数据
	function getPage(page){
		var dtd = $.Deferred();
		
		currentTerm.page=page-1;
		$.post("http://1u5186s163.51mypc.cn/manage/store/query.do",currentTerm,function(data){
			console.log(data);
			if(data.errorCode){
				alert(data.errorMsg);
			}
			else{
				if(data.param==null){//没有数据
					tableData=[];
					Rlength=0;
				}else{
					for(var i=0;i<data.param.length;i++){
					/*data.param[i].createDate=data.param[i].createDate.toString();
					data.param[i].updateDate=data.param[i].updateDate.toString();*/
					tableData[i+PAGELENGTH*currentTerm.page]='<tr class="table-contents" id="'+data.param[i].id+'"><td><a class="td-dealer-name">'+data.param[i].name+'</a></td><td>'
									+data.param[i].range+'</td>'+'<td>'+data.param[i].areaCode+'</td>+<td>'+data.param[i].createDate+'</td><td>'+data.param[i].createDate
									+'</td><td><a class="revise-button">修改</a>&nbsp<a class="delete-button">删除</a></td></tr>';
					}
					Rlength=data.errorMsg;
				}
				//根据表单长度生成页码
				createPageList();
				if(pageNum<currentPage){//如果删除最后一页，则刷新前一页
					currentPage--;
					refreshPage();
				}
			}
			dtd.resolve();
		});
		return dtd;
	}
	//刷新当前页
	function refreshPage(){
		$.when(getPage(currentPage)).done(function(){
			var pageLength=Rlength>=PAGELENGTH*currentPage?PAGELENGTH:Rlength-PAGELENGTH*(currentPage-1);
		
			$dealerInfo.html(defTableHtml);
			for(var i=(currentPage-1)*PAGELENGTH;i<(currentPage-1)*PAGELENGTH+pageLength;i++){
				$(tableData[i]).appendTo($dealerInfo);
			}
			$("#lastPage").css("visibility","visible");
			$("#nextPage").css("visibility","visible");
			if(currentPage==1) $("#lastPage").css("visibility","hidden");
			if(currentPage==pageNum) $("#nextPage").css("visibility","hidden");
			
			addBgToPN();
			//createPageList();
		});
	}
	/*查看*/
	$(".table-wrapper").on("click",".td-dealer-name",function(){
		var $detailSpan=$(".detail-info").find("span"),
			$tableSpan=$(this).parent().parent().find("td");
			
			for(var i=0;i<$detailSpan.length;i++){
				$detailSpan.eq(i).text($tableSpan.eq(i).text());
			}
			$(".detail-info").slideDown("fast");
			$(".mask").show();
	});
	/*添加*/
	$(".add-dealer-button").click(function(){
			$addValidator.resetForm();//重置表单验证
			$("#add-dealer").slideDown("fast");
			$(".mask").show();
		}
	);
	$("#add-dealer .dealer-submit").click(function(){
		if($addValidator.form()){//表单验证
			addDealer();
		}
	});
	function addDealer(){
		var $addDealer=$("#add-dealer"),
			name=$addDealer.find(".dealer-name").val(),
			business=$addDealer.find(".dealer-business").val(),
			location=$addDealer.find(".dealer-location").val();
		var dealerData={name:name,range:business,areaCode:location};
		//alert(dealerData.areaId);
		$.post("http://1u5186s163.51mypc.cn/manage/store/add.do",dealerData,function(data){
			if(data.errorCode){
				alert(data.errorMsg);
			}else{
				currentPage=currentPage<1?1:currentPage;
				refreshPage();
			}
		});
		
	}
	/*修改*/
	$(".table-wrapper").on("click",".revise-button",function(){
			
			$reviseValidator.resetForm();
			
		var $reviseDealer=$("#revise-dealer"),
			$reviseDealerInput=$reviseDealer.find("input"),
			$dealerInfoTd=$(this).parent().parent().find("td");
			ID=parseInt($(this).parent().parent().attr("id"));
			//alert($dealerInfoTd.eq(1).text());
			for(var i=0;i<3;i++){
				if(i==2){
					/*var $option=$("#revise-dealer-location option");
					for(var j=0;j<$option.length;j++){
						if($option.eq(j).text()==$dealerInfoTd.eq(i).text()){
							//alert($option.eq(j).text());
							$option.eq(j).attr("selected",true);
							//alert($("#revise-dealer-location").find("option:selected").text());
						}else $option.eq(j).prop("selected",false);
					}*/
					//alert($dealerInfoTd.eq(i).text());
					$("#revise-dealer-location").val($dealerInfoTd.eq(i).text());
					$("#revise-dealer-location").trigger("chosen:updated");
				}else $reviseDealerInput.eq(i).val($dealerInfoTd.eq(i).text());
			}
			
			$reviseDealer.slideDown("fast");
			$(".mask").show();
	});
	$("#revise-dealer .dealer-submit").click(function(){
		if($reviseValidator.form()){
			reviseDealer();
		}
	});
	function reviseDealer(){
		var $reviseDealer=$("#revise-dealer"),
			name=$reviseDealer.find(".dealer-name").val(),
			business=$reviseDealer.find(".dealer-business").val(),
			location=$reviseDealer.find(".dealer-location").val();
		var dealerData={name:name,id:ID,range:business,areaCode:location};
		//alert(dealerData.oldID);
		$.post("http://1u5186s163.51mypc.cn/manage/store/edit.do",dealerData,function(data){
			if(data.errorCode)
				alert(data.errorMsg);
			else{
				//刷新当前页
				refreshPage();
				
				$reviseDealer.slideUp("fast");
				$(".mask").hide();
			}
		});
	}
	
	/*删除*/
	$(".table-wrapper").on("click",".delete-button",function(){
		//alert("我是");
		$warning.find("h4").text('确定删除经销点'+'"'+$(this).parent().parent().find("td:first-child").text()+'"?');/*出现乱码是因为编码不对*/
		ID=parseInt($(this).parent().parent().attr("id"));
		$warning.slideDown("fast");
		$(".mask").show();
	});
	$("#delete-submit").click(function(){
		deleteDealer();
	});
	$("#delete-cancel").click(function(){
		$warning.slideUp("fast");
		$(".mask").hide();
	});
	function deleteDealer(){
		//alert(ID);
		$.post("http://1u5186s163.51mypc.cn/manage/store/delete.do",{ids:ID},function(data){
			if(data.errorCode)
				alert(data.errorMsg);
				
			$warning.slideUp("fast");
			$(".mask").hide();
			refreshPage();
		});
	}

	/*关闭面板事件*/
	$(".block-close").click(function(){
		$(this).parent().slideUp("fast");
		$(".mask").hide();
	});
	
	/*页码切换事件*/
	//上下翻页
	$("#pages").on("click","#lastPage",function(){
		$.when(getPage(currentPage-1)).done(function(){
			$dealerInfo.html(defTableHtml);
			for(var i=(currentPage-2)*PAGELENGTH;i<(currentPage-1)*PAGELENGTH;i++){
				$(tableData[i]).appendTo($dealerInfo);
			}
			$("#nextPage").css("visibility","visible");
			if(--currentPage==1) $("#lastPage").css("visibility","hidden");
			
			addBgToPN();
		});
	});
	$("#pages").on("click","#nextPage",function(){
		$.when(getPage(currentPage+1)).done(function(){
			var pageLength=Rlength>=PAGELENGTH*(currentPage+1)?PAGELENGTH:Rlength-PAGELENGTH*currentPage;
		
			$dealerInfo.html(defTableHtml);
			for(var i=currentPage*PAGELENGTH;i<currentPage*PAGELENGTH+pageLength;i++){
				$(tableData[i]).appendTo($dealerInfo);
			}
			$("#lastPage").css("visibility","visible");
			if(++currentPage==pageNum) $("#nextPage").css("visibility","hidden");
			
			addBgToPN();
		});
	});
	//数字页码
	$("#pages").on("click",".pageNumber",function(){
		currentPage=parseInt($(this).text());
		$.when(getPage(currentPage)).done(function(){
			var pageLength=Rlength>=PAGELENGTH*currentPage?PAGELENGTH:Rlength-PAGELENGTH*(currentPage-1);
		
			$dealerInfo.html(defTableHtml);
			for(var i=(currentPage-1)*PAGELENGTH;i<(currentPage-1)*PAGELENGTH+pageLength;i++){
				$(tableData[i]).appendTo($dealerInfo);
			}
			$("#lastPage").css("visibility","visible");
			$("#nextPage").css("visibility","visible");
			if(currentPage==1) $("#lastPage").css("visibility","hidden");
			if(currentPage==pageNum) $("#nextPage").css("visibility","hidden");
			
			addBgToPN();
		});
	});
	
	//给当前页码加选中效果
	function addBgToPN(){
		for(var i=0;i<$(".pageNumber").length;i++){
			if(i==currentPage-1)
				$(".pageNumber").eq(i).addClass("current-page");
			else
				$(".pageNumber").eq(i).removeClass("current-page");
		}
	}
});