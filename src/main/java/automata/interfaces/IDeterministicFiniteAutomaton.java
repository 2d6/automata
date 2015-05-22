package automata.interfaces;

import java.util.Set;

import automata.State;

/**
 * A Deterministic Finite Automaton with an alphabet of type T
 * 
 * @author 2d6
 *
 * @param <T>
 */
public interface IDeterministicFiniteAutomaton<T> extends IAutomaton<T> {

	/**
	 * @return The starting state of the automaton
	 */
	public State getStartingState();

	/**
	 * Create a semantically identical copy of the automaton. The copy should
	 * share references to the same symbols as the original automaton.
	 * 
	 * @return The copied automaton
	 */
	public IDeterministicFiniteAutomaton<T> copy();

	/**
	 * Determines whether two automata are structurally identical, i.e. will
	 * evaluate identical input to identical output
	 * 
	 * @param otherCharDfa
	 *            CharDfa to compare to
	 * @return True, if the CharDfa are structurally identical
	 */
	boolean isStructurallyEqualTo(IDeterministicFiniteAutomaton<T> otherDfa);
	
	/**
	 * Returns the next state of the automaton for a given current {@link State} and symbol combination.
	 * @param currentState The current state of the automaton
	 * @param symbol The symbol being evaluated
	 * @return The next State
	 */
	public State getNextState(State currentState, T symbol);
	
	/**
	 * Returns the valid symbols for a given {@link State}, i.e. those symbols for which a transition
	 * has been defined
	 * @param currentState The current state of the automaton
	 * @return A Set of valid symbols
	 */
	public Set<T> getValidSymbols(State currentState);

}