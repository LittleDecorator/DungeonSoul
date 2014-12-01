package com.sample.box.ui.drag;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.*;
import com.sample.box.ui.actor.ImageActor;
import com.sample.box.ui.entity.Item;
import com.sample.box.ui.entity.Slot;


//TODO: repare all for slot
public class DragSource extends Source {

    private Slot slot;

    public DragSource(Slot slot) {
        super(slot.getActor());
        this.slot = slot;
    }

    //create payload of drag item
    @Override
    public Payload dragStart(InputEvent event, float x, float y, int pointer) {
        //check that
        if (slot.isEmpty()) {
            return null;
        }

        //create drag container
        Payload payload = new Payload();
        //hide sourceimage cell
        slot.clearSlot();
        //create source actor copy
        Slot pSlot = new Slot(slot.getItem(), slot.getAmount());
        //add actor as object to payload
        payload.setObject(pSlot);
        // visualisation of draggable actor
        payload.setDragActor(slot.getActor());
        return payload;
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
        Slot pSlot = (Slot) payload.getObject();
        // the payload was dropped over a valid target
        if (target != null) {
            Slot tSlot = ((ImageActor) target.getActor()).getSlot();
            if (tSlot.getItem() == pSlot.getItem() || tSlot.getItem() == null) {
                tSlot.add(pSlot.getItem(), pSlot.getAmount());
            } else {
                // the item is not the same, thus switch the items
                Item targetType = tSlot.getItem();
                int targetAmount = tSlot.getAmount();
                tSlot.take(targetAmount);
                tSlot.add(pSlot.getItem(), pSlot.getAmount());
                slot.add(targetType, targetAmount);
            }
        } else {
//            the payload was not dropped over a target, thus put it back to where it came from
            slot.add(pSlot.getItem(), pSlot.getAmount());
        }

    }

}
