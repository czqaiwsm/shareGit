package com.share.learn.fragment.home;

import android.app.Activity;
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
import com.share.learn.activity.home.IDCardCertifyActivity;
import com.share.learn.activity.teacher.ChooseJoinorActivity;
import com.share.learn.activity.teacher.SchoolCertifyActivity;
import com.share.learn.bean.DataMapConstants;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.utils.BaseApplication;
import com.share.learn.utils.URLConstants;

/**
 * @desc 教师首页
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class TeacherCertifyFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.idCertify_status)
    TextView idCertifyStatus;
    @Bind(R.id.idCertifyRl)
    RelativeLayout idCertifyRl;
    @Bind(R.id.schoolCertify_status)
    TextView schoolCertifyStatus;
    @Bind(R.id.chooseCertifyRl)
    RelativeLayout chooseCertifyRl;

    @Override
    protected void requestData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_certify, null);
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
        idCertifyRl.setOnClickListener(this);
        chooseCertifyRl.setOnClickListener(this);
    }


    private void initTitle() {
        setTitleText(R.string.certify_seeting);
        setLeftHeadIcon(0);
        setHeaderRightText(R.string.sure, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
                Intent intent = new Intent();
                mActivity.setResult(Activity.RESULT_OK, intent);
                mActivity.finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.idCertifyRl:
                intent = new Intent(mActivity, IDCardCertifyActivity.class);
                startActivityForResult(intent, URLConstants.CHOOSE_CITY_REQUEST_CODE);
                break;
            case R.id.chooseCertifyRl:
//                intent = new Intent(mActivity, ChooseJoinorActivity.class);
                intent = new Intent(mActivity, SchoolCertifyActivity.class);
                startActivityForResult(intent, URLConstants.CHOOSE_JOINOR_REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}