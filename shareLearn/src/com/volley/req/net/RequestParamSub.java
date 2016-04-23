package com.volley.req.net;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class RequestParamSub extends RequestParam {
	public RequestParamSub(Context context) {
		super();
		setHeaders(context);
	}

	private void setHeaders(Context context) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("header","header123");
//		headers.put("token", SharePreferenceUtils.getInstance(context.getApplicationContext()).getUserInfoToken());
		setmHeadersMap(headers);
	}
}
