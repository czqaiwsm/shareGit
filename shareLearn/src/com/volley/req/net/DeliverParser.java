package com.volley.req.net;

import android.text.TextUtils;
import android.util.Log;
import com.volley.req.net.inferface.IDeliverParser;
import com.volley.req.net.inferface.IParser;

/**
 * deliver parser control
 * @author Administrator
 * 
 */
public class DeliverParser implements IDeliverParser {
	private static DeliverParser mDeliverParser;
	private static Object mObject = new Object();
	ParserHelper mHelper = new ParserHelper();

	private DeliverParser() {

	}

	public static DeliverParser newDeliverParser() {
		if (mDeliverParser == null) {
			synchronized (mObject) {
				if (mDeliverParser == null) {
					mDeliverParser = new DeliverParser();
				}
			}
		}
		return mDeliverParser;
	}

	@Override
	public Object deliverJson(IParser parserClassName, String json) {
		// TODO Auto-generated method stub
		try {
			if (parserClassName == null || TextUtils.isEmpty(json)
					|| json.equals("null")) {
				return null;
			}
			mHelper.setmParser(parserClassName);
		} catch (Exception e) {
			Log.e("Fly", "InstantiationException" + e.getMessage());
		}
		return mHelper.fromJson(json);
	}

}
