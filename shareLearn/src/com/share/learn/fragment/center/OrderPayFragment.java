package com.share.learn.fragment.center;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.share.learn.R;
import com.share.learn.activity.center.OrderDetailActivity;
import com.share.learn.activity.teacher.ChatMsgActivity;
import com.share.learn.activity.teacher.EvaluateActivity;
import com.share.learn.adapter.OrderPayAdpter;
import com.share.learn.bean.ChatMsgEntity;
import com.share.learn.bean.OrderInfo;
import com.share.learn.bean.OrderListBean;
import com.share.learn.bean.UserInfo;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.help.PullRefreshStatus;
import com.share.learn.help.RequestHelp;
import com.share.learn.help.RequsetListener;
import com.share.learn.parse.OrderListBeanParse;
import com.share.learn.utils.BaseApplication;
import com.share.learn.utils.URLConstants;
import com.share.learn.utils.WaitLayer;
import com.share.learn.view.CustomListView;
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
public class OrderPayFragment extends BaseFragment implements RequsetListener,CustomListView.OnLoadMoreListener ,View.OnClickListener{

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


    public OrderPayFragment(int flag){
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
        setLoadingDilog(WaitLayer.DialogType.NOT_NOMAL);
        isPrepare = true;
        onLazyLoad();
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
                startActivity(intent);
            }
        });

        customListView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                status = PullRefreshStatus.PULL_REFRESH;
                pageNo = 1;
                requestData();

            }
        });
    }

    @Override
    public void onLoadMore() {
        status = PullRefreshStatus.LOAD_MORE;
        pageNo++;
        requestData();
    }

    private int requestType = 1;//1 列表, 2立即支付  3 完成订单
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

        }
//        @"cityName", [ @"pageNo",@"courseId",@"grade", @"cmd",@"123456",@"vcode",@"",@"fInviteCode",@"000000",@"deviceId",@"10",@"appversion",@"4",@"clientType",[[UserInfoManage shareInstance] token],@"accessToken", nil];

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
    @Override
    public void onClick(View v) {
        Intent intent = null;
        OrderInfo orderInfo = list.get((Integer)v.getTag());
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


                }
                break;
            case R.id.right_tv:
                if(flag == 1){//待支付(立即支付)

                }else if(flag == 2){//已支付(申请退款)

                }else if(flag==4){//已完成(立即评价)

                    intent = new Intent(mActivity, EvaluateActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("orderInfo",orderInfo);
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);

                }
                break;

        }
    }

}