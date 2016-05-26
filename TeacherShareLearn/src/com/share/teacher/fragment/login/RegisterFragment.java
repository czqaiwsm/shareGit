package com.share.teacher.fragment.login;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.share.teacher.R;
import com.share.teacher.activity.center.ServiceProtocolActivity;
import com.share.teacher.activity.login.LoginActivity;
import com.share.teacher.bean.VerifyCode;
import com.share.teacher.fragment.BaseFragment;
import com.share.teacher.help.RequestHelp;
import com.share.teacher.help.RequsetListener;
import com.share.teacher.parse.BaseParse;
import com.share.teacher.parse.VerifyCodeParse;
import com.share.teacher.utils.PhoneUitl;
import com.share.teacher.utils.SmartToast;
import com.share.teacher.utils.URLConstants;
import com.share.teacher.utils.WaitLayer;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.Map;

/**
 * 注册完成
 * 
 * @author 储
 * 
 */

public class RegisterFragment extends BaseFragment implements OnClickListener,RequsetListener {

	private EditText register_phone;
	private EditText inputCode;
	private TextView getCode;
	private EditText register_pass;// 密码
	private EditText register_passAgain;// 密码确认
	private TextView register;
	private String phone = null;
    private TextView protocol;
	private CheckBox box;
	private boolean mIsCode = true;
	private int MSG_TOTAL_TIME;
	private final int MSG_UPDATE_TIME = 500;
	// private String mToken;

	private int requetType = 1;//1 获取验证码  2注册

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pcenter_register, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initTitleView();
		initView(view);
		setLoadingDilog(WaitLayer.DialogType.MODALESS);
		requsetListener = this;
	}

	private void initView(View view) {
		register_phone = (EditText) view.findViewById(R.id.register_phone);
		inputCode = (EditText) view.findViewById(R.id.register_passCode);
		getCode = (TextView) view.findViewById(R.id.register_getCode);
		register_pass = (EditText) view.findViewById(R.id.register_passAgain);
		register_passAgain = (EditText) view.findViewById(R.id.register_passAgain2);
		register = (TextView) view.findViewById(R.id.register);
		protocol = (TextView) view.findViewById(R.id.protocol);
        box = (CheckBox)view.findViewById(R.id.greed);
		protocol.setText(Html.fromHtml("<u>此软件的用户协议</u>"));
		getCode.setOnClickListener(this);
		register.setOnClickListener(this);
		protocol.setOnClickListener(this);
	}

	/**
	 * 注册信息的判断 请求注册
	 */
	private void onJudge() {
		if (TextUtils.isEmpty(register_phone.getText().toString()) || TextUtils.isEmpty(register_pass.getText().toString()) || TextUtils.isEmpty(inputCode.getText().toString())) {
			showSmartToast(R.string.input_error, Toast.LENGTH_LONG);
			return;
		}
		if (!register_phone.getText().toString().equalsIgnoreCase(phone)) {
			SmartToast.showText(mActivity, "手机号不一致,请重新获取验证码!");
			return;
		}
		if (!register_passAgain.getText().toString().equals(register_pass.getText().toString())) {
			showSmartToast(R.string.pass_errors, Toast.LENGTH_LONG);
			return;
		}
		if (register_pass.getText().toString().length() < 6 || register_pass.getText().toString().length() > 16) {
			showSmartToast(R.string.pass_error2, Toast.LENGTH_LONG);
			return;
		}

		if(!box.isChecked()){
			SmartToast.showText(mActivity, "请同意协议");
			return;
		}
		if(!verifyCode.getSmsCode().equalsIgnoreCase(inputCode.getText().toString())){
			toasetUtil.showInfo("请输入正确的验证码!");
			return;
		}
		requetType = 2;
		requestTask();
	}

	private void initTitleView() {
		setTitleText(R.string.welcome_register);
		setLeftHeadIcon(0);

	}



	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
		case R.id.register:// 注册
			mIsCode = false;
			onJudge();
			break;
		case R.id.register_getCode:// 获取验证码
			mIsCode = true;
			getCode();
			break;
		case R.id.protocol://服务协议
			Intent intent1 = new Intent(mActivity, ServiceProtocolActivity.class);
			intent1.putExtra("url","http://www.leishangnet.com/learn-wap/html/service_agreement.html");
			mActivity.startActivity(intent1);
			break;
		default:
			break;
		}

	}

	/**
	 * 
	 * 获取验证码
	 */
	private void getCode() {
		if (register_phone.length() == 0) {
			SmartToast.makeText(mActivity, R.string.input_error, Toast.LENGTH_SHORT).show();
		} else {
			if (!PhoneUitl.isPhone(register_phone.getText().toString())) {
				SmartToast.makeText(mActivity, R.string.phone_error, Toast.LENGTH_SHORT).show();
				register_phone.setText("");
			} else {
				getCode.setEnabled(false);
				MSG_TOTAL_TIME = 60;
				// Toast.makeText(mActivity, "短信已发送，请稍候！",
				// Toast.LENGTH_SHORT).show();
				Message message = new Message();
				message.what = MSG_UPDATE_TIME;
				timeHandler.sendMessage(message);
				requetType = 1;
				phone = register_phone.getText().toString();
				requestData(0);// ----------发送请求
				getCode.requestFocus();
			}
		}
	}

	// 验证码倒计时
	private Handler timeHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if(isDetached()){
				return;
			}
			switch (msg.what) {
			case MSG_UPDATE_TIME:
				MSG_TOTAL_TIME--;
				if (MSG_TOTAL_TIME > 0) {
					getCode.setText(String.format(getResources().getString(R.string.has_minuter,MSG_TOTAL_TIME+"")));
					Message message = obtainMessage();
					message.what = MSG_UPDATE_TIME;
					sendMessageDelayed(message, 1000);
				} else if (MSG_TOTAL_TIME < -10) {
					getCode.setText(R.string.addcode_resend);
					getCode.setEnabled(true);
				} else {
					getCode.setText(R.string.addcode_code);
					getCode.setEnabled(true);
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
				param = RequestHelp.getVcodePara("VCode",register_phone.getText().toString(),1);
//				param.setmParserClassName(VerifyCodeParse.class.getName());
				param.setmParserClassName(new VerifyCodeParse());
				break;
			case 2://注册
				HttpURL url = new HttpURL();
				url.setmBaseUrl(URLConstants.BASE_URL);

				Map postParams = RequestHelp.getBaseParaMap("UserReg") ;

				postParams.put("loginName", phone);
				postParams.put("vcode",inputCode.getText().toString());
				postParams.put("sendId",verifyCode.getSendId());
				postParams.put("password",register_pass.getText().toString());
				postParams.put("fInviteCode","");
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
				JsonParserBase<VerifyCode> jsonParserBase1 = (JsonParserBase<VerifyCode>)obj;
				verifyCode = jsonParserBase1.getData();
				inputCode.setText(verifyCode !=null?verifyCode.getSmsCode():"");
//				AlertDialogUtils.displayMyAlertChoice(mActivity,"验证码",verifyCode.getSmsCode()+"",null,null);
				break;
			case 2:
				toClassActivity(RegisterFragment.this, LoginActivity.class.getName());
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

	@Override
	protected void errorRespone() {
		super.errorRespone();
		if (!isDetached()) {
			Message msg = new Message();
			MSG_TOTAL_TIME = -11;
			Message message = new Message();
			message.what = MSG_UPDATE_TIME;
			timeHandler.removeMessages(MSG_UPDATE_TIME);
			timeHandler.sendMessageDelayed(message, 10000);
		}
	}


	public void showSmartToast(int resId, int duration) throws Resources.NotFoundException {
		if (this.getActivity() != null) {
			SmartToast.makeText(this.getActivity(), resId, duration).show();
		}
	}

}
