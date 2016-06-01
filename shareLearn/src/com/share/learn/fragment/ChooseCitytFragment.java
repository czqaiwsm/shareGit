package com.share.learn.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.share.learn.R;
import com.share.learn.adapter.CityAdpter;
import com.share.learn.service.LocationService;
import com.share.learn.service.LocationUitl;
import com.share.learn.utils.AppLog;
import com.share.learn.utils.BaseApplication;
import com.share.learn.utils.URLConstants;
import com.share.learn.view.CustomListView;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;

import java.util.ArrayList;
import java.util.List;

public class ChooseCitytFragment extends BaseFragment implements OnClickListener,LocationUitl.LocationListener{
    /*
     *
     * 城市选择
     */

    private CustomListView listView = null;
    private List<String> list = new ArrayList<String>();
    private CityAdpter adapter;
    private TextView cityName;






    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        LocationUitl.registListener(this);
//        startReqTask(this);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        initTitleView();
        initView(view);
    }

    private void initView(View view) {
        cityName = (TextView) view.findViewById(R.id.city_name);
        listView = (CustomListView) view.findViewById(R.id.callListView);
        cityName.setText(BaseApplication.getInstance().location[0]);
        list.add("合肥");

        listView.setCanLoadMore(false);
        listView.setCanRefresh(false);
        adapter = new CityAdpter(getActivity(), list);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String city = (String) arg0.getItemAtPosition(arg2);
                Intent intent = new Intent();
                intent.putExtra("city", city);
                mActivity.setResult(Activity.RESULT_OK,intent);
                cityName.setText(city);
                mActivity.finish();
            }
        });

    }

    private void initTitleView() {
        setTitleText("选择城市");
        setLeftHeadIcon(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_city, container, false);
        return view;
    }



    @Override
    public void locatinNotify(BDLocation location) {
        cityName.setText(location.getCity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocationUitl.removeListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
