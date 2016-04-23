package com.volley.req.net;

import com.android.volley.Request.Method;
import com.volley.req.net.inferface.IParser;

import java.util.Map;

public class RequestParam<T extends Object> implements IRequestParam {
	private HttpURL mHttpURL;

	private IParser mParserClassName;

	private int mRequestMethod = Method.GET;

	private Map<String, String> mHeadersMap;
	private T mPostJsonObject;

	public HttpURL getmHttpURL() {
		return mHttpURL;
	}

	public void setmHttpURL(HttpURL mHttpURL) {
		this.mHttpURL = mHttpURL;
	}

	public T getmPostJsonObject() {
		return mPostJsonObject;
	}

	/**
	 * 设置post的请求参数
	 * 
	 * @param postJsonObject：可以是Map和自定义的实体类（Entity)
	 */
	public void setmPostarams(T postJsonObject) {
		this.mPostJsonObject = postJsonObject;
	}

	public Map<String, String> getmHeadersMap() {
		return mHeadersMap;
	}

	public void setmHeadersMap(Map<String, String> mHeadersMap) {
		this.mHeadersMap = mHeadersMap;
	}

	public void setPostRequestMethod() {
		mRequestMethod = Method.POST;
	}

	public void setDeleteRequestMethod() {
		mRequestMethod = Method.DELETE;
	}

	public void setPutRequestMethod() {
		mRequestMethod = Method.PUT;
	}

	@Override
	public String buildRequestUrl() {
		// TODO Auto-generated method stub
		return mHttpURL.toString();
	}

	public IParser getmParserClassName() {
		return mParserClassName;
	}

	public void setmParserClassName(IParser mParserClassName) {
		this.mParserClassName = mParserClassName;
	}

	@Override
	public int requestMethod() {
		return mRequestMethod;
	}

}
