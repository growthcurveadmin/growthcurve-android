package com.borstvoeding.growthcurve;

import java.io.Serializable;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;

import com.borstvoeding.growthcurve.db.Child;

public class ChartsActivity extends TabActivity {
	private Child child;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.chart);

		Serializable obj = getIntent().getExtras().getSerializable("child");
		if (obj != null) {
			child = (Child) obj;
			setupTitle(customTitleSupported, child.getName());
		}

		// TODO: !!!! ik ben de tab-bladen kwijt!

		Resources res = getResources();
		TabHost tabHost = getTabHost();

		addWeightChart(tabHost, res);
		addLengthChart(tabHost, res);
		// TODO: add measurements tab
		int iCnt = tabHost.getTabWidget().getChildCount();
		for (int i = 0; i < iCnt; i++) {
			tabHost.getTabWidget().getChildAt(i).getLayoutParams().height *= 2;
		}

		tabHost.setCurrentTab(0);
	}

	void setupTitle(boolean customTitleSupported, String title) {
		if (customTitleSupported) {
			// TODO:
			// http://stackoverflow.com/questions/820398/android-change-custom-title-view-at-run-time
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.chart_title);
			TextView titleTvLeft = (TextView) findViewById(R.id.titleAboveChart);
			titleTvLeft.setText(title);
		}
	}

	private void addWeightChart(TabHost tabHost, Resources res) {
		TabHost.TabSpec spec;
		// Intent intent = new Intent().setClass(this, TabWeightActivity.class);
		// intent.putExtra("child", child);
		spec = tabHost
				.newTabSpec("weight")
				.setIndicator(res.getText(R.string.btTextWeight),
						res.getDrawable(R.drawable.ic_tab_chart_weight))
				.setContent(R.layout.chart);
		// .setContent(
		// new Chart(getApplicationContext(), child,
		// ChartType.weight).createIntent());
		// .setContent(intent);
		tabHost.addTab(spec);
	}

	private void addLengthChart(TabHost tabHost, Resources res) {
		Intent intent = new Intent().setClass(this, TabLengthActivity.class);
		intent.putExtra("child", child);
		TabHost.TabSpec spec = tabHost
				.newTabSpec("length")
				.setIndicator(res.getText(R.string.btTextLength),
						res.getDrawable(R.drawable.ic_tab_chart_length))
				.setContent(intent);
		tabHost.addTab(spec);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		// TODO: what???
	}
}
