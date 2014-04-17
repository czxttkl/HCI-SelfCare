package io.github.czxttkl.game.create;

import io.github.czxttkl.game.help.HelpViewpager;
import io.github.czxttkl.game.mainscreen.MainScreenActivity;
import io.github.czxttkl.game.model.Challenge;
import io.github.czxttkl.game.model.ChallengeLab;
import io.github.czxttkl.game.model.Photo;
import io.github.czxttkl.game.model.PictureUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.actionbarsherlock.sample.shakespeare.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.Menu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ChallengeFragment extends Fragment {
	public static final String TAG = "ChallengeFragment";
	public static final String EXTRA_CRIME_ID = "criminalintent.CRIME_ID";
	private static final String DIALOG_DATE = "date";
	private static final String DIALOG_IMAGE = "image";
	private static final int REQUEST_DATE = 0;
	private static final int REQUEST_PHOTO = 1;

	Challenge mChallenge;
	EditText mTitleField;
	EditText mLocation;
	EditText mFreq;
	EditText mDetail;
	Button mDateButton;
	ImageButton mPhotoButton;
	ImageView mPhotoView;

	Button mCreatChallenge;

	public static ChallengeFragment newInstance(UUID crimeId) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, crimeId);

		ChallengeFragment fragment = new ChallengeFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		UUID challengeId = (UUID) getArguments()
				.getSerializable(EXTRA_CRIME_ID);
		mChallenge = ChallengeLab.get(getActivity()).getChallenge(challengeId);

		if (mChallenge == null) {
			Log.i(TAG, "challenge is null");
		} else {
			Log.i(TAG, "challenge is not null");
		}

		setHasOptionsMenu(true);
	}

	public void updateDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String currentDateandTime = sdf.format(mChallenge.getStartDate());

		mDateButton.setText(currentDateandTime);
	}

	@Override
	@TargetApi(11)
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_challenge, parent, false);

		mTitleField = (EditText) v.findViewById(R.id.crime_title);
		mTitleField.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence c, int start, int before,
					int count) {
				mChallenge.setTitle(c.toString());
			}

			public void beforeTextChanged(CharSequence c, int start, int count,
					int after) {
				// this space intentionally left blank
			}

			public void afterTextChanged(Editable c) {
				// this one too
			}
		});

		mFreq = (EditText) v.findViewById(R.id.edit_freq);
		mFreq.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				mChallenge.setFreq(mFreq.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		mLocation = (EditText) v.findViewById(R.id.edit_location);
		mLocation.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				mChallenge.setLocation(mLocation.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		mDateButton = (Button) v.findViewById(R.id.challenge_date);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String currentDateandTime = sdf.format(new Date(System
				.currentTimeMillis()));

		mDateButton.setText(currentDateandTime);
		mDateButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				FragmentManager fm = ((FragmentActivity) getActivity())
						.getSupportFragmentManager();
				DatePickerFragment dialog = DatePickerFragment
						.newInstance(new Date(System.currentTimeMillis()));
				dialog.setTargetFragment(ChallengeFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_DATE);
			}
		});

		mPhotoButton = (ImageButton) v.findViewById(R.id.crime_imageButton);
		mPhotoButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// launch the camera activity
				Intent i = new Intent(getActivity(), CameraActivity.class);
				startActivityForResult(i, REQUEST_PHOTO);
			}
		});

		// if camera is not available, disable camera functionality
		PackageManager pm = getActivity().getPackageManager();
		if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
				&& !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
			mPhotoButton.setEnabled(false);
		}

		mPhotoView = (ImageView) v.findViewById(R.id.crime_imageView);
		mPhotoView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Photo p = mChallenge.getPhoto();
				if (p == null)
					return;

				FragmentManager fm = ((FragmentActivity) getActivity())
						.getSupportFragmentManager();
				String path = getActivity().getFileStreamPath(p.getFilename())
						.getAbsolutePath();
				ImageFragment.createInstance(path).show(fm, DIALOG_IMAGE);
			}
		});
		
		mDetail = (EditText) v.findViewById(R.id.challenge_detail);
		mDetail.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				mChallenge.setDetails(mDetail.getText().toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});

		mCreatChallenge = (Button) v.findViewById(R.id.create_challenge);
		mCreatChallenge.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});

		return v;
	}

	private void showPhoto() {
		// (re)set the image button's image based on our photo
		if (mChallenge == null)
			return;
		Photo p = mChallenge.getPhoto();
		BitmapDrawable b = null;
		if (p != null) {
			String path = getActivity().getFileStreamPath(p.getFilename())
					.getAbsolutePath();
			b = PictureUtils.getScaledDrawable(getActivity(), path);
		}
		mPhotoView.setImageDrawable(b);
	}

	public boolean onOptionsItemSelected(android.support.v4.view.MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(getActivity(), MainScreenActivity.class);
			startActivity(intent);
			getActivity().finish();
			return true;
		case R.id.menu_help:
			Intent helpIntent = new Intent(getActivity(), HelpViewpager.class);
			helpIntent.putExtra("page", 4);
			startActivity(helpIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.help_on_menu, menu);

	}

	@Override
	public void onStart() {
		super.onStart();
		showPhoto();
	}

	@Override
	public void onStop() {
		super.onStop();
		PictureUtils.cleanImageView(mPhotoView);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK)
			return;
		if (requestCode == REQUEST_DATE) {
			Date date = (Date) data
					.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mChallenge.setStartDate(date);
			updateDate();
		} else if (requestCode == REQUEST_PHOTO) {
			Log.i(TAG, "result ok photo");

			String value = data
					.getStringExtra(CameraFragment.EXTRA_CAMERA_SWITCH);
			Log.i(TAG, value + "photo");
			boolean switchCamera = data.getBooleanExtra(
					CameraFragment.EXTRA_CAMERA_SWITCH, false);

			if (switchCamera) {
				Log.i(TAG, "switching photo");
				Intent i = new Intent(getActivity(), CameraActivity.class);
				i.putExtra(CameraActivity.SWITCH_CAMERA, true);
				startActivityForResult(i, REQUEST_PHOTO);

			} else {
				// create a new Photo object and attach it to the crime
				String filename = data
						.getStringExtra(CameraFragment.EXTRA_PHOTO_FILENAME);
				if (filename != null) {
					Photo p = new Photo(filename);
					mChallenge.setPhoto(p);
					showPhoto();
				}
			}

		}
	}

	@Override
	public void onPause() {
		super.onPause();
		ChallengeLab.get(getActivity()).saveChallenges();
	}

}
