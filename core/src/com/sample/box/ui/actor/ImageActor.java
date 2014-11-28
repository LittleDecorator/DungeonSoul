package com.sample.box.ui.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * extends default Image
 */
public class ImageActor extends Image {

    boolean empty = true;
    TextureRegion image;

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
}
