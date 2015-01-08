package com.sample.box.entities.dialog;

import java.util.ArrayList;

public class Node {

    public int id;
    public String text;
    public ArrayList<Choice> choice;

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Choice> getChoice() {
        return choice;
    }
}
