package com.project.agentintelligent.agents.attacker.state;

import java.io.Serializable;

public class AmbiguousAttackerState implements Serializable {
    private static final long serialVersionUID = 1L; // Ensure compatibility between serialized objects
    private final AmbiguousAttackerAction ambiguousAction;
    private final int position;

    public AmbiguousAttackerState(AmbiguousAttackerAction ambiguousAction, int position) {
        this.ambiguousAction = ambiguousAction;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public AmbiguousAttackerAction getAmbiguousAction() {
        return ambiguousAction;
    }

    @Override
    public String toString() {
        return "AttackerAmbiguousState{" +
                "ambiguousAction=" + ambiguousAction +
                ", position=" + position +
                '}';
    }
}
