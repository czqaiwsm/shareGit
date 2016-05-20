package com.share.teacher.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.share.teacher.R;
import com.share.teacher.activity.center.OrderDetailActivity;
import com.share.teacher.adapter.ChooseJoinorAdapter;
import com.share.teacher.adapter.ContactAdpter;
import com.share.teacher.adapter.DetailAdapter;
import com.share.teacher.bean.Contactor;
import com.share.teacher.bean.ContactorBean;
import com.share.teacher.bean.PayDetail;
import com.share.teacher.fragment.BaseFragment;
import com.share.teacher.help.PullRefreshStatus;
import com.share.teacher.help.RequestHelp;
import com.share.teacher.help.RequsetListener;
import com.share.teacher.parse.ContactorBeanParse;
import com.share.teacher.parse.PayDetailParse;
import com.share.teacher.utils.URLConstants;
import com.share.teacher.utils.WaitLayer;
import com.share.teacher.view.CustomListView;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @desc 钱包--》明细
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class DetailFragment extends BaseFragment implements RequsetListener,CustomListView.OnLoadMoreListener{

    private CustomListView customListView = null;
    private List<PayDetail> list = new ArrayList<PayDetail>();
    private DetailAdapter adapter;

    private TextView noData;

    private int pageCount = 0;//总页数
    private int pageNo = 1;
    private int pageSize = 10;//每页的数据量
    private PullRefreshStatus status = PullRefreshStatus.NORMAL;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title_list,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initTitle();
    }

    private void initTitle(){
        setTitleText(R.string.detail);
        setLeftHeadIcon(0);

        setLoadingDilog(WaitLayer.DialogType.NOT_NOMAL);
        requestTask();
    }


    private void initView(View view){

        customListView = (CustomListView)view.findViewById(R.id.callListView);
        noData = (TextView)view.findViewById(R.id.noData);

        customListView.setCanLoadMore(false);
        customListView.setCanRefresh(true);
        adapter = new DetailAdapter(mActivity, list);
        customListView.setAdapter(adapter);

        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(mActivity,OrderDetailActivity.class);
//                startActivity(intent);
            }
        });

        customListView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                status = PullRefreshStatus.PULL_REFRESH;
                pageNo = 1;
                requestData(0);
            }
        });

    }


    @Override
    public void onLoadMore() {
        status = PullRefreshStatus.LOAD_MORE;
        pageNo++;
        requestData(0);
    }


    @Override
    protected void requestData(int requestType) {

        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = RequestHelp.getBaseParaMap("PayDetailList");
        RequestParam param = new RequestParam();
        param.setmParserClassName(new PayDetailParse());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        customListView.onRefreshComplete();
        customListView.onLoadMoreComplete();
        JsonParserBase<ArrayList<PayDetail>> jsonParserBase = (JsonParserBase<ArrayList<PayDetail>>)obj;
        if(jsonParserBase != null){

            ArrayList<PayDetail> teacherInfos = jsonParserBase.getData();

            list.clear();
            list.addAll(teacherInfos);
            if(list.size()==0){
                noData.setVisibility(View.VISIBLE);
                customListView.setVisibility(View.GONE);
            }else {
                noData.setVisibility(View.GONE);
                customListView.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged();
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

}