package com.share.teacher.activity.teacher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.share.teacher.activity.BaseActivity;
import com.share.teacher.fragment.teacher.EvaluateFragment;
import com.share.teacher.fragment.teacher.MyAssetFragment;

public class MyAssetActivity extends BaseActivity {
	private MyAssetFragment mFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		onInitContent();
	}

	private void onInitContent() {
		mFragment = (MyAssetFragment) Fragment.instantiate(this, MyAssetFragment.class.getName(),getIntent().getBundleExtra("bundle"));
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(android.R.id.content, mFragment);
		ft.commit();
	}
}
