package com.example.matt.projectholsey_todolist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Calendar;

import com.example.matt.projectholsey_todolist.Objects.AgendaObject;

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


    private void addTODOtoDB(String _title)
    {
        //connect to local database
        SQLiteDatabase db = this.getWritableDatabase();

        //create new content for db
        ContentValues values = new ContentValues();

        //Value Order: ID > Title > DateCreated

        //ID should be automatically chosen as it's primary key
        values.put(KEY_TITLE, _title);
        //assign the keyCreated to current datetime
        values.put(KEY_CREATED, Calendar.getInstance().getTime().toString());

        //insert values into database
        db.insert(TABLE_TITLES, null, values);
        //close database interaction
        db.close();

    }


    private void addAgendaContentstoDB(String _title, String _content)
    {
        //open connection to database
        SQLiteDatabase db = this.getWritableDatabase();

        //create new content fro database
        ContentValues values = new ContentValues();

        //create new temp ID to store ID of the AgendaObject
        int TemptoDoID = 0;

        //small query to find the id of the agendaObject from the string passed into this function
        String selectQuery = "SELECT * FROM " + TABLE_TITLES;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do {
                AgendaObject ao = new AgendaObject();

                ao.setID(Integer.parseInt(cursor.getString(0)));
                ao.setTitle(cursor.getString(1));
                //if the title is the same as string passed, assign the ID to temp id and break the loop
                if (ao.getTitle() == _title)
                {
                    TemptoDoID = ao.getID();
                    break;
                }

            }while (cursor.moveToNext());
        }

        //putting values into content
        values.put(KEY_TODO_ID, TemptoDoID);
        values.put(KEY_TODO, _content);
        //default set the isCompelete to false
        values.put(KEY_ISCOMPLETE, "false");

        //insert values into database
        db.insert(TABLE_AGENDAS, null, values);
        //close database interaction
        db.close();
    }


    /*
    TODO:

    Functionality:
    1) Add a new todoObject
        1.1) Params - String (title)
        1.2) Automatic assign new unique ID
        1.3) Record datetime created
    2) Add a new Agenda_ContentObject
        2.1) Associate the ID of the content object with todoObject
        2.2) Automatically make isSelected False
        2.3) Assign a new unique ID to the object
        2.4) Params - String (Content)
    3) Delete Agenda_contentObject
        3.1) Get the id of object to be delete
        3.2) Delete the object from database
    4) Delete todoObject
        4.1) Get ID of object to be deleted
        4.2) Remove all associated agenda_ContentObjects that have the same ID
        4.3) delete the object
    5) Editing....?



     */









}
