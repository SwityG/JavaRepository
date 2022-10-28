package com.stories;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Swity filter requests to check for user log in and active session
 */
public class AuthenticationFilter implements Filter {

	private ServletContext context;
	private List<String> excludedUrls;
	String excludePattern;

	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		this.context.log("AuthenticationFilter initialized");
		excludePattern = fConfig.getInitParameter("excludedUrls");
		System.out.println("excludePattern : " + excludePattern);
		excludedUrls = Arrays.asList(excludePattern.split(","));
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		/*
		 * String uri = req.getRequestURI();
		 * this.context.log("Requested Resource::"+uri);
		 * System.out.println("Request URI" +uri);
		 * 
		 * String requestURL = req.getRequestURL().toString();
		 * System.out.println("RequestURL : " +requestURL);
		 * 
		 * if(excludedUrls.contains(path)) {
		 * System.out.println("Inside If condiation.."); HttpSession session =
		 * req.getSession(false);
		 */
		// uri.endsWith("LoginServlet")
		String path = req.getServletPath();
		System.out.println("Servlet Path : " + path);
		System.out.println("Servlet Path : " + path);
		UserBean user = (UserBean) req.getSession().getAttribute("user");
		if (user == null && !(path.endsWith(excludePattern))) {
			this.context.log("Unauthorized access request");
			res.sendRedirect("loginPage.jsp");
		} else {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}