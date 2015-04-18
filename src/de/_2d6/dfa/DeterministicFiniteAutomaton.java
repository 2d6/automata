package de._2d6.dfa;

import java.util.LinkedList;

/**
 * @author 2d6 Implements a deterministic finite automaton. For further
 *         information, see
 *         https://en.wikipedia.org/wiki/Deterministic_finite_automaton
 */
public class DeterministicFiniteAutomaton {

	/*
	 * TODO: A LinkedList does not exclude duplicate entries by default. It
	 * would be better to use a set. However, in order to do that, one needs to
	 * override the equals() and hashCode() methods of the State object. At this
	 * stage, this would add a lot of complexity without significantly altering
	 * the behavior of the automaton. For the time being, the states are checked
	 * for equality in the addState() method.
	 */
	private LinkedList<State> states;
	private State currentState;
	private State startingState;

	/**
	 * Creates a Deterministic Finite Automaton instance
	 */
	public DeterministicFiniteAutomaton() {
		this.states = new LinkedList<State>();
	}

	/**
	 * @return The identifier of the current state of the automaton
	 */
	public String getCurrentStateIdentifier() {
		return this.currentState.getIdentifier();
	}

	/**
	 * @return The identifier of the starting state of the automaton
	 */
	public String getStartingStateIdentifier() {
		return this.startingState.getIdentifier();
	}

	/**
	 * @return True if the current state is an accepting state
	 */
	public boolean isCurrentStateAccepting() {
		return this.currentState.isAccepting();
	}

	/**
	 * Adds a state to the automaton if it does not already possess a state with
	 * identical identifier. If this is the first state to be added to the
	 * automaton, it is set as the starting state.
	 * 
	 * @param identifier
	 *            The identifier of the state to be added
	 * @param isAccepting
	 *            True if the identifier to be added is accepting
	 * @return True if the state was added to the automaton, false if the
	 *         automaton already contained a state with the same identifier
	 */
	public boolean addState(String identifier, boolean isAccepting) {
		for (State state : states) {
			if (state.getIdentifier() == identifier) {
				return false;
			}
		}

		State state = new State(identifier, isAccepting);
		if (currentState == null) {
			this.currentState = state;
		}
		if (startingState == null) {
			this.startingState = state;
		}
		states.add(state);
		return true;
	}

	/**
	 * Evaluate a certain input to the automaton according to its innate logic.
	 * 
	 * @param input
	 *            A String to be evaluated by the automaton
	 */
	public void evaluateInput(String input) {
		if ((this.startingState != null) && input != null) {
			currentState = startingState;
		}
	}

}
