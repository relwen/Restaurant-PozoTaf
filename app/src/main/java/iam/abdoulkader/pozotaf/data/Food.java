package iam.abdoulkader.pozotaf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by root on 18/04/18.
 */

@DatabaseTable(tableName = "foods")
public class Food implements Parcelable {

    @DatabaseField(id = true)
    public int id;
    @DatabaseField
    public String name;
    @DatabaseField
    public String image;
    @DatabaseField
    public String desc;
    @DatabaseField
    public int price;
    @DatabaseField
    public boolean is_non_halal;

    public Food() {
    }

    public Food(int id, String name, String image, String desc, int price, boolean is_non_halal) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.desc = desc;
        this.price = price;
        this.is_non_halal = is_non_halal;
    }


    protected Food(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        desc = in.readString();
        price = in.readInt();
        is_non_halal = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(desc);
        dest.writeInt(price);
        dest.writeByte((byte) (is_non_halal ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };
}
