package com.lsj.util;

import java.util.List;

public class Configuration {
	private String username;
	private String password;
	private List<UrlButton> urlButtons;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<UrlButton> getUrlButtons() {
		return urlButtons;
	}
	public void setUrlButtons(List<UrlButton> urlButtons) {
		this.urlButtons = urlButtons;
	}
	
}
