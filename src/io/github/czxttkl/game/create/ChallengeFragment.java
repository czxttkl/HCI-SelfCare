package io.github.czxttkl.game.create;

import io.github.czxttkl.game.mainscreen.MainScreenActivity;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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

public class ChallengeFragment extends Fragment {
	public static final String EXTRA_CRIME_ID = "criminalintent.CRIME_ID";
	private static final String DIALOG_DATE = "date";
	private static final String DIALOG_IMAGE = "image";
	private static final int REQUEST_DATE = 0;
	private static final int REQUEST_PHOTO = 1;

	Challenge mCrime;
	EditText mTitleField;
	Button mDateButton;
	ImageButton mPhotoButton;
	ImageView mPhotoView;

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

		UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
		// mCrime = ChallengeLab.get(getActivity()).getCrime(crimeId);

		setHasOptionsMenu(true);
	}

	public void updateDate() {
		// mDateButton.setText(mCrime.getDate().toString());
	}

	@Override
	@TargetApi(11)
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_challenge, parent, false);


		mTitleField = (EditText) v.findViewById(R.id.crime_title);
		// mTitleField.setText(mCrime.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence c, int start, int before,
					int count) {
				// mCrime.setTitle(c.toString());
			}

			public void beforeTextChanged(CharSequence c, int start, int count,
					int after) {
				// this space intentionally left blank
			}

			public void afterTextChanged(Editable c) {
				// this one too
			}
		});

		mDateButton = (Button) v.findViewById(R.id.challenge_date);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDateandTime = sdf.format(new Date(System.currentTimeMillis()));
		
        mDateButton.setText(currentDateandTime);
		mDateButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				 FragmentManager fm = ((FragmentActivity) getActivity()).getSupportFragmentManager();
				 DatePickerFragment dialog = DatePickerFragment.newInstance(new Date(System.currentTimeMillis()));
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
				// Photo p = mCrime.getPhoto();
				// if (p == null)
				// return;

				// FragmentManager fm = getActivity()
				// .getSupportFragmentManager();
				// String path = getActivity()
				// .getFileStreamPath(p.getFilename()).getAbsolutePath();
				// ImageFragment.createInstance(path)
				// .show(fm, DIALOG_IMAGE);
			}
		});

		return v;
	}

	private void showPhoto() {
		// (re)set the image button's image based on our photo
		Photo p = null;// mCrime.getPhoto();
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
		default:
			return super.onOptionsItemSelected(item);
		}
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
			// mCrime.setDate(date);
			updateDate();
		} else if (requestCode == REQUEST_PHOTO) {
			// create a new Photo object and attach it to the crime
			String filename = data
					.getStringExtra(CameraFragment.EXTRA_PHOTO_FILENAME);
			if (filename != null) {
				Photo p = new Photo(filename);
				mCrime.setPhoto(p);
				showPhoto();
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		ChallengeLab.get(getActivity()).saveCrimes();
	}

}
