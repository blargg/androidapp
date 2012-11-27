package com.example.my.first.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LinearPictures extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		HorizontalScrollView sv = new HorizontalScrollView(this);
		
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.HORIZONTAL);
		ll.setGravity(Gravity.CENTER);
		sv.addView(ll);
		
		for(int i = 0; i < 20; i++){
			ImageView imgV = new ImageView(this);
			imgV.setImageResource(R.drawable.ic_launcher);
			ll.addView(imgV);		
		}

		
		Button b = new Button(this);
		b.setText("Test Text");
		ll.addView(b);
		setContentView(sv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_linear_pictures, menu);
		return true;
	}

}
