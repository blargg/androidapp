package com.example.my.first.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LinearPictures extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		
		// the main scrolling view of this activity
		HorizontalScrollView sv = new HorizontalScrollView(this);
		
		// organizes all the different image text pairs horizontally
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.HORIZONTAL);
		ll.setGravity(Gravity.CENTER); // center all the objects (cause those are views... this may not matter)
		sv.addView(ll);
		
		for(int i = 0; i < 20; i++){
			// holds corresponding pairs of image and text (vertically)
			LinearLayout tmp = new LinearLayout(this);
			tmp.setOrientation(LinearLayout.VERTICAL);
			tmp.setGravity(Gravity.CENTER); // center the text and the images
			tmp.setPadding(10, 10, 10, 10);
			
			// make a new image to add
			ImageView imgV = new ImageView(this);
			imgV.setImageResource(R.drawable.ic_launcher);
			tmp.addView(imgV);	
			
			// make text to add
			TextView txt = new TextView(this);
			txt.setText(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));
			tmp.addView(txt);
			
			ll.addView(tmp); // add the small view to the big one
		}

		
		Button b = new Button(this); // here just because
		b.setText("Test Text");
		ll.addView(b);
		setContentView(sv); // set the view to the main view
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_linear_pictures, menu);
		return true;
	}

}
