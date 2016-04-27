package com.share.learn.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.share.learn.R;
import com.share.learn.activity.ChooseCityActivity;
import com.share.learn.activity.teacher.ChooseJoinorActivity;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.utils.URLConstants;

/**
 * @desc 课程设置
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class ScheduleSettingFragment extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.studentName)
    TextView studentName;
    @Bind(R.id.studentNameRl)
    RelativeLayout studentNameRl;
    @Bind(R.id.subjectName)
    TextView subjectName;
    @Bind(R.id.subjectRL)
    RelativeLayout subjectRL;
    @Bind(R.id.joniorName)
    TextView joniorName;
    @Bind(R.id.joniorRl)
    RelativeLayout joniorRl;
    @Bind(R.id.frequencyName)
    TextView frequencyName;
    @Bind(R.id.frequencyRl)
    RelativeLayout frequencyRl;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.timeRl)
    RelativeLayout timeRl;
    @Bind(R.id.sure)
    TextView sure;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_scheduel_setting, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitle();
        initView(view);
    }

    private void initView(View view) {
        setData();
    }


    private void initTitle() {
        setTitleText(R.string.choose);
        setLeftHeadIcon(0);
    }

    private void setData() {
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.city_choose:
                intent = new Intent(mActivity, ChooseCityActivity.class);
                startActivityForResult(intent, URLConstants.CHOOSE_CITY_REQUEST_CODE);
                break;
            case R.id.choose_jonior:
                intent = new Intent(mActivity, ChooseJoinorActivity.class);
                startActivityForResult(intent, URLConstants.CHOOSE_JOINOR_REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == Activity.RESULT_OK){
//            if(requestCode == URLConstants.CHOOSE_CITY_REQUEST_CODE){//城市
//                chooseCityTxt.setText(data.hasExtra("city")?data.getStringExtra("city"):"");
//            }else if(requestCode == URLConstants.CHOOSE_JOINOR_REQUEST_CODE){//年级
//                joniorId = data.hasExtra(URLConstants.CHOOSE)?data.getStringExtra(URLConstants.CHOOSE):"";
//                chooseJonirTxt.setText(DataMapConstants.getJoniorMap().get(joniorId));
//            }
//          setData();
//        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}