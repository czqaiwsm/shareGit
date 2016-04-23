package com.share.learn.activity.center;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.share.learn.activity.BaseActivity;
import com.share.learn.fragment.ChooseCitytFragment;
import com.share.learn.fragment.center.PCenterInfoFragmentUser;

public class PCenterInfoUserActivity extends BaseActivity {
    private PCenterInfoFragmentUser mFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        onInitContent();
    }

    private void onInitContent() {
        mFragment = (PCenterInfoFragmentUser) Fragment.instantiate(this,
                PCenterInfoFragmentUser.class.getName());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, mFragment);
        ft.commit();
    }
}
