<%@include file="header.jsp"%>

<%request.getSession().setAttribute("user", user); %>
<div class="login-root">
	<div class="box-root flex-flex flex-direction--column"
		style="min-height: 100vh; flex-grow: 1;">
		<div class="box-root padding-top--24 flex-flex flex-direction--column"
			style="flex-grow: 1; z-index: 9;">

			<div class="formbg-outer">
				<div class="form-write">
					<div class="formbg-inner padding-horizontal--48">
						<c:if test="${ empty user}">
							<c:redirect url="loginPage.jsp">
								<c:param name="returnTo" value="WritePage"></c:param>
							</c:redirect>
						</c:if>
						<form name="writenewstory" action="writenewstory" method="POST">
							<!-- style="color: #5FCB71;" class="padding-bottom--15 text-center heading" -->
							<h2>Write your new Story here and publish.</h2>
							<div class="field padding-bottom--24">
								<label for="title"><b>Title :</b></label> <input type="text"
									name="title" id="title" required>
							</div>
							<div class="field padding-bottom--24">
								<label for="Category"><b>Category :</b></label> <select
									name="category"><option>Thriller</option><
									<option>Fiction</option><
									<option>Moral</option>
									<option>Horror</option>
									<option>Funny</option>
									<option>Romantic</option><
								</select>
							</div>
							<div class="field">
								<label for="summary"><b>Summary :</b></label>
							</div>
							<div class="field padding-bottom--24">
								<textarea name="summary" cols="30" rows="2"
									class="storycontent-textarea" required="required"></textarea>
							</div>
							<div class="field">
								<label for="content"><p>
										<b>Story Content :</b>
									</p></label>
							</div>
							<div class="field padding-bottom--24">
								<textarea name="content" cols="30" rows="5"
									class="storycontent-textarea"></textarea>
							</div>
							<div class="field padding-bottom--24 grid--50-50">
								<span><input type="submit" name="submit" value="Publish"></span>
								<span><input type="button" name="Cancel" value="Cancel"
									onclick="window.location='index.jsp'"></span>
							</div>
						</form>
					</div>
				</div>
				<%@include file="footer.jsp"%>

			</div>
		</div>
	</div>
</div>
</body>

</html>