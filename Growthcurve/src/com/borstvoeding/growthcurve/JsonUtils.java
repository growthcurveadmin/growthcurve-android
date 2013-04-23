package com.borstvoeding.growthcurve;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
	public static Long getLong(JSONObject obj, String name)
			throws JSONException {
		if (!obj.has(name)) {
			return null;
		}
		String strValue = obj.getString(name);
		try {
			return Long.valueOf(strValue);
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	// FROM http://stackoverflow.com/a/5445161
	public static String convertStreamToString(java.io.InputStream is) {
		try {
			return new java.util.Scanner(is).useDelimiter("\\A").next();
		} catch (java.util.NoSuchElementException e) {
			return "";
		}
	}
}
