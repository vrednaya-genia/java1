package ru.progwards.java2.lessons.synchro;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    enum State {BUSY, FREE}
    private State state;
    //private final Lock lock = new ReentrantLock();

    public Fork() {
        state = State.FREE;
    }

    public State getState() {
        //lock.lock();
        //try {
            return state;
        //}
        //finally { lock.unlock(); }
    }

    public void setState(State state) {
        //lock.lock();
        //try {
            this.state = state;
        //}
        //finally { lock.unlock(); }
    }
}
