package automata;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a simple char-based transition function
 * 
 * @author 2d6
 *
 */
public class SimpleTransitionFunction implements TransitionFunction<Character>, Cloneable {

	private List<Transition<Character>> transitions;
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
		this.transitions = new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see automata.TransitionFunction#add(automata.State, automata.State,
	 * char)
	 */
	@Override
	public void add(State initialState, State targetState, Character symbol) {

		boolean exists = false;
		for (Transition<Character> existingTransition : transitions) {
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

		transitions.add(new Transition<>(initialState, targetState, symbol));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see automata.TransitionFunction#get(automata.State, char)
	 */
	@Override
	public State get(State currentState, Character symbol) {
		State state = null;

		if (!alphabet.isValid(symbol)) {
			throw new IllegalArgumentException(
					"Symbol was not defined in the alphabet");
		}

		for (Transition<Character> transition : transitions) {
			if (transition.getInitialState() == currentState
					&& transition.getSymbol().equals(symbol)) {
				state = transition.getTargetState();
				break;
			}
		}

		return state;
	}

	@Override
	public void setSymbols(List<Character> symbols) {
		if (this.alphabet == null && symbols != null) {
			this.alphabet = new CharAlphabet(symbols);
		} else if (this.alphabet != null) {
			throw new IllegalArgumentException(
					"An alphabet has already been defined");
		} else {
			throw new NullPointerException("The list of symbols must not be null");
		}
	}
	
	@Override
	public List<Character> getSymbols() {
		return this.alphabet.getSymbols();
	}

}
