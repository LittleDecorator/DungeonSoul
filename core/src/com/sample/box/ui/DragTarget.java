package com.sample.box.ui;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.*;
import com.sample.box.ui.actor.ImageActor;

public class DragTarget extends Target {

    private ImageActor iActor;

    public DragTarget(ImageActor iActor) {
        super(iActor);
        this.iActor = iActor;
    }

    @Override
    public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
//        Slot payloadSlot = (Slot) payload.getObject();

        // in case we drag something over this target, we highlight it a bit
//        getActor().setColor(Color.WHITE);

        // returning true means that this is a valid target
        return true;
    }

    @Override
    public void reset(Source source, Payload payload) {
//        getActor().setColor(Color.LIGHT_GRAY);
    }

    @Override
    public void drop(Source source, Payload payload, float x, float y, int pointer) {
    }
}
