package com.stories;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * DAO class to handle database connection and access data
 * */
public class UserStoriesDAO {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	public UserStoriesDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}

	protected void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
	}

	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}

	public boolean registerUser(UserBean user) throws SQLException {
		String email = user.getEmail();
		String userName = user.getUserName();
		boolean rowInserted = false;

		String finUserSql = "Select * from users where email = ?";
		connect();

		PreparedStatement statement1 = jdbcConnection.prepareStatement(finUserSql);
		statement1.setString(1, email);
		ResultSet rs = statement1.executeQuery();
		if (rs.next()) {
			System.out.println("User allready registered");
			rs.close();
			statement1.close();
			disconnect();
			return rowInserted;
		} else {
			String saveUSerSql = "INSERT INTO users (username, password,email ) VALUES (?, ?, ?)";
			PreparedStatement statement2 = jdbcConnection.prepareStatement(saveUSerSql);
			statement2.setString(1, user.getUserName());
			statement2.setString(2, user.getPassword());
			statement2.setString(3, user.getEmail());
			rowInserted = statement2.executeUpdate() > 0;
			rs.close();
			statement2.close();
			disconnect();
			return rowInserted;
		}
	}

	public UserBean userLogin(String email, String password) throws SQLException {
		String sql = "Select * from users where email = ? and password = ?";
		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, email);
		statement.setString(2, password);

		ResultSet rs = statement.executeQuery();
		UserBean user = null;
		if (rs.next()) {
			user = new UserBean();
			user.setUserId(rs.getInt("userid"));
			user.setUserName(rs.getString("username"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
		}
		rs.close();
		statement.close();
		disconnect();
		return user;
	}

	public List<StoryBean> listAllStories(String category) throws SQLException {
		List<StoryBean> listStory = new ArrayList<>();
		String sql = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		if (category != null) {
			sql = "SELECT * FROM story where category = ?";
			connect();
			statement = jdbcConnection.prepareStatement(sql);
			statement.setString(1, category);
			resultSet = statement.executeQuery();
		} else {
			sql = "SELECT * FROM story";
			connect();
			statement = jdbcConnection.prepareStatement(sql);
			resultSet = statement.executeQuery();
		}

		while (resultSet.next()) {
			int storyId = resultSet.getInt("storyId");
			int userId = resultSet.getInt("userId");
			String storyCategory = resultSet.getString("category");
			String title = resultSet.getString("title");
			String summary = resultSet.getString("summary");
			String storyContent = resultSet.getString("storyContent");
			int views = resultSet.getInt("storyViews");
			int downloads = resultSet.getInt("downloads");
			StoryBean story = new StoryBean(storyId, userId, storyCategory, title, summary, storyContent, views,
					downloads);
			listStory.add(story);
		}
		// System.out.println("story list has "+listStory.size()+" stories");
		resultSet.close();
		statement.close();

		disconnect();

		return listStory;
	}

	public StoryBean loadStory(int id) throws SQLException {
		StoryBean story = null;
		String sql = "SELECT * FROM story WHERE storyId = ?";

		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, id);

		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			int storyId = resultSet.getInt("storyId");
			int userId = resultSet.getInt("userId");
			String storyCategory = resultSet.getString("category");
			String title = resultSet.getString("title");
			String summary = resultSet.getString("summary");
			String storyContent = resultSet.getString("storyContent");
			int views = resultSet.getInt("storyViews") + 1;
			int downloads = resultSet.getInt("downloads");
			story = new StoryBean(storyId, userId, storyCategory, title, summary, storyContent, views, downloads);
		}

		resultSet.close();
		statement.close();

		return story;
	}

	public String getStoryWriter(int id) throws SQLException {
		String author = null;
		String sql = "select  userName from users u, story st where st.userId = u.userId and st.storyId = ?";

		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, id);

		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			author = resultSet.getString("userName");
		}

		resultSet.close();
		statement.close();

		return author;
	}

	public boolean updateStoryViews(int storyId, int views, int userId) throws SQLException {
		String sql = "UPDATE story SET storyViews = ?";
		sql += " WHERE storyId = ?";

		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, views);
		statement.setInt(2, storyId);
		boolean rowUpdated = statement.executeUpdate() > 0;
		statement.close();
		disconnect();

		System.out.println("StoryViews Upadted for StoryId " + storyId);
		// make entry of story view
		saveStoryViewsTimeStamp(storyId, userId);
		return rowUpdated;
	}

	public void saveStoryViewsTimeStamp(int storyId, int userId) throws SQLException {
		String sql = "INSERT INTO story_views (storyId, userViewed, viewTimeStamp) values (?,?,?);";

		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, storyId);
		statement.setInt(2, userId);
		java.util.Date javaDate = new java.util.Date();
		java.sql.Date mySQLDate = new java.sql.Date(javaDate.getTime());
		statement.setDate(3, mySQLDate);
		statement.executeUpdate();
		statement.close();
		disconnect();
	}

	public void saveStoryDownloadTimeStamp(int storyId, int userId) throws SQLException {
		String sql = "INSERT INTO story_downloads (storyId, user, download_TimeStamp) values (?,?,?);";

		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, storyId);
		statement.setInt(2, userId);
		java.util.Date javaDate = new java.util.Date();
		java.sql.Date mySQLDate = new java.sql.Date(javaDate.getTime());
		statement.setDate(3, mySQLDate);
		statement.executeUpdate();
		statement.close();
		disconnect();
	}

	public HashMap<Date, Integer> getViewDataToDisplayChart(int storyId) throws SQLException {
		String sql = "Select COUNT(*) as Count, viewTimeStamp from story_views where storyId = ? GROUP BY viewTimeStamp";
		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, storyId);
		ResultSet result = statement.executeQuery();
		HashMap<Date, Integer> viewMap = new HashMap<Date, Integer>();
		while (result.next()) {
			Integer count = result.getInt("Count");
			Date date = result.getDate("viewTimeStamp");
			viewMap.put(date, count);
		}
		statement.close();
		disconnect();

		return viewMap;
	}

	public HashMap<Date, Integer> getDownloadDataToDisplayChart(int storyId) throws SQLException {
		String sql = "Select COUNT(*) as Count, download_TimeStamp from story_downloads where storyId = ? GROUP BY download_TimeStamp";
		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, storyId);
		ResultSet result = statement.executeQuery();
		HashMap<Date, Integer> downLoadMap = new HashMap<Date, Integer>();
		while (result.next()) {
			Integer count = result.getInt("Count");
			Date date = result.getDate("download_TimeStamp");
			downLoadMap.put(date, count);
		}
		statement.close();
		disconnect();

		return downLoadMap;
	}

	public boolean updateDownloadCountForStory(int storyId, int downloadCount, int userId) throws SQLException {
		String sql = "UPDATE story SET downloads = ?";
		sql += " WHERE storyId = ?";
		connect();

		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, downloadCount);
		statement.setInt(2, storyId);
		boolean rowUpdated = statement.executeUpdate() > 0;
		statement.close();
		disconnect();

		// make entry for story downloads count
		saveStoryDownloadTimeStamp(storyId, userId);
		System.out.println("Download Count Upadted for StoryId " + storyId);
		return rowUpdated;
	}

	public boolean writeStory(int userId, String title, String category, String content, String summary) {
		boolean rowInserted = false;
		String saveUSerSql = "INSERT INTO story (userId, category,title, summary,storyContent,storyViews,downloads) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement statement2 = null;
		try {
			connect();
			statement2 = jdbcConnection.prepareStatement(saveUSerSql);
			statement2.setInt(1, userId);
			statement2.setString(2, category);
			statement2.setString(3, title);
			statement2.setString(4, summary);
			byte[] contentBytes = content.getBytes();
			InputStream inputStream = new ByteArrayInputStream(contentBytes);
			statement2.setAsciiStream(5, inputStream);
			statement2.setInt(6, 0);
			statement2.setInt(7, 0);
			rowInserted = statement2.executeUpdate() > 0;
			statement2.close();
			disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// statement2.close();
			// disconnect();
			e.printStackTrace();
		}
		// System.out.println("Story Saved successfully...");
		return rowInserted;
	}

}
