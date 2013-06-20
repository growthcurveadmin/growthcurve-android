package com.borstvoeding.growthcurve;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.borstvoeding.growthcurve.charts.Chart;
import com.borstvoeding.growthcurve.charts.ChartType;
import com.borstvoeding.growthcurve.db.Child;

public class TabWeightFragment extends Fragment {
	private Child child;

	public static Fragment newInstance(Child child) {
		TabWeightFragment activity = new TabWeightFragment();
		activity.child = child;
		return activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return new Chart(getActivity(), child, ChartType.weight).createView();
	}
}
