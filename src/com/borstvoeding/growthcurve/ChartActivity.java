package com.borstvoeding.growthcurve;

import java.io.Serializable;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.borstvoeding.growthcurve.charts.Chart;
import com.borstvoeding.growthcurve.charts.ChartType;
import com.borstvoeding.growthcurve.db.Child;

public class ChartActivity extends Activity {
	private LinearLayout mLayout;
	private GraphicalView mCurrentChartView;
	private Child child;
	private Button mButton;
	private GraphicalView mWeightChartView;
	private GraphicalView mLengthChartView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("gc", "We are in onCreate");
		super.onCreate(savedInstanceState);

		setContentView(R.layout.chart);
		Serializable obj = getIntent().getExtras().getSerializable("child");
		if (obj != null) {
			child = (Child) obj;
		}
	}

	@Override
	protected void onResume() {
		Log.i("gc", "We are in onResume");
		super.onResume();

		if (child == null) {
			Log.e("gc", "No child given!!");
			return;
		}

		if (mLayout != null) {
			mCurrentChartView.repaint();
		}

		mButton = (Button) findViewById(R.id.btOther);
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switchCharts();
			}
		});
		mLayout = (LinearLayout) findViewById(R.id.chart);

		mWeightChartView = new Chart(getApplicationContext(), child,
				ChartType.weight).createView();

		setChart(mWeightChartView);
	}

	private void setChart(GraphicalView view) {
		mCurrentChartView = view;
		mLayout.removeAllViews();
		mLayout.addView(mCurrentChartView, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}

	protected void switchCharts() {
		if (mCurrentChartView == mWeightChartView) {
			mButton.setText(R.string.btTextWeight);
			if (mLengthChartView == null) {
				mLengthChartView = new Chart(getApplicationContext(), child,
						ChartType.length).createView();
			}
			setChart(mLengthChartView);
		} else {
			mButton.setText(R.string.btTextLength);
			setChart(mWeightChartView);
		}
	}
}
