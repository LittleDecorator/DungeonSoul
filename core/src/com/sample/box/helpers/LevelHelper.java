package com.sample.box.helpers;


import com.sample.box.entities.MapInfo;

public class LevelHelper {

    private static MapInfo mapInfo;

    public static void setMapInfo(MapInfo mi) {
        mapInfo = mi;
    }

    public static MapInfo getGetMapInfo(){
        return mapInfo;
    }
}
