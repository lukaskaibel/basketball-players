package com.project.agentintelligent.agents.defender.behaviours;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.agentintelligent.PossessionOutcome;
import com.project.agentintelligent.agents.defender.Defender;

import jade.core.behaviours.SequentialBehaviour;

public class DefenderPossessionBehaviour extends SequentialBehaviour {
    private static final Logger logger = LoggerFactory.getLogger(DefenderPossessionBehaviour.class);

    private final Defender defender;
    private final DefenderGameBehaviour defenderGameBehaviour;

    public DefenderPossessionBehaviour(final Defender defender, final DefenderGameBehaviour defenderGameBehaviour) {
        super(defender);
        this.defender = defender;
        this.defenderGameBehaviour = defenderGameBehaviour;

        addSubBehaviour(new DefenderAwaitAndAnswerStateRequest(defender));
        addSubBehaviour(new DefenderAwaitAmbiguousAttackerState(defender));
        addSubBehaviour(new DefenderPerformAction(defender));
        addSubBehaviour(new DefenderSendStateToAttacker(defender));
        addSubBehaviour(new DefenderAwaitPossessionOutcome(defender));
        addSubBehaviour(new DefenderEvaluatePossessionOutcome(defender));
    }

    @Override
    public int onEnd() {
        if (defender.getLastPossessionOutcome() == PossessionOutcome.UNDETERMINED) {
            logger.debug("DEFENDER: Possession outcome undetermined");
            defenderGameBehaviour.addSubBehaviour(new DefenderPossessionBehaviour(defender, defenderGameBehaviour)); // Re-add the behaviour
            return 0;
        } else {
            logger.debug("DEFENDER: Possession outcome determined");
            defender.resetPossession();
            return super.onEnd();
        }
    }
}
