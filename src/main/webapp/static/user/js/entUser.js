function userManage(userID){
	$('#main-content header h3').text(userID + '管理');
	
	$('.choosen-item').first().click();
	//发送请求  包含userID 在回调函数中addClass
}

/* wyh geer */
//underline-choosen-item
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
/* wyh geer */


//勾选用户前的checkbox时启用/禁用 .user-manage
$('.user-item input').change(function(evt){
	var $this = $(this);
	//true 
	if($this.prop("checked") == true) {
		$('.user-manage button').removeAttr('disabled');
	}else {
		var $item = $('.user-item input').filter(function(){
			return $(this).prop('checked') == true;
		});
		if($item.length != 0) {
		}else {
			$('.user-manage button').attr('disabled', 'disabled');
		}
	}
});

//hover user-item
$('.user-item img').hover(function(){
	var $copy = $('.template.user-detail').clone();
	var $parentDiv = $(this).parent('div');
	$parentDiv.css('position', 'relative');
	$copy.find('img').attr('src', $parentDiv.find('img').attr('src'));
	$copy.removeClass('template');
	$copy.appendTo($parentDiv);
	
},function(){
	$(this).parent('div').find('div.user-detail').remove();
});
