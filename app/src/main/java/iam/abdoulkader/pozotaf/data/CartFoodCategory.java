package iam.abdoulkader.pozotaf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by root on 18/04/18.
 */

@DatabaseTable(tableName = "cart_food_categories")
public class CartFoodCategory extends FoodCategory {

    @ForeignCollectionField(eager = true)
    public ForeignCollection<CartFood> cartFoods;

    public CartFoodCategory() {
    }

    public CartFoodCategory(FoodCategory foodCategory, ForeignCollection<CartFood> cartFoods) {
        super(foodCategory.id, foodCategory.name, foodCategory.desc, foodCategory.image);
        this.cartFoods = cartFoods;
    }

    protected CartFoodCategory(Parcel in) {
        super(in);
    }

    public static final Creator<CartFoodCategory> CREATOR = new Creator<CartFoodCategory>() {
        @Override
        public CartFoodCategory createFromParcel(Parcel in) {
            return new CartFoodCategory(in);
        }

        @Override
        public CartFoodCategory[] newArray(int size) {
            return new CartFoodCategory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
