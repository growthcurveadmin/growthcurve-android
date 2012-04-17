package com.borstvoeding.growthcurve.charts;

import java.util.ArrayList;
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
import com.borstvoeding.growthcurve.db.Measurement;
import com.borstvoeding.growthcurve.ref.BoyWeight;
import com.borstvoeding.growthcurve.ref.Moments;

public class WeightChart {
	private static final int COLOR_IN_RANGE = Color.rgb(193, 255, 193);
	private static final int SECONDS_PER_MONTH_AVG = 2628000;

	public Intent execute(Context context, Child child) {
		String[] titles = new String[] { "2+", "1+", "0", "1-", "2-" };
		List<double[]> values = new ArrayList<double[]>();
		values.add(BoyWeight.refM2);
		values.add(BoyWeight.refM1);
		values.add(BoyWeight.ref0);
		values.add(BoyWeight.ref1);
		values.add(BoyWeight.ref2);

		int[] lineColors = new int[] { Color.BLACK, Color.BLACK, Color.BLACK,
				Color.BLACK, Color.BLACK, Color.RED };
		int[] colorsBelow = new int[] { Color.WHITE, COLOR_IN_RANGE,
				COLOR_IN_RANGE, Color.WHITE, Color.WHITE };
		PointStyle[] styles = new PointStyle[] { PointStyle.POINT,
				PointStyle.POINT, PointStyle.POINT, PointStyle.POINT,
				PointStyle.POINT, PointStyle.CIRCLE };
		XYMultipleSeriesRenderer renderer = buildRenderer(lineColors, styles);
		int weeksToPlot = 52;
		setChartSettings(renderer, //
				"Weight", //
				"months", //
				"grams", //
				0, 13, //
				1800, 14000, //
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

		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer
					.getSeriesRendererAt(i);
			if (i < length - 1) {
				seriesRenderer.setFillBelowLine(true);
				seriesRenderer.setFillBelowLineColor(colorsBelow[i]);
				seriesRenderer.setLineWidth(2.5f);
				seriesRenderer.setDisplayChartValues(false);
			} else {
				seriesRenderer.setFillBelowLine(false);
				seriesRenderer.setLineWidth(3.5f);
				seriesRenderer.setDisplayChartValues(true);
				seriesRenderer.setChartValuesTextSize(10f);
			}
		}
		XYMultipleSeriesDataset refDataset = buildRefDataset(titles, values,
				weeksToPlot);
		XYSeries series = new XYSeries("Child 1");
		for (Measurement measurement : child.getMeasurements()) {
			long moment = measurement.getMoment() - child.getDob();
			if (moment > Moments.refMoments[weeksToPlot]) {
				break;
			}
			series.add(moment / SECONDS_PER_MONTH_AVG, measurement.getWeight());
		}
		refDataset.addSeries(series);
		return ChartFactory.getLineChartIntent(context, refDataset, renderer);
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
	 * @param colors
	 *            the series rendering colors
	 * @param styles
	 *            the series point styles
	 * @return the XY multiple series renderers
	 */
	protected XYMultipleSeriesRenderer buildRenderer(int[] colors,
			PointStyle[] styles) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		setRenderer(renderer, colors, styles);
		return renderer;
	}

	protected void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors,
			PointStyle[] styles) {
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 20 });
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
	}
}
