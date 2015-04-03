package org.bmark.models;

public class Bookmark {
	
	private int id;
	private String url;
	private String title;
	private String description;
	
	public Bookmark() {
		
	}

	public Bookmark(int id, String url, String title, String description) {
		this.id = id;
		this.url = url;
		this.title = title;
		this.description = description;
	}

	public Bookmark(String url, String title, String description) {
		this.url = url;
		this.title = title;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
