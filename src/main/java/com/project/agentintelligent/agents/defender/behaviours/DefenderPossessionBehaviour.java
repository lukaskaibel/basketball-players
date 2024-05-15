package com.project.agentintelligent.agents.defender.behaviours;

import com.project.agentintelligent.PossessionOutcome;
import com.project.agentintelligent.agents.attacker.behaviours.AttackerPossessionBehaviour;
import com.project.agentintelligent.agents.defender.Defender;

import jade.core.behaviours.SequentialBehaviour;

public class DefenderPossessionBehaviour extends SequentialBehaviour {

    private final Defender defender;

    public DefenderPossessionBehaviour(Defender defender) {
        super(defender);
        this.defender = defender;

        addSubBehaviour(new DefenderAwaitAndAnswerStateRequest(defender));
        addSubBehaviour(new DefenderAwaitAmbiguousAttackerState(defender));
        addSubBehaviour(new DefenderPerformAction(defender));
        addSubBehaviour(new DefenderSendStateToAttacker(defender));
        addSubBehaviour(new DefenderAwaitPossessionOutcome(defender));
    }

    @Override
    public int onEnd() {
        if (defender.getLastPossessionOutcome() == PossessionOutcome.UNDETERMINED) {
            System.out.println("DEFENDER: Possession outcome undetermined, resetting");
            defender.addBehaviour(new DefenderPossessionBehaviour(defender)); // Re-add the behaviour
            return super.onEnd();
        } else {
            System.out.println("DEFENDER: Possession outcome determined, finishing");
            defender.resetPossession();
            return super.onEnd();
        }
    }
}
