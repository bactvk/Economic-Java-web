$(document).ready(function(){
	$(".check_change_pass").click(function(){
		if($(this).is(':checked')){
			$(".password_block").css("display","block");
			$(this).val("checked");
		}else{
			$(".password_block").css("display","none");
			$(this).val("");
		}
		
	})
})
