package iam.abdoulkader.pozotaf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by root on 22/12/17.
 */

@DatabaseTable(tableName = "users")
public class User implements Parcelable {

    @DatabaseField(id = true)
    public int id;
    @DatabaseField
    public String full_name;
    @DatabaseField
    public String phone;
    @DatabaseField
    public String email;
    @DatabaseField
    public String password;
    @DatabaseField
    public int status;
    @ForeignCollectionField(eager = true)
    public ForeignCollection<Cart> carts;

    public User() {
    }

    public User(int id, String full_name, String phone, String email, String password, int status) {
        this.id = id;
        this.full_name = full_name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public User(int id, String full_name, String phone, String email, String password, int status,
                ForeignCollection<Cart> carts) {
        this.id = id;
        this.full_name = full_name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.carts = carts;
        this.status = status;
    }


    protected User(Parcel in) {
        id = in.readInt();
        full_name = in.readString();
        phone = in.readString();
        email = in.readString();
        password = in.readString();
        status = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(full_name);
        parcel.writeString(phone);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeInt(status);
    }
}
