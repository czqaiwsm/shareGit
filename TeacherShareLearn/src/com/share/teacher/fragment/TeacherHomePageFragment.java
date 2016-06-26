package com.share.teacher.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.gson.reflect.TypeToken;
import com.share.teacher.R;
import com.share.teacher.activity.center.OrderActivity;
import com.share.teacher.activity.center.WalletActivity;
import com.share.teacher.activity.home.CourseSettingActivity;
import com.share.teacher.activity.home.SearchActivity;
import com.share.teacher.activity.home.TeaCourseSettingActivity;
import com.share.teacher.activity.home.TeacherCertifyActivity;
import com.share.teacher.activity.teacher.CourseListActivity;
import com.share.teacher.adapter.GuideViewPagerAdapter;
import com.share.teacher.bean.BannerImgInfo;
import com.share.teacher.bean.CertifyStatus;
import com.share.teacher.bean.HomeInfo;
import com.share.teacher.bean.HomePagerBanner;
import com.share.teacher.fragment.home.ScheduleSettingFragment;
import com.share.teacher.fragment.home.TeacherCourseSettingFragment;
import com.share.teacher.help.RequestHelp;
import com.share.teacher.help.RequsetListener;
import com.share.teacher.parse.HomePageBannerParse;
import com.share.teacher.utils.AlertDialogUtils;
import com.share.teacher.utils.AppLog;
import com.share.teacher.utils.DensityUtils;
import com.share.teacher.utils.URLConstants;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * @desc 首页
 * @creator caozhiqing
 * @data 2016/3/10
 * 13637055938    111111
 */
public class TeacherHomePageFragment extends BaseFragment implements View.OnClickListener,RequsetListener,IParser {

    @Bind(R.id.courseSettingImg)
    ImageView courseSettingImg;
    @Bind(R.id.certifySettingImg)
    ImageView certifySettingImg;
    @Bind(R.id.myOrder)
    ImageView myOrder;
    @Bind(R.id.myBalance)
    ImageView myBalance;
//    private LinearLayout homeSearch;
//    private EditText head_seach_txt;

    public static HomeInfo homeInfo;
    private View converView;
    private ViewPager viewpager = null;
    private GuideViewPagerAdapter guideAdapter = null;
    private int ids[] = {R.drawable.aot, R.drawable.aot, R.drawable.aot};

    private ArrayList<BannerImgInfo> bannerImgInfos = new ArrayList<BannerImgInfo>();

    private int courseId = 100;//课程名称

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_teacher_home, null);
        converView = view;
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
//        homeSearch = (LinearLayout) view.findViewById(R.id.homeSearch);
//        head_seach_txt = (EditText) view.findViewById(R.id.head_seach_txt);
        viewpager = (ViewPager) view.findViewById(R.id.id_guide_viewpager);

        courseSettingImg.setOnClickListener(this);
        certifySettingImg.setOnClickListener(this);
        myOrder.setOnClickListener(this);
        myBalance.setOnClickListener(this);

//        homeSearch.setOnClickListener(this);
//        head_seach_txt.setOnClickListener(this);
//        initGuidBanner(view);
        requestTask(1);
        requestData(2);
    }

    /**
     * 初始化轮播图
     */
    private void initGuidBanner(View view) {
        if (view == null) {
            return;
        }

        if (bannerImgInfos != null && bannerImgInfos.size() > 0) {
            guideAdapter = new GuideViewPagerAdapter(bannerImgInfos, view, mActivity);
        } else {
            guideAdapter = new GuideViewPagerAdapter(ids, view, mActivity);
        }

        guideAdapter.setDotAlignBottom((int) DensityUtils.px2dp(mActivity, 10f));
        guideAdapter.setAutoPlay(viewpager, true);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
//                int pos = position % ids.length;
                guideAdapter.moveCursorTo(position);// 点的移动
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewpager.setAdapter(guideAdapter);
        if (bannerImgInfos != null && bannerImgInfos.size() > 1) {
            viewpager.setCurrentItem(bannerImgInfos.size() * 30);
        }
    }

    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        RequestParam param = new RequestParam();
        Map postParams = null;
        switch (requestType){
            case 1:
                postParams = RequestHelp.getBaseParaMap("Home");
                postParams.put("cityName", "合肥市");
                param.setmParserClassName(new HomePageBannerParse());
                break;
            case 2:
                postParams = RequestHelp.getBaseParaMap("QueryCertifi") ;
                param.setmParserClassName(this);
                break;
        }
//        param.setmParserClassName(HomePageBannerParse.class.getName());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestType), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        switch (requestType){

            case 1:
                JsonParserBase<HomeInfo> jsonParserBase = (JsonParserBase<HomeInfo>) obj;
                if (jsonParserBase != null) {
                    homeInfo = jsonParserBase.getData();
                    ArrayList<HomePagerBanner> pagerBanners = homeInfo.getTopAdList();
                    BannerImgInfo imgInfo = null;
                    bannerImgInfos.clear();
                    if (pagerBanners != null && pagerBanners.size() > 0) {
                        for (HomePagerBanner banner : pagerBanners) {
                            imgInfo = new BannerImgInfo();
                            imgInfo.setId(banner.getId());
                            imgInfo.setUrl(banner.getIconPath());
                            imgInfo.setRedirect(banner.getRedirect());
                            imgInfo.setTitle(imgInfo.getTitle());
                            bannerImgInfos.add(imgInfo);
                        }
                        initGuidBanner(converView);
                    }
                }

                break;
            case 2:
                JsonParserBase<CertifyStatus> jsonParserBase1 = (JsonParserBase<CertifyStatus>)obj;
                CertifyStatus certifyStatus = jsonParserBase1.getData();
//        idcardStatus;//	身份认证状态	是	Int	0.未认证 1.认证审核中 2.认证通过 3.认证不通过
//        auditStatus;//	学历认证状态	是	Int	0.未认证 1.认证审核中 2.认证通过 3.认证不通过
                String satus = certifyStatus.getIdcardStatus();
                if("0".equalsIgnoreCase(satus) || "0".equalsIgnoreCase(satus)){
                    AlertDialogUtils.displaySingle(mActivity, "认证提示", "您还未认证,请及时认证!", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toClassActivity(TeacherHomePageFragment.this, TeacherCertifyActivity.class.getName());
                        }
                    });
                }
                break;



        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        AppLog.Loge("hidden:" + hidden + "  isHiden:" + isHidden());
//       if(!hidden){//可见时 调接口
//        requestTask();
//       }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        AppLog.Loge("setUserVisibleHint:" + isVisibleToUser + "");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.homeSearch:
                toClassActivity(this, SearchActivity.class.getName());
                return;
//            case R.id.head_seach_txt:
//                homeSearch.performClick();
            case R.id.courseSettingImg:
                 toClassActivity(this, CourseListActivity.class.getName());
                break;
            case R.id.certifySettingImg:
                toClassActivity(this, TeacherCertifyActivity.class.getName());

                break;
            case R.id.myOrder:
                toClassActivity(this, OrderActivity.class.getName());
                break;
            case R.id.myBalance:
                toClassActivity(this,WalletActivity.class.getName());
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }

    @Override
    public Object fromJson(String json) {
        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());
        if(URLConstants.SUCCESS_CODE.equals(result.getRespCode())){
            return ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<CertifyStatus>>() {
            }.getType());
        }
        return result;
    }
}