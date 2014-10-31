package com.sample.box.ui.handler;

import com.sample.box.ui.Slot;

public interface SlotListener {

    /**
     * Will be called whenever the slot has changed.
     */
    void hasChanged(Slot slot);

}
