package com.project.agentintelligent;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

import ch.qos.logback.classic.Level;

/**
 * Entry point of the application that sets up and runs JADE agents.
 */
public class App {
    public static void main(String[] args) {
        LogLevelSetter.setLoggingLevel(Level.INFO);
        // Setup the JADE runtime environment
        Runtime rt = Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.GUI, "true");

        // Create the main container where agents will live
        AgentContainer mainContainer = rt.createMainContainer(profile);

        try {
            // Start Agent1
            AgentController agent1 = mainContainer.createNewAgent("Attacker", "com.project.agentintelligent.agents.attacker.Attacker", null);
            agent1.start();

            // Start Agent2
            AgentController agent2 = mainContainer.createNewAgent("Defender", "com.project.agentintelligent.agents.defender.Defender", null);
            agent2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
