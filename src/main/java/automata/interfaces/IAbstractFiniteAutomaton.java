package automata.interfaces;

import java.util.Set;

import automata.AbstractFiniteAutomaton;
import automata.State;

public interface IAbstractFiniteAutomaton<T> {

	/**
	 * @return The starting state of the automaton
	 */
	public abstract State getStartingState();

	/**
	 * Returns the state corresponding to a given identifier, if the automaton
	 * contains such a state.
	 * 
	 * @param identifier
	 *            The identifier of the requested state
	 * @return The state corresponding to the identifier, or null if it does not
	 *         exist.
	 */
	public abstract State getState(String identifier);

	/**
	 * Adds a state with the given identifier and acceptance state to the
	 * automaton. If the automaton already contains a state with the given
	 * identifier, an IllegalArgumentException is thrown.
	 * 
	 * @param identifier
	 *            The identifier of the new state.
	 * @param isAccepting
	 *            The acceptance status of the new state. True if the state is
	 *            accepting.
	 */
	public abstract void addState(String identifier, boolean isAccepting);

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
	public abstract void addTransition(String initialStateIdentifier,
			String targetStateIdentifier, T symbol);

	/**
	 * Determines whether two automata are structurally identical, i.e. will
	 * evaluate identical input to identical output
	 * 
	 * @param otherDfa
	 *            Dfa to compare to
	 * @return True, if the CharDfa are structurally identical
	 */
	public abstract boolean isStructurallyEqualTo(
			AbstractFiniteAutomaton<T> otherDfa);

	/**
	 * Returns the next state of the automaton for a given current {@link State} and symbol combination.
	 * @param currentState The current state of the automaton
	 * @param symbol The symbol being evaluated
	 * @return The next State
	 */
	public abstract State getNextState(State currentState, T symbol);

	/**
	 * Returns the valid symbols for a given {@link State}, i.e. those symbols for which a transition
	 * has been defined
	 * @param currentState The current state of the automaton
	 * @return A Set of valid symbols
	 */
	public abstract Set<T> getValidSymbols(State currentState);

}