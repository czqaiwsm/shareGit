package com.share.learn.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.share.learn.fragment.ChooseCitytFragment;

public class ChooseCityActivity extends BaseActivity {
    private ChooseCitytFragment mFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        onInitContent();
    }

    private void onInitContent() {

        mFragment = (ChooseCitytFragment) Fragment.instantiate(this,
                ChooseCitytFragment.class.getName());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, mFragment);
        ft.commit();
    }
}
