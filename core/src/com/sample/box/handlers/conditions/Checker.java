package com.sample.box.handlers.conditions;

import com.sample.box.character.Character;

/* Simple dialog and event checker */
public class Checker {

    Character character;

    //check character stat
    public boolean statCheck(String statName, int value, boolean overCap){
        return false;
    }

    // check that character has def perk
    public boolean perkCheck(int perk){
        return false;
    }

    //check item in inventory
    public boolean itemCheck(int item){
        return false;
    }

    public void test(){
        System.out.println("in test void method");
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
