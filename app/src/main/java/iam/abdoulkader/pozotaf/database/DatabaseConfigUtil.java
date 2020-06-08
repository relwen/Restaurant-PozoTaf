package iam.abdoulkader.pozotaf.database;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

import iam.abdoulkader.pozotaf.data.Address;
import iam.abdoulkader.pozotaf.data.Cart;
import iam.abdoulkader.pozotaf.data.CartAddress;
import iam.abdoulkader.pozotaf.data.CartFood;
import iam.abdoulkader.pozotaf.data.CartFoodCategory;
import iam.abdoulkader.pozotaf.data.CartOrder;
import iam.abdoulkader.pozotaf.data.CartResto;
import iam.abdoulkader.pozotaf.data.Food;
import iam.abdoulkader.pozotaf.data.FoodCategory;
import iam.abdoulkader.pozotaf.data.Order;
import iam.abdoulkader.pozotaf.data.Resto;
import iam.abdoulkader.pozotaf.data.User;


/**
 * Created by root on 06/04/17.
 */

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] ormClasses = new Class[]{User.class, Resto.class, FoodCategory.class, Food.class,
            Address.class, Order.class, CartResto.class, CartFoodCategory.class, CartFood.class, CartAddress.class,
            CartOrder.class, Cart.class};

    public static void main(String[] args) throws SQLException, IOException {
        writeConfigFile("ormlite_config.txt", ormClasses);
    }
}