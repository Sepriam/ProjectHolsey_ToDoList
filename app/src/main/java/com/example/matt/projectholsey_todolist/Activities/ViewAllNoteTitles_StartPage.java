package com.example.matt.projectholsey_todolist.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.matt.projectholsey_todolist.R;

public class ViewAllNoteTitles_StartPage extends AppCompatActivity {

    //create listview object
    private ListView lv;

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
}
