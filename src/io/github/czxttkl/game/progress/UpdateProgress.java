package io.github.czxttkl.game.progress;



import io.github.czxttkl.game.create.SingleFragmentActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.actionbarsherlock.sample.shakespeare.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

public class UpdateProgress extends SingleFragmentActivity {
	
	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
        UUID challengeID = (UUID)getIntent()
                .getSerializableExtra(UpdateFragment.EXTRA_CHALLENGE_ID);
            return UpdateFragment.newInstance(challengeID);
	}


}
