<%@include file="header.jsp"%>
<section class="blog-list px-3 py-5 p-md-5">
	<div class="container">
		<%
		request.getSession().setAttribute("user", user);
		%>
		<div style="display: flex">
			<div style="float: left; color: #5FCB71; width: 600px;"
				class="padding-bottom--15 text-center heading">
				<h2>Read Stories here</h2>
			</div>
			<div style="float: right">
				<li class="nav-item dropdown view"><a class=" dropdown-toggle"
					href="storyList.jsp" id="navbarDropdown" role="button"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Select Category </a>
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
			</div>
		</div>
		<!-- 			</div> -->
		<c:forEach items="${listStory}" var="story">
			<div class="item mb-5">
				<div class="media">
					<!-- 						<img class="mr-3 img-fluid post-thumb d-none d-md-flex" -->
					<!-- 							src="/images/blog-post-thumb-1.jpg" alt="image"> -->
					<div class="media-body">
						<h3 class="title mb-1">
							<a href="readStory?storyId=${story.storyId}">${story.title}</a>
						</h3>
						<div class="meta mb-1">
							<span class="date">${story.storyViews} Views</span> <span
								class="time">${story.downloads} downloads</span>
						</div>
						<div class="intro">${story.summary}</div>
						<a class="more-link" href="readStory?storyId=${story.storyId}">Read
							more &rarr;</a>
					</div>
					<!--//media-body-->
				</div>
				<!--//media-->
			</div>
			<!--//item-->
		</c:forEach>
		<!-- 								<nav class="blog-nav nav nav-justified my-5"> -->
		<!-- 					<a class="nav-link-prev nav-item nav-link d-none rounded-left" -->
		<!-- 						href="#">Previous<i -->
		<!-- 						class="arrow-prev fas fa-long-arrow-alt-left"></i></a> <a -->
		<!-- 						class="nav-link-next nav-item nav-link rounded" -->
		<!-- 						href="blog-list.html">Next<i -->
		<!-- 						class="arrow-next fas fa-long-arrow-alt-right"></i></a> -->
		<!-- 				</nav> -->
	</div>
</section>
<%@include file="footer.jsp"%>


