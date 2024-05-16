package com.project.agentintelligent.agents.defender;

import com.project.agentintelligent.PossessionOutcome;
import com.project.agentintelligent.agents.attacker.state.AmbiguousAttackerAction;
import com.project.agentintelligent.agents.attacker.state.AmbiguousAttackerState;

import com.project.agentintelligent.agents.defender.state.DefenderAction;
import com.project.agentintelligent.agents.defender.state.DefenderState;
import com.project.agentintelligent.agents.defender.behaviours.DefenderGameBehaviour;

import jade.core.Agent;

public class Defender extends Agent {
    private int score;
    private DefenderState state;
    private AmbiguousAttackerState ambiguousAttackerState;
    private PossessionOutcome possessionOutcome;

    public Defender() {
        this.state = new DefenderState(DefenderAction.IDLE, 4);
        this.score = 0;
    }

    protected void setup() {
        addBehaviour(new DefenderGameBehaviour(this));
    }

    public void resetPossession() {
        this.setDefenderState(new DefenderState(DefenderAction.IDLE, 4));
        this.setAmbiguousAttackerState(new AmbiguousAttackerState(AmbiguousAttackerAction.IDLE, 5));
        this.possessionOutcome = PossessionOutcome.UNDETERMINED;
    }

    public void resetGame() {
        this.score = 0;
        this.resetPossession();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public DefenderState getDefenderState() {
        return state;
    }

    public void setDefenderState(DefenderState state) {
        this.state = state;
    }

    public AmbiguousAttackerState getAmbiguousAttackerState() {
        return ambiguousAttackerState;
    }

    public void setAmbiguousAttackerState(AmbiguousAttackerState ambiguousAttackerState) {
        this.ambiguousAttackerState = ambiguousAttackerState;
    }

    public PossessionOutcome getLastPossessionOutcome() {
        return possessionOutcome;
    }

    public void setLastPossessionOutcome(PossessionOutcome possessionOutcome) {
        this.possessionOutcome = possessionOutcome;
    }
   
}
