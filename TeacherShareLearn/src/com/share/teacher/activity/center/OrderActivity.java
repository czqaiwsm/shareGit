package com.share.teacher.activity.center;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.share.teacher.activity.BaseActivity;
import com.share.teacher.fragment.center.OrderFragment;

public class OrderActivity extends BaseActivity {
    private OrderFragment mFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        onInitContent();
    }

    private void onInitContent() {
        mFragment = (OrderFragment) Fragment.instantiate(this,
                OrderFragment.class.getName());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, mFragment);
        ft.commit();
    }
}
