package com.share.teacher.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.share.teacher.activity.BaseActivity;
import com.share.teacher.fragment.home.ScheduleSettingFragment;
import com.share.teacher.fragment.home.TeacherCertifyFragment;
import com.share.teacher.fragment.teacher.TeachCourseFragment;

public class CourseSettingActivity extends BaseActivity {
	private ScheduleSettingFragment mFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		onInitContent();
	}

	private void onInitContent() {
		mFragment = (ScheduleSettingFragment) Fragment.instantiate(this, ScheduleSettingFragment.class.getName());
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(android.R.id.content, mFragment);
		ft.commit();
	}
}
