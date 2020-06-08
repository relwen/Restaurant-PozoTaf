package iam.abdoulkader.pozotaf.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import iam.abdoulkader.pozotaf.R;
import iam.abdoulkader.pozotaf.data.Address;
import iam.abdoulkader.pozotaf.data.Cart;
import iam.abdoulkader.pozotaf.data.CartAddress;
import iam.abdoulkader.pozotaf.data.CartFood;
import iam.abdoulkader.pozotaf.data.CartFoodCategory;
import iam.abdoulkader.pozotaf.data.CartOrder;
import iam.abdoulkader.pozotaf.data.Food;
import iam.abdoulkader.pozotaf.data.FoodCategory;
import iam.abdoulkader.pozotaf.data.Order;
import iam.abdoulkader.pozotaf.data.Resto;
import iam.abdoulkader.pozotaf.data.User;


/**
 * Created by root on 06/04/17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "pozotaf_main.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<User, Integer> mUserDao;
    private Dao<FoodCategory, Integer> mFoodCategoryDao;
    private Dao<Food, Integer> mFoodDao;
    private Dao<Resto, Integer> mRestoDao;
    private Dao<Address, Integer> mAddressDao;
    private Dao<Order, Integer> mOrderDao;

    // cart
    private Dao<CartFoodCategory, Integer> mCartFoodCategoryDao;
    private Dao<CartFood, Integer> mCartFoodDao;
    private Dao<CartAddress, Integer> mCartAddressDao;
    private Dao<CartOrder, Integer> mCartOrderDao;
    private Dao<Cart, Integer> mCartDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {

            // Create tables. This onCreate() method will be invoked only once of the application life time i.e. the first time when the application starts.
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, FoodCategory.class);
            TableUtils.createTable(connectionSource, Food.class);
            TableUtils.createTable(connectionSource, Resto.class);
            TableUtils.createTable(connectionSource, Address.class);
            TableUtils.createTable(connectionSource, Order.class);

            // cart
            TableUtils.createTable(connectionSource, CartFoodCategory.class);
            TableUtils.createTable(connectionSource, CartFood.class);
            TableUtils.createTable(connectionSource, CartAddress.class);
            TableUtils.createTable(connectionSource, Cart.class);
            TableUtils.createTable(connectionSource, CartOrder.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {

            // In case of change in database of next version of application, please increase the value of DATABASE_VERSION variable, then this method will be invoked
            //automatically. Developer needs to handle the upgrade logic here, i.e. create a new table or a new column to an existing table, take the backups of the
            // existing database etc.

            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, FoodCategory.class, true);
            TableUtils.dropTable(connectionSource, Food.class, true);
            TableUtils.dropTable(connectionSource, Resto.class, true);
            TableUtils.dropTable(connectionSource, Address.class, true);
            TableUtils.dropTable(connectionSource, Order.class, true);

            // cart
            TableUtils.dropTable(connectionSource, CartFoodCategory.class, true);
            TableUtils.dropTable(connectionSource, CartFood.class, true);
            TableUtils.dropTable(connectionSource, CartAddress.class, true);
            TableUtils.dropTable(connectionSource, CartOrder.class, true);
            TableUtils.dropTable(connectionSource, Cart.class, true);

            onCreate(sqLiteDatabase, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + i + " to new "
                    + i1, e);
        }
    }

    public void recreate() throws SQLException {

        TableUtils.dropTable(connectionSource, User.class, true);
        TableUtils.dropTable(connectionSource, FoodCategory.class, true);
        TableUtils.dropTable(connectionSource, Food.class, true);
        TableUtils.dropTable(connectionSource, Resto.class, true);
        TableUtils.dropTable(connectionSource, Address.class, true);
        TableUtils.dropTable(connectionSource, Order.class, true);

        // cart
        TableUtils.dropTable(connectionSource, CartFoodCategory.class, true);
        TableUtils.dropTable(connectionSource, CartFood.class, true);
        TableUtils.dropTable(connectionSource, CartAddress.class, true);
        TableUtils.dropTable(connectionSource, CartOrder.class, true);
        TableUtils.dropTable(connectionSource, Cart.class, true);

        /*******************************************************************/

        TableUtils.createTable(connectionSource, User.class);
        TableUtils.createTable(connectionSource, FoodCategory.class);
        TableUtils.createTable(connectionSource, Food.class);
        TableUtils.createTable(connectionSource, Resto.class);
        TableUtils.createTable(connectionSource, Address.class);
        TableUtils.createTable(connectionSource, Order.class);

        // cart
        TableUtils.createTable(connectionSource, CartFoodCategory.class);
        TableUtils.createTable(connectionSource, CartFood.class);
        TableUtils.createTable(connectionSource, CartAddress.class);
        TableUtils.createTable(connectionSource, Cart.class);
        TableUtils.createTable(connectionSource, CartOrder.class);
    }

    // Create the getDao methods of all database tables to access those from android code.
    // Insert, delete, read, update everything will be happened through DAOs

    public Dao<User, Integer> getUserDao() throws SQLException {
        if (mUserDao == null) {
            mUserDao = getDao(User.class);
        }
        return mUserDao;
    }

    public Dao<FoodCategory, Integer> getFoodCategoryDao() throws SQLException {
        if (mFoodCategoryDao == null) {
            mFoodCategoryDao = getDao(FoodCategory.class);
        }
        return mFoodCategoryDao;
    }

    public Dao<Food, Integer> getFoodDao() throws SQLException {
        if (mFoodDao == null) {
            mFoodDao = getDao(Food.class);
        }
        return mFoodDao;
    }

    public Dao<Resto, Integer> getRestoDao() throws SQLException {
        if (mRestoDao == null) {
            mRestoDao = getDao(Resto.class);
        }
        return mRestoDao;
    }

    public Dao<Address, Integer> getAddressDao() throws SQLException {
        if (mAddressDao == null) {
            mAddressDao = getDao(Address.class);
        }
        return mAddressDao;
    }

    public Dao<CartFoodCategory, Integer> getCartFoodCategoryDao() throws SQLException {
        if (mCartFoodCategoryDao == null) {
            mCartFoodCategoryDao = getDao(CartFoodCategory.class);
        }
        return mCartFoodCategoryDao;
    }

    public Dao<CartFood, Integer> getCartFoodDao() throws SQLException {
        if (mCartFoodDao == null) {
            mCartFoodDao = getDao(CartFood.class);
        }
        return mCartFoodDao;
    }

    public Dao<CartAddress, Integer> getCartAddressDao() throws SQLException {
        if (mCartAddressDao == null) {
            mCartAddressDao = getDao(CartAddress.class);
        }
        return mCartAddressDao;
    }

    public Dao<Cart, Integer> getCartDao() throws SQLException {
        if (mCartDao == null) {
            mCartDao = getDao(Cart.class);
        }
        return mCartDao;
    }
}
