package com.share.learn.fragment.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.share.learn.R;
import com.share.learn.bean.CourseInfo;
import com.share.learn.bean.TeacherDetailBean;
import com.share.learn.bean.TeacherDetailInfo;
import com.share.learn.fragment.BaseFragment;
import com.share.learn.help.RequestHelp;
import com.share.learn.help.RequsetListener;
import com.share.learn.parse.TeacherDetailParse;
import com.share.learn.utils.ImageLoaderUtil;
import com.share.learn.utils.URLConstants;
import com.share.learn.utils.WaitLayer;
import com.share.learn.view.RoundImageView;
import com.share.learn.view.tab.ScrollingTabContainerView;
import com.share.learn.view.tab.TabsActionBar;
import com.share.learn.view.tab.TabsAdapter;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.ArrayList;
import java.util.Map;

/**
 * 教师详情界面
 */
public class TeacherDetailFragment extends BaseFragment implements RequsetListener{

    private String key;
    private ViewPager mViewPager;
    private ScrollingTabContainerView mTabContainerView;
    private TabsAdapter mTabsAdapter;

    private RoundImageView headPhoto;//头像
    private TextView  name;//姓名
    private TextView  content;//感想。

    private String teacherId = "";//id

    private TeacherDetailInfo teacherDetailInfo ;
    private ArrayList<CourseInfo> courseInfos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method STUB
        super.onCreate(savedInstanceState);
        // startReqTask(this);
        // mLoadHandler.sendEmptyMessageDelayed(Constant.NET_SUCCESS, 100);// 停止加载框
        Intent intent = mActivity.getIntent();
        if(intent != null && intent.hasExtra("teacherId")){
            teacherId = intent.getStringExtra("teacherId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_teacher_detail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        initTitle(view);
        initView(view);
        setLoadingDilog(WaitLayer.DialogType.NOT_NOMAL);
        requestTask();
    }

    private void initView(View view) {

        mViewPager = (ViewPager) view.findViewById(R.id.order_viewpager);
        mViewPager.setOffscreenPageLimit(0);
        mTabContainerView = (ScrollingTabContainerView) view.findViewById(R.id.tab_container);
        headPhoto = (RoundImageView)view.findViewById(R.id.head_photo);
        name = (TextView) view.findViewById(R.id.Name);
        content = (TextView) view.findViewById(R.id.content);

//        onInitTabConfig();
    }

    private void initTitle(View view){
        setTitleText("老师详情");
        setLeftHeadIcon(0);
    }

    private void onInitTabConfig() {
        TabsActionBar tabsActionBar = new TabsActionBar(getActivity(), mTabContainerView);
        mTabsAdapter = new TabsAdapter(getActivity(), mViewPager, tabsActionBar);
        Bundle bundle = new Bundle();
        if(teacherDetailInfo != null) bundle.putSerializable("teacherInfo",teacherDetailInfo);
        mTabsAdapter.addTab(tabsActionBar.newTab().setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.main_page, null))
                .setmTabbgDrawableId(R.drawable.login_tab), MainPageFragment.class, bundle);
        Bundle bundle1 = new Bundle();
        if(courseInfos != null) bundle1.putSerializable("courses",courseInfos);
        mTabsAdapter.addTab(tabsActionBar.newTab().setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.course, null))
                .setmTabbgDrawableId(R.drawable.login_tab), TeachCourseFragment.class, bundle1);
//        mTabsAdapter.addTab(tabsActionBar.newTab().setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.wait_pay, null))
//                .setmTabbgDrawableId(R.drawable.login_tab), WaitBuyFragment.class, null);
        Bundle bundle2 = new Bundle();
        bundle2.putString("teacherId",teacherId);
        bundle2.putString("teacherName",teacherDetailInfo.getNickName());
        bundle2.putString("teacherImg",teacherDetailInfo.getHeadImg());
        mTabsAdapter.addTab(tabsActionBar.newTab().setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.evaluate, null))
                .setmTabbgDrawableId(R.drawable.login_tab), AssetTeacherFragment.class, bundle2);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    /**
     * 请求 用户信息
     */
    @Override
    public void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = RequestHelp.getBaseParaMap("TeacherDetail");
        postParams.put("teacherId",teacherId);
        RequestParam param = new RequestParam();
//        param.setmParserClassName(TeacherDetailParse.class.getName());
        param.setmParserClassName(new TeacherDetailParse());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj)  {
        JsonParserBase<TeacherDetailBean> jsonParserBase = (JsonParserBase<TeacherDetailBean>)obj;
        if ((jsonParserBase != null)){
            courseInfos = jsonParserBase.getData().getCourseList();
            teacherDetailInfo = jsonParserBase.getData().getTeacherInfo();

            if(courseInfos.size()>0 && teacherDetailInfo != null){
               for (CourseInfo courseInfo:courseInfos){
                   courseInfo.setTeacherName(teacherDetailInfo.getNickName());
               }
            }
            name.setText(teacherDetailInfo.getNickName());
            content.setText(teacherDetailInfo.getSignature());
            ImageLoader.getInstance().displayImage(teacherDetailInfo.getHeadImg(),headPhoto, ImageLoaderUtil.mHallIconLoaderOptions);

            onInitTabConfig();
        }
    }
}
