package com.borstvoeding.growthcurve.ref;

import java.util.List;

public interface Reference {
	/**
	 * @return the title the chars should get
	 */
	String getChartTitle();

	/**
	 * @return the title the Y-axis should have
	 */
	String getChartYTitle();

	/**
	 * @return the reference values per week
	 */
	List<double[]> getValues();
}
