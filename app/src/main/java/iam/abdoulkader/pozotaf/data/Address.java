package iam.abdoulkader.pozotaf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by root on 25/04/18.
 */

@DatabaseTable(tableName = "addresses")
public class Address implements Parcelable {

    @DatabaseField(id = true)
    public String id;
    @DatabaseField
    public String name;
    @DatabaseField
    public String location;
    @DatabaseField
    public double longitude;
    @DatabaseField
    public double latitude;

    public Address() {
    }

    public Address(String id, String name, String location, double longitude, double latitude) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    protected Address(Parcel in) {
        id = in.readString();
        name = in.readString();
        location = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
    }
}
