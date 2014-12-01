package com.sample.box.ui.listeners;

import com.sample.box.ui.entity.Slot;

public interface SlotListener {

    /**
     * Will be called whenever the slot has changed.
     */
    void hasChanged(Slot slot);

}
