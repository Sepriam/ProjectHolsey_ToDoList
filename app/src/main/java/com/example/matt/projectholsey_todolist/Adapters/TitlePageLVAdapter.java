package com.example.matt.projectholsey_todolist.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.matt.projectholsey_todolist.Activities.ViewAgendas_SecondPage;
import com.example.matt.projectholsey_todolist.Database.AppDBHandler;
import com.example.matt.projectholsey_todolist.Objects.TitleObject;
import com.example.matt.projectholsey_todolist.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 22/02/2018.
 */

public class TitlePageLVAdapter extends ArrayAdapter<TitleObject>{

    //set context;
    private Context context;

    //new arraylist for the titleObjects
    private ArrayList<TitleObject> _titleObjects;

    public TitlePageLVAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<TitleObject> objects) {
        super(context, resource, objects);
        if (objects.size() == 0)
        {
            //log if no items currently in list
            Log.d("TitlePage:", " No objects recieved");
            //assign arraylist
            this._titleObjects = new ArrayList<>();
        }
        else
        {
            //assign arrayList
            this._titleObjects = new ArrayList<>();
            //add all objects from the arraylist passed to adapter
            this._titleObjects.addAll(objects);
        }

    }

    //internal viewholder class
    public class ViewHolder {
        //assigning the widgets
        TextView displayTitleTV;
        TextView displayDataCreatedTV;
        ImageButton deleteTitleObjectBtn;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //create instance of viewholder internal class
        ViewHolder holder = null;

        //check if view passed is currently null
        if (convertView == null)
        {
            //creating the layoutinflater
            LayoutInflater myInflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);

            //Create the custom view from custom lv element layout file
            convertView = myInflater.inflate(R.layout.customlv_titlepage, null);

            //creating a new viewholder
            holder = new ViewHolder();
            holder.displayTitleTV = convertView.findViewById(R.id.TitleString_Tv);
            holder.displayDataCreatedTV = convertView.findViewById(R.id.DateCreated_Tv);
            // holder.deleteTitleObjectBtn = convertView.findViewById(R.id.DeleteTitle_Btn);

            //set the tag of the convert view as to grab the object again later
            convertView.setTag(holder);
        }
        else
        {
            //if the view is null then set the current view to the one assigned to the current element
            holder = (ViewHolder) convertView.getTag();
        }


        //creating the onClickListener
        /*holder.deleteTitleObjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //use the view passed  -- ie the button being clicked
                ImageButton IB = (ImageButton) view;

                //create a new titleObject and assign it to the one saved in ImageButton tag
                TitleObject TI = (TitleObject) IB.getTag();

                //create a connection to the DB
                AppDBHandler db = new AppDBHandler(getContext());

                //delete the title object -- requires the title to be passed
                db.deleteTitleObject(TI.getTitle());

                //delete the item from the listview
            }
        });*/

        //selecting the object from listview
        TitleObject titleObject = _titleObjects.get(position);

        //checking if the there's a title associated with the object
        if (titleObject.getTitle() == "")
        {
            //log an error if the title object has an empty string
            Log.d("Error:", "Title object had no title string");
        }
        else
        {
            //if here then it displays properly
            holder.displayTitleTV.setText(titleObject.getTitle());
        }

        //set the textview text to created date
        holder.displayDataCreatedTV.setText(titleObject.getCreated());

        //set tag to the button -- As to use for on click method to delete this object
       // holder.deleteTitleObjectBtn.setTag(titleObject);




        return convertView;
    }
}
