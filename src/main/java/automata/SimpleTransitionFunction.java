package automata;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a simple char-based transition function
 * 
 * @author 2d6
 *
 */
public class SimpleTransitionFunction implements TransitionFunction<Character> {

	private List<Transition> transitions;
	private Alphabet<Character> alphabet;

	/**
	 * Creates a new SimpleTransitionFunction with a given {@link Alphabet}.
	 * 
	 * @param alphabet
	 */
	public SimpleTransitionFunction(Alphabet<Character> alphabet) {
		this();
		this.alphabet = alphabet;
	}

	/**
	 * Creates a new SimpleTransitionFunction.
	 * 
	 * @param alphabet
	 */
	public SimpleTransitionFunction() {
		this.transitions = new ArrayList<Transition>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see automata.TransitionFunction#add(automata.State, automata.State,
	 * char)
	 */
	public void add(State initialState, State targetState, Character symbol) {

		boolean exists = false;
		for (Transition existingTransition : transitions) {
			if (existingTransition.getInitialState() == initialState
					&& existingTransition.getSymbol() == symbol) {
				exists = true;
				break;
			}
		}

		if (exists) {
			throw new IllegalArgumentException(
					"Transition was already defined within the transition function");
		} else if (this.alphabet == null) {
			throw new NullPointerException("Alphabet was not defined");
		} else if (!alphabet.isValid(symbol)) {
			throw new IllegalArgumentException(
					"Symbol was not defined in the alphabet");
		}

		transitions.add(new Transition(initialState, targetState, symbol));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see automata.TransitionFunction#get(automata.State, char)
	 */
	public State get(State currentState, Character symbol) {
		State state = null;

		for (Transition transition : transitions) {
			if (transition.getInitialState() == currentState
					&& transition.getSymbol() == symbol) {
				state = transition.getTargetState();
			}
		}

		return state;
	}

	@Override
	public void setAlphabet(Alphabet<Character> alphabet) {
		if (this.alphabet == null && alphabet != null) {
			this.alphabet = alphabet;
		} else if (this.alphabet != null) {
			throw new IllegalArgumentException(
					"An alphabet has already been defined");
		} else {
			throw new NullPointerException("The alphabet must not be null");
		}
	}

}
