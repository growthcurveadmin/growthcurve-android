package com.borstvoeding.growthcurve.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.borstvoeding.growthcurve.db.Child.Gender;

public class DatabaseHandler extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 5;
	private static final String DATABASE_NAME = "growthcurve";

	private static final String TABLE_CHILDREN = "children";
	// Children Table Columns names
	private static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	private static final String KEY_DOB = "dob";
	private static final String SQL_GET_CHILDREN = "SELECT " //
			+ KEY_ID + ", " //
			+ KEY_NAME + ", " //
			+ "strftime('%m/%d/%Y', " + KEY_DOB + ", 11) AS dt" //
			+ " FROM " //
			+ TABLE_CHILDREN;
	private static final String KEY_GENDER = "gender";
	private static final String KEY_STORY = "story";

	private static final String TABLE_MEASUREMENTS = "measurements";
	// Measurements Table Columns names
	private static final String KEY_M_ID = "_id";
	private static final String KEY_M_CHILD_ID = "child_id";
	private static final String KEY_M_MOMENT = "moment";
	private static final String KEY_M_WEIGHT = "weight";
	private static final String KEY_M_LENGTH = "length";
	private static final String KEY_M_STORY = "story";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	DatabaseHandler(Context context, String dbName) {
		super(context, dbName, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createChildrenTable = "CREATE TABLE " + TABLE_CHILDREN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," //
				+ KEY_NAME + " TEXT," //
				+ KEY_DOB + " INTEGER," //
				+ KEY_GENDER + " TEXT," //
				+ KEY_STORY + " TEXT" //
				+ ")";
		db.execSQL(createChildrenTable);

		String createMeasurementsTable = "CREATE TABLE " + TABLE_MEASUREMENTS
				+ "(" + KEY_M_ID + " INTEGER," //
				+ KEY_M_CHILD_ID + " INTEGER," //
				+ KEY_M_MOMENT + " INTEGER," //
				+ KEY_M_WEIGHT + " INTEGER," //
				+ KEY_M_LENGTH + " INTEGER," //
				+ KEY_M_STORY + " TEXT," //
				+ "PRIMARY KEY (" + KEY_M_ID + ", " + KEY_M_CHILD_ID + ")" //
				+ ")";
		db.execSQL(createMeasurementsTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHILDREN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEASUREMENTS);

		onCreate(db);
	}

	public void cleanoutDb() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM " + TABLE_CHILDREN);
		db.execSQL("DELETE FROM " + TABLE_MEASUREMENTS);
		db.close();
	}

	public void addChild(Child child) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, child.getId());
		values.put(KEY_NAME, child.getName());
		values.put(KEY_DOB, child.getDob());
		values.put(KEY_GENDER, child.getGender().name());
		values.put(KEY_STORY, child.getStory());
		db.insert(TABLE_CHILDREN, null, values);

		for (Measurement measurement : child.getMeasurements()) {
			ContentValues measurementValues = new ContentValues();
			measurementValues.put(KEY_M_ID, measurement.getId());
			measurementValues.put(KEY_M_CHILD_ID, child.getId());
			measurementValues.put(KEY_M_MOMENT, measurement.getMoment());
			measurementValues.put(KEY_M_WEIGHT, measurement.getWeight());
			measurementValues.put(KEY_M_LENGTH, measurement.getLength());
			measurementValues.put(KEY_M_STORY, measurement.getStory());
			db.insert(TABLE_MEASUREMENTS, null, measurementValues);
		}
		db.close(); // Closing database connection
	}

	public Child getChild(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = null;
		try {
			cursor = db
					.query(TABLE_CHILDREN, new String[] { KEY_ID, KEY_NAME,
							KEY_DOB, KEY_GENDER, KEY_STORY }, KEY_ID + "=?",
							new String[] { String.valueOf(id) }, null, null,
							null, null);
			if (cursor == null) {
				return null;
			}

			cursor.moveToFirst();
			Child child = readChild(cursor);
			readMeasurements(db, child);
			return child;
		} finally {
			safeClose(cursor);
			db.close();
		}
	}

	private Child readChild(Cursor cursor) {
		Child child = new Child(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getLong(2), Gender.valueOf(cursor
						.getString(3)), cursor.getString(4));
		return child;
	}

	private void readMeasurements(SQLiteDatabase db, Child child) {
		Cursor cursor = null;
		try {
			cursor = db.query(TABLE_MEASUREMENTS, new String[] { KEY_M_ID,
					KEY_M_MOMENT, KEY_M_WEIGHT, KEY_M_LENGTH, KEY_M_STORY },
					KEY_M_CHILD_ID + "=?",
					new String[] { String.valueOf(child.getId()) }, null, null,
					null, null);
			if (cursor == null) {
				return;
			}

			cursor.moveToFirst();
			if (cursor.moveToFirst()) {
				do {
					Measurement measurement = readMeasurement(cursor);
					child.getMeasurements().add(measurement);
				} while (cursor.moveToNext());
			}
		} finally {
			safeClose(cursor);
			db.close();
		}
	}

	private Measurement readMeasurement(Cursor cursor) {
		Measurement measurement = new Measurement(cursor.getLong(0),
				cursor.getLong(1), getNullOrLong(cursor, 2), getNullOrLong(
						cursor, 3), cursor.getString(4));
		return measurement;
	}

	private Long getNullOrLong(Cursor cursor, int columnIndex) {
		if (cursor.isNull(columnIndex)) {
			return null;
		}
		return cursor.getLong(columnIndex);
	}

	public List<Child> getAllChildren() {
		List<Child> childrensList = new ArrayList<Child>();

		String selectQuery = "SELECT * FROM " + TABLE_CHILDREN;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				do {
					Child child = readChild(cursor);
					childrensList.add(child);
				} while (cursor.moveToNext());
			}

			return childrensList;
		} finally {
			safeClose(cursor);
			db.close();
		}
	}

	public int getChildrensCount() {
		String countQuery = "SELECT * FROM " + TABLE_CHILDREN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(countQuery, null);
			return cursor.getCount();
		} finally {
			safeClose(cursor);
			db.close();
		}
	}

	private void safeClose(Cursor cursor) {
		if (cursor != null) {
			cursor.close();
		}
	}

	public Cursor getCursorOnAllChildren() {
		SQLiteDatabase db = this.getReadableDatabase();
		return db.rawQuery(SQL_GET_CHILDREN, null);

		// String orderBy = KEY_NAME;
		// return db.query(TABLE_CHILDREN, new String[] { KEY_ID, KEY_NAME,
		// KEY_DOB }, null, null, null, null, orderBy);
	}
}
