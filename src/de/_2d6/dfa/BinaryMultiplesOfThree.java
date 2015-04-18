package de._2d6.dfa;

/**
 * Implements a Deterministic Finite Automaton that accepts multiples of 3 in
 * binary representation
 * 
 * @author 2d6
 *
 */
public class BinaryMultiplesOfThree extends DeterministicFiniteAutomaton {

	public BinaryMultiplesOfThree() {
		super();
		this.addState("S0", true);
		this.addState("S1", false);
		this.addState("S2", false);
		this.addTransition("S0", '1', "S1");
		this.addTransition("S1", '0', "S2");
		this.addTransition("S2", '0', "S1");
		this.addTransition("S1", '1', "S0");
	}

}
