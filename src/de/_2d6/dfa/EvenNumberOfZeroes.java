package de._2d6.dfa;

public class EvenNumberOfZeroes extends DeterministicFiniteAutomaton {

	/**
	 * Implements the most basic Wikipedia example of a Deterministic Finite Automaton
	 */
	public EvenNumberOfZeroes() {
		super();
		this.addState("S1", true);
		this.addState("S2", false);
		this.addTransition("S1", '0', "S2");
		this.addTransition("S2", '0', "S1");
	}

}
