<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>



<style type="text/css">
	.yz{width: 135px; color: red;font-size: 20px;"}
</style>
		<script>
$(function(){
	
	<c:if test="${!empty msg}">
	$("span.errorMessage").html("${msg}");
	$("div.registerErrorMessageDiv").css("visibility","visible");		
	</c:if>
	
	$(".registerForm").submit(function(){
		if(0==$("#name").val().length){
			$("span.errorMessage").html("请输入用户名");
			$("div.registerErrorMessageDiv").css("visibility","visible");			
			return false;
		}		
		if(0==$("#password").val().length){
			$("span.errorMessage").html("请输入密码");
			$("div.registerErrorMessageDiv").css("visibility","visible");			
			return false;
		}		
		if(0==$("#repeatpassword").val().length){
			$("span.errorMessage").html("请输入重复密码");
			$("div.registerErrorMessageDiv").css("visibility","visible");			
			return false;
		}		
		if($("#password").val() !=$("#repeatpassword").val()){
			$("span.errorMessage").html("重复密码不一致");
			$("div.registerErrorMessageDiv").css("visibility","visible");			
			return false;
		}		

		return true;
	});
})
</script>
		<script type="text/javascript">
		
			//邮箱发送
		var obj;
			$(function(){
			$("#send").click(function(){
				$("#send").val("已发送");
				var email = $("#email").val();
				$.ajax({
					type:"get",
					url:"foreyzm",
					data:"email="+email,
					success: function(result){              
						obj =result;
					
	              }
			});
			})
		})
			
		$(function(){
			$("#yzm").blur(function(){
				var yzm = $("#yzm").val();    
				if($.trim(obj)!==yzm){
	                  	alert("验证码不对，请重新输入");
	                  }else if($("#yzm").val()==""||$("#yzm").val()==null){
	                  	alert("验证码不能为空！");
	                  }else{
	                	  alert("验证码正确");
	                  }
				
			})
		})
			//邮箱格式
			
			$(function(){
				$("#email").blur(function(){
					var email = $(this).val();
					var reg=/^\w+@\w+\.[A-Za-z]{2,3}$/;
					if(!reg.test(email)){
						$("#emailh").text("格式错误！");
						return ;
					}
					$("#emailh").text("没毛病！");
				})
			})
			
			//密码校验
				$(function(){
					$("#repassword").blur(function(){
						var rpw = $("#repassword").val();
						var pw = $("#password").val();
						if(rpw!=pw){
							$("#rpwh").text("密码不一致");
							return;
						}
						$("#rpwh").text("没毛病！");
						})
					})
				//密码长度
				$(function(){
					$("#password").blur(function(){
						var pw = $(this).val();
						if(pw.length<6){
							$("#pwh").text("×密码长度小于6位");
							return;
						}
						$("#pwh").text("没毛病！");
					})
				})
				$()
				
				
				
		</script>
					
<p align="center"><b><font face="微软雅黑" color="#000000" size="6">用户注册</font> </b></p>

<form method="post" action="foreregister" class="registerForm">


	<div class="registerDiv">
		<div class="registerErrorMessageDiv">
			<div class="alert alert-danger" role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close"></button>
				<span class="errorMessage"></span>
			</div>
		</div>


		<table class="registerTable" align="center">
			<tr>
				<td class="registerTip registerTableLeftTD">设置会员名</td>
				<td></td>
			</tr>
			<tr>
				<td class="registerTableLeftTD">登陆名</td>
				<td class="registerTableRightTD"><input id="name" name="name"
					placeholder="会员名一旦设置成功，无法修改"></td>
					<td id="nameh" class="yz"></td>
			</tr>
			<tr>
				<td class="registerTip registerTableLeftTD">设置登陆密码及邮箱</td>
				<td class="registerTableRightTD">登陆时验证，保护账号信息</td>
			</tr>
			<tr>
				
				<td class="registerTableLeftTD">登陆密码</td>
				<td class="registerTableRightTD"><input id="password"
					name="password" type="password" placeholder="设置你的登陆密码"></td>
					<td id="pwh" class="yz"></td>
			</tr>
			<tr>
				<td class="registerTableLeftTD">密码确认</td>
				<td class="registerTableRightTD"><input id="repassword"
					type="password" placeholder="请再次输入你的密码"></td>
					<td id="rpwh" class="yz"></td>
			</tr>
			
			<tr>
			  	<td class="registerTableLeftTD">邮箱账户</td>
				<td class="registerTableRightTD"><input id="email" 
					name="email" placeholder="输入您的邮箱账户" ></td>
					<td id="emailh" class="yz"></td>
			</tr>
			<tr>
				
				<td class="registerTableLeftTD">邮箱验证码</td>
				<td class="registerTableRightTD" ><input id="yzm"
					name="yzm"  placeholder="请输入您的验证码">
					
				<td colspan="1" class="registerButtonTD">
				<input type="button" id="send" value="发送验证码" /></td>
				
			</tr>
			

			<tr>
				<td colspan="2" class="registerButtonTD"><a
					href="registerSuccess.jsp"><button>提 交</button></a></td>
			</tr>
		</table>
	</div>
</form>