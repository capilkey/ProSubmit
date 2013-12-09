/**
 * @class ProSubmit
 * @author ramone
 * @date 22nd September 2013
 */
var ANIMATION_SPEED = 400;
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
		searchProjects:function(){
			this.mask();
			var options = {
				keywords:$("#keywords").val(),
				categories:$("#category").val(),
				statuses:$("#status").val(),
				from_date:$("#from_date").val(),
				to_date:$("#to_date").val(),
			};
			
			console.log(options);	
			$.ajax({
				url:"/ProSubmit/rest/projects/search",
				type:"GET",
				data:{
					options:Base64.encode(JSON.stringify(options))
				},
				success:function(response){
					var projects = response;
					console.log(projects);
					$("#project-search-result-table .search-result").remove();
					for(var i =0;i<projects.length;i++){
						var aProject = projects[i];
						var row = [
						   "<tr class='search-result'>",
						      "<td>" + "#" + eval(i+1) + "</td>",
						      "<td><a href='/ProSubmit/project/"+ aProject.project_id + "-" + aProject.project_title.replace(/\s/g,"_") + "'>"+ aProject.project_title + "</a></td>",
						      "<td>" + aProject.project_createdate + "</td>",
						      "<td>"+ aProject.projstatus_name + "</td>",
						   "</tr>"
						];
						$("#project-search-result-table").append(row.join());
					}
					proSubmit.unMask();
				},
				error:function(jqXHR,textStatus){
					proSubmit.unMask(function(){alert(textStatus);});
				}
			});
			return false;
		},
		
		/**
		 * 
		 */
		addProjectComment:function(){
			var comment = $("#comment").val();
			if(!comment){
				alert("Please enter a comment before submitting");
				return;
			}
			
			
			$.ajax({
				url:"/ProSubmit/Project",
				type:"POST",
				data:{
					v:"addcomment",
					project_id:$("#project-id").val(),
					comment:comment
				},
				success:function(response){
					var success = response.success;
					var message = response.message;
					var comment = response.comment;	
					if(success == "1"){
						window.location.reload();
					}else{
						alert(message);
					}
				},
				error:function(jqXHR,textStatus){
					alert(textStatus);
				}
			});
			return false;
		},
		
		/**
		 * 
		 */
		deleteComment:function(comment_id){
			if(confirm("Are you sure you want to delete this comment?")){
				$.ajax({
					url:"/ProSubmit/Project",
					type:"POST",
					data:{
						v:"deletecomment",
						comment_id:comment_id,
					},
					success:function(response){
						var success = response.success;
						var message = response.message;
						var comment = response.comment;	
						if(success == "1"){
							window.location.reload();
						}else{
							alert(message);
						}
					},
					error:function(jqXHR,textStatus){
						alert(textStatus);
					}
				});
			}
			return false;
		},
		
		
		/**
		 * 
		 */
		login:function(u,p){
			var username = u || $("#username").val();
			var password = p || $("#password").val();
			password = hex_md5(password);
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
			return false;
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
											//alert(message);
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
			var company_name =  $("#company").val();
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
				isValid = this.validatePartnerCompany(company_name);
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
				$("#partner-register .alert,#partner-edit-info-form .alert").show();
			}else{
				$("#partner-register .alert,#partner-edit-info-form .alert").hide();
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
							company_name:company_name,
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
									$("#partner-register-error-message").text(message);
									$("#partner-register-error-message").show();
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
				//this.mask(function(){
					var cancellation_reason = $("#cancellation_reason").val(); 
					$.ajax({
						url:'/ProSubmit/Partner',
						type:"POST",
						data:{
							v:"delete",
							cancelltion_reason:cancellation_reason
						},
						success:function(response){
							console.log(response);
							var success = response.success;
							var message = response.message;
							if(success == "1"){
								proSubmit.logout();
							}else{
								$("#cancellation-failure-message").show();
								$("#cancellation-failure-message").text(message);
							}
						},
						error:function(jqXHR,textStatus){
							proSubmit.ajaxErrorFn(textStatus);
						}
					});
				//});
			}
			
			return false;
		},
		
		/**
		 * 
		 */
		updatePassword:function(){
			var isValid = true;
			var partner_id = $("#partner_id").val();
			var current_password = $("#current_password").val();
			var password = $("#password").val();
			var confirm_password = $("#confirm_password").val(); 
			isValid = this.validatePassword(password, confirm_password);
			if(isValid){
				if(current_password.length == 0){
					isValid = false;
					$("#partner-update-password-form .alert-danger").text("Current password cannot be empty");
				}
			}
			if(!isValid){
				$("#partner-update-password-form .alert-danger").show();
			}else{
				$("#partner-update-password-form .alert-danger").hide();
				$.ajax({
					url:"/ProSubmit/Partner",
					type:"POST",
					data:{
						v:"update_password",
						partner_id:partner_id,
						current_password:hex_md5(current_password),
						password:hex_md5(password)
					},
					success:function(response){
						var success = response.success;
						var message = response.message;
						console.log(response);
						if(success == "1"){
							$("#partner-update-password-form .alert-info").text(message);
							$("#partner-update-password-form .alert-info").show();
						}else{
							$("#partner-update-password-form .alert-danger").text(message);
							$("#partner-update-password-form .alert-danger").show();
						}
					},
					error:function(jqXHR,textStatus){
						alert(textStatus);
					}
				});
			}
			return false;
		},
		
		/**
		 * 
		 */
		resetPassword:function(){
			var email = $("#email").val();
			var isValid = true;
			isValid = this.validateEmail(email);
			if(!isValid){
				$("#partner-reset-password-email-form .alert-danger").show();
			}else{
				$("#partner-reset-password-email-form .alert-danger").hide();
				$.ajax({
					url:"/ProSubmit/Partner",
					type:"GET",
					data:{
						v:"send_reset_password_link",
						email:email
					},
					success:function(response){
						var success = response.success;
						var message = response.message;
						console.log(response);
						if(success == "1"){
							$("#partner-reset-password-email-form .alert-info").text(message);
							$("#partner-reset-password-email-form .alert-info").show();
						}else{
							$("#partner-reset-password-email-form .alert-danger").text(message);
							$("#partner-reset-password-email-form .alert-danger").show();
						}
					},
					error:function(jqXHR,textStatus){
						alert(textStatus);
					}
				});
			}
			return false;
		},
		
		/**
		 * 
		 */
		completePasswordReset:function(){
			var isValid = true;
			var token = $("#token").val();
			var password = $("#password").val();
			var confirm_password = $("#confirm_password").val();
			
			isValid = this.validatePassword(password, confirm_password);
			if(!isValid){
				$("#partner-create-password-form .alert-danger").slideDown(ANIMATION_SPEED);
			}else{
				$.ajax({
					url:"/ProSubmit/Partner",
					type:'POST',
					data:{
						v:"complete_password_reset",
						token:token,
						password:hex_md5(password),
					},
					success:function(response){
						var success = response.success;
						var message = response.message;
						var username = response.username;
						if(success == "1"){
							proSubmit.login(username,$("#password").val());
						}else{
							$("#partner-create-password-form .alert-danger").text(message);
							$("#partner-create-password-form .alert-danger").show();
						}
					},
					error:function(jqXHR,textStatus){
						alert(textStatus);
					}
				});
			}
			return false;
		},
		
		/**
		 * 
		 */
		updateGroupDesc:function(comp,groupId){
			if(comp){
				$("#cont-edit-group-desc").slideDown("slow");
			}else{
				
			}
			return false;
		},
		
		/**
		 * 
		 */
		addProjectCategory:function(){
			var categoryName = $("#new-projcat-name").val();
			var categoryDescription = $("#new-projcat-desc").val();
			if(!categoryName || !categoryDescription){
				alert("Specify both the category name and the description");
			}else{
				$.ajax({
					url:"/ProSubmit/Project",
					type:"POST",
					data:{
						v:"addprojectcategory",
						category_name:categoryName,
						category_description:categoryDescription
					},success:function(response){
						var message = response.message;
						var success = response.success;
						if(success == "1"){
							window.location.reload();
						}else{
							alert(message);
						}
					},error:function(jqXHR,textStatus){
						alert(textStatus);
					}
				});
			}
			return false;
		},
		
		/**
		 * 
		 */
		deleteProjectCategory:function(projcategoryId){
			if(confirm("Are you sure you want yo delete this category?")){
				$.ajax({
					url:"/ProSubmit/Project",
					type:"POST",
					data:{
						v:"deleteprojectcategory",
						projcategory_id:projcategoryId,
					},success:function(response){
						var message = response.message;
						var success = response.success;
						if(success == "1"){
							window.location.reload();
						}else{
							alert(message);
						}
					},error:function(jqXHR,textStatus){
						alert(textStatus);
					}
				});
			}
			
			return false;
		},
		/**
		 * 
		 */
		addProjectStatus:function(){
			var statusName = $("#new-projstatus-name").val();
			var statusDescription = $("#new-projstatus-desc").val();
			if(!statusName || !statusDescription){
				alert("Specify both the status name and the description");
			}else{
				$.ajax({
					url:"/ProSubmit/Project",
					type:"POST",
					data:{
						v:"addprojectstatus",
						status_name:statusName,
						status_description:statusDescription
					},success:function(response){
						var message = response.message;
						var success = response.success;
						if(success == "1"){
							window.location.reload();
						}else{
							alert(message);
						}
					},error:function(jqXHR,textStatus){
						alert(textStatus);
					}
				});
			}
			return false;
		},
		
		/**
		 * 
		 */
		deleteProjectStatus:function(projstatusId){
			if(confirm("Are you sure you want yo delete this status?")){
				$.ajax({
					url:"/ProSubmit/Project",
					type:"POST",
					data:{
						v:"deleteprojectstatus",
						projstatus_id:projstatusId,
					},success:function(response){
						var message = response.message;
						var success = response.success;
						if(success == "1"){
							window.location.reload();
						}else{
							alert(message);
						}
					},error:function(jqXHR,textStatus){
						alert(textStatus);
					}
				});
			}
			
			return false;
		},
		
		createProject:function(redirect){
			var isUpdate = redirect == null ? true : false;
			var v = "addproject";
			var isValid = true;
			this.goTop();
			var projtitle = $("#projtitle").val();
			var projdesc =  $("#projdesc").val();
			var projcat =  $("#projcat").val();
			var partner_id =  $("#partner_id").val();
			
			isValid = this.validateProjectTitle(projtitle);
			if(isValid){
				isValid = this.validateProjectDescription(projdesc);
			}if(isValid){
				isValid = this.validateProjectCategory(projcat);
			}
			
			if(!isValid){
				$("#project-error-message .alert").show();
			}else{
				$("#project-error-message .alert").hide();
				this.mask(function(){
					$.ajax({
						url:"/ProSubmit/Project",
						type:"POST",
						data:{
							v:v,
							partner_id:partner_id,
							projectdescription:projdesc,
							projecttitle:projtitle,
							projectcategory:projcat
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
									$("#project-error-message").text(message);
									$("#project-error-message").show();
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
		updateProjectDesc:function(){
			var v = "updateprojectdesc";
			var projdesc =  $("#projdesc").val();
			var projid =  $("#projectid").val();
			var isValid = true;
			
			if(isValid)	isValid = this.validateProjectDescription(projdesc);
			
			if(!isValid){
				$("#project-error-message .alert,#edit-project .alert").show();
			}else{
				$("#project-error-message .alert").hide();
				this.mask(function(){
					$.ajax({
						url:"/ProSubmit/Project",
						type:"POST",
						data:{
							v:v,
							projectdescription:projdesc,
							projectid:projid
						},success:function(response){
							console.log(response);
							var success = response.success;
							var message = response.message;
							
							if(success == "1"){
								$(document.body).animate({scrollTop:0}); // doesn't work
								window.location.reload();
							} else{
								proSubmit.unMask(function(){
									$("#project-error-message").text(message);
									$("#project-error-message").show();
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
		updateProjectCatStat:function(){
			var v = "updateprojectcatstat";
			var projcat =  $("#projcat").val();
			var projstat =  $("#projstat").val();
			var projid =  $("#projectid").val();
			var isValid = true;
			
			if(!isValid){
				$("#project-error-message .alert,#edit-project .alert").show();
			}else{
				$("#project-error-message .alert").hide();
				this.mask(function(){
					$.ajax({
						url:"/ProSubmit/Project",
						type:"POST",
						data:{
							v:v,
							projectcategory:projcat,
							projectstatus:projstat,
							projectid:projid
						},success:function(response){
							console.log(response);
							var success = response.success;
							var message = response.message;
							
							if(success == "1"){
								$(document.body).animate({scrollTop:0}); // doesn't work
								window.location.reload();
							} else{
								proSubmit.unMask(function(){
									$("#project-error-message").text(message);
									$("#project-error-message").show();
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
		
		addGroup:function(){
			var v = "addgroup";
			var isValid = true;
			this.goTop();
			var groupname = $("#groupname").val();
			var groupnumber =  $("#groupnumber").val();
			var groupdesc =  $("#groupdesc").val();
			var groupsem =  $("#groupsemester").val();
			var groupcourse =  $("#groupcourse").val();
			
			isValid = this.validateGroupName(groupname);
			if(isValid){
				isValid = this.validateGroupNumber(groupnumber);
			}
			
			if(!isValid){
				$("#project-error-message .alert").show();
			}else{
				$("#project-error-message .alert").hide();
				this.mask(function(){
					$.ajax({
						url:"/ProSubmit/Group",
						type:"POST",
						data:{
							v:v,
							groupname:groupname,
							groupnumber:groupnumber,
							groupdescription:groupdesc,
							groupsemester:groupsem,
							groupcourse:groupcourse
						},success:function(response){
							console.log(response);
							var success = response.success;
							var message = response.message;
							
							if(success == "1"){
								window.location.reload();	
							}else{
								proSubmit.unMask(function(){
									$("#project-error-message").text(message);
									$("#project-error-message").show();
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
		deleteGroup:function(group_id){
			if(confirm("Are you sure you want to delete this group")){
				$.ajax({
					url:"/ProSubmit/rest/groups/group/" + group_id,
					type:"DELETE",
					success:function(response){
						var message = response.message;
						var success = response.success;
						if(success == "1"){
							window.location.reload();
						}else{
							alert(message);
						}
					},error:function(jqXHR,textStatus){
						alert(textStatus);
					}
				});
			}
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
				$("form .alert-danger").text("Email cannot be empty");
			}if(isValid){
				if(!email.match(this.emailRegExp)){
					isValid = false;
					$("form .alert-danger").text("Invalid email");
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
				$("form .alert-danger").text("Password and confirm password cannot be empty");
			}if(isValid){
				if(password.length < 10){
					isValid = false;
					$("form .alert-danger").text("Password cannot be less than 10 characters");
				}
			}if(isValid){
				if(password != confirmPassword){
					isValid = false;
					$("#form .alert-danger").text("Passwords do not match");
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
		validateProjectTitle:function(projtitle){
			var isValid = true;
			if(!projtitle){
				isValid =  false;
				$("#project-error-message .alert").text("Specify a project title");
			}
			return isValid;
		},
		
		/**
		 * 
		 */
		validateProjectDescription:function(projdesc){
			var isValid = true;
			if(!projdesc){
				isValid =  false;
				$("#project-error-message .alert").text("Specify a project description");
			}
			return isValid;
		},
		
		/**
		 * 
		 */
		validateProjectCategory:function(projcat){
			var isValid = true;
			if(!projcat){
				isValid =  false;
				$("#project-error-message .alert").text("Specify a project category");
			}
			return isValid;
		},
		
		/**
		 * 
		 */
		validateGroupName:function(groupname){
			var isValid = true;
			if(!groupname){
				isValid =  false;
				$("#add-group-error-message .alert").text("Specify a group name");
			}
			return isValid;
		},
		
		/**
		 * 
		 */
		validateGroupNumber:function(groupnum){
			groupnum = groupnum.replace(/[^\d]{1,}/gi,"");
			var isValid = true;
			if(!groupnum){
				isValid =  false;
				$("#add-group-error-message .alert").text("Specify a group number");
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
	$("#new-projcat-btn").click(function(){
		$("#new-projcat-row").toggle("slow");
	});
	
	$("#new-projstatus-btn").click(function(){
		$("#new-projstatus-row").toggle("slow");
	});
	
	$("#account-cancel-link").click(function(){
		proSubmit.cancelAccount();
		return false;
	});
	
	$("#edit-partner-link").click(function(){
		$("#partner-edit-info-form").toggle("fast");
		return false;
	});
	
	$("#edit-project-link").click(function(){
		$("#project-edit-info-form").toggle("fast");
		return false;
	});
	
	$("#add-group-link").click(function(){
		$("#add-group-info-form").toggle("fast");
		return false;
	});
	
	$("#current_password").blur(function(){
		var current_password = $(this).val();
		if(current_password){
			$("#partner-update-password-form .alert-danger").hide();
			$("#partner-update-password-form .alert-info").hide();
			$.ajax({
				url:"/ProSubmit/Partner",
				type:"GET",
				data:{
					v:"is_password",
					partner_id:$("#partner_id").val(),
					password:hex_md5(current_password)
				},success:function(response){
					var success = response.success;
					var message = response.message;
					var is_password = response.is_password;
					//console.log(response);
					if(success == "1"){
						if(is_password == "0"){
							$("#partner-update-password-form .alert-danger").text("Password specified does not match the one on record");
							$("#partner-update-password-form .alert-danger").show();
						}
					}else{
						$("#partner-update-password-form .alert-danger").text(message);
						$("#partner-update-password-form .alert-danger").show();
					}
				},error:function(jqXHR,textStatus){
					alert(textStatus);
				}
				
			});
		}
	});
});
var proSubmit = new ProSubmit();