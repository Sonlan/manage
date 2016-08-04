$(document).ready(function(){
    var loginUrl = 'http://1u5186s163.51mypc.cn/manage/';
    $('#login').click(function(evt){
	    var getParam = {
		  account: $('.input-group input[placeholder="用户名"]').val(),
		  password: $('.input-group input[placeholder="密码"]').val()
		};
		console.log(getParam);
	    if($('input[type="radio"]:checked').val() == 'ent'){
            $.get(loginUrl+'/companyuser/login', getParam, function(data){
			    if(data.errorCode == 0) {
				    window.location = 'http://1u5186s163.51mypc.cn/manage/html/entUserManagement';
				}else {
				    alert('登录失败,请检查用户名密码');
				}
			}, 'json');
		}else if($('input[type="radio"]:checked').val() == 'agency'){
            $.get(loginUrl+'/agent/login', getParam, function(data){
			    if(data.errorCode == 0) {
				    window.location = 'http://1u5186s163.51mypc.cn/manage/html/entUserManagement';
				}else {
				    alert('登录失败,请检查用户名密码');
				}
			}, 'json');   
		}
	});
});