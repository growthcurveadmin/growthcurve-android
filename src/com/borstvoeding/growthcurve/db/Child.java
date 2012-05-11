package com.borstvoeding.growthcurve.db;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Child implements Serializable {
	private static final Logger LOG = Logger.getLogger(Child.class.getName());

	private static final long serialVersionUID = 1L;

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

	private void loadMeasurements(JSONArray array) throws JSONException {
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);
			measurements.add(Measurement.load(obj));
		}
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

	/**
	 * @return the number of seconds between dob and the measurement of the
	 *         youngest date.
	 */
	public long getTimespanBetweenDobAndLastMeasurement() {
		long lastMeasurement = Long.MIN_VALUE;
		for (Measurement measurement : measurements) {
			if (measurement.getMoment() > lastMeasurement) {
				lastMeasurement = measurement.getMoment();
			}
		}
		long timespan = lastMeasurement - dob;
		if (lastMeasurement == Long.MAX_VALUE || timespan < 0) {
			return 0;
		}
		return timespan;
	}
}
