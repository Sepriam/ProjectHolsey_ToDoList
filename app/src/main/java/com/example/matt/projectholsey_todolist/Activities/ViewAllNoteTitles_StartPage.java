package com.example.matt.projectholsey_todolist.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matt.projectholsey_todolist.R;

import java.util.ArrayList;

public class ViewAllNoteTitles_StartPage extends AppCompatActivity {

    //create listview object
    private ListView lv;

    public static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__titles__start_page);

        InitiateWidgets();

    }


    private void InitiateWidgets()
    {
        lv = (ListView)findViewById(R.id.TitlePage_Lv);



    }


    private void createNewToDoObject_BtnClick(View view)
    {
        //starting new activity for the user to select muscle groups
        Intent i = new Intent(this, ViewAgendas_SecondPage.class);
        startActivityForResult(i, REQUEST_CODE);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //checking request code is correct
        if (requestCode == REQUEST_CODE)
        {
            //checking if result code is correct
            if(resultCode == RESULT_OK)
            {

                //RECIEVING A TITLEOBJECT


                //code to happen should activity return

            }
        }
    }
}
