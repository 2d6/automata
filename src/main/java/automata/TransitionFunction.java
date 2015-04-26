package automata;

import automata.State;

/**
 * A Transition function for arbitrary symbols
 * 
 * @author 2d6
 *
 */
public interface TransitionFunction<T> {

	/**
	 * Defines the alphabet of the transition function, i.e. the valid symbols.
	 * This should only be possible to call once and must be called before
	 * setting or getting the first transition.
	 * 
	 * @param alphabet
	 *            An Alphabet of the valid symbols
	 */
	public void setAlphabet(Alphabet<T> alphabet);

	/**
	 * Adds a new transition to the transition function.
	 * 
	 * @param initialState
	 *            The initial state
	 * @param targetState
	 *            The state the transition points to
	 * @param symbol
	 *            The symbol triggering the transition
	 */
	public void add(State initialState, State targetState, T symbol);

	/**
	 * Gets the output of the transition function. Should return null if no
	 * transition has been defined for the current state and symbol or if the
	 * alphabet does not contain the symbol
	 * 
	 * @param currentState
	 *            The current state
	 * @param symbol
	 *            The symbol being evaluated during the current state
	 * @return The target state of transition
	 */
	public State get(State currentState, T symbol);
}
