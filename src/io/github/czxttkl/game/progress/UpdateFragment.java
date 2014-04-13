package io.github.czxttkl.game.progress;

import io.github.czxttkl.game.create.CameraActivity;
import io.github.czxttkl.game.create.CameraFragment;
import io.github.czxttkl.game.create.Challenge;
import io.github.czxttkl.game.create.DatePickerFragment;
import io.github.czxttkl.game.create.ImageFragment;
import io.github.czxttkl.game.create.Photo;
import io.github.czxttkl.game.create.PictureUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.actionbarsherlock.sample.shakespeare.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateFragment extends Fragment {
	public static final String TAG = "UpdateFragment";
	public static final String EXTRA_CHALLENGE_ID = "criminalintent.CRIME_ID";
	private static final int REQUEST_DATE = 0;
	private static final String DIALOG_DATE = "date";
	private static final String DIALOG_IMAGE = "image";
	public static final String TIME_DATE = "timedate";
	public static final String DETAIL = "details";

	private static final int REQUEST_PHOTO = 1;

	Button mDateButton;
	Challenge mChallenge;
	ImageButton mPhotoButton;
	ImageView mPhotoView;
	Button mUploadButton;
	TextView mDetailTextView;

	private String photoFilePath = null;
	private String currentDateandTime = null;

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
		// mChallenge = ChallengeLab.get(getActivity()).getCrime(challengeId);
		mChallenge = new Challenge();

		if (mChallenge == null) {
			Log.i(TAG, "challenge is null");
		} else {
			Log.i(TAG, "challenge is not null");
		}

		setHasOptionsMenu(true);
	}

	public void updateDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		currentDateandTime = sdf.format(mChallenge.getDate());

		mDateButton.setText(currentDateandTime);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.upload_challenge, container, false);
		mDetailTextView = (TextView) v.findViewById(R.id.update_detail);

		mDateButton = (Button) v.findViewById(R.id.challenge_date);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		currentDateandTime = sdf.format(new Date(System.currentTimeMillis()));

		mDateButton.setText(currentDateandTime);
		mDateButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				FragmentManager fm = ((FragmentActivity) getActivity()).getSupportFragmentManager();
				DatePickerFragment dialog = DatePickerFragment.newInstance(new Date(System.currentTimeMillis()));
				dialog.setTargetFragment(UpdateFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_DATE);
			}
		});

		mPhotoButton = (ImageButton) v.findViewById(R.id.challenge_imageButton);
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

		mPhotoView = (ImageView) v.findViewById(R.id.challenge_imageView);
		mPhotoView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Photo p = mChallenge.getPhoto();
				if (p == null)
					return;

				FragmentManager fm = ((FragmentActivity) getActivity()).getSupportFragmentManager();
				String path = getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
				ImageFragment.createInstance(path).show(fm, DIALOG_IMAGE);
			}
		});

		mUploadButton = (Button) v.findViewById(R.id.updateChallenge);
		mUploadButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (photoFilePath == null) {
					Toast toast = Toast.makeText(getActivity(), "Please upload a photo.", Toast.LENGTH_LONG);
					toast.show();
					return;
				}
				String detailText = mDetailTextView.getText().toString();
				if (detailText.equals("")) {
					Toast toast = Toast.makeText(getActivity(), "Please fill in details.", Toast.LENGTH_LONG);
					toast.show();
					return;
				}

				Intent i = new Intent(getActivity(), ProgressDetailActivity.class);
				i.putExtra(CameraFragment.EXTRA_PHOTO_FILENAME, photoFilePath);
				i.putExtra(UpdateFragment.TIME_DATE, currentDateandTime);
				i.putExtra(UpdateFragment.DETAIL, detailText);
				startActivity(i);
				getActivity().finish();

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
			Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mChallenge.setDate(date);
			updateDate();
		} else if (requestCode == REQUEST_PHOTO) {
			// create a new Photo object and attach it to the crime
			String filename = data.getStringExtra(CameraFragment.EXTRA_PHOTO_FILENAME);
			photoFilePath = filename;
			if (filename != null) {
				Photo p = new Photo(filename);
				mChallenge.setPhoto(p);
				showPhoto();
			}
		}
	}

	private void showPhoto() {
		// (re)set the image button's image based on our photo
		if (mChallenge == null)
			return;
		Photo p = mChallenge.getPhoto();
		BitmapDrawable b = null;
		if (p != null) {
			String path = getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
			b = PictureUtils.getScaledDrawable(getActivity(), path);
		}
		mPhotoView.setImageDrawable(b);
	}
}
