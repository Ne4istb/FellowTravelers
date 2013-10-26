package gdgdevfest.walker.net;

public class NetConstants {

	/**
	 * Server base url
	 */
	public static final String URL_BASE = " http://mrjson.com/data/526b76e785f7feb2e8e1da64/walker/";

	/**
	 * Methods
	 */
	public static final String METHOD_GET_ORDER = URL_BASE + "order/";

	public static final String METHOD_GET_ORDERS = URL_BASE + "orders";

	/**
	 * Parse json
	 */
	public static final String KEY_JSON_ID = "id";
	public static final String KEY_JSON_STATUS = "status";
	public static final String KEY_JSON_DIRECTION = "direction";
	public static final String KEY_JSON_START_POINT = "startPoint";
	public static final String KEY_JSON_LONGTITUDE = "longtitude";
	public static final String KEY_JSON_LATITUDE = "latitude";
	public static final String KEY_JSON_END_POINT = "endPoint";
	public static final String KEY_JSON_PRICE = "Price";
	public static final String KEY_JSON_PHONE = "Phone";
	public static final String KEY_JSON_CREATE_AT = "createdAt";
	public static final String KEY_JSON_UPDATE_AT = "updatedA";

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSS";
}
