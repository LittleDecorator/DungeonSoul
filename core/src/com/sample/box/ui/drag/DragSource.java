package com.sample.box.ui.drag;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.*;
import com.sample.box.ui.actor.ImageActor;

public class DragSource extends Source {

    private ImageActor iActor;

    public DragSource(ImageActor actor) {
        super(actor);
        this.iActor = actor;
    }

    //create payload of drag item
    @Override
    public Payload dragStart(InputEvent event, float x, float y, int pointer) {
        //check that
        if (iActor.isEmpty()) {
            return null;
        }
        //create drag container
        Payload payload = new Payload();
        //hide sourceimage cell
        iActor.clear();
        //create source actor copy
        ImageActor pActor = new ImageActor(iActor.getItem(), iActor.getAmount());
        //add actor as object to payload
        payload.setObject(pActor);
        // visualisation of draggable actor
        payload.setDragActor(pActor);
        return payload;
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
        ImageActor oActor = (ImageActor) payload.getObject();
        // the payload was dropped over a valid target
        if (target != null) {
            ImageActor tActor = ((ImageActor) target.getActor());
            // if the item is the same, stack it
            if (!tActor.isEmpty() && tActor.getItem() == oActor.getItem()) {
                tActor.add(oActor.getItem(),oActor.getAmount());
            //if target slot is empty then just set item and amount
            } else if (tActor.isEmpty()) {
                tActor.setItem(oActor.getItem());
                tActor.setAmount(oActor.getAmount());
            // the item is not the same and we drag all amount, thus switch the items
            } else if(!tActor.isEmpty() && tActor.getItem() != oActor.getItem()){
                if(iActor.getAmount()==oActor.getAmount()){
                    iActor.setItem(tActor.getItem());
                    iActor.setAmount(tActor.getAmount());
                    tActor.setItem(oActor.getItem());
                    tActor.setAmount(oActor.getAmount());
                } else {
                    iActor.setImageFromItem();
                }
            }
        } else {
            //the payload was not dropped over a target, thus put it back to where it came from
            iActor.setImageFromItem();
        }

    }

}
