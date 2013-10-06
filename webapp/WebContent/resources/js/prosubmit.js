/**
 * @class ProSubmit
 * @author ramone
 * @date 22nd September 2013
 */
ProSubmit = function(){}
ProSubmit.prototype = {
		/**
		 * 
		 */
		login:function(){
			$.ajax({
				url:"Authenticate",
				type:"POST",
				data:{
					username:$("#username").val(),
					password:$("#password").val()
				},success:function(response){
					var success = response.success;
					var message = response.message;
					var redirect = response.redirect;
					if(success == "1"){
						window.location = redirect;
					}else{
						alert(message);
					}
				},error:function(jqXHR,textStatus){
					alert(textStatus);
				}
			});
		}
};
var proSubmit = new ProSubmit();