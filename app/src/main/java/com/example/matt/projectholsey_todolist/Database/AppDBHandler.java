package com.example.matt.projectholsey_todolist.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Matt on 17/02/2018.
 */

public class AppDBHandler extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TO_DO_LIST_DB";//"exerciseDatabase.db";

    //Titles table name
    private static final String TABLE_TITLES = "Title";
    //Agendas table name
    private static final String TABLE_AGENDAS = "Agendas";

    //Column(s) Across multiple Tables
    private static final String KEY_ID = "id";

    //Columns in Title Table
    private static final String KEY_TITLE = "title";
    private static final String KEY_CREATED = "created";

    //Columns in Agendas Table
    private static final String KEY_TODO_ID = "tag_id";
    private static final String KEY_TODO = "todo";
    private static final String KEY_ISCOMPLETE = "status";


    public AppDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //TABLE CREATE FUNCTIONS
    //TITLES TABLE
    private static String CREATE_TITLE_TABLE = "CREATE TABLE " + TABLE_TITLES + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_TITLE + " TEXT, " + KEY_CREATED + " DATETIME" + ")";

    //AGENDAS TABLE
    private static String CREATE_AGENDAS_TABLE = "CREATE TABLE " + TABLE_AGENDAS + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_TODO_ID + " INTEGER, " + KEY_TODO + " TEXT, " + KEY_ISCOMPLETE + " TEXT" + ")";


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating the Tables
        db.execSQL(CREATE_TITLE_TABLE);
        db.execSQL(CREATE_AGENDAS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //on upgrade drop older versions of tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TITLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AGENDAS);

        onCreate(db);
    }
}
