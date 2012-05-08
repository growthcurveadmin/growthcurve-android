package com.borstvoeding.growthcurve;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.borstvoeding.growthcurve.db.Child;
import com.borstvoeding.growthcurve.db.DatabaseHandler;

public class GrowthcurveActivity extends ListActivity {
	private static final Logger LOG = Logger
			.getLogger(GrowthcurveActivity.class.getName());

	private static final int ID_MENU_SETTINGS = 0;
	private static final int ID_MENU_RELOAD = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DatabaseHandler db = new DatabaseHandler(this);
		if (db.getChildrensCount() == 0) {
			checkedLoadChildren(db);
		}

		reloadList(db);
	}

	private void reloadList(DatabaseHandler db) {
		Cursor cursor = db.getCursorOnAllChildren();
		ListAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, cursor, //
				new String[] { DatabaseHandler.KEY_NAME, "dt" }, //
				new int[] { android.R.id.text1, android.R.id.text2 });
		// ListAdapter adapter = createAdapter(db);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuItem itemSettings = menu.add(Menu.NONE, ID_MENU_SETTINGS,
				Menu.NONE, R.string.Settings);
		itemSettings.setShortcut('5', 's');
		MenuItem itemReload = menu.add(Menu.NONE, ID_MENU_RELOAD, Menu.NONE,
				R.string.Reload);
		itemReload.setShortcut('7', 'r');
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ID_MENU_SETTINGS:
			startSettings();
			return true;
		case ID_MENU_RELOAD:
			reloadChildren();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
		// TODO: add DOB to the list to nicen it up
		ListAdapter adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, names);

		return adapter;
	}

	private void checkedLoadChildren(DatabaseHandler db) {
		Settings settings = Settings.INSTANCE(this);
		if (settings.getUsername() == null
				|| settings.getUsername().trim().length() == 0) {
			showMessage("Please goto settings first to setup access");
			startSettings();
		} else {
			loadChildren(db, settings);
		}
	}

	private void loadChildren(DatabaseHandler db, Settings settings) {
		LOG.log(Level.INFO, "Load children...");
		DataHandler dataHandler = new DataHandler(settings.getBaseUrl(),
				settings.getUsername(), settings.getPassword());
		List<Child> children;
		try {
			children = readChildren(dataHandler.loadChildren());
			db.cleanoutDb();
			for (Child child : children) {
				db.addChild(child);
			}
		} catch (DataNotLoadedException e) {
			Toast.makeText(this, "Failed to load children: " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
			LOG.log(Level.WARNING, "Failed to load children", e);
		}
	}

	List<Child> readChildren(String json) {
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

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		Child child = db.getChild((int) id);
		Intent chartIntent = new Intent(getApplicationContext(),
				ChartActivity.class);
		chartIntent.putExtra("child", child);
		startActivityForResult(chartIntent, 1);
	}

	private void reloadChildren() {
		DatabaseHandler db = new DatabaseHandler(this);
		checkedLoadChildren(db);
		reloadList(db);
	}

	private void startSettings() {
		Intent settingsIntent = new Intent(getApplicationContext(),
				SettingsActivity.class);
		startActivityForResult(settingsIntent, 0);
	}

	private void showMessage(String message) {
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setMessage(message);
		ad.setButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		// TODO: use ad.setIcon(icon);
		ad.show();
	}
}
