package com.borstvoeding.growthcurve.db;

import java.text.MessageFormat;

public class Child {
	public enum Gender {
		male, female
	}

	private int id;
	private final String name;
	private final long dob;
	private final Gender gender;
	private final String story;

	public Child(int id, String name, long dob, Gender gender, String story) {
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.story = story;
	}

	public Child(String name, long dob, Gender gender, String story) {
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.story = story;
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
}
