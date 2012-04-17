package com.borstvoeding.growthcurve.db;

public class Measurement {
	private final long id;
	private final long moment;
	private final long weight;
	private final long length;
	private final String story;

	public Measurement(long id, long moment, long weight, long length,
			String story) {
		this.id = id;
		this.moment = moment;
		this.weight = weight;
		this.length = length;
		this.story = story;
	}

	public long getId() {
		return id;
	}

	public long getMoment() {
		return moment;
	}

	public long getWeight() {
		return weight;
	}

	public long getLength() {
		return length;
	}

	public String getStory() {
		return story;
	}
}
