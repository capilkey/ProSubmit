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
					$("#cont_edit_student_bio_"+id + ' textarea').val($("#student_bio_"+id).text());
					$("#cont_edit_student_bio_"+id).slideDown("fast");
					$(el).hide();
				});
			}else{
				this.mask(function(){
					var bio = $("#cont_edit_student_bio_"+id + ' textarea').val();
					$.ajax({
						url:"/ProSubmit/Group",
						type:"POST",
						data:{
							v:"updateStudentBio",
							bio:bio,
							student_id:id
						},success:function(response){
							var success = response.success;
							var message = response.message;
							bio = response.bio;
							if(success == '1'){
								proSubmit.unMask(function(){
									$("#cont_edit_student_bio_"+id).slideUp("fast",function(){
										$("#student_bio_"+id).text(bio);
										$("#student_bio_"+id).slideDown("fast",function(){
											$("#edit_student_bio_link_"+id).show();
											alert(message);
										});
									});
								})
							}else{
								proSubmit.unMask(function(){
									alert(message);
								});
							}
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
		registerPartner:function(){
			var isValid = true;
			var firstname =  $("#firstname");
			var lastname =  $("#firstname");
			var email =  $("#email");
			var company =  $("#company");
			var tel =  $("#tel");
			var password =  $("#password");
			var confirmPassword =  $("#confirm-password");
			var companyAddress =  $("#company-address");
			var jobTitle = $("#job-title");
			var industry = $("#industry");
			
			isValid = this.validateNames(firstname,lastname);
			if(isValid){
				isValid = this.validateEmail(email);
			}if(isValid){
				isValid = this.validatePartnerCompany(company);
			}if(isValid){
				isValid = this.validateAddress(companyAddress);
			}if(isValid){
				isValid = this.validateTel(tel);
			}if(isValid){
				isValid = this.validatePassword(password,confirmPassword);
			}
			
			
			if(isValid){
				this.mask(function(){
					$.ajax({
						url:"/ProSubmit/Partner",
						type:"POST",
						data:{
							v:"registerPartner",
							firstname:firstname,
							lastname:lastname,
							email:email,
							company:company,
							tel:tel,
							password:hex_md5(password),
							companyAddess:companyAddess,
							jobTitle:jobTitle,
							industry:industry,
						},success:function(response){
							var success = response.success;
							var message = response.message;
							
							if(success == "1"){
								window.location = "/ProSubmit/Partner/";
							}else{
								proSubmit.unmask(function(){
									alert(message);
								});
							}
						},error:function(jqXHR,textStatus){
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
			$("#body-mask").show("fast",function(){
				$("#body-mask").animate({opacity:0.3},"fast",function(){
					if(callback){
						callback();
					}
				});
			});
		},
		/**
		* 
		*/
		unMask:function(callback){
			$("#body-mask").animate({opacity:0},"fast",function(){
				$(this).hide("fast",function(){
					if(callback){
						callback();
					}
				});
			});
		}
};

$(document).ready(function(){

});
var proSubmit = new ProSubmit();