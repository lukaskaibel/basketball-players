package com.project.agentintelligent.agents.attacker.behaviours;

import com.project.agentintelligent.PossessionOutcome;
import com.project.agentintelligent.agents.attacker.Attacker;

import jade.core.behaviours.SequentialBehaviour;

public class AttackerPossessionBehaviour extends SequentialBehaviour {

    private final Attacker attacker;

    public AttackerPossessionBehaviour(Attacker attacker) {
        super(attacker);
        this.attacker = attacker;

        addSubBehaviour(new AttackerAssessDefender(attacker));
        addSubBehaviour(new AttackerPerformAction(attacker));
        addSubBehaviour(new AttackerSendAbiguousActionToDefender(attacker));
        addSubBehaviour(new AttackerAwaitDefenderState(attacker));
        addSubBehaviour(new AttackerDeterminePossessionOutcome(attacker));
        addSubBehaviour(new AttackerSendPossessionOutcomeToDefender(attacker));
    }

    @Override
    public int onEnd() {
        if (attacker.getPossessionOutcome() == PossessionOutcome.UNDETERMINED) {
            System.out.println("ATTACKER: Possession outcome undetermined, resetting");
            attacker.addBehaviour(new AttackerPossessionBehaviour(attacker)); // Re-add the behaviour
            return super.onEnd();
        } else {
            System.out.println("ATTACKER: Possession outcome determined, finishing");
            attacker.resetPossession();
            return super.onEnd();
        }
    }
}
