package iam.abdoulkader.pozotaf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by root on 24/04/18.
 */

@DatabaseTable(tableName = "cart_restos")
public class CartResto extends Resto {

    @ForeignCollectionField(eager = true)
    public ForeignCollection<CartFood> cartFoods;


    public CartResto() {
    }

    public CartResto(Resto resto, ForeignCollection<CartFood> cartFoods) {
        super(resto.id, resto.name, resto.image, resto.city, resto.desc, resto.phone1, resto.phone2, resto.email, resto.longitude, resto.latitude);

        this.cartFoods = cartFoods;
    }

    protected CartResto(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        desc = in.readString();
        phone1 = in.readString();
        phone2 = in.readString();
        email = in.readString();
        longitude = in.readLong();
        latitude = in.readLong();
    }

    public static final Creator<CartResto> CREATOR = new Creator<CartResto>() {
        @Override
        public CartResto createFromParcel(Parcel in) {
            return new CartResto(in);
        }

        @Override
        public CartResto[] newArray(int size) {
            return new CartResto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeString(desc);
        parcel.writeString(phone1);
        parcel.writeString(phone2);
        parcel.writeString(email);
        parcel.writeLong(longitude);
        parcel.writeLong(latitude);
    }
}
