package gdgdevfest.walker.net.api;

import gdgdevfest.walker.dao.PointDAO;
import gdgdevfest.walker.net.NetConstants;
import gdgdevfest.walker.utils.HelperUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class OrderPostAPI {

	private JSONObject mJsonObjectRequest = null;

	public OrderPostAPI(PointDAO from, PointDAO to, double price, String phone) {

		try {

			mJsonObjectRequest = new JSONObject();
			mJsonObjectRequest.put("longitude_start", from.getLongtitude());
			mJsonObjectRequest.put("latitude_start", from.getLatitude());
			mJsonObjectRequest.put("longitude_end", to.getLongtitude());
			mJsonObjectRequest.put("latitude_end", to.getLatitude());
			mJsonObjectRequest.put("price", price);
			mJsonObjectRequest.put("phone", phone);
		} catch (Exception e) {
		}
	}

	public int sendData() {

		try {

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(NetConstants.METHOD_ORDER);
			httppost.setEntity(new StringEntity(mJsonObjectRequest.toString(),
					"UTF8"));
			httppost.setHeader("Content-type", "application/json");
			httppost.setEntity(new StringEntity(mJsonObjectRequest.toString(),
					"UTF8"));
			httpclient.execute(httppost);

			HttpResponse response = httpclient.execute(httppost);

			String responseText = EntityUtils.toString(response.getEntity());

			HelperUtils.println(responseText);

			if (response != null) {

				JSONObject responseJson = new JSONObject(responseText);
				if (responseJson.has("id")) {

					return responseJson.getInt("id");
				}
			}
		} catch (Exception e) {

		}

		return -1;
	}
}
