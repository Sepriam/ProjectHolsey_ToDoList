package com.example.matt.projectholsey_todolist.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

    //request code to be passed to second act
    public static int REQUEST_CODE = 1;

    //array for titleObjects
    ArrayList<TitleObject> titleList = new ArrayList<>();

    //empty titlePage adapter
    TitlePageLVAdapter titleAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__titles__start_page);

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
        //instanciate the listview
        lv = (ListView)findViewById(R.id.TitlePage_Lv);

        //load items into the list
        loadItemsIntoList();

        //instanciate the adapter for the listview

        //create new titlePage adapter with current titleObjectList
        titleAdapter = new TitlePageLVAdapter(this, R.layout.customlv_titlepage, titleList);

        //set widget to variable
        lv = (ListView) findViewById(R.id.TitlePage_Lv);

        //set listview's adapter
        lv.setAdapter(titleAdapter);

        //create and on item clicked listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //log that the listview is being clicked
                Log.d("Title Page LV Click: ", "Item in Listview was clicked");
                //create a new intent
                Intent startNewAct = new Intent(getApplicationContext(), ViewAgendas_SecondPage.class);
                //retrieve item being clicked in list
                TitleObject titleObjectToPass = titleList.get(i);
                //pass item into intent
                startNewAct.putExtra("passBundle", titleObjectToPass);
                //start new intent
                startActivity(startNewAct);
            }
        });

    }


    public void createNewToDoObject_BtnClick(View view) {
        //create a new titleObject
        //pass the titleObject across the class
        AppDBHandler db = new AppDBHandler(this);
        //creating a new title object in table
        db.addTODOtoDB();
        //creating a new title object
        TitleObject TI = new TitleObject();
        //assigning titleobject into the last created titleobject in database
        TI = db.returnLastTitleObject();

        //starting new activity for the user to select muscle groups
        Intent i = new Intent(this, ViewAgendas_SecondPage.class);
        /*//Creating a new bundle to pass object across activities
        Bundle passBundle = new Bundle();
        //adding the object to the bundle
        passBundle.putSerializable("titleObject", TI);*/
        //adding the bundle to the intent
        i.putExtra("passBundle", TI);
        //starting the next activity
        startActivity(i);
    }
}
