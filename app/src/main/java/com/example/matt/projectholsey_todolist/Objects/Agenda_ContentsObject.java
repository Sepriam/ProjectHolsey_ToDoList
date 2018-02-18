package com.example.matt.projectholsey_todolist.Objects;

import java.util.ArrayList;

/**
 * Created by Matt on 17/02/2018.
 */

public class Agenda_ContentsObject {

    //variables for objects
    private int ID;
    private int tag_ID;
    private boolean IsComplete =  false;
    private String ItemToDo;


    //empty constructor
    public Agenda_ContentsObject(){}

    //constructor (without  tag_ID)
    public Agenda_ContentsObject(boolean isComplete, String itemToDo) {
        IsComplete = isComplete;
        ItemToDo = itemToDo;
    }

    //constructor (with tagID)
    public Agenda_ContentsObject(int tag_ID, boolean isComplete, String itemToDo) {
        this.tag_ID = tag_ID;
        IsComplete = isComplete;
        ItemToDo = itemToDo;
    }

    //constructor with all
    public Agenda_ContentsObject(int ID, int tag_ID, boolean isComplete, String itemToDo) {
        this.ID = ID;
        this.tag_ID = tag_ID;
        IsComplete = isComplete;
        ItemToDo = itemToDo;
    }


    //get and set methods for variables in class
    public boolean isComplete() {
        return IsComplete;
    }

    public void setComplete(boolean complete) {
        IsComplete = complete;
    }

    public String getItemToDo() {
        return ItemToDo;
    }

    public void setItemToDo(String itemToDo) {
        ItemToDo = itemToDo;
    }
}
