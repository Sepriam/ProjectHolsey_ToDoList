package com.example.matt.projectholsey_todolist.Objects;

import java.util.ArrayList;


/**
 * Created by Matt on 17/02/2018.
 */

public class TitleObject {

    //defining class / object variables
    private int ID;
    private String Title;
    private String Created;

    //list of agenda_ContentObjects
    private ArrayList<toDoObject> AgendaContents = new ArrayList<>();


    //empty constructor
    public TitleObject(){}

    //Constructor without ID
    public TitleObject(String _title, ArrayList<toDoObject> _Contents)    {
        this.Title = _title;
        this.AgendaContents = _Contents;

        //assigning unique int
        int tempID = getLastID();
        //note - So far there's no way to re-use deleted ID's

        this.ID = tempID;

    }

    //constructor with ID
    public TitleObject(int ID, String title, String created, ArrayList<toDoObject> agendaContents) {
        this.ID = ID;
        Title = title;
        Created = created;
        AgendaContents = agendaContents;
    }

    //Constructor without Arraylist
    public TitleObject(int ID, String title, String created) {
        this.ID = ID;
        Title = title;
        Created = created;
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

    public ArrayList<toDoObject> getAgendaContents() {
        return AgendaContents;
    }

    public void setAgendaContents(ArrayList<toDoObject> agendaContents) {
        AgendaContents = agendaContents;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }
}
