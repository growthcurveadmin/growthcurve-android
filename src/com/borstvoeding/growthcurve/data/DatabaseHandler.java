package com.borstvoeding.growthcurve.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.borstvoeding.growthcurve.data.Child.Gender;

public class DatabaseHandler extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "growthcurve";

	private static final String TABLE_CHILDREN = "children";
	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_DOB = "dob";
	private static final String KEY_GENDER = "gender";
	private static final String KEY_STORY = "story";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHILDREN);

		onCreate(db);
	}

	public void addChild(Child child) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, child.getName());
		values.put(KEY_DOB, child.getDob());
		values.put(KEY_GENDER, child.getGender().name());
		values.put(KEY_STORY, child.getStory());

		// Inserting Row
		db.insert(TABLE_CHILDREN, null, values);
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

	public List<Child> getAllChildren() {
		List<Child> childrensList = new ArrayList<Child>();

		String selectQuery = "SELECT * FROM " + TABLE_CHILDREN;

		SQLiteDatabase db = this.getWritableDatabase();
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
}
