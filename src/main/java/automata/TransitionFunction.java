package automata;

import automata.State;

/**
 * A Transition function for char symbols
 * 
 * @author 2d6
 *
 */
public interface TransitionFunction {

	/**
	 * Adds a new transition to the transition function
	 * 
	 * @param initialState
	 *            The initial state
	 * @param targetState
	 *            The state the transition points to
	 * @param symbol
	 *            The symbol triggering the transition
	 */
	public void add(State initialState, State targetState, char symbol);

	/**
	 * Gets the output of the transition function. Should return null if no
	 * transition has been defined for the current state and symbol.
	 * 
	 * @param currentState
	 *            The current state
	 * @param symbol
	 *            The symbol being evaluated during the current state
	 * @return The target state of transition
	 */
	public State get(State currentState, char symbol);
}
