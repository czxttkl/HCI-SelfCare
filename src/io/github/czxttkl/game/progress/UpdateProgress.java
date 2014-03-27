package io.github.czxttkl.game.progress;

import io.github.czxttkl.game.ChallengeFragment;
import io.github.czxttkl.game.DatePickerFragment;

import java.util.Date;

import com.actionbarsherlock.sample.shakespeare.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

public class UpdateProgress extends Activity {
	
	Button dateButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_challenge);
		
		dateButton = (Button)findViewById(R.id.challenge_date);
		dateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 DatePickerFragment dialog = DatePickerFragment.newInstance(new Date(System.currentTimeMillis()));
//				 dialog.show
			}
		});
	}

}
