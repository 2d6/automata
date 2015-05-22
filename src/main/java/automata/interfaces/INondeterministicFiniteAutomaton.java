package automata.interfaces;

import java.util.Set;

import automata.State;
import automata.TransitionFunction;

public interface INondeterministicFiniteAutomaton<T> extends IAutomaton<T> {
	/**
	 * @return The starting states of the automaton
	 */
	public Set<State> getStartingStates();
	
	/**
	 * Evaluates a List of symbols according to the logic of the automaton given
	 * by its {@link State}s and {@link TransitionFunction}. Returns a Set of states
	 * the automaton was in after evaluating the last symbol of the input. Each evaluation
	 * begins with the automaton in its starting states.
	 * 
	 * @param input
	 *            list of symbols to be evaluated
	 * @return The states the automaton is in after evaluating the last symbol
	 *         of the input, or a new non-accepting State object with the output
	 *         of the symbol.toString() method as its identifier.
	 */
	public Set<State> evaluate(Iterable<T> input);
	
}
