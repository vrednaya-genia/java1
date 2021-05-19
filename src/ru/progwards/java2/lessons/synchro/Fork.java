package ru.progwards.java2.lessons.synchro;

public class Fork {
    enum State {BUSY, FREE}
    private State state;

    public Fork() {
        state = State.FREE;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
