package com.share.learn.fragment.center;

import android.app.Activity;
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
import com.alipay.sdk.pay.demo.PayCallBack;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.share.learn.R;
import com.share.learn.activity.teacher.ChatMsgActivity;
import com.share.learn.activity.teacher.EvaluateActivity;
import com.share.learn.bean.*;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.help.RequestHelp;
import com.share.learn.help.RequsetListener;
import com.share.learn.parse.BaseParse;
import com.share.learn.parse.OrderDetailBeanParse;
import com.share.learn.parse.OrderListBeanParse;
import com.share.learn.utils.AppLog;
import com.share.learn.utils.BaseApplication;
import com.share.learn.utils.URLConstants;
import com.share.learn.utils.WaitLayer;
import com.share.learn.view.FlowScrollView;
import com.share.learn.view.PayPopupwidow;
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
public class OrederDetailFragment extends BaseFragment implements RequsetListener,View.OnClickListener,PayCallBack {


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
    private String orderStatus = "";
    private OrderDetailInfo orderDetailInfo ;

    private int flag = 0 ;//1 待支付\2 已支付\ 3 已取消\ 4 已完成
    private PayPopupwidow payPopupwidow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = mActivity.getIntent();
        if(intent != null ){
            orderId = intent.getStringExtra("orderId");
            flag = intent.getIntExtra("flag",0);
            orderStatus = intent.getStringExtra("orderStatus");
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
        payPopupwidow = new PayPopupwidow(mActivity,null,this);
        setStatue();
        requestTask(0);
    }

    private void initTitleView() {
        setLeftHeadIcon(0);
        setTitleText(R.string.order_detail);
    }



    /**
     * 请求 用户信息
     */
    @Override
    public void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = null;
        RequestParam param = new RequestParam();
        switch (requestType){
            case 0://详情
                postParams = RequestHelp.getBaseParaMap("OrderDetail");
                postParams.put("orderId",orderId);
                param.setmParserClassName(new OrderDetailBeanParse());

                break;
            case 3://确认订单
        postParams = RequestHelp.getBaseParaMap("ConfirmOrder");
        postParams.put("orderId",orderId);
        postParams.put("teacherId",orderDetailInfo.getTeacherId());
        param.setmParserClassName(new OrderListBeanParse());
        break;
        }

        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestType), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj)  {
        
        switch (requestType){
            case 0:
                 orderDetailInfo = ((JsonParserBase<OrderDetailInfo>)obj).getData();
                if(orderDetailInfo != null){
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

                break;
            case 3:
                mActivity.setResult(Activity.RESULT_OK);
                mActivity.finish();
                break;
        }
       
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.contact:
                if(flag == 1){//待支付(联系老师)
                    intent = new Intent(mActivity, ChatMsgActivity.class);
                    UserInfo userInfo = BaseApplication.getUserInfo();
                    intent.putExtra("teacherId",orderDetailInfo.getTeacherId());

                    ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
                    chatMsgEntity.setDirection("2");
                    chatMsgEntity.setReceiverId(orderDetailInfo.getTeacherId());
                    chatMsgEntity.setSenderId(userInfo.getId());

                    chatMsgEntity.setTeacherName(orderDetailInfo.getTeacherName());
                    chatMsgEntity.setTeacherImg(orderDetailInfo.getTeacherImg());
                    intent.putExtra("bundle",chatMsgEntity);
                    startActivity(intent);
                }else if(flag == 2){//已支付(完成订单)
                    requestTask(3);
                }
                break;
            case R.id.buy:
                if(flag == 1){//待支付(立即支付)
                    if(orderDetailInfo != null){
                        PayInfo payInfo = new PayInfo(orderId,orderDetailInfo.getPayPrice()+"",orderDetailInfo.getCourseName(),orderDetailInfo.getTeacherName());
                        payPopupwidow.payPopShow(v,payInfo);
                    }

                }else if(flag == 2){//已支付(申请退款)

                }else if(flag==4){//已完成(立即评价)
                    intent = new Intent(mActivity, EvaluateActivity.class);
                    Bundle bundle = new Bundle();
                    OrderInfo orderInfo = new OrderInfo();
                    orderInfo.setOrderId(orderId);
                    orderInfo.setTeacherName(orderDetailInfo.getTeacherName());
                    orderInfo.setTeacherImg(orderDetailInfo.getTeacherImg());
                    orderInfo.setCourseName(orderDetailInfo.getCourseName());
                    orderInfo.setEvaluateStatus(orderDetailInfo.getEvaluateStatus());
                    orderInfo.setGrade(orderDetailInfo.getGrade());
                    orderInfo.setPayPrice(orderDetailInfo.getPayPrice()+"");
                    orderInfo.setTeacherId(orderDetailInfo.getTeacherId());
                    bundle.putSerializable("orderInfo",orderInfo);
                    intent.putExtra("bundle",bundle);
                    startActivityForResult(intent,200);
                }
                break;

        }
    }

    @Override
    public void paySucc() {
//      handler.sendEmptyMessage(OrderFragment.PAY_SUCC);
        mActivity.setResult(Activity.RESULT_OK);
        mActivity.finish();
    }
    

    @Override
    public void payFail() {
        toasetUtil.showInfo("支付失败");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    
    private void setStatue(){
        switch (flag){
            case 1:
                contact.setVisibility(View.VISIBLE);
               buy.setVisibility(View.VISIBLE);
                contact.setText(mActivity.getResources().getString(R.string.contact_tea));
               buy.setText(mActivity.getResources().getString(R.string.pay_now));
                break;
            case 2:
               buy.setVisibility(View.VISIBLE);
                contact.setVisibility(View.VISIBLE);
               buy.setText(mActivity.getResources().getString(R.string.feed_money));
                contact.setText("完成订单");
                break;
            case 3:
                buy.setVisibility(View.GONE);
                contact.setVisibility(View.GONE);
                break;
            case 4:
                contact.setVisibility(View.GONE);
               buy.setText(mActivity.getResources().getString(R.string.assert_now));

                if(TextUtils.equals(orderStatus,"0")){
                   buy.setVisibility(View.VISIBLE);
                }else {
                   buy.setVisibility(View.GONE);
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==Activity.RESULT_OK && requestCode==200){
           buy.setVisibility(View.GONE);
        }
    }
}
