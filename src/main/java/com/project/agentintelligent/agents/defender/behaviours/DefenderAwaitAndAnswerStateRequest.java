package com.project.agentintelligent.agents.defender.behaviours;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.agentintelligent.ConversationId;
import com.project.agentintelligent.agents.defender.Defender;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DefenderAwaitAndAnswerStateRequest extends SimpleBehaviour  {
    private static final Logger logger = LoggerFactory.getLogger(DefenderAwaitAndAnswerStateRequest.class);
    
    private boolean done = false;
    private Defender defender;

    public DefenderAwaitAndAnswerStateRequest(Defender defender) {
        super(defender);
        this.defender = defender;
    }

    @Override
    public void action() {
        logger.debug("DEFENDER: Awaiting state request from Attacker");
        MessageTemplate stateRequestTemplate = MessageTemplate.and(
            MessageTemplate.MatchSender(new AID("Attacker", AID.ISLOCALNAME)),
            MessageTemplate.MatchConversationId(ConversationId.DEFENDER_STATE_REQUEST)
        );

        ACLMessage requestMessage = myAgent.blockingReceive(stateRequestTemplate);
        
        logger.debug("DEFENDER: Received state request from Attacker: " + requestMessage.getContent());
        
        try {
            // Serialize the object to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(defender.getDefenderState());
            oos.close();
            byte[] serializedObject = baos.toByteArray();

            // Send state to attacker
            ACLMessage replyMessage = requestMessage.createReply();
            replyMessage.setPerformative(ACLMessage.INFORM);
            replyMessage.setByteSequenceContent(serializedObject);
            myAgent.send(replyMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        done = true;
    }

    @Override
    public boolean done() {
        return done;
    }
}
