package automata.interfaces;

import java.util.List;
import java.util.Set;

import automata.State;

/**
 * A nondeterministic finite automaton with epsilon transitions
 * 
 * @param <T>
 *            The type of the symbols that comprise the language of the
 *            automaton
 */
public interface INondeterministicFiniteAutomaton<T> {

	/**
	 * Add an epsilon transitions with an initial and target {@link State}
	 * 
	 * @param initialStateIdentifier
	 *            The identifier of the initial State
	 * @param targetStateIdentifier
	 *            The identifier of the target State
	 */
	public abstract void addEpsilonTransition(String initialStateIdentifier,
			String targetStateIdentifier);

	/**
	 * Evaluate a list of input symbols and return a {@link Set} of
	 * {@link State States} the automaton was in after evaluating the last
	 * symbol. If the automaton at any point reached a State where no transition
	 * had been defined for the incoming symbol, the Set contains a
	 * non-accepting default state with the output of symbol.toString() as
	 * identifier
	 * 
	 * @param input
	 * @return
	 */
	public abstract Set<State> evaluate(List<T> input);

}