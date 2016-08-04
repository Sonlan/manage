$(document).ready(function(){
    var _navGroup = 1;
    /* 登出按钮 */
	$('header p').hover(function(){
	    $(this).addClass('hover');
	},function(){
	    $(this).removeClass('hover');
	});

	//bar-item hover
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
	$('.bar-item').first().addClass('choosen');
	$('.bar-item').click(function(evt){
		var $this = $(this);
		if($this.hasClass('choosen')) {}
		else {
			$('.bar-item').removeClass('hover choosen');
			$this.addClass('choosen');
		}
		
		if($this.attr('data-nav-group') == 'nav-user') {
		    if(_navGroup == 1) {
			}else {
			  _navGroup = 1;
			  $('#main-content').load('fragUserContent.html');
			  $.getScript('user/js/entUser.js');
			}
		}
		if($this.attr('data-nav-group') == 'nav-info') {
		  if(_navGroup == 3) {
		  }else {
		      _navGroup = 3;
		      $('#main-content').load('fragInfoContent.html', function(){
			      $.getScript('user/js/uploadPreview.js');
			      $.getScript('user/js/entInfo.js');				  
			  });
		  }
		}
    });
	
	$('#main-content').load('fragUserContent.html');
	$.getScript('user/js/entUser.js');
	
	
});