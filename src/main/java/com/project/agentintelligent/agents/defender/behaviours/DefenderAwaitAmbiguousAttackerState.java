package com.project.agentintelligent.agents.defender.behaviours;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import com.project.agentintelligent.ConversationId;
import com.project.agentintelligent.agents.attacker.state.AmbiguousAttackerState;
import com.project.agentintelligent.agents.defender.Defender;
import com.project.agentintelligent.agents.defender.state.DefenderState;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DefenderAwaitAmbiguousAttackerState extends SimpleBehaviour {
    
    private Defender defender;
    private boolean done = false;

    public DefenderAwaitAmbiguousAttackerState(Defender defender) {
        super(defender);
        this.defender = defender;
    }

    @Override
    public void action() {
        MessageTemplate getAmbiguousAttackerStateTemplate = MessageTemplate.and(
            MessageTemplate.MatchSender(new AID("Attacker", AID.ISLOCALNAME)),
            MessageTemplate.MatchConversationId(ConversationId.ATTACKER_AMBIGUOUS_ACTION_INFORM)
        );

        ACLMessage ambiguousAttackerStateMessage = myAgent.blockingReceive(getAmbiguousAttackerStateTemplate);
        
        if (ambiguousAttackerStateMessage != null) {
            try {
                // Deserialize the object from the byte array
                byte[] serializedObject = ambiguousAttackerStateMessage.getByteSequenceContent();
                ByteArrayInputStream bais = new ByteArrayInputStream(serializedObject);
                ObjectInputStream ois = new ObjectInputStream(bais);
                AmbiguousAttackerState ambiguousAttackerState = (AmbiguousAttackerState) ois.readObject();
                ois.close();

                // Set Defender State in attacker
                System.out.println("DEFENDER: Received defender state: " + ambiguousAttackerState);
                
                defender.setAmbiguousAttackerState(ambiguousAttackerState);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        

        done = true;
    }

    @Override
    public boolean done() {
        return done;
    }
}
