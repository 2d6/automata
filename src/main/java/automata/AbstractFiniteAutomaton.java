package automata;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import automata.interfaces.ITransitionFunction;

public class AbstractFiniteAutomaton<T> {

	protected HashMap<String, State> states;
	protected State startingState;
	protected ITransitionFunction<T> transitionFunction;

	public AbstractFiniteAutomaton() {
		super();
	}

	public State getStartingState() {
		return startingState;
	}

	public State getState(String identifier) {
		return states.get(identifier);
	}

	public void addState(String identifier, boolean isAccepting) {
		if (states.containsKey(identifier)) {
			throw new IllegalArgumentException(
					"The automaton already contained a state with the given identifier");
		}
		states.put(identifier, new State(identifier, isAccepting));
	}

	public void addTransition(String initialStateIdentifier, String targetStateIdentifier, T symbol) {
		State initialState = getState(initialStateIdentifier);
		State targetState = getState(targetStateIdentifier);
		transitionFunction.addTransition(initialState, targetState, symbol);
	}

	public Set<T> getValidSymbols(State currentState) {
		return this.transitionFunction.getValidSymbols(currentState);
	}
	

	public State getNextState(State currentState, T symbol) {
		return this.transitionFunction.getNextState(currentState, symbol);
	}

}