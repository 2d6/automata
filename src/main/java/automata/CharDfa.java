package automata;

import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Implements a deterministic finite automaton. For further information, see
 * https://en.wikipedia.org/wiki/Deterministic_finite_automaton
 * 
 * @author 2d6
 */
public class CharDfa implements DeterministicFiniteAutomaton<Character> {

	protected HashMap<String, State> states;
	protected State startingState;
	protected TransitionFunction<Character> transitionFunction;

	/**
	 * Creates a new automaton with a starting state.
	 * 
	 * @param identifier
	 *            The identifier of the starting state
	 * @param isAccepting
	 *            Acceptance status of the starting state. True if the starting
	 *            state is accepting.
	 */
	public CharDfa(String identifier, boolean isAccepting, TransitionFunction<Character> transitionFunction) {
		states = new HashMap<>();
		startingState = new State(identifier, isAccepting);
		states.put(identifier, startingState);

		this.transitionFunction = transitionFunction;
	}
	
	/* (non-Javadoc)
	 * @see automata.DeterministicFiniteAutomaton#getStartingState()
	 */
	@Override
	public State getStartingState() {
		return startingState;
	}

	/* (non-Javadoc)
	 * @see automata.DeterministicFiniteAutomaton#getState(java.lang.String)
	 */
	@Override
	public State getState(String identifier) {
		return states.get(identifier);
	}

	/* (non-Javadoc)
	 * @see automata.DeterministicFiniteAutomaton#addState(java.lang.String, boolean)
	 */
	@Override
	public void addState(String identifier, boolean isAccepting) {
		if (StringUtils.isBlank(identifier) || identifier.matches("\0*")) {
			throw new IllegalArgumentException("The identifier may not be blank");
		}
		else if (states.containsKey(identifier)) {
			throw new IllegalArgumentException(
					"The automaton already contained a state with the given identifier");
		}
		states.put(identifier, new State(identifier, isAccepting));
	}

	/* (non-Javadoc)
	 * @see automata.DeterministicFiniteAutomaton#addTransition(java.lang.String, java.lang.String, T)
	 */
	@Override
	public void addTransition(String initialStateIdentifier,
			String targetStateIdentifier, Character symbol) {
		State initialState = getState(initialStateIdentifier);
		State targetState = getState(targetStateIdentifier);
		transitionFunction.add(initialState, targetState, symbol);
	}

	/* (non-Javadoc)
	 * @see automata.DeterministicFiniteAutomaton#evaluate(T[])
	 */
	@Override
	public State evaluate(Character[] input) {
		State currentState = this.startingState;
		State nextState;
		for (Character symbol : input) {
			nextState = evaluate(currentState, symbol);
			currentState = (nextState == null) ? currentState : nextState;
		}
		return currentState;
	}
	
	/**
	 * Workaround method necessary because autoboxing of arrays is not possible
	 * @param input char Array of symbols
	 * @return the State of the automaton after evaluation of the input symbols
	 */
	public State evaluate(char[] input) {
		return evaluate(ArrayUtils.toObject(input));
	}

	/**
	 * Evaluates a single symbol according to the logic of the automaton given
	 * by its states and transition function. Returns the state the automaton
	 * was in after evaluating the symbol.
	 * 
	 * @param currentState
	 *            The state the automaton is in before evaluation
	 * @param symbol
	 *            The symbol under evaluation
	 * @return The state the automaton is in after evaluation
	 */
	protected State evaluate(State currentState, Character symbol) {
		return transitionFunction.get(currentState, symbol);
	}
}
