package com.project.agentintelligent.agents.attacker.behaviours;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jade.core.behaviours.OneShotBehaviour;

import com.project.agentintelligent.agents.attacker.Attacker;
import com.project.agentintelligent.agents.attacker.state.AttackerAction;
import com.project.agentintelligent.agents.attacker.state.AttackerState;
import com.project.agentintelligent.agents.defender.state.DefenderAction;



public class AttackerPerformAction extends OneShotBehaviour {
    private static final Logger logger = LoggerFactory.getLogger(AttackerPerformAction.class);

    private Attacker attacker;

    public AttackerPerformAction(Attacker attacker) {
        super();
        this.attacker = attacker;
    }

    @Override
    public void action() {
        DefenderAction defenderAction = attacker.getDefenderState().getAction();
        int defenderPosition = attacker.getDefenderState().getPosition();
        switch (defenderAction) {
            case BLOCKING:
                attacker.setAttackerState(new AttackerState(AttackerAction.DRIBBLING, attacker.getAttackerState().getPosition()));
                break;
            case STEALING:
                attacker.setAttackerState(new AttackerState(AttackerAction.SHOOTING, attacker.getAttackerState().getPosition()));
                break;
            default:
                if (attacker.getAttackerState().getPosition() == 0) {
                    attacker.setAttackerState(new AttackerState(AttackerAction.SHOOTING, attacker.getAttackerState().getPosition()));
                } else if (attacker.getAttackerState().getPosition() <= defenderPosition) {
                    attacker.setAttackerState(new AttackerState(AttackerAction.DRIBBLING, attacker.getAttackerState().getPosition()));
                } else if (attacker.getAttackerState().getPosition() > defenderPosition + 1){
                    attacker.setAttackerState(new AttackerState(AttackerAction.SHOOTING, attacker.getAttackerState().getPosition()));
                } else {
                    attacker.setAttackerState(new AttackerState(chooseRandomAction(), attacker.getAttackerState().getPosition()));
                }
                break;
        }
        logger.info("-> ATTACKER " + attacker.getAttackerState().getAction());
    }

    private AttackerAction chooseRandomAction() {
        AttackerAction[] attackerActions = AttackerAction.values();
        Random random = new Random();
        int randomAttackerActionIndex = random.nextInt(attackerActions.length);
        return attackerActions[randomAttackerActionIndex];
    }
    
}
