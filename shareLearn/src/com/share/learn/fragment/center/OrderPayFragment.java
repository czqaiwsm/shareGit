package com.share.learn.fragment.center;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.alipay.sdk.pay.demo.PayCallBack;
import com.share.learn.R;
import com.share.learn.activity.center.OrderDetailActivity;
import com.share.learn.activity.teacher.ChatMsgActivity;
import com.share.learn.activity.teacher.EvaluateActivity;
import com.share.learn.adapter.OrderPayAdpter;
import com.share.learn.bean.*;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.help.PullRefreshStatus;
import com.share.learn.help.RequestHelp;
import com.share.learn.help.RequsetListener;
import com.share.learn.parse.OrderListBeanParse;
import com.share.learn.utils.AlertDialogUtils;
import com.share.learn.utils.BaseApplication;
import com.share.learn.utils.URLConstants;
import com.share.learn.utils.WaitLayer;
import com.share.learn.view.CustomListView;
import com.share.learn.view.PayPopupwidow;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @desc 订单
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class OrderPayFragment extends BaseFragment implements RequsetListener,CustomListView.OnLoadMoreListener ,View.OnClickListener,PayCallBack{

    private CustomListView customListView = null;
    private List<OrderInfo> list = new ArrayList<OrderInfo>();
    private OrderPayAdpter adapter;
    private TextView noData ;
    private int flag = 0 ;//1 待支付\2 已支付\ 3 已取消\ 4 已完成
    private boolean isPrepare = false;
    private boolean isVisible = false;

    private int pageCount = 0;//总页数
    private int pageNo = 1;
    private int pageSize = 10;//每页的数据量
    private PullRefreshStatus status = PullRefreshStatus.NORMAL;

    private  PayPopupwidow  payPopupwidow;
    private Handler handler;


    public OrderPayFragment(int flag,Handler handler){
        this.handler = handler;
        this.flag = flag;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        isPrepare = true;
        setLoadingDilog(WaitLayer.DialogType.NOT_NOMAL);
        onLazyLoad();
        payPopupwidow = new PayPopupwidow(mActivity,null,this);
    }

    private void onLazyLoad(){

        if(!isPrepare || !isVisible){
            return;
        }
         requestTask(1);

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isVisible = true;
        }else {
            isVisible = false;
            if(isPrepare){
                dismissLoadingDilog();
            }
        }

        onLazyLoad();
    }

    private void initView(View view){

        customListView = (CustomListView)view.findViewById(R.id.callListView);
        noData = (TextView)view.findViewById(R.id.noData);
        customListView.setCanRefresh(true);
        adapter = new OrderPayAdpter(mActivity, list,flag,this);
        customListView.setAdapter(adapter);
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mActivity,OrderDetailActivity.class);
                intent.putExtra("orderId",list.get(i-1).getOrderId());
                intent.putExtra("flag",flag);
                intent.putExtra("orderStatus",list.get(i-1).getEvaluateStatus());
                startActivityForResult(intent,flag);
            }
        });

        customListView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                status = PullRefreshStatus.PULL_REFRESH;
                pageNo = 1;
                requestData(1);

            }
        });
    }

    @Override
    public void onLoadMore() {
        status = PullRefreshStatus.LOAD_MORE;
        pageNo++;
        requestData(1);
    }

    private String orderId = "";
    @Override
    protected void requestData(int requestType) {

        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = null;

        RequestParam param = new RequestParam();
//        param.setmParserClassName(OrderListBeanParse.class.getName());
        switch (requestType){
            case 1:
                postParams = RequestHelp.getBaseParaMap("OrderList");
                postParams.put("status",flag);
                postParams.put("vcode", "123456");
                postParams.put("pageNo",pageNo);
                param.setmParserClassName(new OrderListBeanParse());
                break;
            case 3:
                postParams = RequestHelp.getBaseParaMap("ConfirmOrder");
                orderId = orderInfo.getOrderId();
                postParams.put("orderId",orderInfo.getOrderId());
                postParams.put("teacherId",orderInfo.getTeacherId());
                param.setmParserClassName(new OrderListBeanParse());
                break;
            case 4:
                postParams = RequestHelp.getBaseParaMap("Refund");
                orderId = orderInfo.getOrderId();
                postParams.put("orderId",orderInfo.getOrderId());
                param.setmParserClassName(new OrderListBeanParse());
                break;

        }
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestType), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        switch (requestType){
            case 1:
                JsonParserBase<OrderListBean> jsonParserBase = (JsonParserBase<OrderListBean>)obj;
                OrderListBean chooseTeachBean = jsonParserBase.getData();
                if(chooseTeachBean != null){
                    pageCount = chooseTeachBean.getTotalPages();
                    pageSize = chooseTeachBean.getPageSize();
                    ArrayList<OrderInfo> teacherInfos = chooseTeachBean.getElements();
                    switch (status){
                        case NORMAL:
                            refresh(teacherInfos);
                            break;
                        case PULL_REFRESH:
                            refresh(teacherInfos);
                            customListView.onRefreshComplete();
                            break;
                        case LOAD_MORE:
                            if(teacherInfos != null && teacherInfos.size()>0){//有数据
                                list.addAll(teacherInfos);
                                customListView.onLoadMoreComplete(CustomListView.ENDINT_MANUAL_LOAD_DONE);
                                adapter.notifyDataSetInvalidated();
                            }else {
                                pageNo--;
                                customListView.onLoadMoreComplete(CustomListView.ENDINT_AUTO_LOAD_NO_DATA);
                            }
                            break;
                        default:break;
                    }

                }
                break;

            case 3:
                    handler.sendEmptyMessage(OrderFragment.CONFIRM_ORDER)    ;
            case 4://申请退款成功

                    for(OrderInfo orderInfo:list){
                        if(orderInfo.getOrderId().equalsIgnoreCase(orderId)){

                            orderInfo.setRefundtatus("1");
                            adapter.notifyDataSetChanged();
                            break;
                        }
                    }

                break;

        }

    }

    @Override
    protected void failRespone() {
        super.failRespone();
        switch (status) {
            case PULL_REFRESH:
                customListView.onRefreshComplete();
                break;
            case LOAD_MORE:
                pageNo--;
                customListView.onLoadMoreComplete(CustomListView.ENDINT_MANUAL_LOAD_DONE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void errorRespone() {
        super.errorRespone();
        failRespone();
    }

    /**
     * 页数为1时使用
     * @param teacherInfos
     */
    private void refresh(ArrayList<OrderInfo> teacherInfos){
        if(teacherInfos==null || teacherInfos.size()==0){//显示无数据
            if(list.size()==0){
                noData.setVisibility(View.VISIBLE);
            }
        }else {
            noData.setVisibility(View.GONE);
            list.clear();
            list.addAll(teacherInfos);
            if(teacherInfos.size()>=pageSize){//有足够的数据,可以下拉刷新
                customListView.setCanLoadMore(true);
                customListView.setOnLoadListener(this);
            }else {
                customListView.setCanLoadMore(false);
            }
            adapter.notifyDataSetChanged();

        }
    }
    OrderInfo orderInfo = null;
    @Override
    public void onClick(final View v) {
        Intent intent = null;
         orderInfo= list.get((Integer)v.getTag());
        switch (v.getId()){
            case R.id.left_tv:
                if(flag == 1){//待支付(联系老师)
                    intent = new Intent(mActivity, ChatMsgActivity.class);
                    UserInfo userInfo = BaseApplication.getInstance().userInfo;
                    intent.putExtra("teacherId",orderInfo.getTeacherId());

                    ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
                    chatMsgEntity.setDirection("2");
                    chatMsgEntity.setReceiverId(orderInfo.getTeacherId());
                    chatMsgEntity.setSenderId(userInfo.getId());

                    chatMsgEntity.setTeacherName(orderInfo.getTeacherName());
                    chatMsgEntity.setTeacherImg(orderInfo.getTeacherImg());
                    intent.putExtra("bundle",chatMsgEntity);
                    startActivity(intent);
                }else if(flag == 2){//已支付(完成订单)
                    AlertDialogUtils.displayMyAlertChoice(mActivity, "提示", "您确定完成订单?", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            orderInfo= list.get((Integer)v.getTag());
                            requestTask(3);
                        }
                    },null);


                }
                break;
            case R.id.right_tv:
                if(flag == 1){//待支付(立即支付)
                    PayInfo payInfo = new PayInfo(orderInfo.getOrderId(),orderInfo.getPayPrice(),orderInfo.getCourseName(),orderInfo.getTeacherName());
                    payPopupwidow.payPopShow(v,payInfo);
                }else if(flag == 2){//已支付(申请退款)

                    AlertDialogUtils.displayMyAlertChoice(mActivity, "提示", "您正在申请退款", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            orderInfo= list.get((Integer)v.getTag());
                            requestTask(4);
                        }
                    },null);

                }else if(flag==4){//已完成(立即评价)
                    intent = new Intent(mActivity, EvaluateActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("orderInfo",orderInfo);
                    intent.putExtra("bundle",bundle);
                    startActivityForResult(intent,200);
                }
                break;

        }
    }

    @Override
    public void paySucc() {
        handler.sendEmptyMessage(OrderFragment.PAY_SUCC);
    }

    @Override
    public void payFail() {
      toasetUtil.showInfo("支付失败");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case 200:
                    requestTask(1);
                    break;
                case 1:
                    paySucc();
                    break;
                case 2://完成订单
                    handler.sendEmptyMessage(OrderFragment.CONFIRM_ORDER)    ;
                    break;
                case 4://立即评价
                    requestTask(1);
                    break;

            }
        }
    }
}