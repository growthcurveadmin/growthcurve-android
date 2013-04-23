package com.borstvoeding.growthcurve.db;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.InstrumentationTestCase;

import com.borstvoeding.growthcurve.JsonUtils;

public class DatabaseHandlerTest extends InstrumentationTestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddChild() throws JSONException {
		DatabaseHandler dh = new DatabaseHandler(getInstrumentation()
				.getContext(), "gc_test");

		String json = JsonUtils.convertStreamToString(ChildTest.class
				.getResourceAsStream("child.json"));
		JSONObject jsonObject = new JSONObject(json);
		Child jsonChild = Child.load(jsonObject);
		dh.addChild(jsonChild);

		Child child = dh.getChild(jsonChild.getId());
		assertEquals(jsonChild.getId(), child.getId());
		assertEquals(jsonChild.getName(), child.getName());
		assertEquals(jsonChild.getDob(), child.getDob());
		assertEquals(jsonChild.getGender(), child.getGender());
		assertEquals(jsonChild.getStory(), child.getStory());
	}

	public void testGetChild() {
		fail("Not yet implemented");
	}

	public void testGetAllChildren() {
		fail("Not yet implemented");
	}

	public void testGetChildrensCount() {
		fail("Not yet implemented");
	}
}
