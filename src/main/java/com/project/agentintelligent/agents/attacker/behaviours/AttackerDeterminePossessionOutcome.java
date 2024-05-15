package com.project.agentintelligent.agents.attacker.behaviours;

import com.project.agentintelligent.PossessionOutcome;
import com.project.agentintelligent.agents.attacker.Attacker;
import com.project.agentintelligent.agents.attacker.state.AttackerAction;
import com.project.agentintelligent.agents.attacker.state.AttackerState;
import com.project.agentintelligent.agents.defender.state.DefenderAction;

import jade.core.behaviours.OneShotBehaviour;

public class AttackerDeterminePossessionOutcome extends OneShotBehaviour {

    private Attacker attacker;

    public AttackerDeterminePossessionOutcome(Attacker attacker) {
        super(attacker);
        this.attacker = attacker;
    }

    @Override
    public void action() {
        DefenderAction defenderAction = attacker.getDefenderState().getAction();
        int defenderPosition = attacker.getDefenderState().getPosition();
        if (attacker.getAttackerState().getAction() == AttackerAction.SHOOTING) {
            if (defenderAction == DefenderAction.BLOCKING && defenderPosition == attacker.getAttackerState().getPosition() - 1) {
                attacker.setPossessionOutcome(PossessionOutcome.DEFENDER_BLOCKS);
            } else {
                attacker.setScore(attacker.getScore() + 1);
                System.out.println("ATTACKER: Scored a point!");
                attacker.setPossessionOutcome(PossessionOutcome.ATTACKER_SCORES);
            }
            return;
        } else if (attacker.getAttackerState().getAction() == AttackerAction.DRIBBLING) {
            if (defenderAction == DefenderAction.STEALING && defenderPosition == attacker.getAttackerState().getPosition() - 1) {
                attacker.setPossessionOutcome(PossessionOutcome.DEFENDER_STEALS);
                return;
            } else {
                attacker.setAttackerState(new AttackerState(
                    attacker.getAttackerState().getAction(), 
                    attacker.getAttackerState().getPosition() - 1
                ));
            }
        }
        attacker.setPossessionOutcome(PossessionOutcome.UNDETERMINED);
    }
    
}
