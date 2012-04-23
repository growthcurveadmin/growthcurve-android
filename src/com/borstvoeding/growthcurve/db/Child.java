package com.borstvoeding.growthcurve.db;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Child {
	private static final Logger LOG = Logger.getLogger(Child.class.getName());

	public enum Gender {
		male, female
	}

	private int id;
	private final String name;
	private final long dob;
	private final Gender gender;
	private final String story;

	private final List<Measurement> measurements = new ArrayList<Measurement>();

	public Child(int id, String name, long dob, Gender gender, String story) {
		this(name, dob, gender, story);
		this.id = id;
	}

	public Child(String name, long dob, Gender gender, String story) {
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.story = story;
		initMeasurements();
	}

	public static Child load(JSONObject jSONObject) {
		try {
			Child child = new Child(jSONObject.getInt("child_id"),
					jSONObject.getString("name"), jSONObject.getLong("dob"),
					confGender(jSONObject.getString("gender")),
					jSONObject.getString("story"));
			child.loadMeasurements(jSONObject.getJSONArray("measurements"));
			return child;
		} catch (JSONException e) {
			LOG.log(Level.WARNING,
					"Could not load the child from json data...:"
							+ e.getMessage() + "\n" + e.getStackTrace());
			return null;
		}
	}

	private static Gender confGender(String gender) {
		return "m".equalsIgnoreCase(gender) ? Gender.male : Gender.female;
	}

	private void initMeasurements() {
		long length = 0;
		String story = "";
		// TODO: add length
		measurements.add(new Measurement(1, 1147489200, 4450L, length, story));
		measurements.add(new Measurement(1, 1149044400, 4640L, length, story));
		measurements.add(new Measurement(1, 1150686000, 5245L, length, story));
		measurements.add(new Measurement(1, 1153105200, 5795L, length, story));
		measurements.add(new Measurement(1, 1156129200, 6525L, length, story));
		measurements.add(new Measurement(1, 1159326000, 7255L, length, story));
		measurements.add(new Measurement(1, 1165896000, 8220L, length, story));
		measurements.add(new Measurement(1, 1171339200, 8860L, length, story));
		measurements.add(new Measurement(1, 1176174000, 9330L, length, story));
		measurements.add(new Measurement(1, 1182826800, 10390L, length, story));
		measurements.add(new Measurement(1, 1196136000, 11300L, length, story));
		measurements.add(new Measurement(1, 1216782000, 13600L, length, story));
		measurements.add(new Measurement(1, 1226894400, 14000L, length, story));
		measurements.add(new Measurement(1, 1290830400, 16500L, length, story));
	}

	private void loadMeasurements(JSONArray array) throws JSONException {
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);
			Measurement m = new Measurement(//
					obj.getLong("measurement_id"), //
					obj.getLong("moment"), //
					getLong(obj, "weight"), //
					obj.has("length") ? obj.getLong("length") : null, //
					obj.has("story") ? obj.getString("story") : null);
			measurements.add(m);
		}
	}

	private Long getLong(JSONObject obj, String name) throws JSONException {
		return obj.has(name) ? obj.getLong(name) : null;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public long getDob() {
		return dob;
	}

	public Gender getGender() {
		return gender;
	}

	public String getStory() {
		return story;
	}

	@Override
	public String toString() {
		return MessageFormat.format("Child: {0}:{1}", new Object[] { getId(),
				getName() });
	}

	public List<Measurement> getMeasurements() {
		return measurements;
	}
}
