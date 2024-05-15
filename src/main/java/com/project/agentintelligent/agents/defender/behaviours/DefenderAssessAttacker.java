package com.project.agentintelligent.agents.defender.behaviours;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import com.project.agentintelligent.agents.attacker.state.AttackerAction;
import com.project.agentintelligent.agents.defender.Defender;
import com.project.agentintelligent.agents.defender.state.DefenderAction;

public class DefenderAssessAttacker extends SimpleBehaviour {
    
    private Defender defender;
    private boolean done = false;

    public DefenderAssessAttacker(Defender defender) {
        super(defender);
        this.defender = defender;
    }

    @Override
    public void action() {
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.addReceiver(new AID("Attacker", AID.ISLOCALNAME));
        request.setContent("Requesting your action and position");
        request.setConversationId("attacker-action-and-position");
        myAgent.send(request);

        MessageTemplate getAttackerActionTemplate = MessageTemplate.and(
            MessageTemplate.MatchSender(new AID("Attacker", AID.ISLOCALNAME)),
            MessageTemplate.MatchConversationId("attacker-action-and-position")
        );

        ACLMessage attackerActionResponse = myAgent.blockingReceive(getAttackerActionTemplate);
        System.out.println("Received defender action response: " + attackerActionResponse.getContent());

        AttackerAction attackerAction = AttackerAction.valueOf(AttackerAction.class, attackerActionResponse.getContent());

        // defender.setAttackerAction(attackerAction);

        done = true;
    }

    @Override
    public boolean done() {
        return done;
    }
}
