package com.borstvoeding.growthcurve;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends Activity {
	private EditText mBaseUrl;
	private EditText mUsername;
	private EditText mPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		mBaseUrl = (EditText) findViewById(R.id.etBaseUrl);
		mUsername = (EditText) findViewById(R.id.etUsername);
		mPassword = (EditText) findViewById(R.id.etPassword);

		loadSettings();
	}

	@Override
	protected void onStop() {
		super.onStop();
		storeSettings();
	}

	public void onClickHandler(View view) {
		switch (view.getId()) {
		case R.id.btCheckAuth:
			checkAuth();
			break;
		}
	}

	private void checkAuth() {
		storeSettings();
		Settings settings = Settings.INSTANCE(this);
		DataHandler dataHandler = new DataHandler(settings.getBaseUrl(),
				settings.getUsername(), settings.getPassword());
		boolean authSucceeds = dataHandler.checkAuth();
		showAuthStatus(authSucceeds);
	}

	private void showAuthStatus(boolean authSucceeds) {
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setMessage(authSucceeds ? "Authentication ok"
				: "Authentication failed");
		ad.setButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		// TODO: use ad.setIcon(icon);
		ad.show();
	}

	private void loadSettings() {
		Settings settings = Settings.INSTANCE(this);
		mBaseUrl.setText(settings.getBaseUrl());
		mUsername.setText(settings.getUsername());
		mPassword.setText(settings.getPassword());
	}

	private void storeSettings() {
		Settings settings = Settings.INSTANCE(this);
		settings.setBaseUrl(mBaseUrl.getText().toString());
		settings.setUsername(mUsername.getText().toString());
		settings.setPassword(mPassword.getText().toString());
	}
}
