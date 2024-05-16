package com.project.agentintelligent.agents.attacker.behaviours;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.agentintelligent.ConversationId;
import com.project.agentintelligent.agents.attacker.Attacker;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class AttackerSendPossessionOutcomeToDefender extends OneShotBehaviour{
    private static final Logger logger = LoggerFactory.getLogger(AttackerSendPossessionOutcomeToDefender.class);
    
    private Attacker attacker;

    public AttackerSendPossessionOutcomeToDefender(Attacker attacker) {
        super(attacker);
        this.attacker = attacker;
    }

    @Override
    public void action() {
        // Create and send the ACLMessage with the game situation outcome to the defender
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID("Defender", AID.ISLOCALNAME));
        msg.setContent(attacker.getPossessionOutcome().name()); // Convert enum to string
        msg.setConversationId(ConversationId.ATTACKER_GAME_SITUATION_OUTCOME_INFORM);
        myAgent.send(msg);

        logger.debug(attacker.getLocalName() + " sent game situation outcome: " + attacker.getPossessionOutcome() + " to Defender");
    }

}
