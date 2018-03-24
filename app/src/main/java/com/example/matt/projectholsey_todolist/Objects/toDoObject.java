package com.example.matt.projectholsey_todolist.Objects;

/**
 * Created by Matt on 17/02/2018.
 */

public class toDoObject {

    //variables for objects
    private int ID;
    private int Title_ID;
    private boolean IsComplete =  false;
    private String ItemToDo;


    //empty constructor
    public toDoObject(){}



    //constructor with all params
    public toDoObject(int ID, int title_ID, boolean isComplete, String itemToDo) {
        this.ID = ID;
        Title_ID = title_ID;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getTitle_ID() {
        return Title_ID;
    }

    public void setTitle_ID(int title_ID) {
        this.Title_ID = title_ID;
    }
}
