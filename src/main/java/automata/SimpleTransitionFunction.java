package automata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import automata.interfaces.IAlphabet;
import automata.interfaces.ITransitionFunction;

/**
 * Implements a simple char-based transition function
 * 
 * @author 2d6
 *
 */
public class SimpleTransitionFunction implements ITransitionFunction<Character>, Cloneable {

	private List<Transition<Character>> transitions;
	private IAlphabet<Character> alphabet;

	/**
	 * Creates a new SimpleTransitionFunction with a given {@link IAlphabet}.
	 * 
	 * @param alphabet
	 */
	public SimpleTransitionFunction(Set<Character> symbols) {
		this();
		this.setSymbols(symbols);
	}

	/**
	 * Creates a new SimpleTransitionFunction.
	 * 
	 * @param alphabet
	 */
	public SimpleTransitionFunction() {
		this.transitions = new ArrayList<>();
		this.alphabet = new CharAlphabet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see automata.TransitionFunction#add(automata.State, automata.State,
	 * char)
	 */
	@Override
	public void addTransition(State initialState, State targetState, Character symbol) {

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
		} else if (this.alphabet.getSymbols().isEmpty()) {
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
	public State getNextState(State currentState, Character symbol) {
		State state = null;

		if (!alphabet.isValid(symbol)) {
			throw new IllegalArgumentException(
					"Symbol was not defined in the alphabet");
		}

		for (Transition<Character> transition : transitions) {
			if (transition.getInitialState() == currentState
					&& transition.getSymbol() == symbol) {
				state = transition.getTargetState();
				break;
			}
		}

		return state;
	}

	@Override
	public void setSymbols(Set<Character> symbols) {
		boolean alphabetIsEmpty = this.alphabet.getSymbols().isEmpty();
		
		if (alphabetIsEmpty && symbols != null) {
			this.alphabet.addAll(symbols);
		} else if (!alphabetIsEmpty) {
			throw new IllegalArgumentException(
					"An alphabet has already been defined");
		} else {
			throw new IllegalArgumentException("The list of symbols must not be null");
		}
	}
	
	@Override
	public Set<Character> getSymbols() {
		return this.alphabet.getSymbols();
	}

	@Override
	public Set<Character> getValidSymbols(State currentState) {
		Set<Character> validSymbols = new HashSet<>();
		
		for (Transition<Character> existingTransition : transitions) {
			if (existingTransition.getInitialState() == currentState) {
				validSymbols.add(existingTransition.getSymbol());
			}
		}
		return validSymbols;
	}

}
