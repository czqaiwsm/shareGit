package com.share.learn.fragment.center;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.alipay.sdk.pay.demo.PayCallBack;
import com.google.gson.internal.LinkedTreeMap;
import com.share.learn.R;
import com.share.learn.bean.PayInfo;
import com.share.learn.bean.UserInfo;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.help.RequestHelp;
import com.share.learn.help.RequsetListener;
import com.share.learn.parse.BaseParse;
import com.share.learn.utils.BaseApplication;
import com.share.learn.utils.PayUtil;
import com.share.learn.utils.URLConstants;
import com.share.learn.utils.WaitLayer;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.Map;

/**
 * 钱包
 * @author czq
 * @time 2015年9月28日上午11:44:26
 */
public class WidthDrawFragment extends BaseFragment implements OnClickListener, RequsetListener, PayCallBack {


    @Bind(R.id.alipayAccount)
    EditText alipayAccount;
    @Bind(R.id.alipayName)
    EditText alipayName;
    @Bind(R.id.recharge_query)
    TextView rechargeQuery;


    UserInfo userInfo = BaseApplication.getUserInfo();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_widthdraw, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleView();
        setLoadingDilog(WaitLayer.DialogType.MODALESS);
    }

    private void initTitleView() {
        setLeftHeadIcon(0);
        setTitleText("绑定支付宝");
        setLeftHeadIcon(0);

        rechargeQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.recharge_query:

                if (TextUtils.isEmpty(alipayAccount.getText()) || TextUtils.isEmpty(alipayName.getText())) {
                    toasetUtil.showInfo("请输入账号和姓名!");
                } else if(userInfo == null || Integer.valueOf(userInfo.getBalance())<=0){
                    toasetUtil.showInfo("账号没有足够余额!");
                }else {
                    requestTask();
                }
                break;
        }

    }


    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = RequestHelp.getBaseParaMap("Withdraw");

        RequestParam param = new RequestParam();

        postParams.put("account", alipayAccount.getText().toString());
        postParams.put("realName", alipayName.getText().toString());
        postParams.put("price", BaseApplication.getUserInfo() != null?
                BaseApplication.getUserInfo().getBalance():"");
        param.setmParserClassName(new BaseParse());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);

    }


    @Override
    public void paySucc() {
        mActivity.setResult(Activity.RESULT_OK);
        mActivity.finish();
    }

    @Override
    public void payFail() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void handleRspSuccess(int requestType, Object obj) {
        JsonParserBase jsonParserBase = (JsonParserBase) obj;
        if ((jsonParserBase != null)) {
            LinkedTreeMap<String, String> treeMap = (LinkedTreeMap<String, String>) jsonParserBase.getData();
            String order = treeMap.get("tips");
            toasetUtil.showInfo(order);
            mActivity.finish();
        }
    }
}
