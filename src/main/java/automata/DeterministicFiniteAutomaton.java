package automata;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

/**
 * Implements a deterministic finite automaton. For further information, see
 * https://en.wikipedia.org/wiki/Deterministic_finite_automaton
 * 
 * @author 2d6
 */
public class DeterministicFiniteAutomaton<T> {

	private HashMap<String, State> states;
	private State startingState;
	private TransitionFunction<T> transitionFunction;

	/**
	 * Creates a new automaton with a starting state.
	 * 
	 * @param identifier
	 *            The identifier of the starting state
	 * @param isAccepting
	 *            Acceptance status of the starting state. True if the starting
	 *            state is accepting.
	 */
	public DeterministicFiniteAutomaton(String identifier, boolean isAccepting, TransitionFunction<T> transitionFunction) {
		states = new HashMap<>();
		startingState = new State(identifier, isAccepting);
		states.put(identifier, startingState);

		this.transitionFunction = transitionFunction;
	}
	
	/**
	 * @return The starting state of the automaton
	 */
	public State getStartingState() {
		return startingState;
	}

	/**
	 * Returns the state corresponding to a given identifier, if the automaton
	 * contains such a state.
	 * 
	 * @param identifier
	 *            The identifier of the requested state
	 * @return The state corresponding to the identifier, or null if it does not
	 *         exist.
	 */
	public State getState(String identifier) {
		return states.get(identifier);
	}

	/**
	 * Adds a state with the given identifier and acceptance state to the
	 * automaton. If the automaton already contains a state with the given
	 * identifier, an IllegalArgumentException is thrown.
	 * 
	 * @param identifier
	 *            The identifier of the new state. Must not be blank (e.g.
	 *            whitespaces) or empty.
	 * @param isAccepting
	 *            The acceptance status of the new state. True if the state is
	 *            accepting.
	 */
	public void addState(String identifier, boolean isAccepting) {
		if (StringUtils.isBlank(identifier) || identifier.matches("\0*")) {
			throw new IllegalArgumentException("The identifier may not be blank");
		}
		else if (states.containsKey(identifier)) {
			throw new IllegalArgumentException(
					"The automaton already contained a state with the given identifier");
		}
		states.put(identifier, new State(identifier, isAccepting));
	}

	/**
	 * Adds a transition from a state with a given identifier to a target state.
	 * The symbol is the trigger of the transition, i.e. the transition is only
	 * considered during evaluation if the automaton is currently in the
	 * original state of the transition and the symbol is also currently being
	 * evaluated.
	 * 
	 * @param initialStateIdentifier
	 *            The state the automaton is in when before symbol is evaluated
	 * @param targetStateIdentifier
	 *            The state the automaton should transfer to after the symbol
	 *            has been evaluated
	 * @param symbol
	 *            The symbol, i.e. trigger of the transition
	 */
	public void addTransition(String initialStateIdentifier,
			String targetStateIdentifier, T symbol) {
		State initialState = getState(initialStateIdentifier);
		State targetState = getState(targetStateIdentifier);
		transitionFunction.add(initialState, targetState, symbol);
	}

	/**
	 * Evaluates a string according to the logic of the automaton given by its
	 * states and transition function. Returns the state the automaton was in
	 * after evaluating the last symbol of the input. Each evaluation begins
	 * with the automaton in its starting state.
	 * 
	 * @param input
	 *            String to be evaluated
	 * @return The state the automaton was in after evaluating the last symbol
	 *         of the input.
	 */
	public State evaluate(T[] input) {
		State currentState = this.startingState;
		for (T symbol : input) {
			currentState = evaluate(currentState, symbol);
		}
		return currentState;
	}

	/**
	 * Evaluates a single symbol according to the logic of the automaton given
	 * by its states and transition function. Returns the state the automaton
	 * was in after evaluating the symbol.
	 * 
	 * @param currentState
	 *            The state the automaton is in before evaluation
	 * @param symbol
	 *            The symbol under evaluation
	 * @return The state the automaton is in after evaluation
	 */
	private State evaluate(State currentState, T symbol) {
		return transitionFunction.get(currentState, symbol);
	}
}
