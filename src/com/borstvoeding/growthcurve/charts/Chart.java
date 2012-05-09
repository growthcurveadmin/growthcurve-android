package com.borstvoeding.growthcurve.charts;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import com.borstvoeding.growthcurve.R;
import com.borstvoeding.growthcurve.db.Child;
import com.borstvoeding.growthcurve.db.Child.Gender;
import com.borstvoeding.growthcurve.db.Measurement;
import com.borstvoeding.growthcurve.ref.BoyLength;
import com.borstvoeding.growthcurve.ref.BoyWeight;
import com.borstvoeding.growthcurve.ref.GirlLength;
import com.borstvoeding.growthcurve.ref.GirlWeight;
import com.borstvoeding.growthcurve.ref.Moments;
import com.borstvoeding.growthcurve.ref.Reference;

public class Chart {
	private static final String[] titles = new String[] { "2+", "1+", "0",
			"1-", "2-" };
	private static final int COLOR_IN_RANGE = Color.rgb(193, 255, 193);
	private static final int SECONDS_PER_WEEK = 604800;
	private static final int SECONDS_PER_MONTH_AVG = 2628000;

	private XYMultipleSeriesDataset dataset;
	private XYMultipleSeriesRenderer renderer;
	private final Context context;
	private final Child child;
	private final ChartType chartType;

	public Chart(Context context, Child child, ChartType chartType) {
		this.context = context;
		this.child = child;
		this.chartType = chartType;
	}

	public Intent createIntent() {
		setupData();
		return ChartFactory.getLineChartIntent(context, dataset, renderer);
	}

	public GraphicalView createView() {
		setupData();
		return ChartFactory.getLineChartView(context, dataset, renderer);
	}

	private void setupData() {
		renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 20 });
		renderer.setAxesColor(Color.GRAY);
		renderer.setLabelsColor(Color.LTGRAY);
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

		// See which chart we should create
		Reference ref = getReference();
		renderer.setChartTitle(ref.getChartTitle());

		renderer.setXAxisMin(0);
		renderer.setXAxisMax(getAgeInMonths(child) + 1);
		renderer.setXTitle(context.getString(R.string.months));
		// TODO: det. Y-range according to the ref-graph,
		// not the child's weight/length
		long lowest = chartType == ChartType.length ? 35 : 1800; // getLowestMeasurement(child);
		long highest = getHighestMeasurement(child);
		Log.i("gc-chart",
				String.format("lowest: %d, highest: %d", lowest, highest));
		renderer.setYAxisMin(lowest);
		renderer.setYAxisMax(roundUp(highest));
		renderer.setYTitle(ref.getChartYTitle());

		addRefLineSeriesRenderers(renderer);

		int weeksToPlot = getAgeInWeeks(child);
		dataset = buildRefDataset(ref.getValues(), weeksToPlot);

		addChildCurve(renderer, dataset, child, Color.RED);
	}

	private Reference getReference() {
		Reference ref;
		if (child.getGender() == Gender.male) {
			if (chartType == ChartType.weight) {
				ref = new BoyWeight(context);
			} else {
				ref = new BoyLength(context);
			}
		} else {
			if (chartType == ChartType.weight) {
				ref = new GirlWeight(context);
			} else {
				ref = new GirlLength(context);
			}
		}
		return ref;
	}

	private double roundDown(long lowestMeasurement) {
		return ((long) Math.floor(lowestMeasurement / 100)) * 100;
	}

	private double roundUp(long highestMeasurement) {
		return ((long) Math.ceil(highestMeasurement / 100)) * 100;
	}

	private long getLowestMeasurement(Child child) {
		long minMeasurement = Long.MAX_VALUE;
		for (Measurement measurement : child.getMeasurements()) {
			Long value = getValue(measurement);
			if (value != null && value.longValue() < minMeasurement) {
				minMeasurement = value.longValue();
			}
		}
		return minMeasurement == Long.MAX_VALUE ? 1800 : minMeasurement;
	}

	private long getHighestMeasurement(Child child) {
		long maxMeasurement = Long.MIN_VALUE;
		for (Measurement measurement : child.getMeasurements()) {
			Long value = getValue(measurement);
			if (value != null && value.longValue() > maxMeasurement) {
				maxMeasurement = value.longValue();
			}
		}
		return maxMeasurement == Long.MAX_VALUE ? 14000 : maxMeasurement;
	}

	private void addChildCurve(XYMultipleSeriesRenderer renderer,
			XYMultipleSeriesDataset dataset, Child child, int color) {
		addChildSeriesRenderer(renderer, color);

		XYSeries series = new XYSeries(child.getName());
		for (Measurement measurement : child.getMeasurements()) {
			long moment = measurement.getMoment() - child.getDob();
			Long value = getValue(measurement);
			if (value != null) {
				series.add(moment / SECONDS_PER_MONTH_AVG, value.longValue());
			}
		}
		dataset.addSeries(series);
	}

	/**
	 * @param measurement
	 *            the measurement
	 * @return the measurement of the moment based on the chart-type
	 */
	private Long getValue(Measurement measurement) {
		return chartType == ChartType.weight ? measurement.getWeight()
				: measurement.getLength();
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
	 * Builds the reference series dataset using the provided values.
	 * 
	 * @param titles
	 *            the series titles
	 * @param values
	 *            the values
	 * @return the XY multiple bar dataset
	 */
	protected XYMultipleSeriesDataset buildRefDataset(List<double[]> values,
			int nrOfSamplesToUse) {
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
