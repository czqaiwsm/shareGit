package com.share.learn.activity.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.share.learn.fragment.login.LoginFramgent;
import com.share.learn.activity.BaseActivity;

public class LoginActivity extends BaseActivity {
	private LoginFramgent mFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		onInitContent();
	}

	private void onInitContent() {
		mFragment = (LoginFramgent) Fragment.instantiate(this, LoginFramgent.class.getName());
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(android.R.id.content, mFragment);
		ft.commit();
	}
}
