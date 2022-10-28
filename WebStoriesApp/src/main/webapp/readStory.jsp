<%@include file="header.jsp"%>


<section class="cta-section theme-bg-light py-5">
	<div class="container">
		<div class="text-center">
			<h2 class="heading">${story.title}</h2>
		</div>
		<%-- 			<div class="bio mb-3"><span>${story.storyViews} Views</span><span>${story.downloads} Downloads</span></div> --%>
		<div class="bio mb-3">
			<a href="downloadStory">Download this story</a>
			<p class="author">By ${author}</p>
		</div>
		<div style="white-space: pre-line;">${story.storyContent}</div>
		<hr>
		<div class="padding-top--48 padding-bottom--48 center-center">
			<% String chartImage = (request.getAttribute("chart")).toString();%>
			<center>
				<img height="350" width="500"
					src="data:image/png;base64,<%=chartImage%>" />
			</center>
		</div>
	</div>
</section>


<%@include file="footer.jsp"%>