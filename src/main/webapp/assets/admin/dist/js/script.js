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
	

	
	$("#example1").DataTable({
		"paging" : true,
		"lengthChange" : false,
		"info" : true,
		"searching" :false,
		"autoWidth" : false,
		"sorting" : false,
		columnDefs: [
             { targets: 'no-sort', orderable: false }
           ]
	});
		
	

})
