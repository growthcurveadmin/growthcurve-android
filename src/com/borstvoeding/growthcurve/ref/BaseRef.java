package com.borstvoeding.growthcurve.ref;

public abstract class BaseRef implements Reference {
	private static final long SECONDS_PER_YEAR = 60 * 60 * 24 * 365;

	@Override
	public double getMaxValue(long timespanBetweenDobAndLastMeasurement) {
		int momentIndex = findMomentAfter(timespanBetweenDobAndLastMeasurement);
		if (momentIndex == 0) {
			findMomentAfter(SECONDS_PER_YEAR);
		}
		return getValues().get(0)[momentIndex];
	}

	private int findMomentAfter(long timespanBetweenDobAndLastMeasurement) {
		for (int i = 0; i < Moments.refMoments.length; i++) {
			if (Moments.refMoments[i] > timespanBetweenDobAndLastMeasurement) {
				return i;
			}
		}
		return Moments.refMoments.length - 1;
	}
}
