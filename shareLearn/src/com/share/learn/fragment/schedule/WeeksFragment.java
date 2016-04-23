package com.share.learn.fragment.schedule;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.share.learn.R;
import com.share.learn.adapter.MsgAdpter;
import com.share.learn.adapter.WeeksAdpter;
import com.share.learn.bean.CourseInfo;
import com.share.learn.bean.msg.Message;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.utils.AppLog;
import com.share.learn.view.CustomListView;

import java.util.ArrayList;
import java.util.List;

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

    private String weeks[] = new String[]{"周一","周二","周三","周四","周五","周六","周日"};


    @Override
    protected void requestData() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if(bundle != null ){
            list = (ArrayList<CourseInfo>) bundle.getSerializable("courList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
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
        if(list == null || list.size()==0){
            customListView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
    }

}