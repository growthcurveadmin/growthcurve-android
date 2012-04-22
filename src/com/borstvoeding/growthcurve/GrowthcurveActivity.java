package com.borstvoeding.growthcurve;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.borstvoeding.growthcurve.charts.WeightChart;
import com.borstvoeding.growthcurve.db.Child;
import com.borstvoeding.growthcurve.db.Child.Gender;
import com.borstvoeding.growthcurve.db.DatabaseHandler;

public class GrowthcurveActivity extends ListActivity {
	private static final Logger LOG = Logger
			.getLogger(GrowthcurveActivity.class.getName());

	private static final int ID_MENU_SETTINGS = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DatabaseHandler db = new DatabaseHandler(this);
		if (db.getChildrensCount() == 0) {
			LOG.log(Level.INFO, "Adding children");
			db.addChild(new Child("Jan", new Date().getTime(), Gender.male,
					"Some nice story"));
			db.addChild(new Child("Klazien", new Date().getTime(),
					Gender.female, "Some whining"));
		} else {
			LOG.log(Level.INFO, "Children already in db...");
			List<Child> children = readChildren();
			db.cleanoutChildren();
			for (Child child : children) {
				db.addChild(child);
			}
		}

		ListAdapter adapter = createAdapter(db);
		setListAdapter(adapter);
	}

	List<Child> readChildren() {
		String json = convertStreamToString(GrowthcurveActivity.class
				.getResourceAsStream("android-get.json"));
		List<Child> children = new ArrayList<Child>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				children.add(Child.load(array.getJSONObject(i)));
			}
		} catch (JSONException e) {
			LOG.log(Level.SEVERE, "JSON child information invalid", e);
		}
		return children;
	}

	// FROM http://stackoverflow.com/a/5445161
	public String convertStreamToString(java.io.InputStream is) {
		try {
			return new java.util.Scanner(is).useDelimiter("\\A").next();
		} catch (java.util.NoSuchElementException e) {
			return "";
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// TODO: load the selected child's measurements from the db... (the rest
		// is already there)
		Child child = new Child("child1", 1147489200, Gender.male, null);
		startActivity(new WeightChart().execute(getApplicationContext(), child));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ID_MENU_SETTINGS:
			Intent settingsIntent = new Intent(getApplicationContext(),
					SettingsActivity.class);
			startActivityForResult(settingsIntent, 0);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuItem item = menu.add(Menu.NONE, ID_MENU_SETTINGS, Menu.NONE,
				R.string.Settings);
		item.setShortcut('5', 's');
		return true;
	}

	/**
	 * Creates and returns a list adapter for the current list activity
	 * 
	 * @return
	 */
	protected ListAdapter createAdapter(DatabaseHandler db) {
		String[] names = new String[db.getChildrensCount()];
		int i = 0;
		for (Child child : db.getAllChildren()) {
			names[i++] = child.getName();
		}
		ListAdapter adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, names);

		return adapter;
	}
}
