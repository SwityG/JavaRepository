package com.stories;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import javax.servlet.http.Cookie;

/**
 * 
 * @author Swity Controller to map client request and send response back
 */
public class ControllerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserStoriesDAO userStoriesDao;

	public void init() {
		ServletContext context = getServletContext();
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

		userStoriesDao = new UserStoriesDAO(jdbcURL, jdbcUsername, jdbcPassword);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/login":
				userLogin(request, response);
				break;
			case "/userLogout":
				userLogout(request, response);
				break;
			case "/register":
				registerUser(request, response);
				break;
			case "/listStories":
				listSories(request, response);
				break;
			case "/readStory":
				readStory(request, response);
				break;
			case "/downloadStory":
				downloadStory(request, response);
				break;
			case "/writenewstory":
				writeNewStory(request, response);
				break;
			default:
				defaultlistSories(request, response);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServletException(ex);
		}
	}

	private void registerUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		String error = null;
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		UserBean newUser = new UserBean(userName, password, email);
		if (userStoriesDao.registerUser(newUser)) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("loginPage.jsp");
			dispatcher.forward(request, response);
		} else {
			// userName or eamil already exist.
			request.getSession().setAttribute("error", "userName or email id is already registered.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("signUp.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void userLogin(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UserBean user = userStoriesDao.userLogin(email, password);
		if (user != null) {
			session.setAttribute("user", user);
			defaultlistSories(request, response);
			// setting session to expiry in 30 mins
			session.setMaxInactiveInterval(30 * 60);
		} else {

			session.setAttribute("error", "Wrong emailid or password.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("loginPage.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void userLogout(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		// invalidate the session if exists
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute("user");
			session.invalidate();
		}
		this.getServletContext().log("User logged out !!");
		response.sendRedirect("index.jsp");

	}

	private void defaultlistSories(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		this.getServletContext()
				.log("defaultlistSories :-  loading stories of " + request.getParameter("category") + " category");
		String returnPage = (String) request.getAttribute("returnTo");
		List<StoryBean> listStory = userStoriesDao.listAllStories(request.getParameter("category"));
		request.setAttribute("listStory", listStory);
		if (returnPage != null && returnPage == "WritePage") {
			RequestDispatcher dispatcher = request.getRequestDispatcher("writeStory.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("storyList.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void listSories(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		this.getServletContext()
				.log("listSories :-  loading stories of " + request.getParameter("category") + " category");
		List<StoryBean> listStory = userStoriesDao.listAllStories(request.getParameter("category"));
		request.setAttribute("listStory", listStory);
		RequestDispatcher dispatcher = request.getRequestDispatcher("storyList.jsp");
		dispatcher.forward(request, response);
	}

	private void readStory(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		if (user != null) {
			int storyID = Integer.parseInt((request.getParameter("storyId") != null) ? request.getParameter("storyId")
					: (request.getAttribute("storyId")).toString());
			StoryBean story = userStoriesDao.loadStory(storyID);
			String authorName = userStoriesDao.getStoryWriter(storyID);
			request.getSession().setAttribute("story", story);
			request.getSession().setAttribute("author", authorName);
			userStoriesDao.updateStoryViews(storyID, story.getStoryViews(), user.getUserId());

			// Display views and download logs in chart
			TimeSeries viewSeries = new TimeSeries("Views of" + story.getTitle());
			HashMap<Date, Integer> viewMapData = userStoriesDao.getViewDataToDisplayChart(story.getStoryId());
			for (Entry<Date, Integer> entrySet : viewMapData.entrySet()) {
				// Prepare the data set
				viewSeries.add(new Day(entrySet.getKey()), entrySet.getValue());
			}

			TimeSeriesCollection dataset = new TimeSeriesCollection(viewSeries);

			TimeSeries downLoadSeries = new TimeSeries("Downloads of " + story.getTitle());
			HashMap<Date, Integer> downloadMapData = userStoriesDao.getDownloadDataToDisplayChart(story.getStoryId());
			for (Entry<Date, Integer> entrySet : downloadMapData.entrySet()) {
				// Prepare the data set
				downLoadSeries.add(new Day(entrySet.getKey()), entrySet.getValue());
			}

			dataset.addSeries(downLoadSeries);

			// Create the chart
			JFreeChart chart = ChartFactory.createTimeSeriesChart("Story Statistics", "Date", "Views & Downloads",
					dataset, true, true, false);

			// Get the plot and set date format
			XYPlot plot = chart.getXYPlot();
			XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

			renderer.setSeriesPaint(0, Color.RED);
			renderer.setSeriesPaint(1, Color.BLUE);
			plot.setRenderer(renderer);
			plot.setBackgroundPaint(Color.white);
			plot.setRangeGridlinesVisible(false);
			plot.setDomainGridlinesVisible(false);
			chart.getLegend().setFrame(BlockBorder.NONE);

			DateAxis axis = (DateAxis) plot.getDomainAxis();
			axis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy"));

			int width = 700;
			int height = 400;

			// ChartUtils.writeChartAsPNG(outputStream, chart, width, height);
			BufferedImage bufferedImage = chart.createBufferedImage(width, height);
			byte[] byteArrayOfChartImage = ChartUtils.encodeAsPNG(bufferedImage);
			String base64Image = Base64.getEncoder().encodeToString(byteArrayOfChartImage);
			request.setAttribute("chart", base64Image);

			RequestDispatcher dispatcher = request.getRequestDispatcher("readStory.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("loginPage.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void downloadStory(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		HttpSession session = request.getSession();
		StoryBean story = (StoryBean) session.getAttribute("story");
		UserBean user = (UserBean) session.getAttribute("user");
		request.setAttribute("storyId", story.getStoryId());
		userStoriesDao.updateDownloadCountForStory(story.getStoryId(), story.getDownloads() + 1, user.getUserId());
		int width = 600;
		int height = 600;
		try {
			// Create a buffered image in which to write
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			// Create a graphics contents on the buffered image
			Graphics2D g2d = bufferedImage.createGraphics();

			g2d.setColor(Color.white);
			g2d.fillRect(0, 0, width, height);
			Font font = new Font("Serif", Font.PLAIN, 12);
			g2d.setFont(font);
			g2d.setColor(Color.black);
			String storyContent = story.getStoryContent();
			g2d.drawRect(5, 5, width - 10, height - 10);
			g2d.drawString(story.getTitle(), width / 2, 18);
			drawStringMultiLine(g2d, storyContent, 570, 30, 30);

			g2d.dispose();
			String filename = story.getTitle() + ".png";
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			ServletOutputStream sos = response.getOutputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", baos);
			// byte[] bytes = baos.toByteArray();
			baos.writeTo(sos);
			sos.flush();
			sos.close();
			baos.flush();
			baos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// RequestDispatcher dispatcher = request.getRequestDispatcher("readStory.jsp");
		// dispatcher.forward(request, response);
	}

	public void drawStringMultiLine(Graphics2D g, String text, int lineWidth, int x, int y) {
		FontMetrics m = g.getFontMetrics();
		for (String line : text.split("\n")) {
			if (m.stringWidth(line) < lineWidth) {
				g.drawString(line, x, y);
				y += m.getHeight();
			} else {
				String[] words = line.split(" ");
				String currentLine = words[0];
				for (int i = 1; i < words.length; i++) {
					if (m.stringWidth(currentLine + words[i]) < lineWidth) {
						currentLine += " " + words[i];
					} else {
						g.drawString(currentLine, x, y);
						y += m.getHeight();
						currentLine = words[i];
					}
				}
				if (currentLine.trim().length() > 0) {
					g.drawString(currentLine, x, y);
					y += m.getHeight();
				}
			}
		}
	}

	private void writeNewStory(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		if (user != null) {
			String title = request.getParameter("title");
			String category = request.getParameter("category");
			String content = request.getParameter("content");
			content.replaceAll(" ", "&nbsp;");
			String summary = request.getParameter("summary");
			HttpSession session = request.getSession();
			UserBean userBean = (UserBean) session.getAttribute("user");
			userStoriesDao.writeStory(user.getUserId(), title, category, content, summary);
			RequestDispatcher dispatcher = request.getRequestDispatcher("success.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("loginPage.jsp");
			dispatcher.forward(request, response);
		}
	}

}