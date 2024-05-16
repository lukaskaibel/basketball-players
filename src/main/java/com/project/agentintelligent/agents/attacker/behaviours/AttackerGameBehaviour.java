package com.project.agentintelligent.agents.attacker.behaviours;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.agentintelligent.Constants;
import com.project.agentintelligent.ConversationId;
import com.project.agentintelligent.GameOutcome;
import com.project.agentintelligent.agents.attacker.Attacker;

import jade.core.AID;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AttackerGameBehaviour extends SequentialBehaviour {
    private static final Logger logger = LoggerFactory.getLogger(AttackerGameBehaviour.class);
    
    private final Attacker attacker;

    public AttackerGameBehaviour(Attacker attacker) {
        super(attacker);
        this.attacker = attacker;

        addSubBehaviour(new AttackerPossessionBehaviour(attacker, this));
    }

    @Override
    public int onEnd() {
        GameOutcome attackerGameOutcome = attacker.getScore() == Constants.GAME_SCORE_LIMIT ? GameOutcome.ATTACKER_WINS : GameOutcome.UNDETERMINED;
        sendAttackerGameOutcome(attackerGameOutcome);
        GameOutcome defenderGameOutcome = awaitDefenderGameOutcome();

        if (attackerGameOutcome == GameOutcome.ATTACKER_WINS) {
            logger.info("ATTACKER: I won 🥳🥳");
            attacker.resetGame();
            return super.onEnd();
        } else if (defenderGameOutcome == GameOutcome.DEFENDER_WINS) {
            attacker.resetGame();
            return super.onEnd();
        } else {
            attacker.addBehaviour(new AttackerGameBehaviour(attacker));
            return super.onEnd();
        }
    }

    private GameOutcome awaitDefenderGameOutcome() {
        MessageTemplate gameOutcomeTemplate = MessageTemplate.and(
            MessageTemplate.MatchSender(new AID("Defender", AID.ISLOCALNAME)), 
            MessageTemplate.MatchConversationId(ConversationId.GAME_OUTCOME)
        );

        ACLMessage gameOutcomeMessage = myAgent.blockingReceive(gameOutcomeTemplate);
        logger.debug("ATTACKER: Game Outcome: " + gameOutcomeMessage.getContent());

        return GameOutcome.valueOf(GameOutcome.class, gameOutcomeMessage.getContent());
    }

    private void sendAttackerGameOutcome(GameOutcome gameOutcome) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID("Defender", AID.ISLOCALNAME));
        msg.setConversationId(ConversationId.GAME_OUTCOME);
        msg.setContent(gameOutcome.name());
        myAgent.send(msg);
    }

}

