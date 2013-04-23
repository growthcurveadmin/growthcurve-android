package com.borstvoeding.growthcurve;

import java.util.List;

import android.test.AndroidTestCase;

import com.borstvoeding.growthcurve.db.Child;

public class GrowthcurveActivityTest extends AndroidTestCase {
	public void testReadChildren() {
		GrowthcurveActivity growthcurveActivity = new GrowthcurveActivity();
		List<Child> children = growthcurveActivity.readChildren(JsonUtils
				.convertStreamToString(GrowthcurveActivityTest.class
						.getResourceAsStream("children.json")));
		assertEquals(7, children.size());
		assertEquals("Andrin Josha Djurre", children.get(0).getName());
		assertEquals(22, children.get(0).getMeasurements().size());
		assertEquals("Simcha Kas Immanuël (ồ/⑩)", children.get(5).getName());
		assertEquals(14, children.get(5).getMeasurements().size());
	}
}
