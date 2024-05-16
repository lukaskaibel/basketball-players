package com.project.agentintelligent.agents.defender.behaviours;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.agentintelligent.ConversationId;
import com.project.agentintelligent.PossessionOutcome;
import com.project.agentintelligent.agents.defender.Defender;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DefenderAwaitPossessionOutcome extends SimpleBehaviour {
    private static final Logger logger = LoggerFactory.getLogger(DefenderAwaitPossessionOutcome.class);
    
    private boolean done = false;
    private Defender defender;

    public DefenderAwaitPossessionOutcome(Defender defender) {
        super(defender);
        this.defender = defender;
    }

    @Override
    public void action() {
        MessageTemplate possessionOutcomeTemplate = MessageTemplate.and(
            MessageTemplate.MatchSender(new AID("Attacker", AID.ISLOCALNAME)), 
            MessageTemplate.MatchConversationId(ConversationId.ATTACKER_GAME_SITUATION_OUTCOME_INFORM)
        );

        ACLMessage possessionOutcomeMessage = myAgent.blockingReceive(possessionOutcomeTemplate);

        PossessionOutcome possessionOutcome = PossessionOutcome.valueOf(PossessionOutcome.class, possessionOutcomeMessage.getContent());
        defender.setLastPossessionOutcome(possessionOutcome);
        done = true;
    }

    @Override
    public boolean done() {
        return done;
    }
}
