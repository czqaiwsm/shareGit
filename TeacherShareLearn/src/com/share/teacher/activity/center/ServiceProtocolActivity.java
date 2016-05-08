package com.share.teacher.activity.center;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.share.teacher.activity.BaseActivity;
import com.share.teacher.fragment.center.ServiceProtocolFragment;

public class ServiceProtocolActivity extends BaseActivity {
    private ServiceProtocolFragment mFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        onInitContent();
    }

    private void onInitContent() {
        mFragment = (ServiceProtocolFragment) Fragment.instantiate(this,
                ServiceProtocolFragment.class.getName());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, mFragment);
        ft.commit();
    }
}
