package com.borstvoeding.growthcurve;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class DataHandler {
	private final String baseUrl;
	private final String username;
	private final String password;

	public DataHandler(String baseUrl, String username, String password) {
		this.baseUrl = baseUrl;
		this.username = username;
		this.password = password;
	}

	public boolean checkAuth() {
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			authenticate(client);
			return true;
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		} catch (DataNotLoadedException e) {
		}
		return false;
	}

	public String loadChildren() throws DataNotLoadedException {
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			List<Cookie> cookies = authenticate(client);
			return getData(client, cookies);
		} catch (ClientProtocolException e) {
			throw new DataNotLoadedException("Connection failure", e);
		} catch (IOException e) {
			throw new DataNotLoadedException("Read/write failure", e);
		}
	}

	private List<Cookie> authenticate(DefaultHttpClient client)
			throws UnsupportedEncodingException, IOException,
			DataNotLoadedException {
		HttpPost httppost = new HttpPost(getBaseUrl());
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("edit[name]", getUsername()));
		nameValuePairs.add(new BasicNameValuePair("edit[pass]", getPassword()));
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		HttpResponse response = client.execute(httppost);

		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() >= HttpURLConnection.HTTP_MULT_CHOICE) {
			throw new DataNotLoadedException("Authentication faulure:\n"
					+ formatResponse(statusLine), null);
		}
		return client.getCookieStore().getCookies();
	}

	private String getData(DefaultHttpClient client, List<Cookie> cookies)
			throws IOException, ClientProtocolException, DataNotLoadedException {
		HttpGet get = new HttpGet(baseUrl
				+ "growthcurve?q=growthcurve/android/get/j.json");
		get.setHeader("Host", "www.borstvoeding.com"); // TODO: read this from
														// the baseUrl..
		get.setHeader("User-Agent",
				"Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:12.0) Gecko/20100101 Firefox/12.0");
		get.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		get.setHeader("Accept-Language", "en-us,en;q=0.5");
		get.setHeader("Accept-Encoding", "gzip, deflate");
		get.setHeader("Connection", "keep-alive");
		// CookieStore cookieStore = new BasicCookieStore();
		// for (Cookie cookie : cookies) {
		// cookieStore.addCookie(cookie);
		// }
		// client.setCookieStore(cookieStore);
		get.setHeader(
				"Cookie",
				"__utma=70349315.326128394.1266695800.1330979629.1333110626.39; __utmz=70349315.1311933928.25.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); PHPSESSID="
						+ cookies.get(0).getValue());
		HttpResponse response = client.execute(get);
		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() >= HttpURLConnection.HTTP_MULT_CHOICE) {
			throw new DataNotLoadedException(
					"Failed to read the children list:\n"
							+ formatResponse(statusLine), null);
		}
		HttpEntity entity = response.getEntity();
		return convertStreamToString(entity.getContent());
	}

	// FROM http://stackoverflow.com/a/5445161
	public String convertStreamToString(java.io.InputStream is) {
		try {
			return new java.util.Scanner(is).useDelimiter("\\A").next();
		} catch (java.util.NoSuchElementException e) {
			return "";
		}
	}

	private String formatResponse(StatusLine statusLine) {
		return MessageFormat.format("response={0}:{1}", new Object[] {
				statusLine.getStatusCode(), statusLine.getReasonPhrase() });
	}

	private String getBaseUrl() {
		return baseUrl;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
