package com.sample.box.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.*;
import com.sample.box.entities.DialogTree;
import com.sample.box.entities.dialog.Choice;
import com.sample.box.entities.dialog.Node;

import java.util.ArrayList;
import java.util.List;

public class DialogHelper {

    public static String actualQuestion;
    public static List<Choice> actualAnswers = new ArrayList<Choice>();

    private static boolean overDialog = false;

    private static DialogTree tree;

    public static void loadJSON(){
        System.out.println("load JSON");
        Json json = new Json();

        tree = json.fromJson(DialogTree.class,Gdx.files.internal("assets/dialog/hermit.json"));
    }

    public static void next(int id){
        System.out.println("next node id -> "+id);
        //check end dialog
        if(tree.getEndNode()==id){
            overDialog=true;
        } else {
            //look up node
            for(Node n : tree.getMiddleNodes()){
                if(n.getId()==id){
                    actualQuestion = n.getText();
                    actualAnswers.clear();
                    actualAnswers = n.getChoice();
                }
            }
        }
    }

    public static void startHermitDialog(){
        if(tree==null){
            loadJSON();
        }
        next(tree.getStartNode());
    }

    public static boolean isOverDialog() {
        return overDialog;
    }

    public static String getActualQuestion() {
        return actualQuestion;
    }

    public static List<Choice> getActualAnswers() {
        return actualAnswers;
    }
}
