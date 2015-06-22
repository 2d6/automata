package automata;

import java.util.List;
import java.util.Set;

import automata.interfaces.IDeterministicFiniteAutomaton;

/**
 * Implements a deterministic finite automaton. For further information, see
 * https://en.wikipedia.org/wiki/Deterministic_finite_automaton
 *
 * @author 2d6
 */
public class DeterministicFiniteAutomaton<T> extends AbstractFiniteAutomaton<T> implements IDeterministicFiniteAutomaton<T> {

	/**
	 * Creates a new automaton with a starting state.
	 *
	 * @param identifier
	 *            The identifier of the starting state
	 * @param isAccepting
	 *            Acceptance status of the starting state. True if the starting
	 *            state is accepting.
	 * @param symbols The alphabet of the automaton
	 */
	public DeterministicFiniteAutomaton(String identifier, boolean isAccepting,
			Set<T> symbols) {
		super(identifier, isAccepting, symbols);
	}

	/**
	 * Copy-constructor; creates a new {@link DeterministicFiniteAutomaton} with semantics identical to the
	 * supplied originalDfa
	 * 
	 * @param originalDfa
	 *            CharDfa<T> to be used as a blueprint for the new CharDfa<T>
	 */
	private DeterministicFiniteAutomaton(DeterministicFiniteAutomaton<T> originalDfa) {
		super(originalDfa);
	}
	
	/**
	 * Creates a new {@link DeterministicFiniteAutomaton} semantically identical to the current one
	 * 
	 * @return The new Dfa
	 */
	public DeterministicFiniteAutomaton<T> copy() {
		return new DeterministicFiniteAutomaton<T>(this);
	}
	
	public State evaluate(List<T> input) {
		State currentState = this.startingState;
		State nextState;
		for (T symbol : input) {
			if (!transitionFunction.getSymbols().contains(symbol)) {
				throw new IllegalArgumentException("Encountered illegal symbol: " + symbol);
			}
			nextState = evaluate(currentState, symbol);
			if (!states.values().contains(nextState)) {
				return nextState;
			}
			currentState = nextState;
		}
		return currentState;
	}
	
	/**
	 * Evaluates a single symbol according to the logic of the automaton given
	 * by its states and transition function. Returns the new state the automaton
	 * is in after evaluating the symbol, or null if no transition has been defined.
	 *
	 * @param currentState
	 *            The state the automaton is in before evaluation
	 * @param symbol
	 *            The symbol under evaluation
	 * @return The state the automaton is in after evaluation, or null if no transition
	 * has been defined.
	 */
	private State evaluate(State currentState, T symbol) {
		return transitionFunction.getNextState(currentState, symbol);
	}
	
}
