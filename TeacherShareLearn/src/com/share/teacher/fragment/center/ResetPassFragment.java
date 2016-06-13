package com.share.teacher.fragment.center;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.share.teacher.R;
import com.share.teacher.activity.center.ResetPassActivity;
import com.share.teacher.activity.login.LoginActivity;
import com.share.teacher.fragment.BaseFragment;
import com.share.teacher.help.RequestHelp;
import com.share.teacher.help.RequsetListener;
import com.share.teacher.parse.BaseParse;
import com.share.teacher.utils.AppManager;
import com.share.teacher.utils.BaseApplication;
import com.share.teacher.utils.SmartToast;
import com.share.teacher.utils.URLConstants;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.Map;

/**
 *修改密码
 * @author czq
 * @time 2015年9月28日上午11:44:26
 *
 */
public class ResetPassFragment extends BaseFragment implements OnClickListener,RequsetListener{

    private EditText loginPass,resetPass,sure_pass;
    private TextView rechareQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_pass, container, false);
        loginPass = (EditText) view.findViewById(R.id.login_pass);
        resetPass = (EditText) view.findViewById(R.id.new_pass);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleView();
        initView(view);
    }

    private void initTitleView() {
        setLeftHeadIcon(0);
        setTitleText(R.string.reset_pass);
        setLeftHeadIcon(0);
    }

    private void initView(View v) {
        loginPass = (EditText)v.findViewById(R.id.login_pass);
        resetPass = (EditText)v.findViewById(R.id.new_pass);
        sure_pass = (EditText) v.findViewById(R.id.sure_pass);
        rechareQuery = (TextView)v.findViewById(R.id.recharge_query);

        rechareQuery.setOnClickListener(this);

    }


    private Intent intent ;
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.recharge_query:
               resetPass();
                break;
        }

    }

    /**
     * 重新登录
     */
    private void reLogin() {
//        startActivityForResult(new Intent(getActivity(), LoginActivity.class), Constant.RELOGIN);
    }


    private void resetPass(){

        if(loginPass.length()==0 || resetPass.length() == 0){
            SmartToast.showText(mActivity,"新旧密码不能为空");
            return;
        }else if(loginPass.getText().toString().equals(resetPass.getText().toString())){
            SmartToast.showText(mActivity,"新旧密码不能一致!");
            return;
        }else if(!resetPass.getText().toString().equals(sure_pass.getText().toString())){
            toasetUtil.showInfo("两次输入的密码不一致!");
            return;
        }
        requestTask();

    }

    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);

        Map postParams = RequestHelp.getBaseParaMap("UserChangePwd") ;
        postParams.put("oldPassword", loginPass.getText().toString());
        postParams.put("newPassword",resetPass.getText().toString());
        RequestParam param = new RequestParam();
//        param.setmParserClassName(BaseParse.class.getName());
        param.setmParserClassName(new BaseParse());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(),createMyReqErrorListener(), param);

    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        JsonParserBase jsonParserBase = (JsonParserBase)obj;
        if ((jsonParserBase != null)){
            SmartToast.showText("密码修改成功!");
//            toasetUtil.showSuccess("密码修改成功!请登录");
            if(BaseApplication.isLogin()){
                BaseApplication.saveUserInfo(null);
                BaseApplication.setMt_token("");
            }
            ResetPassActivity.exit = true;
            AppManager.getAppManager().finishAllActivity();
        }
    }

}
