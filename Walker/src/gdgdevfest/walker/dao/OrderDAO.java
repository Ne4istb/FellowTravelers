package gdgdevfest.walker.dao;

import java.util.Calendar;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderDAO extends BaseDAO implements Parcelable {

	private int id;
	private String status = null;
	private double price;
	private String phone;
	private Calendar createAt;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public double getPrice() {

		return price;
	}

	public void setPrice(double price) {

		this.price = price;
	}

	public String getPhone() {

		return phone;
	}

	public void setPhone(String phone) {

		this.phone = phone;
	}

	public Calendar getCreateAt() {

		return createAt;
	}

	public void setCreateAt(Calendar createAt) {

		this.createAt = createAt;
	}

	@Override
	public int describeContents() {

		return 0;
	}

	public OrderDAO() {

	}

	private OrderDAO(Parcel parcel) {

		id = parcel.readInt();
		status = parcel.readString();
		price = parcel.readDouble();
		phone = parcel.readString();
		createAt = (Calendar) parcel.readSerializable();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeInt(id);
		dest.writeString(status);
		dest.writeDouble(price);
		dest.writeString(phone);
		dest.writeSerializable(createAt);
	}

	public static final Creator<OrderDAO> CREATOR = new Creator<OrderDAO>() {

		@Override
		public OrderDAO[] newArray(int size) {

			return new OrderDAO[size];
		}

		@Override
		public OrderDAO createFromParcel(Parcel source) {

			return new OrderDAO(source);
		}
	};

	@Override
	public String toString() {

		return "OrderDAO [id=" + id + ", status=" + status + ", price=" + price
				+ ", phone=" + phone + ", createAt=" + createAt + "]";
	}
}
