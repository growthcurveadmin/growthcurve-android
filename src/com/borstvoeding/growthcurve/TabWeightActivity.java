package com.borstvoeding.growthcurve;

import java.io.Serializable;

import android.app.Activity;
import android.os.Bundle;

import com.borstvoeding.growthcurve.charts.Chart;
import com.borstvoeding.growthcurve.charts.ChartType;
import com.borstvoeding.growthcurve.db.Child;

public class TabWeightActivity extends Activity {
	private Child child;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Serializable obj = getIntent().getExtras().getSerializable("child");
		if (obj == null) {
			// TODO: warn??
			return;
		}
		child = (Child) obj;

		setContentView(new Chart(getApplicationContext(), child,
				ChartType.weight).createView());
	}
}
