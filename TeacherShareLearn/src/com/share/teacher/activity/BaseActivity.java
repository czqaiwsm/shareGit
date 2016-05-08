package com.share.teacher.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.share.teacher.utils.AppManager;
import com.toast.ToasetUtil;

public class BaseActivity extends FragmentActivity {

	public ToasetUtil toasetUtil = null;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		toasetUtil  = new ToasetUtil(this);
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	protected void onDestroy() {

		//exit app
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
	}
}
