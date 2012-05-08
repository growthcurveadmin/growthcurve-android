package com.borstvoeding.growthcurve;

import java.io.Serializable;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.borstvoeding.growthcurve.charts.WeightChart;
import com.borstvoeding.growthcurve.db.Child;

public class ChartActivity extends Activity {
	private LinearLayout mLayout;
	private GraphicalView mChartView;
	private Child child;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.chart);
		Serializable obj = getIntent().getExtras().getSerializable("child");
		if (obj != null) {
			child = (Child) obj;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (child == null) {
			Log.e("gc", "No child given!!");
			return;
		}

		if (mLayout == null) {
			mLayout = (LinearLayout) findViewById(R.id.chart);
			mChartView = new WeightChart(child)
					.createView(getApplicationContext());
			mLayout.addView(mChartView, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		} else {
			mChartView.repaint();
		}
	}
}
