package com.borstvoeding.growthcurve;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TabLengthActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Length tab");
        setContentView(textview);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	// TODO: ???
    }
}
