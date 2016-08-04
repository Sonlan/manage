$(document).ready(function(){
    var indexUrl = 'http://1u5186s163.51mypc.cn/manage/html/';
    var agentUrl = 'http://1u5186s163.51mypc.cn/manage/agent';

    //header 登出按钮
	$('header p').hover(function(){
	    $(this).addClass('hover');
	},function(){
	    $(this).removeClass('hover');
	});
  
    /*
	 * init
	 */
    var currentPage = 0;
	var userState = 0; //正常 1-挂起


	/*
	 * nva-bar-item init
	 */
	$('.bar-item.userAgency').addClass('choosen');
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
	
	/*
	 * underline-choosen-item init
	 */
	$('.underline-item-group').each(function(){
		$(this).children('p').first().addClass('choosen');
	});
	$('.underline-item').hover(function(){
		var $this = $(this);
		if($this.hasClass('choosen')){}
		else{
			$this.addClass('hover');
		}
	},function(){
		var $this = $(this);
		if($this.hasClass('hover')){
			$this.removeClass('hover');
		}
	});
	$('.underline-item').click(function(){
		var $this = $(this);
		if($this.hasClass('choosen')){}
		else {
			//indo choosen
			var $choosen = 
			$this.siblings().filter(function(){
				return $(this).hasClass('choosen');
			}).removeClass('choosen');
			$this.removeClass('hover').addClass('choosen');
		}
	});
	
	/*
	 * 勾选经销商 事件委托#user-item-panel
	 */
	$('#saler-item-panel').on('change', 'input.saler-item-radio', function(evt){
		$('div.saler-opt button').removeAttr('disabled');
		//重置
		$('.user-register').removeClass('template');
		$('.user-edit').addClass('template');
	});
	
	/*
	 * 鼠标移动产生详情 事件委托#user-item-panel
	 */
	$('#saler-item-panel').on('mouseenter mouseleave', 'tr.saler-item', function(evt){
	    var $this = $(this);
	    var $detail = $('div.saler-item-detail');
		if(evt.type == 'mouseenter') {
		    $this.addClass('highlight');
			$detail.find('td.account').text($this.find('p.account').text());
            $detail.find('td.nickname').text($this.find('td.nickname').text());
            $detail.find('td.password').text($this.find('td.password').text());
            $detail.find('td.mail').text($this.find('td.mial').text());
			$detail.find('td.id').text($this.find('td.id').text());
			$detail.find('td.store_id').text($this.find('td.store_id').text());
			$detail.find('td.tel').text($this.find('td.tel').text());
			$detail.find('td.openid').text($this.find('td.openid').text());
			$detail.find('td.create_date').text($this.find('td.create_date').text());
			$detail.find('td.update_date').text($this.find('td.update_date').text());
			$detail.find('td.status').text($this.find('td.status').text());
		    $detail.removeClass('template');
		}else if(evt.type == 'mouseleave') {
		    $this.removeClass('highlight');
		    $detail.addClass('template');
		}
	});
	
	/*
	 * 注册提交按钮
	 */
	$('.saler-reg button').click(function(evt){
	    registerUser();
	});
	/*
	 * 编辑提交按钮
	 */
	$('.saler-edit button').click(function(evt){
	    var getParam = {
		    "storeId" : $('.user-edit').find('select option:selected').val(),
		    "id": $('.user-edit').find('input.id').val(),
			"password": $('.user-edit').find('input.password').val(),
			"tel": $('.user-edit').find('input.tel').val(),
			"mail": $('.user-edit').find('input.mail').val()
		};
	    $.get(agentUrl + '/edit.do', getParam, function(data){
		    if(data.errorCode == 0) {
			    alert('编辑成功!');
				$('.user-register').removeClass('template');
				$('.user-edit').addClass('template');
                queryPage(currentPage, userSate);				
			}else {
			    alert(data.errorCode);
			}
		});
	});
	
	/* 
	 * 正常/冻结状态查询 
	 */
	$('.user-state-choose').click(function(evt){
		var $this = $(this);
		if($this.text() == '正常') {
		  userState = 0;
		  $('.saler-opt button:nth-child(1)').removeClass('template');
		  $('.saler-opt button:nth-child(3)').removeClass('template');
		  $('.saler-opt button:nth-child(2)').addClass('template');
		  queryPage(0, userState);
		}else if($this.text() == '冻结'){
		  userState = 1;
          $('.saler-opt button:nth-child(1)').addClass('template');
          $('.saler-opt button:nth-child(3)').addClass('template');
          $('.saler-opt button:nth-child(2)').removeClass('template');
		  queryPage(0, userState);
		}
	});
	
	/* 
	 * 删除/冻结/恢复 操作 
	 */
	$('.saler-opt button').click(function(evt){
		var $this = $(this);
		//获得选中项
		var $item = $('.saler-item input:radio:checked').parent().parent();
		//冻结操作
		if($this.text() == '冻结') {
		  if(confirm('确定冻结选中项?')){
			  //调用冻结函数
			  hangUser($item.find('td.id').text()); 
		  }
		}//恢复操作
		else if($this.text() == '恢复'){
		  if(confirm('确定恢复选中项?')){
			  //调用恢复函数
			  activateUser($item.find('td.id').text()); 
		  }
		}//删除操作
		else if($this.text() == '删除'){
		  if(confirm('确定删除选中项?')){
			  //调用恢复函数
			  deleteUser($item.find('td.id').text()); 
		  }
		}//编辑操作
		else if($this.text() == '编辑'){
		    //加载下拉控件
            
            $.post("http://1u5186s163.51mypc.cn/manage/store/getall","",function (data,status){
                if(0 == data.errorCode){
                    var length = getJsonObjLength(data.param);
                    for(i=0;i<length;i++){
                        $(".chzn-select").append("<option value='"+data.param[i].id+"'>"+data.param[i].name+"</option>");
                    }
                    //$(".chzn-select").trigger("chosen:updated");
					$(".chzn-select").chosen({no_results_text: "没有匹配结果", search_contains: true});
                }
            },'json');

            function getJsonObjLength(jsonObj) {
                var Length = 0;
                for (var item in jsonObj) {
                    Length++;
                }
                return Length;
            }
			
		    $('.user-edit').find('input.id').val($item.find('td.id').text());
		    $('.user-edit').find('input.password').val($item.find('td.password').text());
		    $('.user-edit').find('input.tel').val($item.find('td.tel').text());
		    $('.user-edit').find('input.mail').val($item.find('td.mail').text());
			//选中当前value
			$(".chzn-select").find('option[value=' + $item.find('td.store_id').text() + ']').attr('selected', true);
		    $('.user-register').addClass('template');
			$('.user-edit').removeClass('template');
		}
	});
	
	/*
	 * 注册操作 操作后会返回操作结果并清空注册表
	 */
	function registerUser(){
	    var $regForm = $('.saler-reg');
		var getParam = { 
			"key" : 123456,
			"storeid" : 1, 
            "account" : $regForm.find('input.account').val(),
            "password" : $regForm.find('input.password').val(),
            "tel" : $regForm.find('input.tel').val(),
            "mail" : $regForm.find('input.mail').val()
		};
		$regForm.find('input').val('');
		$.get(agentUrl + '/register', getParam, function(data){
		    console.log('in');
		    if(data.errorCode == 0) {
			    alert('注册成功!');
			}else {
			    alert('注册失败!');
			    console.log(data.errorCode);
			}
		}).fail(function(){
		    alert('请求异常!');
		});
	}
	
	
	/*
	 * 冻结操作 冻结后会自动重新刷新当前页面
	 */
	function hangUser(id) {
		var getParam = {ids : id};
		$.get(agentUrl + '/suspend', getParam, function(data){
		  if(data.errorCode == 0) {
			  alert('操作成功！');
			  queryPage(userCurrentPage, userState);  //重新查询当前页面
		  }else {
		      alert('操作失败!');
		      console.log('errorCode: ' + data.errorCode);
		  }
		});
	}

	/*
	 * 恢复操作 恢复后会自动重新刷新当前页面
	 */
	function activateUser(id) {
	    var getParam = {ids : id};
		$.get(agentUrl + '/activate', getParam, function(data){
		  if(data.errorCode == 0) {
			  alert('操作成功！');
			  queryPage(userCurrentPage, userState);  //重新查询当前页面
		  }else {
		  }
		});
	}

	/*
	 * 删除操作 删除后会自动重新刷新当前页面
	 */
	function deleteUser(id) {
	    var getParam = {ids : id};
		$.get(agentUrl + '/delete.do', getParam, function(data){
		  if(data.errorCode == 0) {
			  alert('操作成功！');
			  queryPage(userCurrentPage, userState);  //重新查询当前页面
		  }else {
		      console.log('errorCode: ' + data.errorCode);
		  }
		});
	}
	
	
	/*
	 * 每次queryPage之后调用生成PageNav
	 * @pageQuantity: 页面数量
	 * @userType:'普通用户','促销员','经销商','企业'
	 * @userSate:0,1 正常状态/冻结状态
	 */
	function createPageNav(pageQuantity, userState){
		var $liLast = $('nav.pageNav li:last');
		for(var i=0; i<pageQuantity; i++) {
			$('<li><a>' + (i+1) + '</a></li>').insertBefore($liLast).on('click', {value: i}, function(evt){
				$('nav.pageNav li').removeClass('data-active');
				$(this).addClass('data-active');
				queryPage(evt.data.value, userState);
			});
		}
		$('nav.pageNav li').removeClass('data-active');
		$('nav.pageNav li:eq(' + (currentPage+1) + ')').addClass('data-active');
	}
	
	/*
	 * 查询操作 
	 * @page:查询页面 
	 * @userType:'普通用户','促销员','经销商','企业'
	 * @userSate:0,1 正常状态/冻结状态
	 */
	function queryPage(page, userState){
	    //先禁用操作按钮
		$('div.saler-opt button').attr('disabled', 'disabled');
		userCurrentPage = page;
		var getParam = {"page": page,
		                "status" : userState};
		$.get(agentUrl + '/query', getParam, function(data){
			if(data.errorCode == 0) {
			    if(data.param) {
				    $('.saler-item-none').addClass('template');
					$('.user-item-table').removeClass('template');
					//删除原导航节点
					$('nav li:not(.disabled)').remove();
					//清空
					$('.user-item-table tbody').empty();
					var pageNnm = Math.ceil(data.errorMsg/5);
					createPageNav(pageNnm, userState);//errorCode=0: errorMsg=总页面数
					for(var i=0; i<data.param.length; i++) {
					  createUserItem(data.param[i]).appendTo('.user-item-table tbody');  //
					}	
				}else {
					//删除原导航节点
					$('nav li:not(.disabled)').remove();
				    $('.user-item-table').addClass('template');
				    $('.saler-item-none').removeClass('template');
				}	
			}else {
				var msg = '';
				switch(data.errorCode){
					case 1: msg = '无操作权限'; break;
					case 2: msg = '后台异常'; break;
				}
				alert(data.errorMsg + '\n' + msg);
				queryPage(0, 0);
			}
		}).fail(function(jqXHR){
		  console.log(jqXHR);
		  //$('div.info-panel')
		  //.html('An error occurred: ' + jqXHR.status)
		  //.append(jqXHR.responseText);
		}, 'jsonp');		
	}
	
	/*
	 * 创建user-item
	 * @dataObj:源对象
	 * @userType:'普通用户','促销员','经销商','企业'
	 */
	function createUserItem(dataObj){
		//数据写入

		var htmlCode = '';
		htmlCode += '<tr class="saler-item">'
		          + '<td><input type="radio" class="saler-item-radio" name="saler-item-radio"/><p class="account" style="display:inline;">'
				  + dataObj.account
				  + '</p></td>'
				  + '<td class="id">' + dataObj.id + '</td>'
				  + '<td class="store_id">' + dataObj.storeId + '</td>'
				  + '<td class="tel">' + dataObj.tel + '</td>'
				  + '<td class="openid template">' + dataObj.openid + '</td>'
				  + '<td class="password template">' + dataObj.password + '</td>'
				  + '<td class="create_date template">' + dataObj.createDate + '</td>'
				  + '<td class="update_date template">' + dataObj.updateDate + '</td>'
				  + '<td class="status template">' + dataObj.status + '</td>'
				  + '<td class="nickname template">' + dataObj.nickname + '</td>'
				  + '<td class="mail template">' + dataObj.mail + '</td>'
				  + '</tr>';

	    htmlCode = htmlCode.replace(/undefined/g, '-');
        		
		return $(htmlCode);
	}

	queryPage(currentPage, userState);
	
});