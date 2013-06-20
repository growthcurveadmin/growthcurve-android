package com.borstvoeding.growthcurve;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.borstvoeding.growthcurve.db.Child;
import com.viewpagerindicator.IconPagerAdapter;

public class ChildTabsAdapter extends FragmentPagerAdapter implements
		IconPagerAdapter {
	static final String[] CONTENT = new String[] { "Length", "Weight",
			"Details", "Food" };
	static final int[] ICONS = new int[] { R.drawable.perm_group_calendar,
			R.drawable.perm_group_camera, R.drawable.perm_group_device_alarms,
			R.drawable.perm_group_location, };

	private final Child child;

	public ChildTabsAdapter(FragmentManager fm, Child child) {
		super(fm);
		this.child = child;
	}

	@Override
	public Fragment getItem(int position) {
		if (position == 0) {
			return TabLengthFragment.newInstance(child);
		} else if (position == 1) {
			return TabWeightFragment.newInstance(child);
		}
		return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return CONTENT[position % CONTENT.length];
	}

	@Override
	public int getIconResId(int index) {
		return ICONS[index];
	}

	@Override
	public int getCount() {
		return CONTENT.length;
	}
}