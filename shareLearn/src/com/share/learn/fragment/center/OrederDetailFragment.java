package com.share.learn.fragment.center;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.share.learn.R;
import com.share.learn.bean.OrderDetailBean;
import com.share.learn.bean.OrderDetailInfo;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.help.RequestHelp;
import com.share.learn.help.RequsetListener;
import com.share.learn.parse.BaseParse;
import com.share.learn.parse.OrderDetailBeanParse;
import com.share.learn.utils.AppLog;
import com.share.learn.utils.URLConstants;
import com.share.learn.utils.WaitLayer;
import com.share.learn.view.FlowScrollView;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.net.RequestParamSub;
import com.volley.req.parser.JsonParserBase;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 订单详情
 *
 * @author czq
 * @time 2015年9月28日上午11:44:26
 */
public class OrederDetailFragment extends BaseFragment implements RequsetListener,View.OnClickListener{


    @Bind(R.id.nickname)
    TextView nickname;
    @Bind(R.id.courseName)
    TextView courseName;
    @Bind(R.id.name_layout)
    RelativeLayout nameLayout;
    @Bind(R.id.account_duration)
    TextView accountDuration;
    @Bind(R.id.sex_layout)
    RelativeLayout sexLayout;
    @Bind(R.id.date_time)
    TextView dateTime;
    @Bind(R.id.jonior_layout)
    RelativeLayout joniorLayout;
    @Bind(R.id.orderPrice)
    TextView orderPrice;
    @Bind(R.id.city_layout)
    RelativeLayout cityLayout;
    @Bind(R.id.discount)
    TextView discount;
    @Bind(R.id.payPrice)
    TextView payPrice;
    @Bind(R.id.contact)
    TextView contact;
    @Bind(R.id.buy)
    TextView buy;
    @Bind(R.id.scrollview)
    FlowScrollView scrollview;
    @Bind(R.id.container)
    LinearLayout container;

    private String orderId = "";
    private OrderDetailInfo orderDetailInfo ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = mActivity.getIntent();
        if(intent != null && intent.hasExtra("orderId")){
            orderId = intent.getStringExtra("orderId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLoadingDilog(WaitLayer.DialogType.NOT_NOMAL);
        initTitleView();
        requestTask();
    }

    private void initTitleView() {
        setLeftHeadIcon(0);
        setTitleText(R.string.order_detail);
    }



    /**
     * 请求 用户信息
     */
    @Override
    public void requestData() {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = RequestHelp.getBaseParaMap("OrderDetail");
        RequestParam param = new RequestParam();
        postParams.put("orderId",orderId);
        param.setmParserClassName(new OrderDetailBeanParse());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(Object obj)  {
        OrderDetailBean data = ((JsonParserBase<OrderDetailBean>)obj).getData();

            if(data != null){
                orderDetailInfo  =  data.getOrderInfo();
                if(orderDetailInfo != null){
                    nickname.setText(orderDetailInfo.getTeacherName());
                    courseName.setText(orderDetailInfo.getCourseName());
                    dateTime.setText(TextUtils.isEmpty(orderDetailInfo.getPayTime())?"":orderDetailInfo.getPayTime());
//                accountDuration.setText(orderDetailInfo.get);
                    orderPrice.setText(String.format(getResources().getString(R.string.balance_has,orderDetailInfo.getOrderPrice())) );
                    discount.setText(String.format(getResources().getString(R.string.balance_has,orderDetailInfo.getDiscountPrice())) );
                    payPrice.setText(getResources().getString(R.string.balance_has,orderDetailInfo.getPayPrice()));;
                    accountDuration.setText((orderDetailInfo.getPayCount()*2)+"");
                    contact.setOnClickListener(this);
                    buy.setOnClickListener(this);
                }
            }




    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.contact://联系老师

                break;
            case R.id.buy://再次购买

                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
