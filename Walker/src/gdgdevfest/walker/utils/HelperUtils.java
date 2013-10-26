package gdgdevfest.walker.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.apache.http.util.ByteArrayBuffer;

import com.google.android.gms.common.data.c;

import android.content.Context;
import android.content.SharedPreferences;
import android.gesture.Prediction;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

public class HelperUtils {

	public static final boolean DEBUG = true;

	public static final String KEY_HAS_ORDER = "has_order";

	public static final String LOG_TAG = "Walker";

	public static final void printException(Throwable e) {

		if (DEBUG) {
			e.printStackTrace();
		}
	}

	public static final void println(String line) {

		if (DEBUG) {
			Log.d(LOG_TAG, line);
		}
	}

	public static String convertStreamToString(InputStream inputStream)
			throws IOException {

		if (inputStream != null) {

			StringBuilder sb = new StringBuilder();
			String line;
			try {

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, "UTF-8"));
				while ((line = reader.readLine()) != null) {

					sb.append(line).append("\n");
				}
			} finally {

				inputStream.close();
			}

			return sb.toString();
		} else {

			return "";
		}
	}

	public static boolean hasConnection(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetwork != null && wifiNetwork.isConnected()) {
			return true;
		}

		NetworkInfo mobileNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetwork != null && mobileNetwork.isConnected()) {
			return true;
		}

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		}

		return false;
	}

	public static void CopyStream(InputStream is, OutputStream os) {

		final int buffer_size = 1024;
		try {

			byte[] bytes = new byte[buffer_size];
			for (;;) {

				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {

		}
	}

	public static byte[] downloadImage(final String imageUrl) {

		byte[] image = null;

		try {

			URL url = new URL(imageUrl);

			InputStream is = url.openStream();

			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(8192);
			int current = 0;

			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			image = baf.toByteArray();

		} catch (MalformedURLException e) {

		} catch (IOException e) {
		}

		return image;
	}

	public void setHasOrder(Context context, boolean flag) {

		PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putBoolean(KEY_HAS_ORDER, flag).commit();
	}

	public static boolean isHasOrder(Context context) {

		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(KEY_HAS_ORDER, false);
	}

	public static String encode(String value) {

		try {
			return URLEncoder.encode(value, Charset.defaultCharset()
					.displayName());
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
