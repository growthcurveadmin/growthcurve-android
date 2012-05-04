package com.borstvoeding.growthcurve;

import java.util.List;

import android.test.AndroidTestCase;

import com.borstvoeding.growthcurve.db.Child;

public class GrowthcurveActivityTest extends AndroidTestCase {
	public void testReadChildren() {
		GrowthcurveActivity growthcurveActivity = new GrowthcurveActivity();
		List<Child> children = growthcurveActivity
				.readChildren(convertStreamToString(GrowthcurveActivityTest.class
						.getResourceAsStream("android-get.json")));
		assertEquals(7, children.size());
		assertEquals("Andrin Josha Djurre", children.get(0).getName());
		assertEquals(22, children.get(0).getMeasurements().size());
		assertEquals("Simcha Kas Immanuël (ồ/⑩)", children.get(5).getName());
		assertEquals(14, children.get(5).getMeasurements().size());
	}

	// FROM http://stackoverflow.com/a/5445161
	public String convertStreamToString(java.io.InputStream is) {
		try {
			return new java.util.Scanner(is).useDelimiter("\\A").next();
		} catch (java.util.NoSuchElementException e) {
			return "";
		}
	}
}
