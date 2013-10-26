/**
 * @class ProSubmit
 * @author ramone
 * @date 22nd September 2013
 */
ProSubmit = function(){}
ProSubmit.prototype = {
		emailRegExp:/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
		urlRegExp:/\b(?:(?:https?|ftp):\/\/|www\.)[-a-z0-9+&@#\/%?=~_|!:,.;]*[-a-z0-9+&@#\/%=~_|]/,
		emptyFn:function(){},
		
		/**
		 * 
		 */
		ajaxErrorFn:function(message){
			this.unMask(function(){
				alert(message);
			});
		},
		
		
		/**
		 * 
		 */
		login:function(){
			var username = $("#username").val();
			var password = $("#password").val();
			if(this.validateEmail(username)){
				password = hex_md5(password);
			}
			$.ajax({
				url:"/ProSubmit/Authenticate",
				type:"POST",
				data:{
					username:username,
					password:password,
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
							proSubmit.unMask(function(){
								alert(message);
							});
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
			return this.updatePartner("/ProSubmit/Partner/register/?registered=1");
		},
		/**
		* 
		*/
		updatePartner:function(redirect){
			var isUpdate = redirect == null ? true : false;
			var v = isUpdate ? "update" : "register";
			var isValid = true;
			this.goTop();
			var partner_id = $("#partner_id").val();
			var firstname =  $("#firstname").val();
			var lastname =  $("#lastname").val();
			var email =  $("#email").val();
			var company =  $("#company").val();
			var company_url =  $("#url").val();
			var telephone =  $("#tel").val();
			var extension =  $("#extension").val();
			var password =  $("#password").val();
			var confirm_password =  $("#confirm-password").val();
			var company_address =  $("#company-address").val();
			var job_title = $("#jobtitle").val();
			var industry = $("#industry option:selected").attr("value");
			
			isValid = this.validateNames(firstname,lastname);
			if(isValid){
				isValid = this.validateEmail(email);
			}if(isValid){
				isValid = this.validatePartnerCompany(company);
			}if(isValid){
				isValid = this.validateURL(company_url);
			}if(isValid){
				isValid = this.validateAddress(company_address);
			}if(isValid){
				isValid = this.validateTel(telephone);
			}if(isValid){
				isValid = this.validateExtension(extension);
			}if(!isUpdate && isValid){
				isValid = this.validatePassword(password,confirm_password);
			}if(isValid){
				isValid = this.validateJobTitle(job_title);
			}
			
			if(!isValid){
				$("#partner-register-update-info-error-message").show();
			}else{
				$("#partner-register-update-info-error-message").hide();
				this.mask(function(){
					$.ajax({
						url:"/ProSubmit/Partner",
						type:"POST",
						data:{
							v:v,
							partner_id:partner_id,
							firstname:firstname.toUpperCase(),
							lastname:lastname.toUpperCase(),
							email:email,
							company_name:company,
							telephone:telephone,
							password:(v == "register") ? hex_md5(password) : null,
							company_address:company_address,
							job_title:job_title,
							industry:industry,
							company_url:company_url,
							extension:extension,
						},success:function(response){
							console.log(response);
							var success = response.success;
							var message = response.message;
							
							if(success == "1"){
								if(redirect){
									window.location = redirect;
								}else{
									window.location.reload();
								}
								
							}else{
								proSubmit.unMask(function(){
									$("#partner-register-update-info-error-message").text(message);
									$("#partner-register-update-info-error-message").show();
								});
							}
						},error:function(jqXHR,textStatus){
							proSubmit.ajaxErrorFn(textStatus);
						}
					});
				});
			}
			return false;
		},
		/**
		 * 
		 */
		cancelAccount:function(){
			if(confirm("Are you sure you want yo cancel this account?")){
				this.mask(function(){
					var cancellation_reason = $("#cancellation_reason"); 
					$.ajax({
						url:'/ProSubmit/Partner',
						type:"POST",
						data:{
							v:"delete",
							cancelltion_reason:cancellation_reason
						},
						success:function(response){
							var success = response.success;
							var message = response.message;
							if(success == "1"){
								proSubmit.logout();
							}else{
								proSubmit.unMask(function(){
									$("#cancellation-failure-message").show();
									$("#cancellation-failure-message").text(message);
								});
							}
						},
						error:function(jqXHR,textStatus){
							proSubmit.ajaxErrorFn(textStatus);
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
				$("#partner-register .alert,#partner-edit-info-form .alert").text("First and last names must not be empty");
			}if(isValid){
				if(firstname.length < 2 || lastname.length < 2){
					isValid = false;
					$("#partner-register .alert,#partner-edit-info-form .alert").text("First and last names cannot be less than two charters");
				}
			}if(isValid){
				if(firstname.length > 25 || lastname.length > 25){
					isValid = false;
					$("#partner-register .alert,#partner-edit-info-form .alert").text("First and last names cannot be greater than twenty five characters");
				}
			}if(isValid){ 
				var exp = /^[\w\s-]{1,}[\w]$/;
				if(firstname.match(exp) == null || lastname.match(exp) == null){
					isValid = false;
					$("#partner-register .alert,#partner-edit-info-form .alert").text("First and last names connot have special characters or numbers");
				}
			}
			return isValid;
		},
		
		/**
		 * 
		 */
		validateEmail:function(email){
			var isValid = true;
			if(!email){
				isValid = false;
				$("#partner-register .alert,#partner-edit-info-form .alert").text("Email cannot be empty");
			}if(isValid){
				if(!email.match(this.emailRegExp)){
					isValid = false;
					$("#partner-register .alert,#partner-edit-info-form .alert").text("Invalid email");
				}
			}
			return isValid;
		},
		
		/**
		 * 
		 */
		validatePartnerCompany:function(company){
			var isValid = true;
			if(!company){
				isValid = false;
				$("#partner-register .alert,#partner-edit-info-form .alert").text("Company cannot be empty");
			}if(isValid){
				if(company.length < 2){
					isValid = false;
					$("#partner-register .alert,#partner-edit-info-form .alert").text("Company name cannot be less than 2 characters");
				}
			}
			return isValid;
		},
		
		/**
		 * 
		 */
		validateURL:function(company_url){
			var isValid = true;
			if(company_url){
				if(company_url.match(this.urlRegExp) == null){
					isValid = false;
					$("#partner-register .alert,#partner-edit-info-form .alert").text("Invalid URL");
				}
			}
			return isValid;
		},
		
		/**
		 * 
		 */
		validateAddress:function(company_address){
			var isValid = true;
			if(!company_address){
				isValid = false;
				$("#partner-register .alert,#partner-edit-info-form .alert").text("Company address cannot be empty");
			}if(isValid){
				var exp = /^[\w\s\d-'.()&]{1,}$/;
				if(company_address.match() == null){
					isValid = false;
					$("#partner-register .alert,#partner-edit-info-form .alert").text("Invalid characters found in company address");
				}
			}
			return isValid;
		},
		
		/**
		 * 
		 */
		validateTel:function(tel){
			tel = tel.replace(/[^\d]{1,}/gi,"");
			var isValid = true;
			if(!tel){
				isValid = false;
				$("#partner-register .alert,#partner-edit-info-form .alert").text("Telephone cannot be empty");
			}if(isValid){
				if(tel.length < 10){
					isValid = false;
					$("#partner-register .alert,#partner-edit-info-form .alert").text("Phone number should be atleast ten digits long");
				}
			}if(isValid){
				if(tel.length > 11){
					isValid = false;
					$("#partner-register .alert,#partner-edit-info-form .alert").text("Phone number cannot be greater than 11 digits");
				}
			}
			return isValid;
		},
		
		/**
		 * 
		 */
		validateExtension:function(extension){
			var isValid = true;
			if(extension){
				extension = extension.replace(/[^\d]{1,}/);
				if(!extension){
					isValid = false;
					$("#partner-register .alert,#partner-edit-info-form .alert").text("Invalid extension");
				}
			}
			return isValid;
		},
		
		/**
		 * 
		 */
		validatePassword:function(password,confirmPassword){
			var isValid = true;
			if(!password || !confirmPassword){
				isValid = false;
				$("#partner-register .alert,#partner-edit-info-form .alert").text("Password and confirm password cannot be empty");
			}if(isValid){
				if(password.length < 10){
					isValid = false;
					$("#partner-register .alert,#partner-edit-info-form .alert").text("Password cannot be less than 10 characters");
				}
			}if(isValid){
				if(password != confirmPassword){
					isValid = false;
					$("#partner-register .alert,#partner-edit-info-form .alert").text("Passwords do not match");
				}
			}
			return isValid;
		},
		
		/**
		 * 
		 */
		validateJobTitle:function(job_title){
			var isValid = true;
			if(!job_title){
				isValid =  false;
				$("#partner-register .alert,#partner-edit-info-form .alert").text("Specify a job title");
			}
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
		},
		
		/**
		* 
		*/
		logout:function(){
			window.location = "/ProSubmit//Authenticate?v=logout";
		},
		
		/**
		* 
		*/
		goTop:function(){
			$(document.body).animate({scrollTop:0});
		}
};

$(document).ready(function(){
	$("#account-cancel-link").click(function(){
		proSubmit.cancelAccount();
		return false;
	});
	
	$("#edit-partner-link").click(function(){
		$("#partner-edit-info-form").toggle("fast");
		return false;
	});
});
var proSubmit = new ProSubmit();