package gdgdevfest.walker.dao;

import android.os.Parcel;
import android.os.Parcelable;

public class PointDAO extends BaseDAO implements Parcelable {

	private double longtitude;
	private double latitude;

	public double getLongtitude() {

		return longtitude;
	}

	public void setLongtitude(double longtitude) {

		this.longtitude = longtitude;
	}

	public double getLatitude() {

		return latitude;
	}

	public void setLatitude(double latitude) {

		this.latitude = latitude;
	}

	public PointDAO() {

	}

	public PointDAO(double longtitude, double latitude) {

		this.longtitude = longtitude;
		this.latitude = latitude;
	}

	private PointDAO(Parcel parcel) {

		longtitude = parcel.readDouble();
		latitude = parcel.readDouble();
	}

	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeDouble(longtitude);
		dest.writeDouble(latitude);
	}

	public static final Creator<PointDAO> CREATOR = new Creator<PointDAO>() {

		@Override
		public PointDAO[] newArray(int size) {

			return new PointDAO[size];
		}

		@Override
		public PointDAO createFromParcel(Parcel source) {

			return new PointDAO(source);
		}
	};

	@Override
	public String toString() {

		return "PointDAO [longtitude=" + longtitude + ", latitude=" + latitude
				+ "]";
	}
}
