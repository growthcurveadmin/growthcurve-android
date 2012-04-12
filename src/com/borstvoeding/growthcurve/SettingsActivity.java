package com.borstvoeding.growthcurve;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends Activity {
	private static final Logger LOG = Logger.getLogger(SettingsActivity.class
			.getName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
	}

	public void onClickHandler(View view) {
		switch (view.getId()) {
		case R.id.btConnect:
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://www.borstvoeding.com/groeicurve-2012-04-10/");

			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs
						.add(new BasicNameValuePair("edit[name]", "admin"));
				nameValuePairs.add(new BasicNameValuePair("edit[pass]",
						"skgk06gc"));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse response = client.execute(httppost);
				StatusLine statusLine = response.getStatusLine();
				LOG.log(Level.INFO,
						MessageFormat.format("response={0}:{1}",
								new Object[] { statusLine.getStatusCode(),
										statusLine.getReasonPhrase() }));

				// TODO: now the client can be used to read the childrens list.
				// This should then be stored in SQLlive for future use. And
				// only reload on demand...

			} catch (ClientProtocolException e) {
				LOG.log(Level.INFO, "Connection failure", e);
			} catch (IOException e) {
				LOG.log(Level.INFO, "Read/write failure", e);
			}
			break;
		}
	}
}
