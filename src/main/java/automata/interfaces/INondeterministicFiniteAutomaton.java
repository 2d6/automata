package automata.interfaces;

import java.util.List;
import java.util.Set;

import automata.EpsilonTransition;
import automata.states.State;

/**
 * A nondeterministic finite automaton with epsilon transitions
 * 
 * @param <T>
 *            The type of the symbols that comprise the language of the
 *            automaton
 */
public interface INondeterministicFiniteAutomaton<T> extends IAbstractFiniteAutomaton<T>{

	/**
	 * Add an epsilon transitions with an initial and target {@link State}
	 * 
	 * @param initialStateIdentifier
	 *            The identifier of the initial State
	 * @param targetStateIdentifier
	 *            The identifier of the target State
	 */
	public void addEpsilonTransition(String initialStateIdentifier,
			String targetStateIdentifier);

	/**
	 * Evaluate a list of input symbols and return a {@link Set} of
	 * {@link IState IStates} the automaton was in after evaluating the last
	 * symbol. If the automaton at any point reached a State where no transition
	 * had been defined for the incoming symbol, the Set contains a
	 * non-accepting default state with the output of symbol.toString() as
	 * identifier
	 * 
	 * @param input
	 * @return
	 */
	public Set<IState> evaluate(List<T> input);
	
	/**
	 * Returns the expanded {@link IState IStates} of a given {@link IState}, i.e. the
	 * {@link Set} of IStates connected to the given IState via {@link EpsilonTransition
	 * EpsilonTransitions}. 
	 * @param state The IState
	 * @return The Set of expanded IStates
	 */
	public Set<IState> getExpandedStates(IState state);

}