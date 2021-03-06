package com.share.teacher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.share.teacher.R;
import com.share.teacher.activity.home.SearchActivity;
import com.share.teacher.activity.teacher.ChooseTeacherActivity;
import com.share.teacher.adapter.GuideViewPagerAdapter;
import com.share.teacher.bean.BannerImgInfo;
import com.share.teacher.bean.HomeInfo;
import com.share.teacher.bean.HomePagerBanner;
import com.share.teacher.help.RequestHelp;
import com.share.teacher.help.RequsetListener;
import com.share.teacher.parse.HomePageBannerParse;
import com.share.teacher.utils.AppLog;
import com.share.teacher.utils.DensityUtils;
import com.share.teacher.utils.URLConstants;
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
 *
 */
public class HomePageFragment extends BaseFragment implements View.OnClickListener,RequsetListener {

    private  LinearLayout homeSearch ;
    private EditText head_seach_txt;

    private ImageView chineseImg;
    private ImageView mathImg;
    private ImageView englishImg;
    private ImageView physicImg;
    private ImageView chemistryImg;
    private ImageView biologyImg;
    private ImageView politicsImg;
    private ImageView historyImg;
    private ImageView geographyImg;

    private View converView;
    private ViewPager viewpager = null;
    private GuideViewPagerAdapter guideAdapter = null;
    private int ids[] = { R.drawable.aot, R.drawable.aot,R.drawable.aot};

    private ArrayList<BannerImgInfo> bannerImgInfos = new ArrayList<BannerImgInfo>();

    private int courseId = 100;//课程名称
      /*语文	101
        数学	102
        英语	103
        物理	104
        化学	105
        生物	106
        政治	107
        历史	108
        地理	109*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home,null);
        converView= view;
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view){
        homeSearch = (LinearLayout) view.findViewById(R.id.homeSearch);
        viewpager = (ViewPager) view.findViewById(R.id.id_guide_viewpager);
        head_seach_txt = (EditText)view.findViewById(R.id.head_seach_txt);

        chineseImg = (ImageView)view.findViewById(R.id.chineseImg);
        mathImg = (ImageView)view.findViewById(R.id.mathImg);
        englishImg = (ImageView)view.findViewById(R.id.englishImg);
        physicImg = (ImageView)view.findViewById(R.id.physicsImg);
        chemistryImg = (ImageView)view.findViewById(R.id.chemistryImg);
        biologyImg = (ImageView)view.findViewById(R.id.biologyImg);
        politicsImg = (ImageView)view.findViewById(R.id.politicsImg);
        historyImg = (ImageView)view.findViewById(R.id.historyImg);
        geographyImg = (ImageView)view.findViewById(R.id.geographyImg);

        chineseImg.setOnClickListener(this);
        mathImg.setOnClickListener(this);
        englishImg.setOnClickListener(this);
        physicImg.setOnClickListener(this);
        chemistryImg.setOnClickListener(this);
        biologyImg.setOnClickListener(this);
        politicsImg.setOnClickListener(this);
        historyImg.setOnClickListener(this);
        geographyImg.setOnClickListener(this);

        homeSearch.setOnClickListener(this);
        head_seach_txt.setOnClickListener(this);
//        initGuidBanner(view);
        requestTask();
    }

    /**
     * 初始化轮播图
     */
    private void initGuidBanner(View view){
        if(view == null){
            return;
        }

        if(bannerImgInfos != null && bannerImgInfos.size()>0){
            guideAdapter = new GuideViewPagerAdapter(bannerImgInfos, view,mActivity);
        }else {
            guideAdapter = new GuideViewPagerAdapter(ids, view,mActivity);
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
        if(bannerImgInfos != null && bannerImgInfos.size()>1){
            viewpager.setCurrentItem(bannerImgInfos.size()*30);
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
        JsonParserBase<HomeInfo> jsonParserBase = (JsonParserBase<HomeInfo>)obj;
        if(jsonParserBase != null){
            HomeInfo homeInfo = jsonParserBase.getData();
            ArrayList<HomePagerBanner> pagerBanners = homeInfo.getTopAdList();
            BannerImgInfo imgInfo = null;
            bannerImgInfos.clear();
            if(pagerBanners != null && pagerBanners.size()>0){
                for(HomePagerBanner banner:pagerBanners){
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
        AppLog.Loge("setUserVisibleHint:"+isVisibleToUser+"");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.homeSearch:
                toClassActivity(this, SearchActivity.class.getName());
                return;
            case R.id.head_seach_txt:
                homeSearch.performClick();
                return;
            case R.id.chineseImg:
                courseId = 101;
                break;
            case R.id.mathImg:
                courseId = 102;
                break;
            case R.id.englishImg:
                courseId = 103;
                break;
            case R.id.physicsImg:
                courseId = 104;
                break;
            case R.id.chemistryImg:
                courseId = 105;
                break;
            case R.id.biologyImg:
                courseId = 106;
                break;
            case R.id.politicsImg:
                courseId = 107;
                break;
            case R.id.historyImg:
                courseId = 108;
                break;
            case R.id.geographyImg:
                courseId = 109;
                break;
            default:break;
        }
        Intent intent = new Intent(mActivity,ChooseTeacherActivity.class);
        intent.putExtra(URLConstants.COURSEID,courseId);
        startActivity(intent);
    }
}