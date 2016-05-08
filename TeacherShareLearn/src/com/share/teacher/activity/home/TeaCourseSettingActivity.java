package com.share.teacher.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.share.teacher.activity.BaseActivity;
import com.share.teacher.fragment.home.ScheduleSettingFragment;
import com.share.teacher.fragment.home.TeacherCourseSettingFragment;

public class TeaCourseSettingActivity extends BaseActivity {
	private TeacherCourseSettingFragment mFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		onInitContent();
	}

	private void onInitContent() {
		mFragment = (TeacherCourseSettingFragment) Fragment.instantiate(this, TeacherCourseSettingFragment.class.getName());
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(android.R.id.content, mFragment);
		ft.commit();
	}
}
