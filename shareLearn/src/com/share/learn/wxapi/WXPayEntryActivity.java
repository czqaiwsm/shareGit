package com.share.learn.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.share.learn.R;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.wxutils.WxConstants;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.pay_result);

		api = WXAPIFactory.createWXAPI(this, WxConstants.APP_ID.trim());

		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if (resp.errCode == 0) {
				Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
//				UIHelper.toMyOrderActivity(this, SharePreferenceUtils.getInstance(this).getUser().getId());
				finish();
			} else if (resp.errCode == -2) {
				Toast.makeText(this, "取消支付", Toast.LENGTH_SHORT).show();
				finish();
			} else if (resp.errCode == -1) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("支付失败");
				builder.setMessage("失败信息:"+resp.errStr+" "+resp.errCode);
				builder.show();
				Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
			}


		}
	}
}