package iam.abdoulkader.pozotaf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by root on 18/04/18.
 */

@DatabaseTable(tableName = "cart_foods")
public class CartFood extends Food {

    @DatabaseField
    public int number;
    @DatabaseField
    public String message;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    public CartResto cartResto;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    public CartFoodCategory cartFoodCategory;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    public Cart cart;

    public CartFood() {
    }

    public CartFood(Food food, int number, String message, CartResto cartResto, CartFoodCategory cartFoodCategory, Cart cart) {
        super(food.id, food.name, food.image, food.desc, food.price, food.is_non_halal);

        this.number = number;
        this.message = message;
        this.cartResto = cartResto;
        this.cartFoodCategory = cartFoodCategory;
        this.cart = cart;
    }

    protected CartFood(Parcel in) {
        super(in);

        number = in.readInt();
        message = in.readString();
        cartResto = in.readParcelable(CartResto.class.getClassLoader());
        cartFoodCategory = in.readParcelable(CartFoodCategory.class.getClassLoader());
        cart = in.readParcelable(Cart.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeInt(number);
        dest.writeString(message);
        dest.writeParcelable(cartResto, flags);
        dest.writeParcelable(cartFoodCategory, flags);
        dest.writeParcelable(cart, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CartFood> CREATOR = new Creator<CartFood>() {
        @Override
        public CartFood createFromParcel(Parcel in) {
            return new CartFood(in);
        }

        @Override
        public CartFood[] newArray(int size) {
            return new CartFood[size];
        }
    };
}
