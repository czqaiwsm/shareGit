package com.share.teacher.activity.center;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.share.teacher.activity.BaseActivity;
import com.share.teacher.activity.login.LoginActivity;
import com.share.teacher.fragment.center.SettingFragmentUser;

public class SettingActivity extends BaseActivity {
    private SettingFragmentUser mFragment ;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        onInitContent();
        count++;
    }

    private void onInitContent() {
        mFragment = (SettingFragmentUser) Fragment.instantiate(this,
                SettingFragmentUser.class.getName());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, mFragment);
        ft.commit();
    }
    int count = 0;
    public static boolean exit = false;
    @Override
    public void finish() {
        super.finish();
        if(count == 1 && exit)
            startActivity(new Intent(this, LoginActivity.class));
        exit = false;
    }
}
