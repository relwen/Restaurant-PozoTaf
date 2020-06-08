package iam.abdoulkader.pozotaf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by root on 02/05/18.
 */

@DatabaseTable(tableName = "orders")
public class Order implements Parcelable {

    @DatabaseField(id = true)
    public int id;
    @DatabaseField
    public Date date;
    @DatabaseField
    public int state;

    public Order() {
    }

    public Order(int id, Date date, int state) {
        this.id = id;
        this.date = date;
        this.state = state;
    }

    protected Order(Parcel in) {
        id = in.readInt();
        state = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(state);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
