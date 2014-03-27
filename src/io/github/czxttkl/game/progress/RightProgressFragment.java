package io.github.czxttkl.game.progress;

import com.actionbarsherlock.sample.shakespeare.R;
import com.actionbarsherlock.sample.shakespeare.Shakespeare;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class RightProgressFragment extends Fragment {
    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }
        RelativeLayout mCompInfoView = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.join_comp_info, null);
        ScrollView scroller = new ScrollView(getActivity());
        TextView text = new TextView(getActivity());
        int padding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getActivity().getResources().getDisplayMetrics());
        text.setPadding(padding, padding, padding, padding);
        scroller.addView(mCompInfoView);
        //scroller.addView(text);
        //text.setText(Shakespeare.DIALOGUE[getArguments().getInt("index", 0)]);
        return scroller;
    }
}
