package com.framgia.movie06.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.framgia.movie06.data.model.Genre;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/29/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movie.db";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_GENRE = "tbl_genrefeature";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COMMAND_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_GENRE;
    public static final String COMMAND_CREATE_TABLE = "CREATE TABLE " + TABLE_GENRE + "(" +
        COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(COMMAND_DELETE_TABLE);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE);
    }

    public boolean insertData(Genre genre) {
        long result = -1;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, genre.getId());
            contentValues.put(COLUMN_NAME, genre.getName());
            result = sqLiteDatabase.insert(TABLE_GENRE, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result != -1;
    }

    public List<Genre> getAllGenre() {
        List<Genre> results = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_GENRE,
                new String[]{COLUMN_ID, COLUMN_NAME},
                null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                results = new ArrayList<>();
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    results.add(new Genre(id, name));
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return results;
    }

    public String getNameGenre(int idGenre) {
        String result = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String selection = COLUMN_ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(idGenre)};
            Cursor cursor = sqLiteDatabase.query(TABLE_GENRE,
                new String[]{COLUMN_ID, COLUMN_NAME},
                selection, selectionArgs, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }
}
