package com.share.teacher.fragment.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.share.teacher.R;
import com.share.teacher.adapter.WeeksAdpter;
import com.share.teacher.bean.CourseInfo;
import com.share.teacher.fragment.BaseFragment;
import com.share.teacher.view.CustomListView;

import java.util.ArrayList;

/**
 * @desc 课程-->每天的课程
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class WeeksFragment extends BaseFragment {

    private CustomListView customListView = null;
    private ArrayList<CourseInfo> list = new ArrayList<CourseInfo>();
    private WeeksAdpter adapter;
    private int position;
    private TextView noData ;

//    private String weeks[] = new String[]{"周一","周二","周三","周四","周五","周六","周日"};

    View view;
    private boolean isPrepare = false;
    private boolean isVisible = false;

    int flag = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if(bundle != null ){
            list = (ArrayList<CourseInfo>) bundle.getSerializable("courList");
            flag = bundle.getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_msg,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        isPrepare = true;
        onLazyLoad();

    }


    private void onLazyLoad(){

        if(!isPrepare || !isVisible){
            return;
        }
//        requestTask(1);
        if(ScheduleFragment.weekCourseList != null)
        notifyData(ScheduleFragment.weekCourseList.get(flag));
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isVisible = true;
        }else {
            isVisible = false;
            if(isPrepare){
                dismissLoadingDilog();
            }
        }

        onLazyLoad();
    }


    private void initView(View view){

        customListView = (CustomListView)view.findViewById(R.id.callListView);
        noData = (TextView)view.findViewById(R.id.noData);
        customListView.setCanLoadMore(false);
        customListView.setCanRefresh(false);
        adapter = new WeeksAdpter(mActivity, list);
        customListView.setAdapter(adapter);

        customListView.setVisibility(View.VISIBLE);
        noData.setVisibility(View.GONE);
        noData.setText("暂无课程安排");
        if(list == null || list.size()==0){
            customListView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
    }



    public void notifyData(ArrayList<CourseInfo> list){
        this.list = new ArrayList<CourseInfo>();
        if(list != null){
           this.list.addAll(list);
        }
//        initView(view);
        adapter = new WeeksAdpter(mActivity, list);
        customListView.setAdapter(adapter);
        customListView.setVisibility(View.VISIBLE);
        noData.setVisibility(View.GONE);
        if(list == null || list.size()==0){
            customListView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();

    }

}