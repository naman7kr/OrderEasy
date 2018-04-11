package com.oeasy.ordereasy.Others;

/**
 * Created by Stan on 4/10/2018.
 */

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;
        import java.util.Locale;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import com.oeasy.ordereasy.Modals.FoodItem;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = DatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "OrderEasy";

    // Table Names
    private static final String TABLE_FOOD_ITEMS = "fooditem";


    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PRICE = "price";
    private static final String KEY_QUANTITY_TYPE = "quantity_type";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_QTY = "quantity";


    // Table Create Statements

    private static final String CREATE_TABLE_FOOD_ITEMS = "CREATE TABLE "
            + TABLE_FOOD_ITEMS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +KEY_NAME+" varchar(100), "+ KEY_CATEGORY +" INTEGER, "
            + KEY_DESCRIPTION+" varchar(255), " +KEY_PRICE+" float, "
            +KEY_QUANTITY_TYPE +" int, "+ KEY_IMAGE+" varchar(100), "
            +KEY_QTY+" varchar(50)"+")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_FOOD_ITEMS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_ITEMS);
        // create new tables
        onCreate(db);
    }

    public void createFoodItems(FoodItem fItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        String quary="SELECT COUNT(*) FROM "+TABLE_FOOD_ITEMS+" WHERE "+KEY_ID+"='"+fItem.getId()+"'";
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, fItem.getName());
        values.put(KEY_CATEGORY, fItem.getcategory());
        values.put(KEY_DESCRIPTION, fItem.getDesc());
        values.put(KEY_IMAGE,fItem.getImg());
        values.put(KEY_QUANTITY_TYPE,fItem.getQtyType());
       // values.put(KEY_QTY,fItem.getQty());
        values.put(KEY_PRICE,fItem.getPrice());
        // insert row
        db.insert(TABLE_FOOD_ITEMS, null, values);
        db.close();
    }

    public int countFoodItem(FoodItem fItem){
        SQLiteDatabase db = this.getWritableDatabase();
        String countQuery="SELECT * FROM "+TABLE_FOOD_ITEMS+" WHERE "+KEY_ID+"='"+fItem.getId()+"'";
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public ArrayList<FoodItem> getAllFoodItems() {
        ArrayList<FoodItem> items = new ArrayList<FoodItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_FOOD_ITEMS;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                FoodItem item = new FoodItem();
                item.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                item.setDesc((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
                item.setQtyType(c.getInt(c.getColumnIndex(KEY_QUANTITY_TYPE)));
              //  item.setQty(c.getString(c.getColumnIndex(KEY_QTY)));
                item.setcategory(c.getInt(c.getColumnIndex(KEY_CATEGORY)));
                item.setImg(c.getString(c.getColumnIndex(KEY_IMAGE)));

                // adding to todo list
                items.add(item);
            } while (c.moveToNext());
        }
        db.close();
        return items;
    }

    public void deleteFoodItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_FOOD_ITEMS+" WHERE "+KEY_ID+"='"+id+"'");
        db.close();
    }
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE * FROM "+TABLE_FOOD_ITEMS);
        db.close();
    }




}