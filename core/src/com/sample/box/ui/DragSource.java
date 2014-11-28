package com.sample.box.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.*;
import com.sample.box.ui.actor.ImageActor;

public class DragSource extends Source {

    private ImageActor iActor;

    public DragSource(ImageActor iActor) {
        super(iActor);
        this.iActor = iActor;
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
        ImageActor actor = new ImageActor(iActor.getImage());
        //add actor as object to payload
        payload.setObject(actor);
        // visualisation of dragable actor
        payload.setDragActor(actor);

        return payload;
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
        ImageActor oActor = (ImageActor) payload.getObject();
        TextureRegion region;
        // the payload was dropped over a valid target
        if (target != null) {
            ImageActor tActor = ((ImageActor) target.getActor());
            // if the item is the same, stack it
            if (tActor.isEmpty()) {
                tActor.setImage(oActor.getImage());
            } else {
                // the item is not the same, thus switch the items
                region = tActor.getImage();
                tActor.setImage(oActor.getImage());
                oActor.setImage(region);
            }
        } else {
//            the payload was not dropped over a target, thus put it back to where it came from
            iActor.setImage();
        }
    }

}
