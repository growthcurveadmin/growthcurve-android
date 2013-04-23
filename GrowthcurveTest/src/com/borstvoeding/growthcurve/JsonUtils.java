package com.borstvoeding.growthcurve;

public class JsonUtils {
	// FROM http://stackoverflow.com/a/5445161
	public static String convertStreamToString(java.io.InputStream is) {
		try {
			return new java.util.Scanner(is).useDelimiter("\\A").next();
		} catch (java.util.NoSuchElementException e) {
			return "";
		}
	}
}
