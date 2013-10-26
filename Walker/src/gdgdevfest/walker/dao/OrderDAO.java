package gdgdevfest.walker.dao;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderDAO extends BaseDAO implements Parcelable {

	private int id;
	private String status = null;
	private PointDAO from = null;
	private PointDAO to = null;
	private double price;
	private String phone;
	private Date createAt = null;
	private Date updateAt = null;

	public PointDAO getFrom() {

		return from;
	}

	public void setFrom(PointDAO from) {

		this.from = from;
	}

	public void setTo(PointDAO to) {

		this.to = to;
	}

	public PointDAO getTo() {

		return to;
	}

	public Date getUpdateAt() {

		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {

		this.updateAt = updateAt;
	}

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

	public Date getCreateAt() {

		return createAt;
	}

	public void setCreateAt(Date createAt) {

		this.createAt = createAt;
	}

	@Override
	public int describeContents() {

		return 0;
	}

	public OrderDAO() {

	}

	public OrderDAO(int id, String status, PointDAO from, PointDAO to,
			double price, String phone, Date createAt, Date updateAt) {

		this.id = id;
		this.status = status;
		this.from = from;
		this.to = to;
		this.price = price;
		this.phone = phone;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	private OrderDAO(Parcel parcel) {

		id = parcel.readInt();
		status = parcel.readString();
		from = parcel.readParcelable(null);
		to = parcel.readParcelable(null);
		price = parcel.readDouble();
		phone = parcel.readString();
		createAt = (Date) parcel.readSerializable();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeInt(id);
		dest.writeString(status);
		dest.writeParcelable(from, 0);
		dest.writeParcelable(to, 0);
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

		return "OrderDAO [id=" + id + ", status=" + status + ", from=" + from
				+ ", to=" + to + ", price=" + price + ", phone=" + phone
				+ ", createAt=" + createAt + ", updateAt=" + updateAt + "]";
	}
}
