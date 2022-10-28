<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>WebStories: Sign in</title>
<link id="theme-style" rel="stylesheet" href="assets/css/theme-1.css">
</head>

<body>
	<div class="login-root">
		<div class="box-root flex-flex flex-direction--column"
			style="min-height: 100vh; flex-grow: 1;">
			<div class="loginbackground box-background--white padding-top--64">


				<%if(request.getParameter("returnTo") != null) {request.setAttribute("returnTo", "WritePage"); }%>
			</div>

			<div
				class="box-root padding-top--24 flex-flex flex-direction--column"
				style="flex-grow: 1; z-index: 9;">
				<div
					class="box-root padding-top--48 padding-bottom--24 flex-flex flex-justifyContent--center">
					<h2>WebStories-A platform to start your imagination</h2>
				</div>
				<div class="formbg-outer">
					<div class="formbg">
						<div class="formbg-inner padding-horizontal--48">
							<span class="padding-bottom--15">Welcome, Sign in to Web
								Stories</span>
							<form name="login" action="login" method="POST">

								<c:if test="${not empty error}">
									<div class="field padding-bottom--24 error-msg">
										<label class="error-msg">Error : </label>
										<p class="error-msg">${error}</p>
									</div>
								</c:if>
								<div class="field padding-bottom--24">
									<label for="email">Email</label> <input type="email"
										name="email" required="required">
								</div>
								<div class="field padding-bottom--24">
									<div class="grid--50-50">
										<label for="password">Password</label>
										<div class="reset-pass">
											<a href="#">Forgot your password?</a>
										</div>
									</div>
									<input type="password" name="password" required="required">
								</div>
								<div
									class="field field-checkbox padding-bottom--24 flex-flex align-center">
									<label for="checkbox"> <input type="checkbox"
										name="checkbox"> Stay signed in
									</label>
								</div>
								<div class="field padding-bottom--24 grid--50-50">
									<input type="submit" name="submit" value="Log in"
										onclick="<%request.getSession().removeAttribute("error");%>">
									<input type="button" name="cancel" value="Cancel"
										onclick="window.location='index.jsp'">
								</div>

							</form>
						</div>
					</div>
					<div class="footer-link padding-top--24">
						<span>Don't have an account? <a href="signUp.jsp">Sign
								up</a></span>
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