package com.example.matt.projectholsey_todolist.Objects;

import java.util.ArrayList;
import com.example.matt.projectholsey_todolist.Database.*;


/**
 * Created by Matt on 17/02/2018.
 */

public class AgendaObject {

    //defining class / object variables
    private String Title;
    private  int ID;
    //list of agenda_ContentObjects
    private ArrayList<Agenda_ContentsObject> AgendaContents = new ArrayList<>();

    //Constructor
    AgendaObject(String _title, ArrayList<Agenda_ContentsObject> _Contents)
    {
        this.Title = _title;
        this.AgendaContents = _Contents;

        //assigning unique int
        int tempID = getLastID();
        //note - So far there's no way to re-use deleted ID's

        this.ID = tempID;

    }

    //method to get unique int
    public int getLastID()
    {
        //Create a function that will look through the list of objects
        //output the item with the biggest ID

        return 1;

    }


    //Get and set methods for the class variables
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<Agenda_ContentsObject> getAgendaContents() {
        return AgendaContents;
    }

    public void setAgendaContents(ArrayList<Agenda_ContentsObject> agendaContents) {
        AgendaContents = agendaContents;
    }
}
