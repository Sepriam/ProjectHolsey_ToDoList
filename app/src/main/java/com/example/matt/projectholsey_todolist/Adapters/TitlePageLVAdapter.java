package com.example.matt.projectholsey_todolist.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.matt.projectholsey_todolist.Objects.TitleObject;
import com.example.matt.projectholsey_todolist.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 22/02/2018.
 */

public class TitlePageLVAdapter extends ArrayAdapter<TitleObject>{

    public ArrayList<TitleObject> _titleObjects;

    public TitlePageLVAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<TitleObject> objects) {
        super(context, resource, objects);
        this._titleObjects.addAll(objects);

    }

    public class ViewHolder {
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
            holder.deleteTitleObjectBtn = convertView.findViewById(R.id.DeleteTitle_Btn);

            //set the tag of the convert view as to grab the object again later
            convertView.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        //selecting the object from listview
        TitleObject titleObject = _titleObjects.get(position);

        if (titleObject.getTitle() == "")
        {
            Log.d("Error:", "Title object had no title string");
        }
        else
            //if here then it displays properly
            holder.displayTitleTV.setText(titleObject.getTitle());

        //set the textview text to created date
        holder.displayDataCreatedTV.setText(titleObject.getCreated());

        //set tag to the button -- As to use for on click method to delete this object
        holder.deleteTitleObjectBtn.setTag(titleObject);

        return convertView;
    }
}