package automata.interfaces;

import java.util.Set;

import automata.State;

/**
 * A Transition function for arbitrary symbols
 * 
 * @author 2d6
 *
 */
public interface ITransitionFunction<T> {

	/**
	 * Defines the alphabet of the transition function, i.e. the valid symbols.
	 * This should only be possible to call once and must be called before
	 * setting or getting the first transition.
	 * 
	 * @param symbols
	 *            A Set of the valid symbols
	 */
	public void setSymbols(Set<T> symbols);
	
	/**
	 * Returns all symbols of the transition function
	 * @return Set of the symbols
	 */
	public Set<T> getSymbols();

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
	public void addTransition(IState initialState, IState targetState, T symbol);

	/**
	 * Gets the output of the transition function. Should return null if no
	 * transition has been defined for the current {@link State} and symbol.
	 * 
	 * @param currentState
	 *            The current state
	 * @param symbol
	 *            The symbol being evaluated during the current state
	 * @return The target state of transition
	 */
	public IState getNextState(IState currentState, T symbol);
	
	/**
	 * Gets a {@link Set} of all symbols which trigger a transition for a given state
	 * @param currentState The {@link State} for which the triggering symbols should be determined
	 * @return A Set of triggering symbols
	 */
	public Set<T> getValidSymbols(IState currentState);
}
