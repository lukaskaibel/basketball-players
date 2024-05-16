package com.project.agentintelligent.agents.attacker;

import com.project.agentintelligent.PossessionOutcome;
import com.project.agentintelligent.agents.attacker.behaviours.AttackerGameBehaviour;
import com.project.agentintelligent.agents.attacker.state.AttackerAction;
import com.project.agentintelligent.agents.attacker.state.AttackerState;
import com.project.agentintelligent.agents.defender.state.DefenderAction;
import com.project.agentintelligent.agents.defender.state.DefenderState;

import jade.core.Agent;

public class Attacker extends Agent {
    private AttackerState state;
    private int score;
    private DefenderState defenderState;
    private PossessionOutcome lastPossessionOutcome;

    public Attacker() {
        this.state = new AttackerState(AttackerAction.IDLE, 5);
        this.score = 0;
    }

    protected void setup() {
        addBehaviour(new AttackerGameBehaviour(this));
    }

    public void resetPossession() {
        this.state = new AttackerState(AttackerAction.IDLE, 5);
        this.defenderState = new DefenderState(DefenderAction.IDLE, 4);
        this.lastPossessionOutcome = PossessionOutcome.UNDETERMINED;
    }

    public void resetGame() {
        this.score = 0;
        this.resetPossession();
    }

    public AttackerState getAttackerState() {
        return state;
    }

    public void setAttackerState(AttackerState state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public DefenderState getDefenderState() {
        return defenderState;
    }

    public void setDefenderState(DefenderState defenderState) {
        this.defenderState = defenderState;
    }

    public PossessionOutcome getPossessionOutcome() {
        return lastPossessionOutcome;
    }

    public void setPossessionOutcome(PossessionOutcome possessionOutcome) {
        this.lastPossessionOutcome = possessionOutcome;
    }

}
