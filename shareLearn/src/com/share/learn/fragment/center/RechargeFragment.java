package com.share.learn.fragment.center;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.share.learn.R;
import com.share.learn.activity.ChooseCityActivity;
import com.share.learn.activity.center.PCenterModifyInfoActivity;
import com.share.learn.activity.teacher.ChooseJoinorActivity;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.utils.AppLog;
import com.volley.req.UserInfo;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.net.RequestParamSub;

/**
 *钱包
 * @author czq
 * @time 2015年9月28日上午11:44:26
 *
 */
public class RechargeFragment extends BaseFragment implements OnClickListener {

    private EditText rechargePrice ;
    private TextView rechareQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recharge, container, false);
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
        setTitleText(R.string.wallet);
        setLeftHeadIcon(0);
    }

    private void initView(View v) {
        rechargePrice = (EditText)v.findViewById(R.id.recharge_price);
        rechareQuery = (TextView)v.findViewById(R.id.recharge_query);
        rechareQuery.setOnClickListener(this);

    }


    private Intent intent ;
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.recharge_query:

                break;
        }

    }

    /**
     * 重新登录
     */
    private void reLogin() {
//        startActivityForResult(new Intent(getActivity(), LoginActivity.class), Constant.RELOGIN);
    }

    /**
     * 请求 用户信息
     */
    @Override
    public void requestData() {
        // TODO Auto-generated method stub
        RequestParam param = new RequestParamSub(getActivity());
        HttpURL url = new HttpURL();
//        url.setmBaseUrl(Constant.GET_PcenterInfo);
//        param.setmHttpURL(url);
//        param.setmParserClassName(PcenterInfoParser.class.getName());
        RequestManager.getRequestData(getActivity(), createMyReqSuccessListener(), createMyReqErrorListener(), param);
    }

    private Listener<Object> createMyReqSuccessListener() {
        return new Listener<Object>() {
            @Override
            public void onResponse(Object object) {
//                ResultInfo info = (ResultInfo) object;
//                if (getActivity() != null && !isDetached()) {
//                    if (info.getmCode() == ResultCode.CODE_0) {
//                        mUserInfo = (UserInfo) info.getObject();
//                        setData(mUserInfo);
//                    } else {
//                        SmartToast.showText(info.getmData());
//                    }
//                }
            }
        };
    }

    protected ErrorListener createMyReqErrorListener() {
        return new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.Loge(" data failed to load" + error.getMessage());
                if (!isDetached()) {
//                    showUIDialogState(false);
                }
            }
        };
    }


}
