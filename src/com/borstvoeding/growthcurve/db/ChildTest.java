package com.borstvoeding.growthcurve.db;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;

import com.borstvoeding.growthcurve.JsonUtils;
import com.borstvoeding.growthcurve.db.Child.Gender;

public class ChildTest extends AndroidTestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testChildIntStringLongGenderString() {
		Random random = new Random();
		int id = random.nextInt(50);
		String name = "name-" + Math.random();
		long dob = random.nextInt(4000);
		Gender gender = Gender.male;
		String story = "story-" + Math.random();
		Child child = new Child(id, name, dob, gender, story);

		assertEquals(id, child.getId());
		assertEquals(name, child.getName());
		assertEquals(dob, child.getDob());
		assertEquals(gender, child.getGender());
		assertEquals(story, child.getStory());
	}

	public void testChildStringLongGenderString() {
		Random random = new Random();
		String name = "name-" + Math.random();
		long dob = random.nextInt(4000);
		Gender gender = Gender.male;
		String story = "story-" + Math.random();
		Child child = new Child(name, dob, gender, story);

		assertEquals(name, child.getName());
		assertEquals(dob, child.getDob());
		assertEquals(gender, child.getGender());
		assertEquals(story, child.getStory());
	}

	public void testLoad() throws JSONException {
		String json = JsonUtils.convertStreamToString(ChildTest.class
				.getResourceAsStream("child.json"));
		JSONObject jsonObject = new JSONObject(json);

		Child child = Child.load(jsonObject);
		assertEquals(6375, child.getId());
		assertEquals("Andrin Josha Djurre", child.getName());
		assertEquals(1202011200, child.getDob());
		assertEquals(Gender.male, child.getGender());
		assertEquals(
				"Een thuisbevallen jongen. Heerlijk om nog een wonder te hebben ontvangen.",
				child.getStory());
		assertEquals(22, child.getMeasurements().size());
		// TODO: check some of the measurements
	}
}
