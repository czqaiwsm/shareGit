package com.share.learn.activity.center;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.share.learn.activity.BaseActivity;
import com.share.learn.fragment.center.SettingFragmentUser;
import com.share.learn.fragment.center.WalletFragment;

public class SettingActivity extends BaseActivity {
    private SettingFragmentUser mFragment ;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        onInitContent();
    }

    private void onInitContent() {
        mFragment = (SettingFragmentUser) Fragment.instantiate(this,
                SettingFragmentUser.class.getName());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, mFragment);
        ft.commit();
    }
}
