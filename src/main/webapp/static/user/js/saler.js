$(document).ready(function(){
    var indexUrl = 'http://1u5186s163.51mypc.cn/manage/html/';
    //header 登出按钮
	$('header p').hover(function(){
	    $(this).addClass('hover');
	},function(){
	    $(this).removeClass('hover');
	});
  
    /*
	 * init
	 */
	var numPerPage = 10;
    var currentPage = 0;
	var userState = 0; //正常 1-挂起
    var salerUrl = 'http://1u5186s163.51mypc.cn/manage/salesman';


	/*
	 * nva-bar-item init
	 */
	$('.bar-item.userSaler').addClass('choosen');
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
	 * 勾选user-item 事件委托#user-item-panel
	 */
	$('#saler-item-panel').on('change', 'input.saler-item-radio', function(evt){
		$('div.saler-opt button').removeAttr('disabled');
	});
	
	/*
	 * hover .saler-item-detail 事件委托#user-item-panel
	 */
	$('#saler-item-panel').on('mouseenter mouseleave', 'tr.saler-item', function(evt){
	    var $this = $(this);
	    var $detail = $('div.saler-item-detail');
		if(evt.type == 'mouseenter') {
		    $this.addClass('highlight');
			$detail.find('th.name').text($this.find('th.name').text());
			$detail.find('td.nickname').text($this.find('td.nickname').text());
			$detail.find('td.id').text($this.find('td.id').text());
			$detail.find('td.work_id').text($this.find('td.work_id').text());
			$detail.find('td.store_id').text($this.find('td.store_id').text());
			$detail.find('td.openid').text($this.find('td.openid').text());
			$detail.find('td.create_date').text($this.find('td.create_date').text());
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
		    "id": $('.user-edit').find('input.id').val(),
			"work_id": $('.user-edit').find('input.work_id').val(),
			"name":encodeURI($('.user-edit').find('input.name').val()),
			//"status": $('.user-edit').find('input.status').val()
		};
	    $.get(salerUrl + '/edit.do', getParam, function(data){
		    if(data.errorCode == 0) {
			    alert('编辑成功!');
                queryPage(currentPage, userState);				
			}else {
			    alert(data.errorCode);
                queryPage(currentPage, userState);
			}
		})
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
		}else if($this.text() == '编辑'){
		    $('.user-edit').find('input.id').val($item.find('td.id').text());
		    $('.user-edit').find('input.work_id').val($item.find('td.work_id').text());
		    $('.user-edit').find('input.name').val($item.find('th.name').text());
		    $('.user-edit').find('input.status').val($item.find('td.statsu').text());
		    $('.user-register').addClass('template');
			$('.user-edit').removeClass('template');
		}
	});
	
	/*
	 * 注册操作 操作后会返回操作结果并清空注册表
	 */
	function registerUser(){
	    $('.saler-reg img').attr('src', 'http://1u5186s163.51mypc.cn/manage/salesman/testqrcode');
	}
	
	
	/*
	 * 冻结操作 冻结后会自动重新刷新当前页面
	 */
	function hangUser(id) {   
	    var getParam = {ids : id};
		$.get(salerUrl + '/suspend', getParam, function(data){
		  if(data.errorCode == 0) {
			  alert('冻结成功!');
			  queryPage(userCurrentPage, userState);  //重新查询当前页面
		  }else {
		      alert('冻结失败!');
			  queryPage(userCurrentPage, userState);
		  }
		});
	}

	/*
	 * 恢复操作 恢复后会自动重新刷新当前页面
	 */
	function activateUser(id) {
	    var getParam = {ids : id};
		$.get(salerUrl + '/activate', getParam, function(data){
		  if(data.errorCode == 0) {
			  alert('恢复成功！');
			  queryPage(userCurrentPage, userState);  //重新查询当前页面
		  }else {
		      alert('恢复失败!');
			  queryPage(userCurrentPage, userState);
		  }
		});
	}

	/*
	 * 删除操作 删除后会自动重新刷新当前页面
	 */
	function deleteUser(id) {
	    var getParam = {ids : id};
		$.get(salerUrl + 'delete.do', getParam, function(data){
		  if(data.errorCode == 0) {
			  alert('操作成功！');
			  queryPage(userCurrentPage, userState);  //重新查询当前页面
		  }else {
		      alert('删除失败!');
			  queryPage(userCurrentPage, userState);
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
				$('.user-register').removeClass('template');
				$('.user-edit').addClass('template');
	    //先禁用操作按钮
		$('div.saler-opt button').attr('disabled', 'disabled');

		userCurrentPage = page;
		var getParam = {"page": page,
					   "status": userState};
					   
		$.get(salerUrl + '/query', getParam, function(data){
			if(data.errorCode == 0) {
			    if(data.param) {
				    $('.saler-item-none').addClass('template');
					$('.user-item-table').removeClass('template')
				    //清空 用户列表栏
		            $('.user-item-table tbody').empty();
					//清空导航栏
					$('nav.pageNav li:not(.disabled)').remove();
					var pageNum = Math.ceil(data.errorMsg/numPerPage);
					createPageNav(pageNum, userState);//errorCode=0: errorMsg=总页面数
					for(var i=0; i<data.param.length; i++) {
					  console.log(i);
					  createUserItem(data.param[i]).appendTo('.user-item-table tbody');  //
					}	
				}else {
					//清空导航栏
					$('nav.pageNav li:not(.disabled)').remove();
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
			}
		}).fail(function(jqXHR){
		  //$('div.info-panel')
		  //.html('An error occurred: ' + jqXHR.status)
		  //.append(jqXHR.responseText);
		});		
	}
	
	/*
	 * 创建user-item
	 * @dataObj:源对象
	 * @userType:'普通用户','促销员','经销商','企业'
	 */
	function createUserItem(dataObj){
	    console.log(dataObj);
		//数据写入
		var htmlCode = '';
		htmlCode += '<tr class="saler-item">'
				  + '<td><input type="radio" class="saler-item-radio" name="saler-item-radio"/></td>'
				  + '<th class="name">' + dataObj.name + '</th>'
				  + '<td class="nickname">' + dataObj.nickname + '</td>'
				  + '<td class="id template">' + dataObj.id + '</td>'
				  + '<td class="openid template">' + dataObj.openid + '</td>'
				  + '<td class="store_id template">' + dataObj.storeId + '</td>'
				  + '<td class="work_id template">' + dataObj.workId + '</td>'
				  + '<td class="create_date">' + dataObj.createDate + '</td>'
				  + '<td class="status template">' + dataObj.status + '</td>'
				  + '</tr>';
		htmlCode = htmlCode.replace(/undefined/g, '-');
		return $(htmlCode);
	}

	queryPage(currentPage, userState);
	
});