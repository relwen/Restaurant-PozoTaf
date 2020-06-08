package iam.abdoulkader.pozotaf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by root on 21/04/18.
 */

@DatabaseTable(tableName = "carts")
public class Cart implements Parcelable {

    @DatabaseField(id = true)
    public int id;
    @ForeignCollectionField(eager = true)
    public ForeignCollection<CartFood> cartFoods;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = true)
    public User user;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = true)
    public CartAddress cartAddress;

    public Cart() {
    }

    public Cart(int id, ForeignCollection<CartFood> foods, User user, CartAddress cartAddress) {
        this.id = id;
        this.cartFoods = foods;
        this.user = user;
        this.cartAddress = cartAddress;
    }


    protected Cart(Parcel in) {
        id = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
        cartAddress = in.readParcelable(CartAddress.class.getClassLoader());
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeParcelable(user, i);
        parcel.writeParcelable(cartAddress, i);
    }
}
