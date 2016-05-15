package com.share.learn.fragment.center;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.share.learn.R;
import com.share.learn.activity.login.LoginActivity;
import com.share.learn.fragment.BaseFragment;

public class UnLoginPCenterFragment extends BaseFragment implements
        OnClickListener {
    private Button mLoginBtn;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        initTitleView();
        initView(view);
    }

    private void initView(View view) {
        mLoginBtn = (Button) view.findViewById(R.id.login_button);
        mLoginBtn.setOnClickListener(this);
    }

    private void initTitleView() {
        setTitleText("请登录");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pcenter_unlogin,
                container, false);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.login_button:
                onLauncherLogin();
                break;

            default:
                break;
        }

    }

    private void onLauncherLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        mActivity.startActivityForResult(intent,100);
    }
}
