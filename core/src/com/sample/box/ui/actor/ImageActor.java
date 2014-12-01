package com.sample.box.ui.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.sample.box.ui.entity.Slot;

/**
 * extends default Image
 * simple actor for slot
 */
public class ImageActor extends Image {

    boolean empty = true;
    TextureRegion image;
    private Slot slot;

    public ImageActor(TextureRegion texture) {
        super(texture);
        empty = false;
        image = texture;
    }

    public ImageActor() {}

    @Override
    public void setDrawable(Drawable drawable) {
        super.setDrawable(drawable);
        if(drawable==null){
            empty = true;
        } else {
            empty = false;
        }
    }

    public boolean isEmpty() {
        return empty;
    }

    public void clear(){
        if(!empty){
            setDrawable(null);
            empty = true;
        }
    }

    public TextureRegion getImage() {
        return image;
    }

    public void setImage(TextureRegion image) {
        this.image = image;
        setDrawable(new TextureRegionDrawable(image));
    }

    public void setImage() {
        setDrawable(new TextureRegionDrawable(image));
    }

    @Override
    public String toString() {
        return "ImageActor{" +
                "empty=" + empty +
                ", image=" + image +
                ", this=" + this.hashCode()+
                '}';
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }
}
