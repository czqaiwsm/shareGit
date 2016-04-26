package com.share.learn.fragment.center;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.share.learn.R;
import com.share.learn.adapter.DetailAdapter;
import com.share.learn.adapter.SystemMsgAdapter;
import com.share.learn.adapter.TeacherAssetAdapter;
import com.share.learn.bean.SystemMsg;
import com.share.learn.bean.CommentInfo;
import com.share.learn.bean.SystemMsg;
import com.share.learn.bean.SystemMsgListBean;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.help.PullRefreshStatus;
import com.share.learn.help.RequestHelp;
import com.share.learn.help.RequsetListener;
import com.share.learn.parse.BaseParse;
import com.share.learn.parse.CommentParse;
import com.share.learn.parse.SystemMsgParse;
import com.share.learn.utils.BaseApplication;
import com.share.learn.utils.URLConstants;
import com.share.learn.view.CustomListView;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @desc 系统消息
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class SystemMsgFragment extends BaseFragment implements RequsetListener ,CustomListView.OnLoadMoreListener{

    private CustomListView customListView = null;
    private List<SystemMsg> list = new ArrayList<SystemMsg>();
    private SystemMsgAdapter adapter;
    private TextView noData;


    private int pageNo = 1;
    private int pageCount = 0;//总页数
    private int pageSize = 10;//每页的数据量
    private PullRefreshStatus status = PullRefreshStatus.NORMAL;
    private int requestType = 1;//请求信息 1  \ 2删除信息
    private String delMsgId = "";

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
        requestTask();
    }

    private void initTitle(){
        setTitleText(R.string.sys_msg);
        setLeftHeadIcon(0);
    }


    private void initView(View view){

        customListView = (CustomListView)view.findViewById(R.id.callListView);
        noData = (TextView)view.findViewById(R.id.noData);

        customListView.setCanRefresh(true);
        adapter = new SystemMsgAdapter(mActivity, list,SystemMsgFragment.this);
        customListView.setAdapter(adapter);

        customListView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                status = PullRefreshStatus.PULL_REFRESH;
                pageNo = 1;
                requestType = 1;
                requestData();
            }
        });


    }

    @Override
    public void onLoadMore() {
        status = PullRefreshStatus.LOAD_MORE;
        pageNo++;
        requestType = 1;
        requestData();
    }

    public void delMsgReq(String id){
        if(!TextUtils.isEmpty(id)){
            delMsgId = id;
            requestType = 2;
            requestTask();
        }

    }

    private void delMsgSucc(){
        Iterator<SystemMsg> msgIterator = list.iterator();
        while (msgIterator.hasNext()){
            SystemMsg systemMsg = msgIterator.next();
           if (systemMsg.getId().equalsIgnoreCase(delMsgId)){
               msgIterator.remove();
               adapter.notifyDataSetChanged();
               return;
           }
        }
    }

    @Override
    protected void requestData() {
        HttpURL url = new HttpURL();
        RequestParam param = new RequestParam();
        Map postParams  = null;
        switch (requestType){
           case 1:
               postParams = RequestHelp.getBaseParaMap("SysMsgList");
//        param.setmParserClassName(SystemMsgParse.class.getName());
               postParams.put("pageNo",pageNo);
               param.setmParserClassName(new SystemMsgParse());
               break;
           case 2:
               postParams = RequestHelp.getBaseParaMap("DelSysMsg");
//        param.setmParserClassName(SystemMsgParse.class.getName());
               postParams.put("msgId",delMsgId);
               param.setmParserClassName(new BaseParse());
               break;
       }


        url.setmBaseUrl(URLConstants.BASE_URL);
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        switch (requestType){
            case 1:
                JsonParserBase<SystemMsgListBean> jsonParserBase = (JsonParserBase<SystemMsgListBean>)obj;
                SystemMsgListBean systemMsgs = jsonParserBase.getData();
                if(systemMsgs != null){
                    pageCount = systemMsgs.getTotalPages();
                    pageSize = systemMsgs.getPageSize();
                    ArrayList<SystemMsg> teacherInfos = systemMsgs.getElements();

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
            case 2:
                delMsgSucc();
                toasetUtil.showSuccess("信息删除成功");
                break;
        }

    }

    @Override
    protected void failRespone() {
        super.failRespone();
        if(requestType == 1){
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
    private void refresh(ArrayList<SystemMsg> teacherInfos){
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

}