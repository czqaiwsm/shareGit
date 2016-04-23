package com.volley.req;

import com.google.gson.reflect.TypeToken;
import com.share.learn.utils.AppLog;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

public class LoginParser implements IParser {
	@Override
	public Object fromJson(JSONObject object) {
		return null;
	}

	@Override
	public JsonParserBase<UserInfo> fromJson(String json) {
		AppLog.Logi("responJson:"+json);
		JsonParserBase<UserInfo> result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<UserInfo>>() {
		}.getType());
		return result;
	}
}

