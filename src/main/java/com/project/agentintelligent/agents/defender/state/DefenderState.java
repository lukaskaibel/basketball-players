package com.project.agentintelligent.agents.defender.state;

import java.io.Serializable;

public final class DefenderState implements Serializable {
    private static final long serialVersionUID = 1L; // Ensure compatibility between serialized objects
    private final DefenderAction action;
    private final int position;

    public DefenderState(DefenderAction action, int position) {
        this.action = action;
        this.position = position;
    }

    public DefenderAction getAction() {
        return action;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "DefenderState{" +
                "action=" + action +
                ", position=" + position +
                '}';
    }

}

