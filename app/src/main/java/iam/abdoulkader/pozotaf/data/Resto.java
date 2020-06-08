package iam.abdoulkader.pozotaf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by root on 24/04/18.
 */

@DatabaseTable(tableName = "restos")
public class Resto implements Parcelable {

    @DatabaseField(id = true)
    public int id;
    @DatabaseField
    public String name;
    @DatabaseField
    public String image;
    @DatabaseField
    public String city;
    @DatabaseField
    public String desc;
    @DatabaseField
    public String phone1;
    @DatabaseField
    public String phone2;
    @DatabaseField
    public String email;
    @DatabaseField
    public long longitude;
    @DatabaseField
    public long latitude;

    public Resto() {
    }

    public Resto(int id, String name, String image, String city, String desc, String phone1, String phone2, String email, long longitude, long latitude) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.city = city;
        this.desc = desc;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    protected Resto(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        city = in.readString();
        desc = in.readString();
        phone1 = in.readString();
        phone2 = in.readString();
        email = in.readString();
        longitude = in.readLong();
        latitude = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(city);
        dest.writeString(desc);
        dest.writeString(phone1);
        dest.writeString(phone2);
        dest.writeString(email);
        dest.writeLong(longitude);
        dest.writeLong(latitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Resto> CREATOR = new Creator<Resto>() {
        @Override
        public Resto createFromParcel(Parcel in) {
            return new Resto(in);
        }

        @Override
        public Resto[] newArray(int size) {
            return new Resto[size];
        }
    };
}
