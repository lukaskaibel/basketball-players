package com.project.agentintelligent.agents.attacker.behaviours;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.agentintelligent.ConversationId;
import com.project.agentintelligent.agents.attacker.Attacker;
import com.project.agentintelligent.agents.attacker.state.AmbiguousAttackerAction;
import com.project.agentintelligent.agents.attacker.state.AmbiguousAttackerState;
import com.project.agentintelligent.agents.attacker.state.AttackerAction;

public class AttackerSendAbiguousActionToDefender extends OneShotBehaviour {
    private static final Logger logger = LoggerFactory.getLogger(AttackerSendAbiguousActionToDefender.class);

    private Attacker attacker;

    public AttackerSendAbiguousActionToDefender(Attacker attacker) {
        super(attacker);
        this.attacker = attacker;
    }

    @Override
    public void action() {
        AmbiguousAttackerAction ambiguousAttackerAction = convertAttackerActionToAmbiguousAttackerAction(attacker.getAttackerState().getAction());
        AmbiguousAttackerState ambiguousAttackerState = new AmbiguousAttackerState(ambiguousAttackerAction, attacker.getAttackerState().getPosition());

        try {
            // Serialize the object to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(ambiguousAttackerState);
            oos.close();
            byte[] serializedObject = baos.toByteArray();

            // Send state to attacker
            ACLMessage ambiguousStateInform = new ACLMessage(ACLMessage.INFORM);
            ambiguousStateInform.addReceiver(new AID("Defender", AID.ISLOCALNAME));
            ambiguousStateInform.setByteSequenceContent(serializedObject);
            ambiguousStateInform.setConversationId(ConversationId.ATTACKER_AMBIGUOUS_ACTION_INFORM);
            logger.debug("ATTACKER: Sending ambiguous state to Defender");
            myAgent.send(ambiguousStateInform);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AmbiguousAttackerAction convertAttackerActionToAmbiguousAttackerAction(AttackerAction attackerAction) {
        switch (attackerAction) {
            case SHOOTING: case FAKE_SHOOTING:
                return AmbiguousAttackerAction.SHOOTING_OR_FAKE_SHOOTING;
            case DRIBBLING: case FAKE_DRIBBLING:
                return AmbiguousAttackerAction.DRIBBLING_OR_FAKE_DRIBBLING;
            default:
                return AmbiguousAttackerAction.IDLE;
        }
    }
    
}