package com.borstvoeding.growthcurve.db;

public class Measurement {
	private final long id;
	private final long moment;
	private final Long weight;
	private final Long length;
	private final String story;

	public Measurement(long id, long moment, Long weight, Long length,
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

	public Long getWeight() {
		return weight;
	}

	public Long getLength() {
		return length;
	}

	public String getStory() {
		return story;
	}
}
