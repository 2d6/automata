package automata;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import automata.comparators.FiniteAutomatonComparator;
import automata.interfaces.IState;
import automata.interfaces.ITransitionFunction;

public class AbstractFiniteAutomaton<T> {

	protected HashMap<String, IState> states;
	protected IState startingState;
	protected ITransitionFunction<T> transitionFunction;
	
	public AbstractFiniteAutomaton() {
		super();
	}
	
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
	public AbstractFiniteAutomaton(String identifier, boolean isAccepting,
			Set<T> symbols) {
		states = new HashMap<>();
		startingState = new State(identifier, isAccepting);
		states.put(identifier, startingState);
		
		if (symbols == null) {
			throw new IllegalArgumentException("Set of symbols may not be null");
		}
		this.transitionFunction = new TransitionFunction<>(symbols);
	}
	
	public AbstractFiniteAutomaton(AbstractFiniteAutomaton<T> otherAutomaton) {
		// Copy the states
		this.states = new HashMap<>();
		Collection<IState> originalStates = otherAutomaton.states.values();
		originalStates.stream().forEach(
				state -> this.addState(state.getId(),
						state.isAccepting()));	
		
		this.startingState = this.getState(otherAutomaton.getStartingState()
				.getId());

		// Copy the transition function, using references to the original
		// symbols
		this.transitionFunction = new TransitionFunction<>();
		Set<T> symbols = otherAutomaton.transitionFunction.getSymbols();
		this.transitionFunction.setSymbols(symbols);

		// Create transitions for all State/symbol combinations which had
		// transitions in the original DFA
		for (IState originalState : originalStates) {
			for (T symbol : symbols) {
				IState targetState = otherAutomaton.transitionFunction
						.getNextState(originalState, symbol);
				if (originalStates.contains(targetState)) {
					this.addTransition(originalState.getId(),
							targetState.getId(), symbol);
				}
			}
		}
	}

	public IState getStartingState() {
		return startingState;
	}

	public IState getState(String identifier) {
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
		IState initialState = getState(initialStateIdentifier);
		IState targetState = getState(targetStateIdentifier);
		transitionFunction.addTransition(initialState, targetState, symbol);
	}

	public Set<T> getValidSymbols(IState currentState) {
		return this.transitionFunction.getValidSymbols(currentState);
	}
	

	public IState getNextState(IState currentState, T symbol) {
		return this.transitionFunction.getNextState(currentState, symbol);
	}
	
	public boolean isStructurallyEqualTo(AbstractFiniteAutomaton<T> otherDfa) {
		return new FiniteAutomatonComparator<T>().structurallyEqual(this, otherDfa);
	}

}