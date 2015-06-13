package automata;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import automata.interfaces.IEpsilonTransitionFunction;

public class NondeterministicFiniteAutomaton<T> extends
		AbstractFiniteAutomaton<T> implements
		INondeterministicFiniteAutomaton<T> {

	private IEpsilonTransitionFunction<T> epsilonTransitionFunction;

	public NondeterministicFiniteAutomaton(String identifier,
			boolean isAccepting,
			IEpsilonTransitionFunction<T> transitionFunction) {
		states = new HashMap<>();
		startingState = new State(identifier, isAccepting);
		states.put(identifier, startingState);

		if (transitionFunction == null) {
			throw new IllegalArgumentException(
					"Transition function may not be null");
		}
		this.transitionFunction = transitionFunction;
		this.epsilonTransitionFunction = transitionFunction;
	}

	public NondeterministicFiniteAutomaton(
			NondeterministicFiniteAutomaton<T> originalNfa) {

		// Copy the states
		this.states = new HashMap<>();
		Collection<State> originalStates = originalNfa.states.values();
		originalStates.stream().forEach(
				state -> this.addState(state.getIdentifier(),
						state.isAccepting()));
		
		this.startingState = this.getState(originalNfa.getStartingState()
				.getIdentifier());

		// Copy the transition function, using references to the original
		// symbols
		this.epsilonTransitionFunction = new EpsilonTransitionFunction<>();
		this.transitionFunction = this.epsilonTransitionFunction;
		Set<T> symbols = originalNfa.transitionFunction.getSymbols();
		this.transitionFunction.setSymbols(symbols);

		// Create transitions for all State/symbol combinations which had
		// transitions in the original NFA
		for (State originalState : originalStates) {
			String stateIdentifier = originalState.getIdentifier();
			
			for (T symbol : symbols) {
				State targetState = originalNfa.transitionFunction
						.getNextState(originalState, symbol);
				if (originalStates.contains(targetState)) {
					this.addTransition(stateIdentifier,
							targetState.getIdentifier(), symbol);
				}
			}

			State copiedState = this.getState(stateIdentifier);
			originalNfa.epsilonTransitionFunction.getExpandedStates(originalState)
					.stream()
					.map(state -> this.getState(state.getIdentifier()))
					.filter(state -> !state.equals(copiedState))
					.forEach(state -> this.epsilonTransitionFunction
							.addEpsilonTransition(copiedState, state));
		}
	}

	@Override
	public void addEpsilonTransition(String initialStateIdentifier,
			String targetStateIdentifier) {
		State initialState = getState(initialStateIdentifier);
		State targetState = getState(targetStateIdentifier);
		epsilonTransitionFunction.addEpsilonTransition(initialState,
				targetState);
	}

	@Override
	public Set<State> evaluate(List<T> input) {
		Set<State> states = new HashSet<>();
		states.addAll(epsilonTransitionFunction
				.getExpandedStates(startingState));

		for (T symbol : input) {

			if (!transitionFunction.getSymbols().contains(symbol)) {
				throw new IllegalArgumentException(
						"Illegal Symbol encountered: " + symbol);
			}

			states = evaluate(states, symbol);
			if (states.size() == 1) {
				State state = new LinkedList<>(states).getFirst();
				if (!this.states.containsValue(state)) {
					return states;
				}
			}
		}

		return states;
	}

	private Set<State> evaluate(Set<State> currentStates, T symbol) {
		Set<State> expandedStates = new HashSet<>();

		for (State state : currentStates) {
			expandedStates.addAll(epsilonTransitionFunction
					.getExpandedStates(state));
		}

		return expandedStates.stream()
				.map(state -> transitionFunction.getNextState(state, symbol))
				.collect(Collectors.toSet());
	}

	/**
	 * Creates a new {@link NondeterministicFiniteAutomaton} semantically
	 * identical to the current one
	 * 
	 * @return The new {@link NondeterministicFiniteAutomaton}
	 */
	public NondeterministicFiniteAutomaton<T> copy() {
		return new NondeterministicFiniteAutomaton<T>(this);
	}

}
