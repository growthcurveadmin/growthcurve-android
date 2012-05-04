package com.borstvoeding.growthcurve;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
	private static final String SETTING_BASE_URL = "baseUrl";
	private static final String SETTING_USERNAME = "username";
	private static final String SETTING_PASSWORD = "password";

	private static Settings settings;

	private final Activity activity;

	private String baseUrl;
	private String username;
	private String password;

	public static Settings INSTANCE(Activity activity) {
		if (settings == null) {
			settings = new Settings(activity);
			settings.load();
		}
		return settings;
	}

	public void load() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(activity.getApplicationContext());
		setBaseUrl(sharedPreferences.getString(SETTING_BASE_URL,
				"http://www.borstvoeding.com/groeicurve-2012-04-10/"));
		username = sharedPreferences.getString(SETTING_USERNAME, "");
		// TODO: decrypt
		password = sharedPreferences.getString(SETTING_PASSWORD, "");
	}

	public void store() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(activity.getApplicationContext());
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(SETTING_BASE_URL, getBaseUrl());
		editor.putString(SETTING_USERNAME, username);
		// TODO: encrypt
		editor.putString(SETTING_PASSWORD, password);
		editor.commit();
	}

	/**
	 * Hide C'tor.
	 * 
	 * @param activity
	 *            the activity to work for
	 */
	private Settings(Activity activity) {
		this.activity = activity;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		if (!baseUrl.endsWith("/")) {
			baseUrl += "/";
		}
		this.baseUrl = baseUrl;
	}

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
}
