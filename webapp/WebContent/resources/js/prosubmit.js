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
			var firstname =  $("#firstname").val();
			var lastname =  $("#firstname").val();
			var email =  $("#email").val();
			var company =  $("#company").val();
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
								window.location = "/ProSubmit/Partner/register/?registered=1";
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
		validateNames:function(firstname,lastname){
			var isValid = true;
			if(!firstname || !lastname){
				isValid = false;
				alert("First and last names must not be empty");
			}if(isValid){
				if(firstname.length < 2 || lastname.length < 2){
					isValid = false;
					alert("First and last names cannot be less than two charters");
				}
			}if(isValid){
				if(firstname.length > 25 || lastname.length > 25){
					isValid = false;
					alert("First and last names cannot be greater than twenty five characters");
				}
			}if(isValid){
				var exp = "^[\w]{1,}[\w-\s]{1,}[\w]$";
				if(!firstname.match(new RegExp(exp)) || !lastname.match(new RegExp(exp))){
					isValid = false;
					alert("First and last names connt have special characters");
				}
			}
			return isValid;
		},
		
		/**
		 * 
		 */
		validateEmail:function(email){
			var isValid = true;
			
			return isValid;
		},
		
		/**
		 * 
		 */
		validatePartnerCompany:function(company){
			var isValid = true;
			
			return isValid;
		},
		
		/**
		 * 
		 */
		validateAddress:function(companyAddress){
			var isValid = true;
			
			return isValid;
		},
		
		/**
		 * 
		 */
		validateTel:function(tel){
			var isValid = true;
			
			return isValid;
		},
		
		/**
		 * 
		 */
		validatePassword:function(password,confirmPassword){
			var isValid = true;
			
			return isValid;
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