package com.sample.box.helpers;

import com.sample.box.entities.Info;

public class InfoHelper {

    private static Info info;

    public static Info getInfo() {
        return info;
    }

    public static void setInfo(Info info) {
        InfoHelper.info = info;
    }
}
