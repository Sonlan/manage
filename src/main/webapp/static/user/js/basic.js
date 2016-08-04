$(document).ready(function(){
    var indexUrl = 'http://1u5186s163.51mypc.cn/manage/html/';
    var userUrl = 'http://1u5186s163.51mypc.cn/manage/user';
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
	var userType = '普通用户';
	var userState = '0';


	/*
	 * nva-bar-item init
	 */
	$('.bar-item').first().addClass('choosen');
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
	$('#user-item-panel').on('change select-all-change', 'input.user-item-checkbox', function(evt){
	    if(evt.type == 'change')  $('#select_all').prop('checked', false);
		var $item = $('input.user-item-checkbox').filter(function(){
			return $(this).prop('checked') == true;
		});
		if($item.length == 0) {
		  $('#user-item-thead button').attr('disabled', 'disabled');
		}else {
		  $('#user-item-thead button').removeAttr('disabled');
		}
	});
	
	/*
	 * user-item-detail 事件委托#user-item-panel
	 */
	$('#user-item-panel').on('mouseenter mouseleave', 'tr.user-item', function(evt){
	    var $this = $(this);
	    var $detail = $('div.user-item-detail');
		if(evt.type == 'mouseenter') {
		    $this.addClass('highlight');
		    $detail.find('img').attr('src', $this.find('img').attr('src'));
			$detail.find('td.sex span').text($this.find('span.sex').text());
			$detail.find('th.nickname').text($this.find('span.nickname').text());
			$detail.find('td.id').text($this.find('td.id').text());
			$detail.find('td.openid').text($this.find('td.openid').text());
			$detail.find('td.create_date').text($this.find('td.create_date').text());
			$detail.find('td.status').text($this.find('td.status').text());
		    $detail.removeClass('template');
		}else if(evt.type == 'mouseleave') {
		    $detail.addClass('template');
			$this.removeClass('highlight');
		}
	});
	
	/*
	 * #select_all
	 */
	$('#select_all').on('change', function(evt){
	    var $this = $(this);
	    if($this.prop('checked') == true) {
		    $('.user-item input').prop('checked', true).trigger('select-all-change');
		} else {
		    $('.user-item input').prop('checked', false).trigger('select-all-change');
		}
	});
	
	/* 
	 * 正常/冻结状态查询 
	 */
	$('.user-state-choose').click(function(evt){
		var $this = $(this);
		if($this.text() == '正常') {
		  userState = 0;
		  $('#user-item-panel header h4').text('正常用户列表');
		  $('.user-item-optButton:first').removeClass('template btn-success').addClass('btn-warning');
		  $('.user-item-optButton:eq(1)').addClass('template');
		  queryPage(0, userState);
		}else if($this.text() == '冻结'){
		  userState = 1;
		  $('#user-item-panel header h4').text('冻结用户列表');
		  $('.user-item-optButton:first').addClass('template');
		  $('.user-item-optButton:eq(1)').removeClass('template btn-warning').addClass('btn-success');
		  queryPage(0, userState);
		}
	});
	
	/* 
	 * 删除/冻结/恢复 操作 
	 */
	$('.user-item-optButton').click(function(evt){
		var $this = $(this);
		//获得勾选中的item id
		var idArray = [];  //创建一个空数组
		$('.user-item input:checked').each(function(){
		  idArray[idArray.length] = $(this).parent().parent().find('td.id').text();
		});
		//冻结操作
		if($this.text() == '冻结') {
		  if(confirm('确定冻结选中项?')){
			  //调用冻结函数
			  hangUser(idArray); 
		  }
		}//恢复操作
		else if($this.text() == '恢复'){
		  if(confirm('确定恢复选中项?')){
			  //调用恢复函数
			  activateUser(idArray); 
		  }
		}//删除操作
		else if($this.text() == '删除'){
		  if(confirm('确定删除选中项?')){
			  //调用恢复函数
			  deleteUser(idArray); 
		  }
		}
	});
	
	/*
	 * 冻结操作 冻结后会自动重新刷新当前页面
	 */
	function hangUser(idArray) {
	    var getParam = {ids : idArray.join(',')};
		$.post(userUrl + '/suspend', getParam, function(data){
		  console.log(data.errorCode);
		  if(data.errorCode == 0) {
			  alert('操作成功！');
			  queryPage(userCurrentPage, userState);  //重新查询当前页面
		  }else {
			  alert(data.errorMsg + '\n' + data.errorCode);
		  }
		}, 'json');
	}

	/*
	 * 恢复操作 恢复后会自动重新刷新当前页面
	 */
	function activateUser(idArray) {
	    var getParam = {ids : idArray.join(',')};
		$.post(userUrl + '/activate', getParam, function(data){
		  if(data.errorCode == 0) {
			  alert('操作成功！');
			  queryPage(userCurrentPage, userState);  //重新查询当前页面
		  }else {
			  alert(data.errorMsg + '\n' + data.errorCode);
		  }
		}, 'json');
	}

	/*
	 * 删除操作 删除后会自动重新刷新当前页面
	 */
	function deleteUser(idArray) {
	    var getParam = {ids : idArray.join(',')};
		console.log(getParam);
		$.post(userUrl + '/delete.do', getParam, function(data){
		  if(data.errorCode == 0) {
			  alert('删除成功！');
			  queryPage(userCurrentPage, userState);  //重新查询当前页面
		  }else {
			  alert(data.errorMsg + '\n' + data.errorCode);
		  }
		}, 'json');
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
		userCurrentPage = page;
		var getParam = {"page": page,
					   "status": userState};
			$.get(userUrl + '/query', getParam, function(data){
				if(data.errorCode == 0) {
				    if(data.param) {
					    $('.user-item-table').removeClass('template');
						$('.user-item-none').addClass('template');
						//清空 用户列表栏
						$('#user-item-tbody').empty();
						//清空导航栏
						$('nav.pageNav li:not(.disabled)').remove();
						var pageNum = Math.ceil(data.errorMsg/10);
						createPageNav(pageNum, userState);//errorCode=0: errorMsg=总页面数
						for(var i=0; i<data.param.length; i++) {
						  createUserItem(data.param[i], userType).appendTo('#user-item-tbody');  //
						}
					}else {
						//清空导航栏
						$('nav.pageNav li:not(.disabled)').remove();
					    $('.user-item-table').addClass('template');
						$('.user-item-none').removeClass('template');
					}		
				}else {
					var msg = '';
					switch(data.errorCode){
						case 1: msg = '无操作权限'; break;
						case 2: msg = '后台异常'; break;
					}
					alert(data.errorMsg + '\n' + msg);
					queryPage(0, userState);
				}
			}).fail(function(jqXHR){
			  //$('div.info-panel')
			  //.html('An error occurred: ' + jqXHR.status)
			  //.append(jqXHR.responseText);
			}, 'json');
			

	}
	
	/*
	 * 创建user-item
	 * @dataObj:源对象
	 * @userType:'普通用户','促销员','经销商','企业'
	 */
	function createUserItem(dataObj, userType){
		var htmlCode = '';
		//数据写入
	    htmlCode += '<tr class="user-item"><td><input type="checkbox" class="user-item-checkbox"/>'
		          + '<img src=' + dataObj.headimgurl + ' class="img-rounded" alt="user" /></td>'
				  + '<td><span class="nickname">' + dataObj.nickname + '</span><span class="label label-info sex">' + ((dataObj.sex=1) ? '男' : '女') + '</span></td>'
				  + '<td><span class="label label-danger">普通用户</span></td>'
				  + '<td class="id template">' + dataObj.id + '</td>'
				  + '<td class="openid template">' + dataObj.openid + '</td>'
				  + '<td class="create_date template">' + dataObj.create_date + '</td>'
				  + '<td class="status template">' + dataObj.status + '</td></tr>';
	    htmlCode = htmlCode.replace(/undefined/g, '-');
		return $(htmlCode);
	}

	queryPage(currentPage, userState);
	
});