$(document).ready(function(){
    var indexUrl = 'http://1u5186s163.51mypc.cn/manage/html/';
    var currentPage = 0;
	
    //header 登出按钮
	$('header p').hover(function(){
	    $(this).addClass('hover');
	},function(){
	    $(this).removeClass('hover');
	});

	/*
	 * nva-bar-item init
	 */
	$('.bar-item.productInfo').addClass('choosen');
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
			                   window.location=indexUrl+ 'entProductInfo';
				               break;
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
	 * 新建产品信息/已有产品信息切换
	 */
	$('.product-state-choose').click(function(evt){
		var $this = $(this);
		if($this.text() == '新建产品信息') {
		    $('.editGoods').addClass('template');
            $('.createGoods').removeClass('template');  
		}else if($this.text() == '已有产品信息'){
		    $('.editGoods').removeClass('template');
			$('.createGoods').addClass('template');
		}
	});
	
	/*test*/
	var $copy = $('.goods-item');
	function initEditModal(){
	$copy.find('input[name="name"]').val($copy.find('tr.name td').text());
	$copy.find('input[name="description"]').val($copy.find('tr.description td').text());
	$copy.find('input[name="producer"]').val($copy.find('tr.producer td').text());
	$copy.find('input[name="origin"]').val($copy.find('tr.origin td').text());	
	$copy.find('input[name="price"]').val($copy.find('tr.price td').text());
	$copy.find('input[name="url"]').val($copy.find('tr.url td').text());
	$copy.find('input[name="exp_date"]').val($copy.find('tr.exp_date td').text());
	$copy.find('input[name="box_cnt"]').val($copy.find('tr.box_cnt td').text());
	$copy.find('textarea[name="description"]').text($copy.find('tr.description td').text());
	}
	initEditModal();
	//编辑功能
		$copy.find('button.edit-done').click(function(evt){
			
		});
		$copy.find('button.edit-close').click(function(evt){
            initEditModal();
			
		});
		
	/* test-end */
	

	
	/*
	 * 冻结操作 冻结后会自动重新刷新当前页面
	 */


	/*
	 * 恢复操作 恢复后会自动重新刷新当前页面
	 */


	/*
	 * 删除操作 删除后会自动重新刷新当前页面
	 */

	
	
	/*
	 * 每次queryPage之后调用生成PageNav
	 * @pageQuantity: 页面数量
	 */
	function createPageNav(pageQuantity){
		var $liLast = $('nav.pageNav li:last');
		for(var i=0; i<pageQuantity; i++) {
			$('<li><a>' + (i+1) + '</a></li>').insertBefore($liLast).on('click', {value: i}, function(evt){
				$('nav.pageNav li').removeClass('data-active');
				$(this).addClass('data-active');
				//queryPage(evt.data.value);              //注意这里不能写成 _queryPage(i)
			});
		}
		$('nav.pageNav li').removeClass('data-active');
		$('nav.pageNav li:eq(' + (currentPage+1) + ')').addClass('data-active');
	}

	
	/*
	 * 查询操作 
	 * @page:查询页面 
	 */
	function queryPage(page) {
		$('div.editGoods').empty();
		//nav
		//$('.queryGoods.template').clone().removeClass('template').appendTo('div.currentPage');
		currentPage = page;
		var queryData = {"page": page};
		$.get('http:ip:port/sales/productitem/query', queryData, function(data){
			if(data.errorCode == 0) {
				createPageNav(data.errorMsg);//errorCode=0: errorMsg=总页面数
				for(var i=0; i<data.param.length; i++) {
				  createGoodsItem(data.param[i]).prependTo('div.editGoods');  //不要写成insertBefore('nav.pageNav');
				}		
			}else {
			}
		}).fail(function(jqXHR){
		  //$('div.info-panel')
		  //.html('An error occurred: ' + jqXHR.status)
		  //.append(jqXHR.responseText);
		});
	}

	
	/*
	 * 创建goods-item
	 * @dataObj:源对象
	 */
	function createGoodsItem(dataObj){
		var $copy = $('.goods-item.template').clone().removeClass('template');
		
		$copy.find('tr.name td').text(dataObj.name);
		$copy.find('tr.description td').text(dataObj.description); 
		$copy.find('tr.producer td').text(dataObj.producer);
		$copy.find('tr.origin td').text(dataObj.origin);
		$copy.find('tr.price td').text(dataObj.price);
		$copy.find('tr.url td').text(dataObj.url);
		$copy.find('tr.exp_date td').text(dataObj.exp_date);
		$copy.find('tr.box_cnt td').text(dataObj.box_cnt);
		
		//模态框
		function initEditModal(){
			$copy.find('input[name="name"]').val($copy.find('tr.name td').text());
			$copy.find('input[name="description"]').val($copy.find('tr.description td').text());
			$copy.find('input[name="producer"]').val($copy.find('tr.producer td').text());
			$copy.find('input[name="origin"]').val($copy.find('tr.origin td').text());	
			$copy.find('input[name="price"]').val($copy.find('tr.price td').text());
			$copy.find('input[name="url"]').val($copy.find('tr.url td').text());
			$copy.find('input[name="exp_date"]').val($copy.find('tr.exp_date td').text());
			$copy.find('input[name="box_cnt"]').val($copy.find('tr.box_cnt td').text());
			$copy.find('textarea[name="description"]').text($copy.find('tr.description td').text());
		}
		
		initEditModal();
		
		var id = dataObj.id;
		var id = 'hellomima';
		var create_date = dataObj.create_date;
		var update_date = dataObj.update_date;
		
		//更改'编辑'摸态框id为 dataObj.id + edit
		$copy.find('button[data-target="#myModal-edit"]').attr('data-target', '#'+id+'edit');
		$copy.find('#myModal-edit').attr('id', id+'edit');
		
		//更改'删除'摸态框的id为 dataObj.id + delete
		$copy.find('button[data-target="#myModal-delete"]').attr('data-target', '#'+id+'delete');
		$copy.find('#myModal-delete').attr('id', id+'delete');
		
		//编辑功能
		$copy.find('button.edit-done').click(function(evt){
			
		});
		$copy.find('button.edit-close').click(function(evt){
            initEditModal();
			
		});
		
		return $copy;
	}


	createPageNav(5);
	
});