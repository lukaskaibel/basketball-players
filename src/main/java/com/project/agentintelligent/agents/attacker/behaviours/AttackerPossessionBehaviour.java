package com.project.agentintelligent.agents.attacker.behaviours;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.agentintelligent.PossessionOutcome;
import com.project.agentintelligent.agents.attacker.Attacker;

import jade.core.behaviours.SequentialBehaviour;

public class AttackerPossessionBehaviour extends SequentialBehaviour {
    private static final Logger logger = LoggerFactory.getLogger(AttackerPossessionBehaviour.class);

    private final Attacker attacker;
    private final AttackerGameBehaviour attackerGameBehaviour;

    public AttackerPossessionBehaviour(Attacker attacker, final AttackerGameBehaviour attackerGameBehaviour) {
        super(attacker);
        this.attacker = attacker;
        this.attackerGameBehaviour = attackerGameBehaviour;

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
            logger.debug("ATTACKER: Possession outcome undetermined");
            attackerGameBehaviour.addSubBehaviour(new AttackerPossessionBehaviour(attacker, attackerGameBehaviour)); // Re-add the behaviour
            return super.onEnd();
        } else {
            logger.debug("ATTACKER: Possession outcome determined");
            attacker.resetPossession();
            return super.onEnd();
        }
    }
}
