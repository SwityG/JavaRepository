package com.stories;

/**
 * 
 * @author Swity java bean for Story information and content
 */
public class StoryBean {
	private int storyId;
	private int userId;
	private String category;
	private String title;
	private String summary;
	private String storyContent;
	private int storyViews;
	private int downloads;

	public StoryBean(int storyId, int userId, String category, String title, String summary, String storyContent,
			int storyViews, int downloads) {
		super();
		this.storyId = storyId;
		this.userId = userId;
		this.category = category;
		this.title = title;
		this.summary = summary;
		this.storyContent = storyContent;
		this.storyViews = storyViews;
		this.downloads = downloads;
	}

	public int getStoryId() {
		return storyId;
	}

	public void setStoryId(int storyId) {
		this.storyId = storyId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getStoryContent() {
		return storyContent;
	}

	public void setStoryContent(String storyContent) {
		this.storyContent = storyContent;
	}

	public int getStoryViews() {
		return storyViews;
	}

	public void setStoryViews(int storyViews) {
		this.storyViews = storyViews;
	}

	public int getDownloads() {
		return downloads;
	}

	public void setDownloads(int downloads) {
		this.downloads = downloads;
	}

}
