package com.sample.box.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DialogXMLHelper {

    private static final String QUESTION = "Question";
    private static final String ANSWER = "Answer";

    private static FileHandle xml = null;
    private static Element root = null;
    private static Element currDialogTree = null;
    private static Element questions = null;
    private static Element answers = null;
    private static Element actions = null;

    public static String actualQuestion;
    public static Map<String, String> actualAnswers = new HashMap<String, String>();

    private static boolean overDialog = false;

    private static void loadXML() throws IOException {
        System.out.println("in load xml");
        xml = Gdx.files.internal("assets/dialog/hermit.xml");
        root = new XmlReader().parse(xml);
        questions = root.getChildByName("Questions");
        answers = root.getChildByName("Answers");
        actions = root.getChildByName("Actions");
    }

    public static void startHermitDialog() throws IOException {
        System.out.println("in start dialog");
        if (xml == null) {
            loadXML();
        }
        System.out.println("Search for npc as root");
        Array<Element> npcList = root.getChildrenByNameRecursively("NPC");
        for (Element element : npcList) {
            System.out.println("check elem id -> "+element.get("id"));
            if (element.get("id").contains("hermit")) {
                currDialogTree = element.getChildByName(QUESTION);
                System.out.println("Tree now not null");
                initDialog();
                break;
            }
        }
    }

    //wait next question for given answer
    public static void next(String id) {
        System.out.println("chat iterate for ->"+id);
        Element anchor = null;

        if (currDialogTree != null) {
            System.out.println("tree root children cou -> "+ currDialogTree.getChildCount());
            //find elem
            anchor = findAnswerInTree(currDialogTree, id);
            if(anchor!=null){
                //go further
                if (anchor.getChildCount() == 0) {
                    overDialog = true;
                } else {
                    Element node= null;
                    if(anchor.getName().contentEquals(QUESTION)){
                        node = anchor;
                    } else {
                        node = anchor.getChildByName(QUESTION);
                    }
                    getQuestion(node.get("ref"));
                    getAnswers(node.getChildrenByName(ANSWER));
                }
            } else {
                System.out.println("Can't start chat :((((");
            }
        }
    }

    private static void initDialog() {
        System.out.println("init dialog");
        next(currDialogTree.get("ref"));
    }


    //refactor to less concrete
    private static Element findAnswerInTree(Element from, String id) {
        Element el = null;
        System.out.println("check root tree element");
        if (from.get("ref").contentEquals(id)) {
            el = from;
        } else {
            System.out.println("find element "+id+" in tree");
            for (Element e : from.getChildrenByNameRecursively(ANSWER)) {
                System.out.println("cur elem ref -> "+ e.get("ref"));
                if (e.get("ref").contentEquals(id)) {
                    el = e;
                    break;
                }
            }
        }
        return el;
    }

    private static void getAnswers(Array<Element> list) {
        System.out.println("look for answer");
        actualAnswers.clear();
        //fill map
        for (Element e : list) {
            actualAnswers.put(e.get("ref"), "");
        }
        //lookup for text
        for (Element e : answers.getChildrenByName(ANSWER)) {
            if (actualAnswers.containsKey(e.get("id"))) {
                actualAnswers.put(e.get("id"), e.get("text"));
                continue;
            }
        }
        System.out.println("Answer keys ->"+actualAnswers);
    }

    //get next question text
    private static void getQuestion(String id) {
        System.out.println("look for question -> "+id);
        for (Element e : questions.getChildrenByName(QUESTION)) {
            if (e.get("id").contentEquals(id)) {
                actualQuestion = e.get("text");
                System.out.println(actualQuestion);
                break;
            }
        }
    }

}
