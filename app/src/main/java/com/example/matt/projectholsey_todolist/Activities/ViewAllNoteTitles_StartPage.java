package com.example.matt.projectholsey_todolist.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matt.projectholsey_todolist.Adapters.TitlePageLVAdapter;
import com.example.matt.projectholsey_todolist.Adapters.ToDoPageLVAdapter;
import com.example.matt.projectholsey_todolist.Objects.TitleObject;
import com.example.matt.projectholsey_todolist.R;
import com.example.matt.projectholsey_todolist.Database.*;

import java.util.ArrayList;

public class ViewAllNoteTitles_StartPage extends AppCompatActivity {

    //create listview object
    private ListView lv;

    String newToDoListName = "";


    //array for titleObjects
    ArrayList<TitleObject> titleList = new ArrayList<>();

    //empty titlePage adapter
    TitlePageLVAdapter titleAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__titles__start_page);

        //instanciate the listview
        lv = (ListView)findViewById(R.id.TitlePage_Lv);

        InitiateWidgets();

    }


    private void loadItemsIntoList()
    {
        //create connection to database class
        AppDBHandler db = new AppDBHandler(this);

        //load all toDoList items into ArrayList
        titleList.addAll(db.returnTitleObjects());

        //check if there's any entries in database
        if (titleList.size() == 0)
        {
            //quickly display there's no items currently in list
            Toast.makeText(this, "Currently no items in list", Toast.LENGTH_SHORT).show();
        }
    }

    private void InitiateWidgets()
    {


        //load items into the list
        loadItemsIntoList();

        //create new titlePage adapter with current titleObjectList
        ListAdapter newAdapter = new TitlePageLVAdapter(this,
                R.layout.customlv_titlepage, titleList);

        //set listview's adapter
        lv.setAdapter(newAdapter);

        //setting an onclicklistener for the listview
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), ViewAgendas_SecondPage.class);
                TitleObject passtitleObject = titleList.get(i);
                intent.putExtra("passBundle", passtitleObject);
                startActivity(intent);
            }
        });
    }


    public void createNewToDoObject_BtnClick(View view)
    {
        //calling the prompt function to declare a new name for the titleObject
        newNamePrompt();
    }


    public void newNamePrompt()
    {
        //creating new view
        View view = (LayoutInflater.from(ViewAllNoteTitles_StartPage.this)).inflate(R.layout.new_naming_prompt, null);

        //creating a new dialog window for prompot
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ViewAllNoteTitles_StartPage.this);
        alertBuilder.setView(view);

        //assiging editText variable to widget
        final EditText userInput = (EditText) view.findViewById(R.id.newNamePromptET);

        //setting an onclick method for the positiveAction button
        alertBuilder.setCancelable(true).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //positive action means it will assign string value to global value
                newToDoListName = userInput.getText().toString();

                //call the function to start net activity
                createNewObject_Act();
            }
        });

        Dialog dialog = alertBuilder.create();
        dialog.show();



    }

    public void createNewObject_Act()
    {

        //create a new titleObject
        //pass the titleObject across the class
        AppDBHandler db = new AppDBHandler(this);

        //creating a new title object in table
        db.addTODOtoDB(newToDoListName);

        //starting new activity for the user to select muscle groups
        Intent i = new Intent(this, ViewAgendas_SecondPage.class);

        //starting the next activity
        startActivity(i);
    }


    @Override
    protected void onPostResume() {
        titleList.clear();
        InitiateWidgets();
        super.onPostResume();
    }
}
