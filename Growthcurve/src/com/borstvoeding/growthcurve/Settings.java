package com.borstvoeding.growthcurve;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
	private static final String SETTING_BASE_URL = "baseUrl";
	private static final String SETTING_USERNAME = "username";
	private static final String SETTING_PASSWORD = "password";

	private final String baseUrl;
	private final String username;
	private final String password;

	/**
	 * C'tor.
	 * 
	 * @param activity
	 *            the activity to work for
	 */
	public Settings(Activity activity) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(activity.getApplicationContext());
		baseUrl = sharedPreferences.getString(SETTING_BASE_URL,
				"http://www.borstvoeding.com/groeicurve-2012-04-10/");
		username = sharedPreferences.getString(SETTING_USERNAME, "");
		// TODO: decrypt
		password = sharedPreferences.getString(SETTING_PASSWORD, "");
	}

	public String getBaseUrl() {
		if (!baseUrl.endsWith("/")) {
			return baseUrl + "/";
		}
		return baseUrl;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
