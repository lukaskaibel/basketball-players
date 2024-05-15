package com.project.agentintelligent.agents.defender.behaviours;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import com.project.agentintelligent.ConversationId;
import com.project.agentintelligent.agents.defender.Defender;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class DefenderSendStateToAttacker extends OneShotBehaviour {

    private Defender defender;

    public DefenderSendStateToAttacker(Defender defender) {
        super(defender);
        this.defender = defender;
    }

    @Override
    public void action() {
        try {
            // Serialize the object to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(defender.getDefenderState());
            oos.close();
            byte[] serializedObject = baos.toByteArray();

            // Send state to defender
            ACLMessage stateInform = new ACLMessage(ACLMessage.INFORM);
            stateInform.addReceiver(new AID("Attacker", AID.ISLOCALNAME));
            stateInform.setByteSequenceContent(serializedObject);
            stateInform.setConversationId(ConversationId.DEFENDER_STATE_INFORM);
            System.out.println("DEFENDER: Sending state to Attacker");
            myAgent.send(stateInform);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
