package com.borstvoeding.growthcurve;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.impl.client.DefaultHttpClient;

import android.test.AndroidTestCase;

public class DataHandlerTest extends AndroidTestCase {
	public void testDataHandlerReadToken() throws IOException,
			DataNotLoadedException {
		DataHandler dataHandler = new DataHandler(
				"http://www.borstvoeding.com/groeicurve/", null, null);
		DefaultHttpClient client = new DefaultHttpClient();
		String token = dataHandler.readToken(client);
		Logger.getLogger(DataHandlerTest.class.getName()).log(Level.INFO,
				"token:" + token + "<");
		assertNotNull(token);
		assertTrue(token.length() > 30);
	}

	public void testDataHandlerLoad() {

		DataHandler dataHandler = new DataHandler(
				"http://www.borstvoeding.com/groeicurve-2012-04-10/", username,
				password);
		try {
			String json = dataHandler.loadChildren();
			assertNotNull(json);
			assertTrue(json.length() > 2);

		} catch (DataNotLoadedException e) {
			fail("Should not throw " + DataNotLoadedException.class.getName());
		}
	}
}
