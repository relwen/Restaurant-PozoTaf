package iam.abdoulkader.pozotaf.data;

import android.os.Parcel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by root on 02/05/18.
 */

@DatabaseTable(tableName = "cart_orders")
public class CartOrder extends Order {

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Cart cart;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private CartAddress cartAddress;

    public CartOrder() {
    }

    public CartOrder(Order order, Cart cart, CartAddress cartAddress) {
        super(order.id, order.date, order.state);

        this.cart = cart;
        this.cartAddress = cartAddress;
    }

    public CartOrder(Parcel in) {
        super(in);

        cart = in.readParcelable(Cart.class.getClassLoader());
        cartAddress = in.readParcelable(CartAddress.class.getClassLoader());
    }

    public CartOrder(Parcel in, Cart cart, CartAddress cartAddress) {
        super(in);
        this.cart = cart;
        this.cartAddress = cartAddress;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeParcelable(cart, flags);
        dest.writeParcelable(cartAddress, flags);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }
}
