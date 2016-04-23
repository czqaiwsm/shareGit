package com.share.learn.activity.center;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.share.learn.activity.BaseActivity;
import com.share.learn.fragment.center.RechargeFragment;
import com.share.learn.fragment.center.WalletFragment;

public class RechargeActivity extends BaseActivity {
    private RechargeFragment mFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        onInitContent();
    }

    private void onInitContent() {
        mFragment = (RechargeFragment) Fragment.instantiate(this,
                RechargeFragment.class.getName());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, mFragment);
        ft.commit();
    }
}
