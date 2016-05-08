package com.share.teacher.fragment.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.share.teacher.R;
import com.share.teacher.activity.login.LoginActivity;
import com.share.teacher.bean.VerifyCode;
import com.share.teacher.fragment.BaseFragment;
import com.share.teacher.help.RequestHelp;
import com.share.teacher.help.RequsetListener;
import com.share.teacher.parse.BaseParse;
import com.share.teacher.parse.VerifyCodeParse;
import com.share.teacher.utils.BaseApplication;
import com.share.teacher.utils.PhoneUitl;
import com.share.teacher.utils.SmartToast;
import com.share.teacher.utils.URLConstants;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.Map;

public class ForgetPasswordFragment extends BaseFragment implements OnClickListener,RequsetListener {

	private EditText forget_phone;
	private EditText forget_inputCode;
	private EditText forget_pass;
	private TextView forget_getCode;
	private TextView forget_next;
	private boolean isgetCode = true;

	private int MSG_TOTAL_TIME;
	private final int MSG_UPDATE_TIME = 500;
	private  int requetType = -1;
	private String phone = null;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// startReqTask(this);
		// mLoadHandler.sendEmptyMessageDelayed(Constant.NET_SUCCESS, 100);// 停止加载框
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pcenter_forget_password, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initTitleView();
		initView(view);

	}

	private void initTitleView() {
		setTitleText(R.string.pcenter_forget_pass);
		setLeftHeadIcon(0);
	}

	private void initView(View view) {
		forget_phone = (EditText) view.findViewById(R.id.forget_username);
		forget_inputCode = (EditText) view.findViewById(R.id.forget_passCode);
		forget_pass = (EditText) view.findViewById(R.id.forget_pass);
		forget_getCode = (TextView) view.findViewById(R.id.forget_getCode);
		forget_next = (TextView) view.findViewById(R.id.forget_next);

		forget_next.setOnClickListener(this);
		forget_getCode.setOnClickListener(this);
	}


	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.forget_next:
			isgetCode = false;
			onJudge();
			break;
		case R.id.forget_getCode:
			isgetCode = true;
			getCode();
			break;
		default:
			break;
		}
	}

	private void onJudge() {
		if (TextUtils.isEmpty(forget_phone.getText().toString()+forget_inputCode.getText().toString()+forget_pass.getText().toString())) {
			SmartToast.showText(BaseApplication.getInstance(), R.string.input_error);
			return;
		}
		if (!forget_phone.getText().toString().equalsIgnoreCase(phone)) {
			toasetUtil.showInfo("手机号不一致,请重新获取验证码!");
			return;
		}
		requetType = 2;
		requestTask();
	}

	private void getCode() {
		if (forget_phone.length() == 0) {
			SmartToast.makeText(mActivity, R.string.input_error, Toast.LENGTH_SHORT).show();
		} else {
			if (!PhoneUitl.isPhone(forget_phone.getText().toString())) {
				toasetUtil.showInfo(R.string.phone_error);
				forget_phone.setText("");
			} else {
				forget_getCode.setEnabled(false);
				MSG_TOTAL_TIME = 60;
				Message message = new Message();
				message.what = MSG_UPDATE_TIME;
				timeHandler.sendMessage(message);
				phone = forget_phone.getText().toString();
				requetType = 1;
				requestData(0);// ----------发送请求
				forget_getCode.requestFocus();
			}
		}
	}

	// 验证码倒计时
	public Handler timeHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_UPDATE_TIME:
				MSG_TOTAL_TIME--;
				if (MSG_TOTAL_TIME > 0) {
					forget_getCode.setText(String.format(getResources().getString(R.string.has_minuter,MSG_TOTAL_TIME+"")));

					forget_getCode.setText(MSG_TOTAL_TIME + " 秒");
					Message message = obtainMessage();
					message.what = MSG_UPDATE_TIME;
					sendMessageDelayed(message, 1000);
				} else if (MSG_TOTAL_TIME < -10) {
					forget_getCode.setText(R.string.addcode_resend);
					forget_getCode.setEnabled(true);
				} else {
					forget_getCode.setText(R.string.addcode_code);
					forget_getCode.setEnabled(true);
				}
				break;
			default:
				break;
			}
		}
	};



	@Override
	public void requestData(int requestType) {
		RequestParam param = null;
		switch (requetType){
			case 1:
				param = RequestHelp.getVcodePara("VCode",forget_phone.getText().toString(),2);
//				param.setmParserClassName(VerifyCodeParse.class.getName());
				param.setmParserClassName(new  VerifyCodeParse());
				break;
			case 2://注册
				HttpURL url = new HttpURL();
				url.setmBaseUrl(URLConstants.BASE_URL);

				Map postParams = RequestHelp.getBaseParaMap("UserFindPwd") ;

				postParams.put("loginName", phone);
//				postParams.put("vcode",inputCode.getText().toString());
//				postParams.put("sendId",verifyCode.getSendId());
				postParams.put("vcode","123456");
//				postParams.put("sendId",verifyCode.getSendId());
				postParams.put("password",forget_pass.getText().toString());
				postParams.put("sendId","111111");
				param = new RequestParam();
//				param.setmParserClassName(BaseParse.class.getName());
				param.setmParserClassName(new BaseParse());
				param.setmPostarams(postParams);
				param.setmHttpURL(url);
				param.setPostRequestMethod();
				break;
		}
		RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
	}

	private VerifyCode verifyCode = null;//验证码

	@Override
	public void handleRspSuccess(int requestType,Object obj) {
		switch (requetType){
			case 1:
				MSG_TOTAL_TIME = -1;
				phone = forget_phone.getText().toString();
				JsonParserBase<VerifyCode> jsonParserBase1 = (JsonParserBase<VerifyCode>)obj;
				verifyCode = jsonParserBase1.getData();
				forget_inputCode.setText(verifyCode !=null?verifyCode.getSmsCode():"");
//				AlertDialogUtils.displayMyAlertChoice(mActivity,"验证码",verifyCode.getSmsCode()+"",null,null);
				break;
			case 2:
				toClassActivity(ForgetPasswordFragment.this, LoginActivity.class.getName());
				SmartToast.showText(mActivity,"注册成功");
				mActivity.finish();
				break;
		}

	}

	@Override
	protected void failRespone() {
		super.failRespone();
		MSG_TOTAL_TIME = -11;

	}
	protected Response.ErrorListener createMyReqErrorListener() {
		super.createMyReqErrorListener();
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (!isDetached()) {
					Message msg = new Message();
					MSG_TOTAL_TIME = -11;
					Message message = new Message();
					message.what = MSG_UPDATE_TIME;
					timeHandler.removeMessages(MSG_UPDATE_TIME);
					timeHandler.sendMessageDelayed(message, 10000);
				}
			}
		};
	}
}
