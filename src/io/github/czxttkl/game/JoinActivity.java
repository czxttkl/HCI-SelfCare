package io.github.czxttkl.game;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.actionbarsherlock.sample.shakespeare.R;

public class JoinActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setTitle(R.string.activity_titles);
        setContentView(R.layout.join);
    }
//    
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.fragment_crime_list, menu);
//    }
}
