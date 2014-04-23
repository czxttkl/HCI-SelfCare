package io.github.czxttkl.game.progress;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

import io.github.czxttkl.game.create.CameraFragment;
import io.github.czxttkl.game.create.ChallengeActivity;
import io.github.czxttkl.game.graph.GraphActivity;
import io.github.czxttkl.game.help.HelpViewpager;
import io.github.czxttkl.game.join.LeftJoinFragment;
import io.github.czxttkl.game.mainscreen.MainScreenActivity;
import io.github.czxttkl.game.model.Challenge;
import io.github.czxttkl.game.model.ChallengeLab;
import io.github.czxttkl.game.model.Photo;
import io.github.czxttkl.game.model.PictureUtils;

import com.actionbarsherlock.sample.shakespeare.R;
import com.actionbarsherlock.sample.shakespeare.Shakespeare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class RightProgressFragment extends Fragment {
	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at 'index'.
	 */
	public static RightProgressFragment newInstance(int index) {
		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);

		RightProgressFragment f = new RightProgressFragment();
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.chart_on_menu, menu);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.chartBtnOnMeu:
			// do something //
			Intent i = new Intent(getActivity(), GraphActivity.class);
			startActivity(i);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
		Challenge c = LeftProgressFragment.mAdapter.getItem(pos);
		
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
