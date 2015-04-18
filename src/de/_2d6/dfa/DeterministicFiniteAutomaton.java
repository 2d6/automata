package de._2d6.dfa;

import java.util.HashMap;

/**
 * @author 2d6 Implements a deterministic finite automaton. For further
 *         information, see
 *         https://en.wikipedia.org/wiki/Deterministic_finite_automaton
 */
public class DeterministicFiniteAutomaton {

	private HashMap<String,State> states;
	private State currentState;
	private State startingState;
	private TransitionFunction transitionFunction;

	/**
	 * Creates a Deterministic Finite Automaton instance
	 */
	public DeterministicFiniteAutomaton() {
		this.states = new HashMap<String,State>();
		this.transitionFunction = new TransitionFunction();
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
	 * Adds a state to the automaton. If the automaton already possesses a state with
	 * identical identifier, the existing state is overwritten. If this is the first state to be added to the
	 * automaton, it is set as the starting state.
	 * 
	 * @param identifier
	 *            The identifier of the state to be added
	 * @param isAccepting
	 *            True if the identifier to be added is accepting
	 */
	public void addState(String identifier, boolean isAccepting) {
		State state = new State(identifier, isAccepting);
		
		states.put(identifier, state);
		if (currentState == null) {
			this.currentState = state;
		}
		if (startingState == null) {
			this.startingState = state;
		}
	}

	/**
	 * Evaluates a certain input to the automaton according to its innate logic.
	 * 
	 * @param input
	 *            A String to be evaluated by the automaton
	 */
	public void evaluateInput(String input) {
		// Always start evaluation from starting state
		if ((this.startingState != null) && input != null) {
			currentState = startingState;
		}
		
		for (char symbol : input.toCharArray()) {
			this.currentState = transitionFunction.getNewState(currentState, symbol);
		}
	}

	/**
	 * Adds a transition rule to the transition function of the automaton.
	 * @param inputStateIdentifier The identifier of the state prior to the transition
	 * @param symbol The symbol effecting the transition
	 * @param outputStateIdentifier The identifier of the state subsequent to the transition
	 */
	public void addTransition(String inputStateIdentifier, char symbol, String outputStateIdentifier) {
		if (states.containsKey(inputStateIdentifier) && states.containsKey(outputStateIdentifier)) {
			
			State inputState = states.get(inputStateIdentifier);
			State outputState = states.get(outputStateIdentifier);
			this.transitionFunction.defineTransition(inputState, symbol, outputState);		
		}
	}

}
