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
public class TransitionFunction<T> implements ITransitionFunction<T> {

	private List<Transition<T>> transitions;
	private IAlphabet<T> alphabet;

	/**
	 * Creates a new SimpleTransitionFunction with a given {@link IAlphabet}.
	 * 
	 * @param alphabet
	 */
	public TransitionFunction(Set<T> symbols) {
		this();
		this.setSymbols(symbols);
	}

	/**
	 * Creates a new SimpleTransitionFunction.
	 * 
	 * @param alphabet
	 */
	public TransitionFunction() {
		this.transitions = new ArrayList<>();
		this.alphabet = new Alphabet<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see automata.TransitionFunction#add(automata.State, automata.State,
	 * char)
	 */
	@Override
	public void addTransition(State initialState, State targetState, T symbol) {

		for (Transition<T> existingTransition : transitions) {
			if (existingTransition.getInitialState() == initialState
					&& existingTransition.getSymbol() == symbol) {
				throw new IllegalArgumentException(
						"Transition was already defined within the transition function");
			}
		}

		if (this.alphabet.getSymbols().isEmpty()) {
			throw new NullPointerException("Alphabet was not defined");
		} else if (!alphabet.isValid(symbol)) {
			throw new IllegalArgumentException(
					"Symbol was not defined in the alphabet");
		}

		transitions.add(new Transition<>(initialState, targetState, symbol));
	}

	@Override
	public State getNextState(State currentState, T symbol) {

		if (!alphabet.isValid(symbol)) {
			throw new IllegalArgumentException(
					"Symbol was not defined in the alphabet");
		}

		for (Transition<T> transition : transitions) {
			if (transition.getInitialState() == currentState
					&& transition.getSymbol() == symbol) {
				return transition.getTargetState();
			}
		}

		return null;
	}

	@Override
	public void setSymbols(Set<T> symbols) {
		if (!this.alphabet.getSymbols().isEmpty()) {
			throw new IllegalArgumentException("An alphabet has already been defined");
		} 
		else if (symbols == null) {
			throw new IllegalArgumentException("The list of symbols must not be null");
		}
		this.alphabet.addAll(symbols);
	}
	
	@Override
	public Set<T> getSymbols() {
		return this.alphabet.getSymbols();
	}

	@Override
	public Set<T> getValidSymbols(State currentState) {
		Set<T> validSymbols = new HashSet<>();
		
		for (Transition<T> existingTransition : transitions) {
			if (existingTransition.getInitialState() == currentState) {
				validSymbols.add(existingTransition.getSymbol());
			}
		}
		return validSymbols;
	}

}
