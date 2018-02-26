package com.example.matt.projectholsey_todolist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.matt.projectholsey_todolist.Objects.TitleObject;
import com.example.matt.projectholsey_todolist.Objects.toDoObject;

/**
 * Created by Matt on 17/02/2018.
 */


/**
 * NOTE TO SELF -- TITLE ID is the tag id
 *
 */

public class AppDBHandler extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Default value for titleObject's String
    private final String TITLEDEFAULTSTRINGVALUE = "ToDo List";

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
    private static final String KEY_TITLE_ID = "tag_id";
    private static final String KEY_TODO = "todo";
    private static final String KEY_ISCOMPLETE = "status";

    //default constructor for
    public AppDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //TABLE CREATE FUNCTIONS
    //TITLES TABLE
    private static String CREATE_TITLE_TABLE = "CREATE TABLE " + TABLE_TITLES + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_TITLE + " TEXT, " + KEY_CREATED + " DATETIME" + ")";

    //AGENDAS TABLE
    private static String CREATE_AGENDAS_TABLE = "CREATE TABLE " + TABLE_AGENDAS + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_TITLE_ID + " INTEGER, " + KEY_TODO + " TEXT, " + KEY_ISCOMPLETE + " TEXT" + ")";


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


    public void addTODOtoDB(String _title) {
        //connect to local database
        SQLiteDatabase db = this.getWritableDatabase();

        //create new content for db
        ContentValues values = new ContentValues();

        //Value Order: ID > Title > DateCreated

        //ID should be automatically chosen as it's primary key
        //assign string value to that passed in params

        if (_title.equals(""))
        {
            //if there's no string passed default value to ToDoList
            values.put(KEY_TITLE, TITLEDEFAULTSTRINGVALUE);
        }
        else
            values.put(KEY_TITLE, _title);


        //assign the keyCreated to current datetime
        values.put(KEY_CREATED, Calendar.getInstance().getTime().toString());

        //insert values into database
        db.insert(TABLE_TITLES, null, values);
        //close database interaction
        db.close();

    }

    //In the case there's no string passed
    public void addTODOtoDB() {

        Log.d("No Item Passed", "No items were passed to addTitleObjectToDB. Adding Default String value");

        //connect to local database
        SQLiteDatabase db = this.getWritableDatabase();

        //create new content for db
        ContentValues values = new ContentValues();

        //Value Order: ID > Title > DateCreated

        //ID should be automatically chosen as it's primary key

        //Default the string value to ToDoList if there's none passed in params
        values.put(KEY_TITLE, TITLEDEFAULTSTRINGVALUE);
        //assign the keyCreated to current datetime
        values.put(KEY_CREATED, Calendar.getInstance().getTime().toString());

        //insert values into database
        db.insert(TABLE_TITLES, null, values);
        //close database interaction
        db.close();

    }


    public void addAgendaContentstoDB(String _title, String _content) {
        //open connection to database
        SQLiteDatabase db = this.getWritableDatabase();

        //create new content fro database
        ContentValues values = new ContentValues();

        //create new temp ID to store ID of the TitleObject
        int TemptoDoID = 0;

        //small query to find the id of the agendaObject from the string passed into this function
        String selectQuery = "SELECT * FROM " + TABLE_TITLES;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TitleObject ao = new TitleObject();

                ao.setID(Integer.parseInt(cursor.getString(0)));
                ao.setTitle(cursor.getString(1));
                //if the title is the same as string passed, assign the ID to temp id and break the loop
                if (ao.getTitle() == _title) {
                    TemptoDoID = ao.getID();
                    break;
                }

            } while (cursor.moveToNext());
        }

        //putting values into content
        values.put(KEY_TITLE_ID, TemptoDoID);
        values.put(KEY_TODO, _content);
        //default set the isCompelete to false
        values.put(KEY_ISCOMPLETE, "false");

        //insert values into database
        db.insert(TABLE_AGENDAS, null, values);
        //close database interaction
        db.close();
    }


    public void deleteTitleObject(String _title) {
        //Delete current object
        // delete all related agendaContentObjects

        String selectQuery = "SELECT * FROM " + TABLE_TITLES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //creating a temporary id as to pass to delete all associated todoObjects
        int tempID = 0;

        if (cursor.moveToFirst()) {
            do {
                //instanciating a new titleObject
                TitleObject ti = new TitleObject();

                //setting the title of the new object instance to the one currently in focus in db query
                ti.setTitle(cursor.getString(1));

                //comparing the current queries title string to the string passed
                if (ti.getTitle() == _title) {
                    //set the temp id to id of current titleobject
                    tempID = ti.getID();
                    //call the function to delete all asscoiated objects
                    deleteRelatedAgendaContentObjects(tempID, db);

                    //need to delete the current row
                    db.delete(TABLE_TITLES, KEY_ID + " = " + tempID, null);
                    //break out of loop
                    break;
                }

            } while (cursor.moveToNext());
            //moveToNext 'moves' the cursor to the next item in database until end is reached in this case
        }

        //close entry to database
        db.close();
    }


    public void deleteRelatedAgendaContentObjects(int _tempID, SQLiteDatabase db) {
        //select all from database
        //delete where item.gettodoid = tempid

        String selectQuery = "SELECT * FROM " + TABLE_AGENDAS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        //moveToFirst will 'move' cursor to first item in database
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                toDoObject td = new toDoObject();

                //ORDER = ID > TITLEID > TEXT > ISCOMPLETE
                if (Integer.parseInt(cursor.getString(1)) == _tempID) {
                    //delete the current row
                    db.delete(TABLE_AGENDAS, KEY_TITLE_ID + " = " + _tempID, null);

                }

            } while (cursor.moveToNext());
            //moveToNext 'moves' the cursor to the next item in database until end is reached in this case
        }
    }


    public void deleteToDoObject(toDoObject _tdO) {
        //select all from the table of agendas
        String selectQuery = "SELECT * FROM " + TABLE_AGENDAS;

        //create a temporary int to store the tagID of the toDoObject
        int tempID = _tdO.getTitle_ID();

        //create a connection to the database
        SQLiteDatabase db = this.getWritableDatabase();
        //situate the cursor on the first result of the query previously created
        Cursor cursor = db.rawQuery(selectQuery, null);

        //move to first result
        if (cursor.moveToFirst()) {
            do {

                //order KEY_ID  KEY_TITLE_ID  KEY_TODO KEY_ISCOMPLETE + " TEXT" + ")";

                //check if the tag id is equal to the object's tag id that was passed
                if (Integer.parseInt(cursor.getString(0)) == tempID) {
                    //if true, delete the item
                    db.delete(TABLE_AGENDAS, KEY_TITLE_ID + " = " + tempID, null);
                    //Break out the loop as single item was deleted
                    break;
                }

            } while (cursor.moveToNext());
            //moveToNext 'moves' the cursor to the next item in database until end is reached in this case
        }

    }


    public void updateContentOfToDoObject(toDoObject _tdO, String _updateToDo) {
        //select all from the table of agendas
        String selectQuery = "SELECT * FROM " + TABLE_AGENDAS;

        //create a temporary int to store the tagID of the toDoObject
        int tempID = _tdO.getTitle_ID();

        //create a connection to the database
        SQLiteDatabase db = this.getWritableDatabase();
        //situate the cursor on the first result of the query previously created
        Cursor cursor = db.rawQuery(selectQuery, null);

        //create some content to put in db
        ContentValues cv = new ContentValues();

        //move to first result
        if (cursor.moveToFirst()) {
            do {

                //if the current tagId is equal to that of the toDoObject's passed
                if (Integer.parseInt(cursor.getString(0)) == _tdO.getTitle_ID()) {
                    //putting values into content
                    cv.put(KEY_ID, _tdO.getID());
                    cv.put(KEY_TITLE_ID, _tdO.getTitle_ID());
                    //updating the value of object with the string passed in params
                    cv.put(KEY_TODO, _updateToDo);
                    cv.put(KEY_ISCOMPLETE, _tdO.isComplete());

                    //set the toDoObject's string (content) to the string passed in params
                    _tdO.setItemToDo(_updateToDo);

                    //updates the database
                    db.update(TABLE_AGENDAS, cv, KEY_ID + " = ?", new String[]{String.valueOf(_tdO.getID())});
                    //close the connection to the database
                    db.close();
                }


            } while (cursor.moveToNext());
            //moveToNext 'moves' the cursor to the next item in database until end is reached in this case
        }
    }


    public void updateBooleanOfToDoObject(toDoObject _tdO) {
        //select all from the table of agendas
        String selectQuery = "SELECT * FROM " + TABLE_AGENDAS;

        //create a temporary int to store the tagID of the toDoObject
        int tempID = _tdO.getID();

        //create a connection to the database
        SQLiteDatabase db = this.getWritableDatabase();
        //situate the cursor on the first result of the query previously created
        Cursor cursor = db.rawQuery(selectQuery, null);

        //create some content to put in db
        ContentValues cv = new ContentValues();

        //move to first result
        if (cursor.moveToFirst()) {
            do {

                //check if the current item in focus in database is equal to the one passed in params
                if (Integer.parseInt(cursor.getString(0)) == tempID) {
                    {
                        //putting values into content
                        cv.put(KEY_ID, _tdO.getID());
                        cv.put(KEY_TITLE_ID, _tdO.getTitle_ID());
                        cv.put(KEY_TODO, _tdO.getItemToDo());

                        //put the opposite value into the object dependant on what is currently selected
                        if (!_tdO.isComplete()) {
                            cv.put(KEY_ISCOMPLETE, "true");
                            _tdO.setComplete(true);
                        } else {
                            cv.put(KEY_ISCOMPLETE, "false");
                            _tdO.setComplete(false);
                        }

                        db.update(TABLE_AGENDAS, cv, KEY_ID + " = ?", new String[]{String.valueOf(_tdO.getID())});

                        //DON'T NEED TO RETURN ANYTHING AS JUST UPDATING DATABASE WITH 1 NEW VALUE
                        db.close();

                    }


                }
            } while (cursor.moveToNext());
            //moveToNext 'moves' the cursor to the next item in database until end is reached in this case
        }




    }


    public ArrayList<toDoObject> returnToDoObjects(int _titleID) {
        //select all from the table of agendas
        String selectQuery = "SELECT * FROM " + TABLE_AGENDAS;

        //create a connection to the database
        SQLiteDatabase db = this.getWritableDatabase();
        //situate the cursor on the first result of the query previously created
        Cursor cursor = db.rawQuery(selectQuery, null);

        //creating a return list of todoObjects
        ArrayList<toDoObject> returnList = new ArrayList<>();


        //move to first result
        if (cursor.moveToFirst()) {
            do {
                //condition to see if passed ID is equal to current db object's title ID
                if (Integer.parseInt(cursor.getString(1)) == _titleID)
                {
                    toDoObject tempObject = new toDoObject();
                    tempObject.setID(Integer.parseInt(cursor.getString(0)));
                    tempObject.setTitle_ID(Integer.parseInt(cursor.getString(1)));
                    tempObject.setItemToDo(cursor.getString(2));
                    tempObject.setComplete(Boolean.parseBoolean(cursor.getString(3)));

                    returnList.add(tempObject);
                }


            } while (cursor.moveToNext());
            //moveToNext 'moves' the cursor to the next item in database until end is reached in this case
        }

        //return list of toDoObjects
        return returnList;
    }


    public TitleObject returnLastTitleObject()
    {
        //creating a temporary titleObject
        TitleObject tempTitleObject = new TitleObject();

        //Creating connection to SQLite Database
        SQLiteDatabase db = this.getWritableDatabase();

        //Creating query
        String query = "SELECT * FROM " + TABLE_TITLES + " WHERE " + KEY_ID + "=( SELECT MAX(" + KEY_ID + ") FROM " + TABLE_TITLES + ")";

        //Executing query
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            //order : KEY_ID  KEY_TITLE  KEY_CREATED
            tempTitleObject.setID(Integer.parseInt(cursor.getString(0)));
            tempTitleObject.setTitle(cursor.getString(1));
            tempTitleObject.setCreated(cursor.getString(2));

        }

        return tempTitleObject;
    }


    public toDoObject returnLastTodoObject()
    {
        //creating a temporary toDoObject
        toDoObject temptoDoObject = new toDoObject();

        //Creating connection to SQLite Database
        SQLiteDatabase db = this.getWritableDatabase();

        //Creating query
        String query = "SELECT * FROM " + TABLE_AGENDAS + " WHERE " + KEY_ID + "=( SELECT MAX(" + KEY_ID + ") FROM " + TABLE_AGENDAS + ")";

        //Executing query
        Cursor cursor = db.rawQuery(query, null);

            /*
            Order: KEY_ID + KEY_TITLE_ID + KEY_TODO + KEY_ISCOMPLETE +
            */

        if (cursor.moveToFirst()) {

            //order : KEY_ID  KEY_TITLE  KEY_CREATED
            temptoDoObject.setID(Integer.parseInt(cursor.getString(0)));
            temptoDoObject.setTitle_ID(Integer.parseInt(cursor.getString(1)));
            temptoDoObject.setItemToDo(cursor.getString(2));
            temptoDoObject.setComplete(Boolean.parseBoolean(cursor.getString(3)));

        }

        return temptoDoObject;

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
    6) Add a way to update the boolean of a current item
        6.1) takes a titleID
        6.2) takes a tagID
        6.3) updates the value of the selected object
        6.4) maybe return true if function executed as expected
    7) return list of ToDoObjects
        7.1) Takes the titleId as a param
        7.2) Compares all todoObject's todoID to the param
        7.3) if true, add this object to the list
        7.4) repeat 7.3 until all objects are found
    8) Create a way to return the last object passed

     */
}
