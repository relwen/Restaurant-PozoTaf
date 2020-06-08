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

@DatabaseTable(tableName = "food_categories")
public class FoodCategory implements Parcelable {

    @DatabaseField(id = true)
    public int id;
    @DatabaseField
    public String name;
    @DatabaseField
    public String desc;
    @DatabaseField
    public String image;

    public FoodCategory() {
    }

    public FoodCategory(int id, String name, String image, String desc) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.desc = desc;
    }

    public FoodCategory(int id, String name, String image, String desc, ForeignCollection<Food> foods) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.desc = desc;
    }

    protected FoodCategory(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
    }

    public static final Creator<FoodCategory> CREATOR = new Creator<FoodCategory>() {
        @Override
        public FoodCategory createFromParcel(Parcel in) {
            return new FoodCategory(in);
        }

        @Override
        public FoodCategory[] newArray(int size) {
            return new FoodCategory[size];
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
    }
}
