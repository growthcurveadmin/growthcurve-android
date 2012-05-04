package com.borstvoeding.growthcurve;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class DataHandler {
	private final HttpContext HTTP_CONTEXT = new BasicHttpContext();

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
			String token = readToken(client);
			authenticate(client, token);
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
			String token = readToken(client);
			List<Cookie> cookies = authenticate(client, token);
			return getData(client, cookies);
		} catch (ClientProtocolException e) {
			throw new DataNotLoadedException("Connection failure", e);
		} catch (IOException e) {
			throw new DataNotLoadedException("Read/write failure", e);
		}
	}

	String readToken(DefaultHttpClient client) throws IOException,
			DataNotLoadedException {
		HttpGet get = new HttpGet(getBaseUrl() + "user");
		setHeaders(get);
		HttpResponse response;
		response = client.execute(get, HTTP_CONTEXT);
		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() >= HttpURLConnection.HTTP_MULT_CHOICE) {
			throw new DataNotLoadedException("Failed to read the login page:\n"
					+ formatResponse(statusLine), null);
		}
		HttpEntity entity = response.getEntity();
		String loginPage = convertStreamToString(entity.getContent());
		// Ugly grep to find token, example:
		// name="edit[token]" value="b42a7ab40a99678d5c6950b304e23c48"
		Pattern findToken = Pattern.compile(
				"name=\\\"edit\\[token\\]\\\"\\s+value=\\\"([^\\\"]+)\\\"",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = findToken.matcher(loginPage);
		if (!matcher.find()) {
			return null;
		}
		return matcher.group(1);
	}

	private List<Cookie> authenticate(DefaultHttpClient client, String token)
			throws UnsupportedEncodingException, IOException,
			DataNotLoadedException {
		HttpPost httppost = new HttpPost(getBaseUrl()
				+ "user/login?destination=node");
		setHeaders(httppost);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("op", "Log in"));
		nameValuePairs.add(new BasicNameValuePair("edit[name]", getUsername()));
		nameValuePairs.add(new BasicNameValuePair("edit[pass]", getPassword()));
		nameValuePairs.add(new BasicNameValuePair("edit[token]", token));
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		HttpResponse response = client.execute(httppost, HTTP_CONTEXT);

		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() >= HttpURLConnection.HTTP_MULT_CHOICE
				&& statusLine.getStatusCode() != HttpURLConnection.HTTP_MOVED_TEMP) {
			throw new DataNotLoadedException("Authentication faulure:\n"
					+ formatResponse(statusLine), null);
		}
		// read the page???
		String html = convertStreamToString(response.getEntity().getContent());

		return client.getCookieStore().getCookies();
	}

	private String getData(DefaultHttpClient client, List<Cookie> cookies)
			throws IOException, ClientProtocolException, DataNotLoadedException {
		HttpGet get = new HttpGet(baseUrl
				+ "growthcurve?q=growthcurve/android/get/j.json");
		setHeaders(get);
		HttpResponse response = client.execute(get, HTTP_CONTEXT);
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

	private void setHeaders(HttpRequestBase request) {
		request.setHeader("User-Agent",
				"Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:12.0) Gecko/20100101 Firefox/12.0");
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
