<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Web Stories</title>
<!-- Meta -->
<meta charset="ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Story reading and writing website">
<meta name="author" content="Swity Gangwal">

<!-- Theme CSS -->
<link id="theme-style" rel="stylesheet" href="assets/css/theme-1.css">
<%@page import="com.stories.UserBean"%>
</head>
<body>
	<header class="header text-center">
		<h1 class="blog-name pt-lg-4 mb-0">
			<a href="index.html">Web Stories</a>
		</h1>

		<nav class="navbar navbar-expand-lg navbar-dark">

			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navigation" aria-controls="navigation"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div id="navigation" class="collapse navbar-collapse flex-column">
				<div class="profile-section pt-3 pt-lg-0">
					<img class="profile-image mb-3 rounded-circle mx-auto"
						src="/images/storywriting.jfif" alt="image" />
					<div class="bio mb-3">Million writers in more than 100
						countries around the world use WebStories to tell their stories.
						Read the best writers, publish your work, and get expert feedback
						from teachers, professional editors, and authors.</div>
					<hr>
				</div>
				<%
				UserBean user = null;
				user = (UserBean) request.getSession().getAttribute("user");
				if (user != null) {
				%>
				<ul class="navbar-nav flex-column text-left">
					<li class="nav-item active">
						<!-- 					    <a class="nav-link" href="index.html"><i class="fas fa-home fa-fw mr-2"></i>Read Stories <span class="sr-only">(current)</span></a> -->
						<i class="fas fa-home fa-fw">Welcome <%=user.getUserName()%></i><span
						class="sr-only">(current)</span>
					</li>
					<li class="nav-item"><a class="nav-link" href="userLogout"><i
							class="fas fa-user fa-fw"></i>Log Out</a></li>

					<li class="nav-item"><a class="nav-link" href="writeStory.jsp"><i
							class="fas fa-bookmark fa-fw"></i>Write Stories</a></li>

					<li class="nav-item dropdown view"><a
						class="nav-link dropdown-toggle" href="" id="navbarDropdown"
						role="button" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false"> Read Stories</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item"
								href="listStories?category=thriller" data-value="thriller">Thriller</a></li>
							<li><a class="dropdown-item"
								href="listStories?category=fiction" data-value="fiction">Fiction</a></li>
							<li><a class="dropdown-item"
								href="listStories?category=funny" data-value="funny">Funny</a></li>
							<li><a class="dropdown-item"
								href="listStories?category=horror" data-value="horror">Horror</a></li>
							<li><a class="dropdown-item"
								href="listStories?category=romantic" data-value="romantic">Romantic</a></li>
							<li><a class="dropdown-item"
								href="listStories?category=moral" data-value="moral">Moral</a></li>

						</ul></li>

				</ul>
				<%
				} else {
				%>
				<ul class="navbar-nav flex-column text-left">
					<li class="nav-item"><a class="nav-link" href="signUp.jsp"><i
							class="fas fa-user fa-fw"></i>Sign Up</a></li>
					<li class="nav-item"><a class="nav-link" href="loginPage.jsp"><i
							class="fas fa-user fa-fw"></i>Login</a></li>
					<li class="nav-item"><a class="nav-link" href="writeStory.jsp"><i
							class="fas fa-bookmark fa-fw"></i>Write Stories</a></li>

					<li class="nav-item dropdown view"><a
						class="nav-link dropdown-toggle" href="storyList.jsp"
						id="navbarDropdown" role="button" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false"> Read </a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item"
								href="listStories?category=thriller" data-value="thriller">Thriller</a></li>
							<li><a class="dropdown-item"
								href="listStories?category=fiction" data-value="fiction">Fiction</a></li>
							<li><a class="dropdown-item"
								href="listStories?category=funny" data-value="funny">Funny</a></li>
							<li><a class="dropdown-item"
								href="listStories?category=horror" data-value="horror">Horror</a></li>
							<li><a class="dropdown-item"
								href="listStories?category=romantic" data-value="romantic">Romantic</a></li>
							<li><a class="dropdown-item"
								href="listStories?category=moral" action="" data-value="moral">Moral</a></li>
						</ul></li>
				</ul>
				<%
				}
				%>

			</div>
		</nav>
	</header>
	<div class="main-wrapper">