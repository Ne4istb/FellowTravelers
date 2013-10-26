package gdgdevfest.walker.net.api;

import gdgdevfest.walker.dao.OrderDAO;
import gdgdevfest.walker.dao.PointDAO;
import gdgdevfest.walker.net.BaseGet;
import gdgdevfest.walker.net.NetConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderGetAPI extends BaseGet {

	private boolean isList = false;
	private SimpleDateFormat dateFormat = null;

	public OrderGetAPI(String id) {

		super((id == null ? NetConstants.METHOD_GET_ORDERS
				: NetConstants.METHOD_GET_ORDER + id));

		if (id == null)
			isList = true;
		dateFormat = new SimpleDateFormat(NetConstants.DATE_FORMAT,
				Locale.getDefault());
	}

	@Override
	protected Object parseResponse() throws JSONException, ParseException {

		if (getResponse() != null) {

			List<OrderDAO> list = new LinkedList<OrderDAO>();
			if (isList) {

				JSONArray orders = new JSONArray(getResponse());
				if (orders != null) {

					for (int i = 0; i < orders.length(); i++) {

						JSONObject orderItem = orders.getJSONObject(i);
						list.add(parseOrder(orderItem));
					}
				}
			}

			else {

				JSONObject order = new JSONObject(getResponse());
				list.add(parseOrder(order));
			}

			return list;
		}

		return null;
	}

	private OrderDAO parseOrder(JSONObject order) throws JSONException,
			ParseException {

		return new OrderDAO(order.getInt(NetConstants.KEY_JSON_ID),
				order.getString(NetConstants.KEY_JSON_STATUS), new PointDAO(
						order.getJSONObject(NetConstants.KEY_JSON_START_POINT)
								.getDouble(NetConstants.KEY_JSON_LONGTITUDE),
						order.getJSONObject(NetConstants.KEY_JSON_START_POINT)
								.getDouble(NetConstants.KEY_JSON_LATITUDE)),
				new PointDAO(order.getJSONObject(
						NetConstants.KEY_JSON_END_POINT).getDouble(
						NetConstants.KEY_JSON_LONGTITUDE), order.getJSONObject(
						NetConstants.KEY_JSON_START_POINT).getDouble(
						NetConstants.KEY_JSON_LATITUDE)),
				order.getDouble(NetConstants.KEY_JSON_PRICE),
				order.getString(NetConstants.KEY_JSON_PHONE),
				dateFormat.parse(order
						.getString(NetConstants.KEY_JSON_CREATE_AT)),
				dateFormat.parse(order
						.getString(NetConstants.KEY_JSON_UPDATE_AT)));
	}
}
