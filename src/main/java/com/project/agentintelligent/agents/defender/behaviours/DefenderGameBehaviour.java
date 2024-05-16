package com.project.agentintelligent.agents.defender.behaviours;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.agentintelligent.Constants;
import com.project.agentintelligent.ConversationId;
import com.project.agentintelligent.GameOutcome;
import com.project.agentintelligent.agents.defender.Defender;

import jade.core.AID;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DefenderGameBehaviour extends SequentialBehaviour {
    private static final Logger logger = LoggerFactory.getLogger(DefenderGameBehaviour.class);
    
    private Defender defender;
    
    public DefenderGameBehaviour(Defender defender) {
        super(defender);
        this.defender = defender;
        
        addSubBehaviour(new DefenderPossessionBehaviour(defender, this));

    }

    @Override
    public int onEnd() {
        GameOutcome defenderGameOutcome = defender.getScore() == Constants.GAME_SCORE_LIMIT ? GameOutcome.DEFENDER_WINS : GameOutcome.UNDETERMINED;
        GameOutcome attackerGameOutcome = awaitAttackerGameOutcome();
        sendDefenderGameOutcome(defenderGameOutcome);

        if (defenderGameOutcome == GameOutcome.DEFENDER_WINS) {
            logger.info("DEFENDER: I won 🎉🎉🎉");
            defender.resetGame();
            return super.onEnd();
        } else if (attackerGameOutcome == GameOutcome.ATTACKER_WINS) {
            defender.resetGame();
            return super.onEnd();
        } else {
            defender.addBehaviour(new DefenderGameBehaviour(defender));
            return super.onEnd();
        }
    }

    private GameOutcome awaitAttackerGameOutcome() {
        MessageTemplate gameOutcomeTemplate = MessageTemplate.and(
            MessageTemplate.MatchSender(new AID("Attacker", AID.ISLOCALNAME)), 
            MessageTemplate.MatchConversationId(ConversationId.GAME_OUTCOME)
        );

        ACLMessage gameOutcomeMessage = myAgent.blockingReceive(gameOutcomeTemplate);
        logger.debug("DEFENDER: Game Outcome: " + gameOutcomeMessage.getContent());

        return GameOutcome.valueOf(GameOutcome.class, gameOutcomeMessage.getContent());
    }

    private void sendDefenderGameOutcome(GameOutcome gameOutcome) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID("Attacker", AID.ISLOCALNAME));
        msg.setContent(gameOutcome.name()); // Convert enum to string
        msg.setConversationId(ConversationId.GAME_OUTCOME);
        myAgent.send(msg);
    }
}
