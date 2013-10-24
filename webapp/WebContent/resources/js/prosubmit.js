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
				url:"/ProSubmit/Authenticate",
				type:"POST",
				data:{
					username:$("#username").val(),
					password:$("#password").val(),
					v:"login"
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
		},
		
		
		/**
		* 
		*/
		updateStudentBio:function(el,id){
			if(el){
				$("#student_bio_"+id).slideUp("fast",function(){
					$("#cont_edit_student_bio_"+id + ' textarea').text($("#student_bio_"+id).text());
					$("#cont_edit_student_bio_"+id).slideDown("fast");
					$(el).hide();
				});
			}else{
				this.mask(function(){
					$.ajax({
						url:"/ProSubmit/Group",
						type:"POST",
						data:{
							v:"updateStudentBio",
							bio:$("#cont_edit_student_bio_"+id + ' textarea').text(),
							student_id:id
						},success:function(){
							
						},
						error:function(jqXHR,textStatus){
							alert(textStatus);
						}
					});
				});
				
			}
			return false;
		},
		/**
		* 
		*/
		mask:function(callback){
			$("#body-mask").slideDown("fast",function(){
				if(callback){
					callback();
				}
			});
		},
		/**
		* 
		*/
		unMask:function(callback){
			$("#body-mask").slideUp("fast",function(){});
		}
};

$(document).ready(function(){

});
var proSubmit = new ProSubmit();