<html>
<head>
<meta charset="utf-8">
<title>WebStories: Sign in</title>
<link id="theme-style" rel="stylesheet" href="assets/css/theme-1.css">
</head>

<body>
	<div class="login-root">
		<div class="box-root flex-flex flex-direction--column"
			style="min-height: 100vh; flex-grow: 1;">
			<div class="loginbackground box-background--white padding-top--64">

			</div>
			<div
				class="box-root padding-top--24 flex-flex flex-direction--column"
				style="flex-grow: 1; z-index: 9;">
				<div
					class="box-root padding-top--48 padding-bottom--24 flex-flex flex-justifyContent--center">
					<h2>Register to WebStories-A platform to start your
						imagination</h2>
				</div>
				<div class="formbg-outer">
					<div class="formbg-singup">
						<div class="formbg-inner padding-horizontal--48">
							<!--               <span class="padding-bottom--15">Welcome, Sign in to Web Stories</span> -->
							<form name="register" action="register" method="POST">

								<p class="padding-bottom--15">Please fill in this form to
									create an account.</p>
								<div class="field padding-bottom--24">
									<label for="userName"><b>User Name</b></label> <input
										type="text" placeholder="Enter User Name" name="userName"
										id="userName" required>
								</div>
								<div class="field padding-bottom--24">
									<label for="email"><b>Email</b></label> <input type="text"
										placeholder="Enter Email" name="email" id="email" required>
								</div>
								<div class="field padding-bottom--24">
									<label for="password"><b>Password</b></label> <input
										type="password" placeholder="Enter Password" name="password"
										id="psw" required>
								</div>
								<!--     <label for="psw-repeat"><b>Repeat Password</b></label> -->
								<!--     <input type="password" placeholder="Repeat Password" name="psw-repeat" id="psw-repeat" required> -->

								<p class="padding-bottom--15">
									By creating an account you agree to our <a href="#">Terms &
										Privacy</a>.
								</p>

								<div class="field padding-bottom--24 grid--50-50">
									<input type="submit" name="submit" value="Register"><input
										type="button" name="cancel" value="Cancel"
										onclick="window.location='index.jsp'">
								</div>
								<div class="container signin">
									<p>
										Already have an account? <a href="loginPage.jsp">Sign in</a>.
									</p>
								</div>
							</form>
						</div>
					</div>
					<div class="footer-link padding-top--24">
						<div
							class="listing padding-top--24 padding-bottom--24 flex-flex center-center">
							<span><a href="#">© WebStories</a></span> <span><a
								href="#">Contact</a></span> <span><a href="#">Privacy &
									terms</a></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>