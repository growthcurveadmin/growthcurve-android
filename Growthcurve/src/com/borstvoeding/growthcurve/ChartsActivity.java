package com.borstvoeding.growthcurve;

import java.io.Serializable;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.Window;

import com.borstvoeding.growthcurve.db.Child;
import com.viewpagerindicator.TabPageIndicator;

public class ChartsActivity extends FragmentActivity {
	private Child child;

	public static Activity newInstance(Child child) {
		ChartsActivity chartsActivity = new ChartsActivity();
		chartsActivity.child = child;
		return chartsActivity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		Serializable obj = getIntent().getExtras().getSerializable("child");
		if (obj != null) {
			child = (Child) obj;
			setupTitle(customTitleSupported, child.getName());
		}

		setContentView(R.layout.one_child);

		FragmentPagerAdapter adapter = new ChildTabsAdapter(
				getSupportFragmentManager(), child);

		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.one_child, menu);
		return true;
	}

	void setupTitle(boolean customTitleSupported, String title) {
		if (customTitleSupported) {
			// TODO:
			// http://stackoverflow.com/questions/820398/android-change-custom-title-view-at-run-time
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.chart_title);
			// TextView titleTvLeft = (TextView)
			// findViewById(R.id.titleAboveChart);
			// titleTvLeft.setText(title);
		}
	}
}
