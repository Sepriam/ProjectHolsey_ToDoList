package com.example.matt.projectholsey_todolist.Adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt.projectholsey_todolist.Database.AppDBHandler;
import com.example.matt.projectholsey_todolist.Objects.TitleObject;
import com.example.matt.projectholsey_todolist.Objects.toDoObject;
import com.example.matt.projectholsey_todolist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 22/02/2018.
 */

public class ToDoPageLVAdapter extends ArrayAdapter<toDoObject>{

    //arraylist of todoobjects
    ArrayList<toDoObject> toDoObject_List;

    //constructor for class that assigns the passed array list to the global one just created
    public ToDoPageLVAdapter(Context context, int resource, ArrayList<toDoObject> objects) {
        super(context, resource, objects);
        this.toDoObject_List = new ArrayList<>();
        this.toDoObject_List.addAll(objects);
    }

    //internal viewholder class
    public class ViewHolder {
        //class to hold views of the different widgets of the custom listview element
        CheckBox toDoObject_CB;
        TextView toDoObject_TV;
        ImageButton deleteToDoObject_Btn;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //create instance of viewholder internal class
        ViewHolder holder = null;

        //check if view passed is currently null
        if (convertView == null)
        {
            //creating the layoutinflater
            LayoutInflater myInflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);

            //Create the custom view from custom lv element layout file
            convertView = myInflater.inflate(R.layout.customlv_todopage, null);

            //creating a new viewholder
            holder = new ViewHolder();

            //assigning the viewholder the current widgets
            holder.toDoObject_CB = convertView.findViewById(R.id.ToDo_CB);
            holder.toDoObject_TV = convertView.findViewById(R.id.ToDo_TV);
            holder.deleteToDoObject_Btn = convertView.findViewById(R.id.DeleteToDo_Btn);

            //set the tag of the convert view as to grab the object again later
            convertView.setTag(holder);

            //creating an onclicklistener for the btn
            holder.deleteToDoObject_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //use the view passed  -- ie the button being clicked
                    ImageButton IB = (ImageButton) view;

                    //create a new titleObject and assign it to the one saved in ImageButton tag
                    toDoObject TI = (toDoObject) IB.getTag();

                    //create a connection to the DB
                    AppDBHandler db = new AppDBHandler(getContext());

                    int tempTitleID = TI.getTitle_ID();


                    //find item in list
                    for (toDoObject tempToDo : toDoObject_List)
                    {
                        //check if object is same
                        if (tempToDo.getID() == TI.getID())
                        {
                            //remove from list
                            toDoObject_List.remove(TI);
                            //delete the toDoObject object -- requires the title to be passed
                            db.deleteToDoObject(TI);
                            //refresh listview
                            refreshList(tempTitleID);
                            break;
                        }
                    }
                }
            });

            //creating an on clickListener for the checkbox
            holder.toDoObject_CB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //assign variable to view of checkbox press
                    CheckBox toDoCheckBox =  (CheckBox) view;
                    //grab the item associated with that view's checkbox
                    toDoObject TDO = (toDoObject) toDoCheckBox.getTag();
                    //set the boolean value of the item to whatever it is not currently
                    TDO.setComplete(toDoCheckBox.isChecked());

                    //make a quick toast to see if the items have different id's
                    Toast.makeText(getContext(), "TODOOBJECT ID : " + TDO.getID() + ", IS NOW " + TDO.isComplete(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        //creating the toDoObject
        toDoObject ToDoObj = new toDoObject();

        //selecting the object from listview
        if (toDoObject_List.size() == 0)
        {
            //if there's no items in the listview, log there's no current items (new TitleObject was ccreated)
            Log.d("New Title Object:", "No Items located in listview");
            ToDoObj =  toDoObject_List.get(position);
        }
        else
            ToDoObj =  toDoObject_List.get(position);

        //if statement to check whether there's something contained in the toDoObject's content string
        if(!ToDoObj.getItemToDo().equals(""))
            holder.toDoObject_TV.setText(ToDoObj.getItemToDo()); //if true, assign this value to the textview

        //set tag to the button -- As to use for on click method to delete this object
        holder.toDoObject_CB.setTag(ToDoObj);
        holder.deleteToDoObject_Btn.setTag(ToDoObj);

        //return the view created
        return convertView;
    }



    //creating a way to refresh the listview
    public void refreshList(ArrayList<toDoObject> newList)
    {
        ArrayList<toDoObject> tempList = new ArrayList<>();
        tempList.addAll(newList);
        //clearing current list
        this.toDoObject_List.clear();
        //adding new list passed
        this.toDoObject_List.addAll(tempList);
        //updating the listview
        notifyDataSetChanged();
    }


    public void refreshList(int _tempTitleID)
    {
        //connection to db Class
        AppDBHandler db = new AppDBHandler(getContext());
        //use title ID to get list of all associated toDoObjects
        ArrayList<toDoObject> newToDoList = db.returnToDoObjects(_tempTitleID);
        //refresh the dataSet with the correct list
        this.toDoObject_List.clear();
        this.toDoObject_List.addAll(newToDoList);
        notifyDataSetChanged();


    }
}
