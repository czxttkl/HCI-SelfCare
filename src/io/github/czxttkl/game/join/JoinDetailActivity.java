package io.github.czxttkl.game.join;

import io.github.czxttkl.game.progress.ProgressActivity;
import io.github.czxttkl.game.progress.RightProgressFragment;
import io.github.czxttkl.game.progress.UpdateProgress;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItem;
import android.view.View;

public class JoinDetailActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            RightJoinFragment details = new RightJoinFragment();
            details.setArguments(getIntent().getExtras());
            
            getSupportFragmentManager()
            	.beginTransaction()
            	.add(android.R.id.content, details)
            	.commit();
        }
        
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Intent intent = new Intent(this, JoinDetailActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
				//Get rid of the slide-in animation, if possible
	            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
	                OverridePendingTransition.invoke(this);
	            }
		}
		
		return super.onOptionsItemSelected(item);
	}

    private static final class OverridePendingTransition {
        @SuppressLint("NewApi")
		static void invoke(Activity activity) {
            activity.overridePendingTransition(0, 0);
        }
    }
    
    public void onClickJoin(View v) {
    	Intent i = new Intent(this, UpdateProgress.class);
		startActivity(i);
	}
}