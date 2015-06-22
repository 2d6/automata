package automata;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import automata.interfaces.IEpsilonTransitionFunction;
import automata.interfaces.INondeterministicFiniteAutomaton;

public class NondeterministicFiniteAutomaton<T> extends
		AbstractFiniteAutomaton<T> implements
		INondeterministicFiniteAutomaton<T> {

	private IEpsilonTransitionFunction<T> epsilonTransitionFunction;

	public NondeterministicFiniteAutomaton(String identifier,
			boolean isAccepting,
			Set<T> symbols) {
		
		super(identifier, isAccepting, symbols);
		
		this.epsilonTransitionFunction = new EpsilonTransitionFunction<T>(symbols); 
		this.transitionFunction = this.epsilonTransitionFunction;
	}

	public NondeterministicFiniteAutomaton(
			NondeterministicFiniteAutomaton<T> originalNfa) {

		super(originalNfa);
		
		Collection<State> originalStates = originalNfa.states.values();
		this.epsilonTransitionFunction = originalNfa.epsilonTransitionFunction;

		// Create epsilon transitions for all States which had
		// epsilon transitions in the original NFA
		for (State originalState : originalStates) {
			String stateIdentifier = originalState.getIdentifier();
			
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
