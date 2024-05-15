package com.project.agentintelligent.agents.attacker.behaviours;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import com.project.agentintelligent.ConversationId;
import com.project.agentintelligent.agents.attacker.Attacker;
import com.project.agentintelligent.agents.defender.state.DefenderAction;
import com.project.agentintelligent.agents.defender.state.DefenderState;

public class AttackerAwaitDefenderState extends SimpleBehaviour {
    
    private Attacker attacker;
    private boolean done = false;

    public AttackerAwaitDefenderState(Attacker attacker) {
        super(attacker);
        this.attacker = attacker;
    }

    @Override
    public void action() {
        MessageTemplate getDefenderActionTemplate = MessageTemplate.and(
            MessageTemplate.MatchSender(new AID("Defender", AID.ISLOCALNAME)),
            MessageTemplate.MatchConversationId(ConversationId.DEFENDER_STATE_INFORM)
        );
        System.out.println("ATTACKER: Waiting for defender state response...");
        ACLMessage defenderStateResponse = myAgent.blockingReceive(getDefenderActionTemplate);

        if (defenderStateResponse != null) {
            try {
                // Deserialize the object from the byte array
                byte[] serializedObject = defenderStateResponse.getByteSequenceContent();
                ByteArrayInputStream bais = new ByteArrayInputStream(serializedObject);
                ObjectInputStream ois = new ObjectInputStream(bais);
                DefenderState defenderState = (DefenderState) ois.readObject();
                ois.close();


                // Set Defender State in attacker
                attacker.setDefenderState(defenderState);
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
