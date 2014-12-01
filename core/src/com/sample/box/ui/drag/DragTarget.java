package com.sample.box.ui.drag;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.*;
import com.sample.box.ui.actor.ImageActor;
import com.sample.box.ui.entity.Slot;

public class DragTarget extends Target {

    private Slot slot;

    public DragTarget(Slot slot) {
        super(slot.getActor());
        this.slot = slot;
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
