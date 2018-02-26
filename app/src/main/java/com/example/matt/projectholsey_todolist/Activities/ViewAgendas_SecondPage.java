package com.example.matt.projectholsey_todolist.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.matt.projectholsey_todolist.Adapters.ToDoPageLVAdapter;
import com.example.matt.projectholsey_todolist.Database.AppDBHandler;
import com.example.matt.projectholsey_todolist.Objects.TitleObject;
import com.example.matt.projectholsey_todolist.R;
import com.example.matt.projectholsey_todolist.Objects.toDoObject;

import java.util.ArrayList;

public class ViewAgendas_SecondPage extends AppCompatActivity {

    EditText titleString_ET;
    ListView toDo_LV;
    Button saveBtn;

    ToDoPageLVAdapter _toDoPageAdapter = null;

    //create an arrayList of to-do objects
    ArrayList<toDoObject> listOfToDoObjects =  new ArrayList<>();

    //string of the currentTitleObject
    private final String TITLEDEFAULTSTRINGVALUE = "ToDo List";

    //Current Title Object
    TitleObject TI = new TitleObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todoobjects__second_page);

        initiateWidgets();
    }


    private void initiateWidgets()
    {
        //instanciate the edittext
        titleString_ET = (EditText)findViewById(R.id.TitleInput_ET);

        //instanciate save button
        saveBtn = (Button)findViewById(R.id.SaveToDo_Btn);

        //get list of toDoObjects? -- it's empty to begin with..

        //initiating connection with db
        AppDBHandler db = new AppDBHandler(this);

        //get the extra passed from last one
        Intent recieveIntent = new Intent();

        //checking if the intent passed includes an object
        if (recieveIntent.getExtras() == null)
        {
            db.addTODOtoDB();
            titleString_ET.setHint(TITLEDEFAULTSTRINGVALUE);
            //Call a function to return the last created object in database and add here
            TI = db.returnLastTitleObject();
        }
        else
        {
            //need to add the string title to the params of this function
            db.addTODOtoDB();

            //also need to call the function to retrieve all toDoObjects associated with one passed
        }

        //create the adapter with correct item(s)
        _toDoPageAdapter = new ToDoPageLVAdapter(this, R.layout.customlv_todopage, listOfToDoObjects);

        //set widget to variable
        toDo_LV = (ListView) findViewById(R.id.ToDoPage_LV);

        //set listview's adapter
        toDo_LV.setAdapter(_toDoPageAdapter);

    }


    public void saveToDo_BtnClick(View view)
    {
        //create connection to db class
        AppDBHandler db = new AppDBHandler(this);

        //string of the current item stored as title
        String titleString;
        //assigning this string to the string currently stored in title edittext
        titleString = titleString_ET.toString();

        //cycle through all objects in list and add them to the database.
        for (toDoObject a : listOfToDoObjects)
        {
            //NEED TO RESTRICT THIS SO IT DOESN'T REPEATEDLY ADD THE SAME ITEMS TO DATABASE

            //add every object to the toDoList
            db.addAgendaContentstoDB(titleString, a.getItemToDo());
        }
        //save the item

        Intent returnResult = new Intent();
        //pass back TitleObject with its new string
        returnResult.putExtra("_result", TI);
        setResult(RESULT_OK,returnResult);
        finish();
    }


    public void addNewToDo_BtnClick(View view)
    {
        //need to add new todoObject to database
        //then load that object into listview

        //create new instance of db class
        AppDBHandler db = new AppDBHandler(this);
        //no content for time being
        db.addAgendaContentstoDB(TITLEDEFAULTSTRINGVALUE, "");
        //create a new toDoObject for List
        toDoObject newtoDoObject = new toDoObject();
        //assign to do object to last entry into database
        newtoDoObject = db.returnLastTodoObject();
        //add toDoObject to Listview
        listOfToDoObjects.add(newtoDoObject);
        //update listViewAdapter
        _toDoPageAdapter.refreshList(listOfToDoObjects);

    }
}
