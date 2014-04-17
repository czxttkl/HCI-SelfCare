package io.github.czxttkl.game.join;

import java.text.SimpleDateFormat;

import io.github.czxttkl.game.model.Challenge;
import io.github.czxttkl.game.model.Photo;
import io.github.czxttkl.game.model.PictureUtils;

import com.actionbarsherlock.sample.shakespeare.R;
import com.actionbarsherlock.sample.shakespeare.Shakespeare;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class RightJoinFragment extends Fragment {
	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	
	private static final String TAG = "RightJoinFragment";
	
	public static RightJoinFragment newInstance(int index) {
		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);

		RightJoinFragment f = new RightJoinFragment();
		f.setArguments(args);

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			// We have different layouts, and in one of them this
			// fragment's containing frame doesn't exist. The fragment
			// may still be created from its saved state, but there is
			// no reason to try to create its view hierarchy because it
			// won't be displayed. Note this is not needed -- we could
			// just run the code below, where we would create and return
			// the view hierarchy; it would just never be used.
			return null;
		}
		
		int pos = getArguments().getInt("index");
		Challenge c = LeftJoinFragment.mAdapter.getItem(pos);

		Log.i(TAG, "click pos is: " + pos);
		
		RelativeLayout mCompInfoView = (RelativeLayout) getActivity()
				.getLayoutInflater().inflate(R.layout.join_comp_info, null);
		
		TextView startDateTextView = (TextView) mCompInfoView.findViewById(R.id.start_date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDateandTime = sdf.format(c.getStartDate());
		startDateTextView.setText(startDateandTime);
		
		TextView endDateTextView = (TextView) mCompInfoView.findViewById(R.id.end_date);
		SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd");
		String endDateandTime = sdf.format(c.getStartDate());
		endDateTextView.setText(endDateandTime);
		
		ProgressBar progressBar = (ProgressBar) mCompInfoView.findViewById(R.id.progressBar1);
		progressBar.setProgress(c.getProgress());
		
		TextView freqTextView = (TextView) mCompInfoView.findViewById(R.id.freq_text);
		freqTextView.setText(c.getFreq());
		
		TextView detailView = (TextView) mCompInfoView.findViewById(R.id.chllg_profile1_detail);
		detailView.setText(c.getDetails());
		
		ScrollView scroller = new ScrollView(getActivity());
		scroller.addView(mCompInfoView);
		
		ImageView imageView = (ImageView) mCompInfoView.findViewById(R.id.chllg_profile1_event);
		Photo p = c.getPhoto();
		BitmapDrawable b = null;
		if (p != null) {
			String path = getActivity().getFileStreamPath(p.getFilename())
					.getAbsolutePath();
			b = PictureUtils.getScaledDrawable(getActivity(), path);
		}
		imageView.setImageDrawable(b);

		return scroller;
	}

}
