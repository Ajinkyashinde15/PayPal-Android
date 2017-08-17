package tennis.parks.com.parkstennis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    public static final String DB_NAME = "tennis.db";
    public static final int DB_VERSION = 1;

    public static final String USER_TABLE = "users";
    public static final String COLOUMN_ID = "_id";

    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,age TEXT,address TEXT,date TEXT,phone TEXT,email TEXT,med TEXT);";


    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_USERS);
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE" + USER_TABLE);
        onCreate(db);
    }

    //storing user details in the database
    public void insertUser(String name, String age,String address, String date,String phone, String email,String med) {
        SQLiteDatabase DataBase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", age);
        values.put("address", address);
        values.put("date", date);
        values.put("phone", phone);
        values.put("email", email);
        values.put("med", med);

        long id = DataBase.insert(USER_TABLE, null, values);
        DataBase.close();
    }

    //Seeing if the user exists

    public Cursor getUser() {

        String selectQuery = "select * from users";

        SQLiteDatabase DataBase = this.getReadableDatabase();
        Cursor cursor = DataBase.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return cursor;
        }
        cursor.close();
        DataBase.close();

        return null;
    }
}

