package com.rahul.spotlight;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "files.db";
    private static final String TABLE_NAME = "files";

    private static final String FILE_NAME = "FILE_NAME";
    private static final String FILE_PATH = "FILE_PATH";
    private static final String EXTENSION = "EXTENSION";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FILES_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + FILE_PATH + " TEXT PRIMARY KEY," + FILE_NAME + " TEXT,"
                + EXTENSION + " TEXT " + ")";
        db.execSQL(CREATE_FILES_TABLE);

        String CREATE_INDEX = "CREATE INDEX ext_index ON " + TABLE_NAME + "("
                + EXTENSION + ")";
        db.execSQL(CREATE_INDEX);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertFile(Vector<Files> filesVector) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            SQLiteStatement stmt = db.compileStatement("INSERT INTO "
                    + TABLE_NAME + "(FILE_NAME,FILE_PATH,EXTENSION)"
                    + " VALUES(?,?,?)");
            for (Files f : filesVector) {
                stmt.bindString(1, f.getFileName());
                stmt.bindString(2, f.getFilePath());
                stmt.bindString(3, f.getExtension());
                long a = stmt.executeInsert();
                System.out.println("Insert result: " + a);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        db.close();
    }

    public List<Files> getFiles(String pattern) {
        List<Files> fileList = new ArrayList<Files>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " where "
                + FILE_NAME + " like '%" + pattern + "%'";

        this.close();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Files event = new Files(cursor.getString(1),
                        cursor.getString(0), cursor.getString(2));
                fileList.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return fileList;
    }

    public int deleteEvent(String path) {
        SQLiteDatabase db = this.getWritableDatabase();
       return db.delete(TABLE_NAME, FILE_PATH + " = ?",
                new String[]{String.valueOf(path)});
    }
}