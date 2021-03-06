package com.share.learn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.share.learn.R;
import com.share.learn.activity.center.WalletActivity;
import com.share.learn.activity.home.SearchActivity;
import com.share.learn.activity.home.TeacherCertifyActivity;
import com.share.learn.activity.teacher.ChooseTeacherActivity;
import com.share.learn.adapter.GuideViewPagerAdapter;
import com.share.learn.bean.BannerImgInfo;
import com.share.learn.bean.HomeInfo;
import com.share.learn.bean.HomePagerBanner;
import com.share.learn.help.RequestHelp;
import com.share.learn.help.RequsetListener;
import com.share.learn.parse.HomePageBannerParse;
import com.share.learn.utils.AppLog;
import com.share.learn.utils.DensityUtils;
import com.share.learn.utils.URLConstants;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.ArrayList;
import java.util.Map;

/**
 * @desc 首页
 * @creator caozhiqing
 * @data 2016/3/10
 * 13637055938    111111
 */
public class TeacherHomePageFragment extends BaseFragment implements View.OnClickListener, RequsetListener {

    @Bind(R.id.courseSettingImg)
    ImageView courseSettingImg;
    @Bind(R.id.certifySettingImg)
    ImageView certifySettingImg;
    @Bind(R.id.myOrder)
    ImageView myOrder;
    @Bind(R.id.myBalance)
    ImageView myBalance;
    private LinearLayout homeSearch;
    private EditText head_seach_txt;


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
        homeSearch = (LinearLayout) view.findViewById(R.id.homeSearch);
        viewpager = (ViewPager) view.findViewById(R.id.id_guide_viewpager);
        head_seach_txt = (EditText) view.findViewById(R.id.head_seach_txt);

        courseSettingImg.setOnClickListener(this);
        certifySettingImg.setOnClickListener(this);
        myOrder.setOnClickListener(this);
        myBalance.setOnClickListener(this);

        homeSearch.setOnClickListener(this);
        head_seach_txt.setOnClickListener(this);
//        initGuidBanner(view);
        requestTask();
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

        Map postParams = RequestHelp.getBaseParaMap("Home");
        postParams.put("cityName", "合肥市");
        RequestParam param = new RequestParam();
//        param.setmParserClassName(HomePageBannerParse.class.getName());
        param.setmParserClassName(new HomePageBannerParse());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        JsonParserBase<HomeInfo> jsonParserBase = (JsonParserBase<HomeInfo>) obj;
        if (jsonParserBase != null) {
            HomeInfo homeInfo = jsonParserBase.getData();
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
            case R.id.head_seach_txt:
                homeSearch.performClick();
            case R.id.courseSettingImg:

                break;
            case R.id.certifySettingImg:
                toClassActivity(this, TeacherCertifyActivity.class.getName());

                break;
            case R.id.myOrder:
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
}