package iam.abdoulkader.pozotaf.data;

import android.os.Parcel;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by root on 25/04/18.
 */

@DatabaseTable(tableName = "cart_addresses")
public class CartAddress extends Address {

    @ForeignCollectionField(eager = true)
    public ForeignCollection<Cart> carts;

    public CartAddress() {
        super();
    }

    public CartAddress(Address address, ForeignCollection<Cart> carts) {
        super(address.id, address.name, address.location, address.longitude, address.latitude);
        this.carts = carts;
    }

    protected CartAddress(Parcel in) {
        super(in);
    }
}
