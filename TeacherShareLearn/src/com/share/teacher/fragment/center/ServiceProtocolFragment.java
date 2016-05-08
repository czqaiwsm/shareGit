package com.share.teacher.fragment.center;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.share.teacher.R;
import com.share.teacher.fragment.BaseFragment;

public class ServiceProtocolFragment extends BaseFragment {

    private WebView mWebView;
    private String url,shippingStr;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_web_logic, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    @SuppressWarnings("deprecation")
    private void initView(View view) {
        // TODO Auto-generated method stub
        setTitleText("我要学服务协议");
        setLeftHeadIcon(R.drawable.back, new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                getActivity().finish();
            }
        });
        mWebView = (WebView) view.findViewById(R.id.rule_webview);
        mWebView.setWebViewClient(getClient());
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(false);
        int screenDensity = getResources().getDisplayMetrics().densityDpi;
        WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;
        switch (screenDensity) {
            case DisplayMetrics.DENSITY_LOW:
                zoomDensity = WebSettings.ZoomDensity.CLOSE;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                zoomDensity = WebSettings.ZoomDensity.MEDIUM;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                zoomDensity = WebSettings.ZoomDensity.FAR;
                break;
        }
        WebSettings settings = mWebView.getSettings();
        settings.setDefaultZoom(zoomDensity);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);// 可能的话不要超过屏幕宽度
        if(TextUtils.isEmpty(url)){
            mWebView.loadUrl(url);
        }
        mWebView.loadUrl("http://www.sf-express.com/cn/sc/");

    }

    private WebViewClient getClient() {
        // TODO Auto-generated method stub
        WebViewClient mClient = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                // startLoading(mFragment);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
            }

        };
        return mClient;
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
        if (mWebView != null) {
            mWebView.getSettings().setLoadWithOverviewMode(false);
            mWebView.getSettings().setUseWideViewPort(false);
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.getSettings().setSupportZoom(false);
            mWebView.getSettings().setBuiltInZoomControls(false);
            mWebView.destroy();
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ViewGroup view = (ViewGroup) getActivity().getWindow().getDecorView();
        view.removeAllViews();
    }



}
