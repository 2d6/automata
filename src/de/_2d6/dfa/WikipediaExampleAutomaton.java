package de._2d6.dfa;

public class WikipediaExampleAutomaton {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton();
		dfa.addState("S1", true);
		dfa.addState("S2", false);
		dfa.addTransition("S1", '0', "S2");
		dfa.addTransition("S2", '0', "S1");

		dfa.evaluateInput("");
		System.out.println(String.format("Empty string is accepted: %s",dfa.isCurrentStateAccepting()));
		
		dfa.evaluateInput("00");
		System.out.println(String.format("String \"00\" is accepted: %s",dfa.isCurrentStateAccepting()));
		
		dfa.evaluateInput("01");
		System.out.println(String.format("String \"01\" is accepted: %s",dfa.isCurrentStateAccepting()));
		
		dfa.evaluateInput("010");
		System.out.println(String.format("String \"010\" is accepted: %s",dfa.isCurrentStateAccepting()));
		
		dfa.evaluateInput("01011110110");
		System.out.println(String.format("String \"01011110110\" is accepted: %s",dfa.isCurrentStateAccepting()));
		
		dfa.evaluateInput("01011110111");
		System.out.println(String.format("String \"01011110110\" is accepted: %s",dfa.isCurrentStateAccepting()));
	}
}
