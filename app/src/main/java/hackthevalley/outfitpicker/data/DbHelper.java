package hackthevalley.outfitpicker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import hackthevalley.outfitpicker.data.AttireContract.AttireEntry;

/**
 * Created by Thao on 2/24/18.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mDatabase.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ATTIRE_ENTRIES =
                "CREATE TABLE " + AttireEntry.TABLE_NAME + " (" +
                        AttireEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        AttireEntry.COLUMN_ATTIRE_TYPE + " TEXT, " +
                        AttireEntry.COLUMN_ATTIRE_USAGE + " TEXT);";

        db.execSQL(SQL_CREATE_ATTIRE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
