package com.share.learn.fragment.center;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import com.alipay.sdk.pay.demo.AlipayUtil;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.share.learn.R;
import com.share.learn.activity.ChooseCityActivity;
import com.share.learn.activity.center.DetailActivity;
import com.share.learn.activity.center.RechargeActivity;
import com.share.learn.bean.CourseInfo;
import com.share.learn.bean.PayCourseInfo;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.help.RequestHelp;
import com.share.learn.help.RequsetListener;
import com.share.learn.parse.HomePageBannerParse;
import com.share.learn.parse.PayCourseInfoParse;
import com.share.learn.service.LocationUitl;
import com.share.learn.utils.AppLog;
import com.share.learn.utils.BaseApplication;
import com.share.learn.utils.URLConstants;
import com.share.learn.view.CustomListView;
import com.volley.req.UserInfo;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.net.RequestParamSub;
import com.volley.req.parser.JsonParserBase;

import java.util.ArrayList;
import java.util.Map;

/**
 *钱包
 * @author czq
 * @time 2015年9月28日上午11:44:26
 *
 */
public class PurchaseCourseFragment extends BaseFragment implements OnClickListener ,LocationUitl.LocationListener,RequsetListener{



    private TextView couseName;
    private TextView price;
    private TextView account;//购买次数
    private TextView favourable;//优惠金额
    private TextView truePay;
    private TextView login_pay;
    private EditText address;
    private RelativeLayout buy_layout;


    private CourseInfo courseInfo = null;

    private boolean isNeedLocaiton = true;//是否需要定位
    private final int MODIFY_INFO = 111;//个人信息
    private int priceMoney = 0;
    private int trueMoey = 0;
    private int orderPay = 0;


    private CoursePopupWindow coursePopupWindow ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = mActivity.getIntent();
        if(intent != null && intent.hasExtra("bundle")){
            Bundle bundle = intent.getBundleExtra("bundle");
            if(bundle != null ){
                courseInfo = (CourseInfo) bundle.getSerializable("course");
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_course, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleView();
        initView(view);
        iniData();
    }

    private void initTitleView() {
        setLeftHeadIcon(0);
        setTitleText(R.string.purchase);
    }

    private void initView(final View v) {

        couseName = (TextView)v.findViewById(R.id.couseName);
        price = (TextView)v.findViewById(R.id.price);
        account = (TextView)v.findViewById(R.id.account);
        favourable = (TextView)v.findViewById(R.id.favourable);
        truePay = (TextView)v.findViewById(R.id.truePay);
        login_pay = (TextView)v.findViewById(R.id.login_text);
        address = (EditText)v.findViewById(R.id.address);



        buy_layout = (RelativeLayout)v.findViewById(R.id.buy_layout);


        login_pay.setOnClickListener(this);
        buy_layout.setOnClickListener(this);
    }


    private void iniData(){
        if(courseInfo == null) return;
        orderPay = trueMoey = priceMoney = Integer.valueOf(courseInfo.getPrice());
        account.setTag(1);

        couseName.setText(courseInfo.getCourseName());
        price.setText(String.format(getResources().getString(R.string.price,courseInfo.getPrice())));

        address.setText(BaseApplication.getInstance().location[0]);

        favourable.setText(String.format(getResources().getString(R.string.balance_has,0)));
        truePay.setText(String.format(getResources().getString(R.string.balance_has,courseInfo.getPrice())));

    }


    private int getDiscontPrice(int buyCount){
        int  cheapMone = 0;
        if (2<=buyCount && buyCount<10) {
            cheapMone = 20;
        }
        else if (10<=buyCount &&buyCount<30)
        {
            cheapMone = 50;
        }
        else if (30<=buyCount &&buyCount<50)
        {
            cheapMone = 150;
        }
        else if (50<=buyCount &&buyCount<100)
        {
            cheapMone = 300;
        }
        else
        {
            cheapMone = 800;
        }

        return cheapMone;
    }

    private Intent intent ;
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.buy_layout:// 次数
                if ((coursePopupWindow == null)){
                    coursePopupWindow = new CoursePopupWindow(mActivity,v,this);
                }
                coursePopupWindow.showAtLocation(buy_layout, Gravity.BOTTOM, 0, 0);
            break;
            case R.id.login_text:// 立即支付
            requestTask();
            break;
            case R.id.withDraw_layout:// 提现
            intent = new Intent(mActivity, ChooseCityActivity.class);
            startActivityForResult(intent,MODIFY_INFO);
            break;
        }

    }


    /**
     * 请求 用户信息
     */
    @Override
    public void requestData() {
        // TODO Auto-generated method stub
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);

        Map postParams = RequestHelp.getBaseParaMap("PayCourseOrder");

//        payType	支付类型 1-支付宝，8-账户余额
//        studentName	学生姓名
//        teacherId	老师ID
//        teacherName	老师姓名
//        courseId	课程ID
//        orderPrice	订单金额
//        payPrice	实际支付金额
//        payCount	购买次数
//        remark	详细地址

        postParams.put("payType", "1");
        postParams.put("studentName", BaseApplication.getInstance().userInfo.getNickName());
        postParams.put("teacherId", courseInfo.getTeacherId());
        postParams.put("teacherName", courseInfo.getTeacherName());
        postParams.put("courseId", courseInfo.getCourseId());
        postParams.put("orderPrice", orderPay);
        postParams.put("payPrice", trueMoey);
        postParams.put("payCount", account.getTag().toString());
        postParams.put("remark", address.getText().toString());

        RequestParam param = new RequestParam();
//        param.setmParserClassName(HomePageBannerParse.class.getName());
        param.setmParserClassName(new PayCourseInfoParse());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
    }


    @Override
    public void handleRspSuccess(Object obj) {
        PayCourseInfo payCourseInfo =( (JsonParserBase<PayCourseInfo>)obj).getData();
        if(payCourseInfo != null){

            //todo 弹出对话框,选择支付方式
            AlipayUtil alipayUtil = new AlipayUtil(mActivity,payCourseInfo.getOrderCode(),"test",payCourseInfo.getCourseName(),payCourseInfo.getPayPrice());

            alipayUtil.alipay();
        }
    }

    @Override
    public void locatinNotify(BDLocation location) {
          if(isNeedLocaiton && location != null){
              address.setText(location.getStreet());
          }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocationUitl.removeListener(this);
    }

    public class CoursePopupWindow extends PopupWindow {

        private View mPopView;
        private CustomListView customListView;
        private ArrayList<String> datas = new ArrayList<String>();

        public CoursePopupWindow(Activity context, View view, OnClickListener itemsOnClick) {
            super(view);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mPopView = inflater.inflate(R.layout.popup_choose, null);
            customListView = (CustomListView) mPopView.findViewById(R.id.callListView);
            this.setContentView(mPopView);
            this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
            this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            this.setFocusable(true);
            this.setAnimationStyle(R.style.AnimBottom);
            final ColorDrawable dw = new ColorDrawable(0x66000000);
            this.setBackgroundDrawable(dw);

            customListView.setCanLoadMore(false);
            customListView.setCanRefresh(false);
            for(int i=0;i<20;i++){
                datas.add(i+"次");
            }

            customListView.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return datas.size();
                }

                @Override
                public Object getItem(int i) {
                    return datas.get(i);
                }

                @Override
                public long getItemId(int i) {
                    return i;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    TextView textView = null;
                    if(view == null){
                        view = LinearLayout.inflate(mActivity,R.layout.pop_cuorse,null);
                        textView = (TextView) view.findViewById(R.id.times);
                        view.setTag(textView);
                    }else {
                        textView =(TextView) view.getTag();
                    }
                    textView.setText(datas.get(i));
                    return view;
                }
            });

            mPopView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    account.setText(datas.get(i));
                    account.setTag(i+1);

                    orderPay = priceMoney * (i+1);
                    trueMoey = orderPay - getDiscontPrice(i+1);
                    favourable.setText(String.format(getResources().getString(R.string.balance_has,getDiscontPrice(i+1))));
                    truePay.setText(String.format(getResources().getString(R.string.balance_has,trueMoey)));
                    dismiss();
                }
            });

        }

    }

}
