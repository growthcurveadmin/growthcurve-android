package com.borstvoeding.growthcurve.db;

import java.util.ArrayList;
import java.util.List;

public class ChildMeasurement1 {
	private final List<Measurement> measurements = new ArrayList<Measurement>();

	public ChildMeasurement1() {
		long length = 0;
		String story = "";
		getMeasurements().add(
				new Measurement(1, 1147489200, 4450, length, story));
		getMeasurements().add(
				new Measurement(1, 1149044400, 4640, length, story));
		getMeasurements().add(
				new Measurement(1, 1150686000, 5245, length, story));
		getMeasurements().add(
				new Measurement(1, 1153105200, 5795, length, story));
		getMeasurements().add(
				new Measurement(1, 1156129200, 6525, length, story));
		getMeasurements().add(
				new Measurement(1, 1159326000, 7255, length, story));
		getMeasurements().add(
				new Measurement(1, 1165896000, 8220, length, story));
		getMeasurements().add(
				new Measurement(1, 1171339200, 8860, length, story));
		getMeasurements().add(
				new Measurement(1, 1176174000, 9330, length, story));
		getMeasurements().add(
				new Measurement(1, 1182826800, 10390, length, story));
		getMeasurements().add(
				new Measurement(1, 1196136000, 11300, length, story));
		getMeasurements().add(
				new Measurement(1, 1216782000, 13600, length, story));
		getMeasurements().add(
				new Measurement(1, 1226894400, 14000, length, story));
		getMeasurements().add(
				new Measurement(1, 1290830400, 16500, length, story));
	}

	public List<Measurement> getMeasurements() {
		return measurements;
	}
}
