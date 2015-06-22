package automata.interfaces;

import java.util.List;

import automata.State;


/**
 * A Deterministic Finite Automaton with an alphabet of type T
 * 
 * @author 2d6
 *
 * @param <T>
 */
public interface IDeterministicFiniteAutomaton<T> extends IAbstractFiniteAutomaton<T> {

	/**
	 * Evaluates a List of symbols according to the logic of the automaton given
	 * by its states and transition function. Returns the state the automaton
	 * was in after evaluating the last symbol of the input. Each evaluation
	 * begins with the automaton in its starting state.
	 * 
	 * @param input
	 *            list of symbols to be evaluated
	 * @return The state the automaton was in after evaluating the last symbol
	 *         of the input, or a new non-accepting State object with the output
	 *         of the symbol.toString() method as its identifier.
	 */
	public abstract State evaluate(List<T> input);
	
	/**
	 * Create a semantically identical copy of the automaton. The copy should
	 * share references to the same symbols as the original automaton.
	 * 
	 * @return The copied automaton
	 */
	public IDeterministicFiniteAutomaton<T> copy();

}