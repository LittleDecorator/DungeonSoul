package com.sample.box.entities;

import com.sample.box.entities.dialog.Node;

import java.util.List;

public class DialogTree {

    public int startNode;
    public List<Node> middleNodes;
    public int endNode;

    public List<Node> getMiddleNodes() {
        return middleNodes;
    }

    public int getStartNode() {
        return startNode;
    }

    public int getEndNode() {
        return endNode;
    }
}
