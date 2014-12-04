package com.sample.box.ai;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.sample.box.character.Hermit;

/*simple move\wait ai*/
public enum HermitState implements State<Hermit> {

    WALKING() {
        @Override
        public void update(Hermit hermit) {
            System.out.println("in WALK STATE");
//                hermit.stateMachine.changeState(SLEEP);
        }
    },

    SLEEP() {
        @Override
        public void update(Hermit hermit) {
            System.out.println("in SLEEP STATE");
//            hermit.stateMachine.changeState(WALKING);
        }
    };

    @Override
    public void enter(Hermit hermit) {
    }

    @Override
    public void exit(Hermit hermit) {
    }

    @Override
    public boolean onMessage(Hermit hermit, Telegram telegram) {
        // We don't use messaging in this example
        return false;
    }

}
