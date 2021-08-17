package sg.edu.rp.c346.id20027025.l13_record_weight_new;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "L13weight.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_WEIGHT = "Weight";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_WEIGHT = "weight";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSql = "CREATE TABLE " + TABLE_WEIGHT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_WEIGHT + " INTEGER)";
        db.execSQL(createSongTableSql);
        Log.i("info", createSongTableSql + "\ncreated tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT);
        onCreate(db);
    }

    public long insertWeight(int weight, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_WEIGHT, weight);
        long result = db.insert(TABLE_WEIGHT, null, values);
        db.close();
        Log.d("SQL Insert","" + result);
        return result;
    }

    public ArrayList<Weight> getAllWeight() {
        ArrayList<Weight> weightlist = new ArrayList<Weight>();
        String selectQuery = "SELECT " + COLUMN_ID + "," + COLUMN_WEIGHT + ","
                + COLUMN_DESCRIPTION + " FROM " + TABLE_WEIGHT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int weight = cursor.getInt(1);
                String description = cursor.getString(2);

                Weight newWeight = new Weight(id, weight, description);
                weightlist.add(newWeight);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return weightlist;
    }

    public ArrayList<Weight> getAllWeightBy(int weight) {
        ArrayList<Weight> weightlist = new ArrayList<Weight>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_WEIGHT};
        String condition = COLUMN_WEIGHT + "<= ?";

        Cursor cursor;
        cursor = db.query(TABLE_WEIGHT, columns, condition, null,null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String description = cursor.getString(1);

                Weight newWeight = new Weight(id, weight, description);
                weightlist.add(newWeight);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return weightlist;
    }

    public int updateWeight(Weight data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_WEIGHT, data.getWeight());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_WEIGHT, values, condition, args);
        db.close();
        return result;
    }


    public int deleteWeight(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_WEIGHT, condition, args);
        db.close();
        return result;
    }

}
