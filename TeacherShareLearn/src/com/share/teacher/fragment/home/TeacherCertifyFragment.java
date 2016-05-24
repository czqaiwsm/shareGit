package com.share.teacher.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.gson.reflect.TypeToken;
import com.share.teacher.R;
import com.share.teacher.activity.TeacherMainActivity;
import com.share.teacher.activity.home.IDCardCertifyActivity;
import com.share.teacher.activity.teacher.SchoolCertifyActivity;
import com.share.teacher.bean.CertifyStatus;
import com.share.teacher.bean.ContactorBean;
import com.share.teacher.bean.LoginInfo;
import com.share.teacher.fragment.BaseFragment;
import com.share.teacher.help.RequestHelp;
import com.share.teacher.help.RequsetListener;
import com.share.teacher.parse.LoginInfoParse;
import com.share.teacher.utils.BaseApplication;
import com.share.teacher.utils.URLConstants;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

import java.util.Map;

/**
 * @DESC 教师首页
 * @CREATOR CAOZHIQING
 * @DATA 2016/3/10
 */
public class TeacherCertifyFragment extends BaseFragment implements View.OnClickListener ,RequsetListener,IParser{

    @Bind(R.id.idCertify_status)
    TextView idCertifyStatus;
    @Bind(R.id.idCertifyRl)
    RelativeLayout idCertifyRl;
    @Bind(R.id.schoolCertify_status)
    TextView schoolCertifyStatus;
    @Bind(R.id.chooseCertifyRl)
    RelativeLayout chooseCertifyRl;

    private  CertifyStatus certifyStatus;

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
//        initView();
        requestTask();
    }

    private void initView() {
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
                intent.putExtra("idcardInfo",certifyStatus.getIdcardInfo());
                startActivityForResult(intent, URLConstants.CHOOSE_CITY_REQUEST_CODE);
                break;
            case R.id.chooseCertifyRl:
//                intent = new Intent(mActivity, ChooseJoinorActivity.class);
                intent = new Intent(mActivity, SchoolCertifyActivity.class);
                intent.putExtra("auditInfo",certifyStatus.getAuditInfo());
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
            requestTask();
        }

    }

    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = RequestHelp.getBaseParaMap("QueryCertifi") ;
        RequestParam param = new RequestParam();
        param.setmParserClassName(this);
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(),createMyReqErrorListener(), param);

    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        JsonParserBase<CertifyStatus> jsonParserBase = (JsonParserBase<CertifyStatus>)obj;
        certifyStatus = jsonParserBase.getData();
//        idcardStatus;//	身份认证状态	是	Int	0.未认证 1.认证审核中 2.认证通过 3.认证不通过
//        auditStatus;//	学历认证状态	是	Int	0.未认证 1.认证审核中 2.认证通过 3.认证不通过
        String satus = certifyStatus.getIdcardStatus();
        initView();
        idCertifyRl.setClickable(true);
        if("0".equalsIgnoreCase(satus)){
            idCertifyStatus.setText("未认证");
        }else if("1".equalsIgnoreCase(satus)){
            idCertifyStatus.setText("认证审核中");
            idCertifyRl.setClickable(false);
        }else  if("2".equalsIgnoreCase(satus)){
            idCertifyStatus.setText("认证通过");
        }else if("3".equalsIgnoreCase(satus)){
            idCertifyStatus.setText("认证不通过");
        }

        satus = certifyStatus.getAuditStatus();
        chooseCertifyRl.setClickable(true);
        if("0".equalsIgnoreCase(satus)){
            schoolCertifyStatus.setText("未认证");
        }else if("1".equalsIgnoreCase(satus)){
            schoolCertifyStatus.setText("认证审核中");
            chooseCertifyRl.setClickable(false);
        }else  if("2".equalsIgnoreCase(satus)){
            schoolCertifyStatus.setText("认证通过");
        }else if("3".equalsIgnoreCase(satus)){
            schoolCertifyStatus.setText("认证不通过");
        }

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }

    @Override
    public Object fromJson(String json) {
        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());
        if(URLConstants.SUCCESS_CODE.equals(result.getRespCode())){
            return ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<CertifyStatus>>() {
            }.getType());
        }
        return result;
    }


}