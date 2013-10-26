package gdgdevfest.walker.net;

import gdgdevfest.walker.dao.BaseDAO;
import gdgdevfest.walker.utils.HelperUtils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

public abstract class BasePost {

	private String mUrl;
	private String mResponse;
	private boolean mResponseSuccessful = false;

	private HttpGet httpGet;

	public BasePost(String url) {

		mUrl = url;
	}

	public void setUrl(String url) {

		mUrl = url;
	};

	public Object sendData() {

		HelperUtils.println("BaseGet sendData start. request = " + mUrl);

		HttpClient httpclient = new DefaultHttpClient();
		if (httpGet == null)
			httpGet = new HttpGet(mUrl);
		try {

			HttpResponse response = httpclient.execute(httpGet);
			mResponse = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {

			HelperUtils.printException(e);
		} catch (IOException e) {

			HelperUtils.printException(e);
		}

		HelperUtils.println("BaseGet sendData end. response = " + mResponse);

		if (mResponse != null)
			mResponseSuccessful = true;
		else
			mResponseSuccessful = false;

		try {

			return parseResponse();
		} catch (JSONException e) {

			HelperUtils.printException(e);
			return null;
		}
	}

	protected void setHeader(String key, String value) {

		if (httpGet == null)
			httpGet = new HttpGet(mUrl);
		httpGet.setHeader(key, value);
	}

	protected abstract BaseDAO parseResponse() throws JSONException;

	public String getResponse() {

		return mResponse;
	}

	public boolean isResponseSuccessful() {

		return mResponseSuccessful;
	}
}
