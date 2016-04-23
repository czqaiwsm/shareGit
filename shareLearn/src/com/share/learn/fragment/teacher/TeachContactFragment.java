package com.share.learn.fragment.teacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.share.learn.R;
import com.share.learn.adapter.ContactAdpter;
import com.share.learn.bean.Contactor;
import com.share.learn.bean.msg.Message;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.view.CustomListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 联系人
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class TeachContactFragment extends BaseFragment {

    private CustomListView customListView = null;
    private List<Contactor> list = new ArrayList<Contactor>();
    ContactAdpter adapter;

    @Override
    protected void requestData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        customListView = (CustomListView) view.findViewById(R.id.callListView);

//        Message message = null;
//        for(int i=0;i<8;i++){
//            message = new Message();
////            message.setTime("2016-03-18");
////            message.setMsg_content("HELLO!HOW ARE YOU?");
//            message.setName("完颜姓");
//            message.setHeadPhoto("");
//            list.add(message);
//        }
        customListView.setCanLoadMore(false);
        customListView.setCanRefresh(false);
         adapter = new ContactAdpter(mActivity, list);
        customListView.setAdapter(adapter);
    }

}