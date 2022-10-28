<%@include file="header.jsp"%>
<%@include file="backgroundimage.jsp"%>
<section class="blog-list px-3 py-5 p-md-5">
	<div class="container">

		<c:forEach items="${listStory}" var="story">
			<div class="item mb-5">
				<div class="media">
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

	</div>
</section>
</div>
<%@include file="footer.jsp"%>


