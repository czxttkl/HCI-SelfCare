package io.github.czxttkl.i3;

import com.actionbarsherlock.sample.shakespeare.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class I3 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setTitle(R.string.activity_titles);
		setContentView(R.layout.i3);
	}
	
	public void onClickFood(View v) {
		Toast.makeText(this, "Foods menu shows up", Toast.LENGTH_SHORT).show();
	}
	
	public void onClickDrink(View v) {
		Toast.makeText(this, "Drinks menu shows up", Toast.LENGTH_SHORT).show();
	}
	
	public void onClickPatron(View v) {
		Toast.makeText(this, "Hold on. A patron is coming.", Toast.LENGTH_SHORT).show();
	}
	
	public void onClickCard(View v) {
		Toast.makeText(this, "We will give your bills in a moment.", Toast.LENGTH_SHORT).show();
	}
	
	public void onClickCash(View v) {
		Toast.makeText(this, "We will give your bills in a moment.", Toast.LENGTH_SHORT).show();
	}
	
	public void onClickShare(View v) {
		Toast.makeText(this, "Thanks for sharing.", Toast.LENGTH_SHORT).show();
	}
	
	public void onClickRate(View v) {
		Toast.makeText(this, "Thanks for rating.", Toast.LENGTH_SHORT).show();
	}
	
	public void onClickHelp(View v) {
		Toast.makeText(this, "Help documents show up.", Toast.LENGTH_SHORT).show();
	}
	
}
