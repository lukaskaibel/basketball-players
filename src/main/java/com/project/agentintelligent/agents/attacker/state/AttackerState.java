package com.project.agentintelligent.agents.attacker.state;

public class AttackerState {
    private final AttackerAction action;
    private final int position;

    public AttackerState(AttackerAction action, int position) {
        this.action = action;
        this.position = position;
    }

    public AttackerAction getAction() {
        return action;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "AttackerState{" +
                "action=" + action +
                ", position=" + position +
                '}';
    }
}
