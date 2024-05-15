package com.project.agentintelligent.agents.defender.behaviours;

import jade.core.behaviours.OneShotBehaviour;

import java.util.Random;

import com.project.agentintelligent.agents.attacker.state.AmbiguousAttackerAction;
import com.project.agentintelligent.agents.attacker.state.AmbiguousAttackerState;
import com.project.agentintelligent.agents.attacker.state.AttackerAction;
import com.project.agentintelligent.agents.defender.Defender;
import com.project.agentintelligent.agents.defender.state.DefenderAction;
import com.project.agentintelligent.agents.defender.state.DefenderState;

public class DefenderPerformAction extends OneShotBehaviour {

    private Defender defender;

    public DefenderPerformAction(Defender defender) {
        super();
        this.defender = defender;
    }

    @Override
    public void action() {
        if (defender.getDefenderState().getAction() == DefenderAction.STEALING
            || defender.getDefenderState().getAction() == DefenderAction.BLOCKING
        ) {
            System.out.println("-> DEFENDER FAKED OUT");
            defender.setDefenderState(new DefenderState(
                DefenderAction.IDLE, 
                defender.getDefenderState().getPosition()
            ));
        }
        AmbiguousAttackerState ambiguousAttackerState = defender.getAmbiguousAttackerState();
        int attackerPosition = ambiguousAttackerState.getPosition();
        AmbiguousAttackerAction ambiguousAttackerAction = ambiguousAttackerState.getAmbiguousAction();

        if (attackerPosition <= defender.getDefenderState().getPosition() 
            && defender.getDefenderState().getPosition() > 0
        ) {
            defender.setDefenderState(new DefenderState(
                DefenderAction.MOVING_BACK, 
                defender.getDefenderState().getPosition() - 1
            ));
            System.out.println("-> DEFENDER " + defender.getDefenderState().getAction());
            return;
        }
        switch (ambiguousAttackerAction) {
            case SHOOTING_OR_FAKE_SHOOTING:
                defender.setDefenderState(new DefenderState(
                    chooseBlockOrIdle(), 
                    defender.getDefenderState().getPosition()
                ));
                break;
            case DRIBBLING_OR_FAKE_DRIBBLING:
                DefenderAction stealMoveBackOrIdle = chooseStealMoveBackOrIdle();
                defender.setDefenderState(new DefenderState(
                    stealMoveBackOrIdle, 
                    defender.getDefenderState().getPosition() - (stealMoveBackOrIdle == DefenderAction.MOVING_BACK ? 1 : 0)
                ));
                break;
            default:
                defender.setDefenderState(new DefenderState(
                    DefenderAction.IDLE, 
                    defender.getDefenderState().getPosition()
                ));
        }
        System.out.println("-> DEFENDER " + defender.getDefenderState().getAction());
    }

    private DefenderAction chooseBlockOrIdle() {
        DefenderAction[] defenderActions = {DefenderAction.BLOCKING, DefenderAction.IDLE};
        Random random = new Random();
        int randomDefenderActionIndex = random.nextInt(defenderActions.length);
        return defenderActions[randomDefenderActionIndex];
    }

    private DefenderAction chooseStealMoveBackOrIdle() {
        DefenderAction[] defenderActions = {DefenderAction.STEALING, DefenderAction.MOVING_BACK, DefenderAction.IDLE};
        Random random = new Random();
        int randomDefenderActionIndex = random.nextInt(defenderActions.length);
        return defenderActions[randomDefenderActionIndex];
    }
    
}
