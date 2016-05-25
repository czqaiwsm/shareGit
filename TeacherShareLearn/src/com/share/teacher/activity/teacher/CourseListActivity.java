package com.share.teacher.activity.teacher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.share.teacher.activity.BaseActivity;
import com.share.teacher.fragment.teacher.CourseListFragment;

public class CourseListActivity extends BaseActivity {
		private CourseListFragment mFragment;

		@Override
		protected void onCreate(Bundle arg0) {
			super.onCreate(arg0);
			onInitContent();
		}

		private void onInitContent() {
			mFragment = (CourseListFragment) Fragment.instantiate(this, CourseListFragment.class.getName());
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.add(android.R.id.content, mFragment);
			ft.commit();
		}}