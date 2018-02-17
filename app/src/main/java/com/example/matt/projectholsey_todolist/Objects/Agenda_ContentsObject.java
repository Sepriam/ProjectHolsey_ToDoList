package com.example.matt.projectholsey_todolist.Objects;

/**
 * Created by Matt on 17/02/2018.
 */

public class Agenda_ContentsObject {

    //variables for objects
    private boolean IsComplete =  false;
    private String ItemToDo;

    //constructor
    public Agenda_ContentsObject(boolean isComplete, String itemToDo) {
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
