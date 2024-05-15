package com.project.agentintelligent.agents.defender.behaviours;

import com.project.agentintelligent.PossessionOutcome;
import com.project.agentintelligent.agents.defender.Defender;

import jade.core.behaviours.OneShotBehaviour;

public class DefenderEvaluatePossessionOutcome extends OneShotBehaviour {
    private Defender defender;

    public DefenderEvaluatePossessionOutcome(Defender defender) {
        super(defender);
        this.defender = defender;
    }

    @Override
    public void action() {
        // Evaluate the game situation outcome
        if (defender.getLastPossessionOutcome() == PossessionOutcome.DEFENDER_BLOCKS 
            || defender.getLastPossessionOutcome() == PossessionOutcome.DEFENDER_STEALS
        ) {
            defender.setScore(defender.getScore() + 1);
            System.out.println("DEFENDER: Scored a point!");
            this.possessionOver();
        } else if (defender.getLastPossessionOutcome() == PossessionOutcome.ATTACKER_SCORES) {
            this.possessionOver();
        }
    }

    private void possessionOver() {
        
    }
}
