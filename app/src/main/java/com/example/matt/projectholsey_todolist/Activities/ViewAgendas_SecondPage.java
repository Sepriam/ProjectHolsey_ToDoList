package com.example.matt.projectholsey_todolist.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt.projectholsey_todolist.Adapters.ToDoPageLVAdapter;
import com.example.matt.projectholsey_todolist.Database.AppDBHandler;
import com.example.matt.projectholsey_todolist.Objects.TitleObject;
import com.example.matt.projectholsey_todolist.R;
import com.example.matt.projectholsey_todolist.Objects.toDoObject;

import java.util.ArrayList;

public class ViewAgendas_SecondPage extends AppCompatActivity {

    //initiate variables for widgets
    TextView titleString_TV;
    ListView toDo_LV;
    Button saveBtn;

    //create a null listview adapter
    ToDoPageLVAdapter _toDoPageAdapter = null;

    //create an arrayList of to-do objects
    ArrayList<toDoObject> listOfToDoObjects =  new ArrayList<>();

    //Current Title Object
    TitleObject TI = new TitleObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todoobjects__second_page);

        TI = new TitleObject();

        initiateWidgets();
    }

    //function to assign variables to their respective widgets
    private void initiateWidgets()
    {
        //instanciate the edittext
        titleString_TV = (TextView) findViewById(R.id.TitleInput_TV);

        //instanciate save button
        saveBtn = (Button)findViewById(R.id.SaveToDo_Btn);

        //call function to retrieve the current title object
        retrieveCurrentTitleObject();

        //call populate todoList Function
        populateToDoList(TI);

        //setting textview to title passed
        titleString_TV.setText(TI.getTitle());

        Toast.makeText(getApplicationContext(), "Current title ID = " + TI.getID(), Toast.LENGTH_SHORT).show();
    }


    /*
    function to grab the correct toDoObjects associated with the current titleObject
    Also calls the populateList() function
     */
    public void populateToDoList(TitleObject _titleObject)
    {
        //get the title's ID
        int titleId = _titleObject.getID();
        //create connection to db class
        AppDBHandler db = new AppDBHandler(this);
        //find all toDoList items that have the same titleID as the titleObject passed
        ArrayList<toDoObject> tempList = db.returnToDoObjects(titleId);
        //check if the array contains the contents
        if (tempList.size() == 0)
        {
            //log there's currently no items with this titleID
            Log.d("Populating list: ", "Currently no items with this titleID");
            populateListView();
        }
        else
        {
            //log items were found
            Log.d("Populating list: ", "Found items");
            //pass all the items found to this activities global list
            listOfToDoObjects.addAll(tempList);
            //populate the listview
            populateListView();
        }
        //close db
        db.close();
    }


    //function to initiate and populate the activity's listview
    public void populateListView()
    {
        //create the adapter with correct item(s)
        _toDoPageAdapter = new ToDoPageLVAdapter(this, R.layout.customlv_todopage, listOfToDoObjects);

        //set widget to variable
        toDo_LV = (ListView) findViewById(R.id.ToDoPage_LV);

        //set listview's adapter
        toDo_LV.setAdapter(_toDoPageAdapter);
    }


    /*
    function to retrieve the current title object
    this can be from the intent passed or from last object create in database
     */
    public void retrieveCurrentTitleObject()
    {
        //initiating connection with db
        AppDBHandler db = new AppDBHandler(this);

        Bundle retrieveBundle = getIntent().getExtras();
        TitleObject tempTitleObject = new TitleObject();
        if(retrieveBundle!=null)
        {
            //get the titleObject passed from last act
            tempTitleObject = (TitleObject) getIntent().getSerializableExtra("passBundle");

            //set to the global title object of class
            TI = tempTitleObject;
        }
        else
        {
            //assigning titleobject into the last created titleobject in database
            TI = db.returnLastTitleObject();
        }

        //close db
        db.close();
    }



    //button functionality to save the toDoList
    public void saveToDo_BtnClick(View view)
    {
        //create connection to db class
        AppDBHandler db = new AppDBHandler(this);

        //delete all the current toDoObjects with titleID in db
        db.deleteAgendaContentObjectsWithTID(TI.getID());

        //cycle through all objects in list and add them to the database.
        for (toDoObject a : listOfToDoObjects)
        {
            //creating a string to store the state of the object
            String tempState = String.valueOf(a.isComplete());

            //add every object to the toDoList
            //doesnt add because there's no item assigned to the to-do.......
            //maybe ontextUpdated??
            db.addAgendaContentstoDB(TI.getID(), a.getItemToDo(), tempState);
        }

        //close conn to db
        db.close();

        //finish the current activity
        finish();
    }

    //button functionality to add new item to ToDoList
    public void addNewToDo_BtnClick(View view)
    {
        //need to add new todoObject to database
        //then load that object into listview

        //create new instance of db class
        AppDBHandler db = new AppDBHandler(this);

        //no content for time being
        db.addAgendaContentstoDB(TI.getID(), " ", "false");

        //create a new toDoObject for List
        toDoObject newtoDoObject = db.returnLastTodoObject();
        //assign to do object to last entry into database

        //add toDoObject to Listview
        listOfToDoObjects.add(newtoDoObject);

        //update listViewAdapter
        if (listOfToDoObjects == null)
        {
            Log.d("Error: ", "Passed a null arraylist");
        }

        //refreshes the current list
        _toDoPageAdapter.refreshList(listOfToDoObjects);

        db.close();

    }
}


/*
        //TitleObject newTempTitleObject = (TitleObject) receiveIntent.getSerializableExtra("passBundle");

        //set to the global title object of class
        TI = newTempTitleObject;

        //call populate todoList Function
        populateToDoList(TI);
 */
