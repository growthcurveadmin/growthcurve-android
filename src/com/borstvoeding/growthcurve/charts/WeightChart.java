package com.borstvoeding.growthcurve.charts;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;

import com.borstvoeding.growthcurve.db.Child;
import com.borstvoeding.growthcurve.db.Child.Gender;
import com.borstvoeding.growthcurve.db.Measurement;
import com.borstvoeding.growthcurve.ref.BoyWeight;
import com.borstvoeding.growthcurve.ref.GirlWeight;
import com.borstvoeding.growthcurve.ref.Moments;

public class WeightChart {
	private static final int COLOR_IN_RANGE = Color.rgb(193, 255, 193);
	private static final int SECONDS_PER_WEEK = 604800;
	private static final int SECONDS_PER_MONTH_AVG = 2628000;

	public Intent execute(Context context, Child child) {
		String[] titles = new String[] { "2+", "1+", "0", "1-", "2-" };

		XYMultipleSeriesRenderer renderer = buildRenderer();
		int weeksToPlot = getAgeInWeeks(child);
		// TODO: det. range according to the ref-graph, not the child's weight
		setChartSettings(renderer, //
				"Weight", //
				"months", //
				"grams", //
				0, getAgeInMonths(child) + 1, //
				// TODO: determine the max-range on the ref-curve-end-values
				roundDown(getLowestWeight(child)), // min weight in range
				roundUp(getHighestWeight(child)), // max weight in range
				Color.GRAY, Color.LTGRAY);
		renderer.setXLabels(12);
		renderer.setYLabels(10);
		renderer.setChartTitleTextSize(20);
		renderer.setTextTypeface("sans_serif", Typeface.BOLD);
		renderer.setLabelsTextSize(14f);
		renderer.setAxisTitleTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setGridColor(Color.CYAN);
		renderer.setShowGrid(true);

		renderer.setZoomButtonsVisible(true);
		renderer.setZoomEnabled(true, true);

		addRefLineSeriesRenderers(renderer);
		List<double[]> refValues = child.getGender() == Gender.male ? BoyWeight.values
				: GirlWeight.values;
		XYMultipleSeriesDataset dataset = buildRefDataset(titles, refValues,
				weeksToPlot);

		addChildCurve(renderer, dataset, child, Color.RED);

		return ChartFactory.getLineChartIntent(context, dataset, renderer);
	}

	private double roundDown(long lowestWeight) {
		return ((long) Math.floor(lowestWeight / 100)) * 100;
	}

	private double roundUp(long highestWeight) {
		return ((long) Math.ceil(highestWeight / 100)) * 100;
	}

	private long getLowestWeight(Child child) {
		long minWeight = Long.MAX_VALUE;
		for (Measurement measurement : child.getMeasurements()) {
			if (measurement.getWeight() != null
					&& measurement.getWeight().longValue() < minWeight) {
				minWeight = measurement.getWeight().longValue();
			}
		}
		return minWeight == Long.MAX_VALUE ? 1800 : minWeight;
	}

	private long getHighestWeight(Child child) {
		long maxWeight = Long.MIN_VALUE;
		for (Measurement measurement : child.getMeasurements()) {
			if (measurement.getWeight() != null
					&& measurement.getWeight().longValue() > maxWeight) {
				maxWeight = measurement.getWeight().longValue();
			}
		}
		return maxWeight == Long.MAX_VALUE ? 14000 : maxWeight;
	}

	private void addChildCurve(XYMultipleSeriesRenderer renderer,
			XYMultipleSeriesDataset dataset, Child child, int color) {
		addChildSeriesRenderer(renderer, color);

		XYSeries series = new XYSeries(child.getName());
		for (Measurement measurement : child.getMeasurements()) {
			long moment = measurement.getMoment() - child.getDob();
			series.add(moment / SECONDS_PER_MONTH_AVG, measurement.getWeight());
		}
		dataset.addSeries(series);
	}

	private void addChildSeriesRenderer(XYMultipleSeriesRenderer renderer,
			int color) {
		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(color);
		r.setPointStyle(PointStyle.CIRCLE);
		r.setFillBelowLine(false);
		r.setLineWidth(3.5f);
		r.setDisplayChartValues(true);
		r.setChartValuesTextSize(10f);

		renderer.addSeriesRenderer(r);
	}

	private int getAgeInWeeks(Child child) {
		long ageInSeconds = child.getMeasurements()
				.get(child.getMeasurements().size() - 1).getMoment()
				- child.getDob();
		return (int) Math.ceil(ageInSeconds / SECONDS_PER_WEEK);
	}

	private double getAgeInMonths(Child child) {
		long ageInSeconds = child.getMeasurements()
				.get(child.getMeasurements().size() - 1).getMoment()
				- child.getDob();
		return (int) Math.ceil(ageInSeconds / SECONDS_PER_MONTH_AVG);
	}

	/**
	 * Sets a few of the series renderer settings.
	 * 
	 * @param renderer
	 *            the renderer to set the properties to
	 * @param title
	 *            the chart title
	 * @param xTitle
	 *            the title for the X axis
	 * @param yTitle
	 *            the title for the Y axis
	 * @param xMin
	 *            the minimum value on the X axis
	 * @param xMax
	 *            the maximum value on the X axis
	 * @param yMin
	 *            the minimum value on the Y axis
	 * @param yMax
	 *            the maximum value on the Y axis
	 * @param axesColor
	 *            the axes color
	 * @param labelsColor
	 *            the labels color
	 */
	protected void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	/**
	 * Builds a bar multiple series dataset using the provided values.
	 * 
	 * @param titles
	 *            the series titles
	 * @param values
	 *            the values
	 * @return the XY multiple bar dataset
	 */
	protected XYMultipleSeriesDataset buildRefDataset(String[] titles,
			List<double[]> values, int nrOfSamplesToUse) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			XYSeries series = new XYSeries(titles[i]);
			double[] v = values.get(i);
			int seriesLength = nrOfSamplesToUse > v.length
					|| nrOfSamplesToUse < 1 ? v.length : nrOfSamplesToUse;
			for (int k = 0; k < seriesLength; k++) {
				series.add(Moments.refMoments[k] / SECONDS_PER_MONTH_AVG, v[k]);
			}
			dataset.addSeries(series);
		}
		return dataset;
	}

	/**
	 * Builds an XY multiple series renderer.
	 * 
	 * @return the XY multiple series renderers
	 */
	protected XYMultipleSeriesRenderer buildRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 20 });
		return renderer;
	}

	private void addRefLineSeriesRenderers(XYMultipleSeriesRenderer renderer) {
		int[] lineColors = new int[] { Color.BLACK, Color.BLACK, Color.BLACK,
				Color.BLACK, Color.BLACK };
		int[] colorsBelow = new int[] { Color.WHITE, COLOR_IN_RANGE,
				COLOR_IN_RANGE, Color.WHITE, Color.BLACK };
		PointStyle[] styles = new PointStyle[] { PointStyle.POINT,
				PointStyle.POINT, PointStyle.POINT, PointStyle.POINT,
				PointStyle.POINT };

		int length = lineColors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(lineColors[i]);
			r.setPointStyle(styles[i]);
			r.setFillBelowLine(true);
			r.setFillBelowLineColor(colorsBelow[i]);
			r.setLineWidth(2.5f);
			r.setDisplayChartValues(false);

			renderer.addSeriesRenderer(r);
		}
	}
}
