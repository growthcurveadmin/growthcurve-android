package com.borstvoeding.growthcurve;

import java.io.Serializable;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
		super.onCreate(savedInstanceState);

		boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		Serializable obj = getIntent().getExtras().getSerializable("child");
		if (obj != null) {
			child = (Child) obj;
		}
		setContentView(R.layout.chart);

		setupTitle(customTitleSupported, child.getName());
	}

	void setupTitle(boolean customTitleSupported, String title) {
		if (customTitleSupported) {
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.chart_title);
			TextView titleTvLeft = (TextView) findViewById(R.id.titleAboveChart);
			titleTvLeft.setText(title);
		}
	}

	@Override
	protected void onResume() {
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
