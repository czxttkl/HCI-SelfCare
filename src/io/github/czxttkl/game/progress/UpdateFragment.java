package io.github.czxttkl.game.progress;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.actionbarsherlock.sample.shakespeare.R;

import io.github.czxttkl.game.create.CameraFragment;
import io.github.czxttkl.game.create.Challenge;
import io.github.czxttkl.game.create.ChallengeLab;
import io.github.czxttkl.game.create.DatePickerFragment;
import io.github.czxttkl.game.create.Photo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class UpdateFragment extends Fragment{
	public static final String EXTRA_CHALLENGE_ID = "coolCare.CHALLENGE_ID";
	private static final int REQUEST_DATE = 0;
	private static final String DIALOG_DATE = "date";


	Button mDateButton;
	Challenge mChallenge;
	
	public static UpdateFragment newInstance(UUID challengeId) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CHALLENGE_ID, challengeId);

		UpdateFragment fragment = new UpdateFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		UUID challengeId = (UUID) getArguments().getSerializable(EXTRA_CHALLENGE_ID);
		mChallenge = ChallengeLab.get(getActivity()).getCrime(challengeId);

		setHasOptionsMenu(true);
	}
	
	public void updateDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String currentDateandTime = sdf.format(mChallenge.getDate());
		
        mDateButton.setText(currentDateandTime);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.upload_challenge, container, false);
		
		mDateButton = (Button) v.findViewById(R.id.challenge_date);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String currentDateandTime = sdf.format(new Date(System.currentTimeMillis()));
		
        mDateButton.setText(currentDateandTime);
		mDateButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				 FragmentManager fm = ((FragmentActivity) getActivity()).getSupportFragmentManager();
				 DatePickerFragment dialog = DatePickerFragment.newInstance(new Date(System.currentTimeMillis()));
				 dialog.setTargetFragment(UpdateFragment.this, REQUEST_DATE);
				 dialog.show(fm, DIALOG_DATE);
			}
		});
		
		return v;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != Activity.RESULT_OK)
			return;
		if (requestCode == REQUEST_DATE) {
			Date date = (Date) data
					.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
//			mChallenge.setDate(date);
//			updateDate();
		} 
	}	
}
