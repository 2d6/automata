package automata;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import automata.interfaces.IEpsilonTransitionFunction;
import automata.interfaces.INondeterministicFiniteAutomaton;
import automata.interfaces.IState;

public class NondeterministicFiniteAutomaton<T> extends
		AbstractFiniteAutomaton<T> implements
		INondeterministicFiniteAutomaton<T> {

	private static final String EPSILON = "ε";
	
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
		
		Collection<IState> originalStates = originalNfa.states.values();
		this.epsilonTransitionFunction = originalNfa.epsilonTransitionFunction;

		// Create epsilon transitions for all States which had
		// epsilon transitions in the original NFA
		for (IState originalState : originalStates) {
			String stateIdentifier = originalState.getId();
			
			IState copiedState = this.getState(stateIdentifier);
			originalNfa.epsilonTransitionFunction.getExpandedStates(originalState)
					.stream()
					.map(state -> this.getState(state.getId()))
					.filter(state -> !state.equals(copiedState))
					.forEach(state -> this.epsilonTransitionFunction
							.addEpsilonTransition(copiedState, state));
		}
	}

	@Override
	public void addEpsilonTransition(String initialStateIdentifier,
			String targetStateIdentifier) {
		IState initialState = getState(initialStateIdentifier);
		IState targetState = getState(targetStateIdentifier);
		epsilonTransitionFunction.addEpsilonTransition(initialState,
				targetState);
	}

	@Override
	public Set<IState> evaluate(List<T> input) {
		Set<IState> states = new HashSet<>();
		states.addAll(epsilonTransitionFunction
				.getExpandedStates(startingState));

		for (T symbol : input) {

			if (!transitionFunction.getSymbols().contains(symbol)) {
				throw new IllegalArgumentException(
						"Illegal Symbol encountered: " + symbol);
			}

			states = evaluate(states, symbol);
			if (states.size() == 1) {
				IState state = new LinkedList<>(states).getFirst();
				if (!this.states.containsValue(state)) {
					return states;
				}
			}
		}

		return states;
	}

	private Set<IState> evaluate(Set<IState> currentStates, T symbol) {
		Set<IState> expandedStates = new HashSet<>();

		for (IState state : currentStates) {
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

	@Override
	public String toGraphViz() {
		String graphVizTemplate = getGraphVizBaseTemplate();
		
		String acceptingStates = states.values().stream()
					.filter(state -> state.isAccepting())
					.map(state -> state.getId())
					.collect(Collectors.joining(" "));
		
		String transitionTemplate = getGraphVizTransitionTemplate();
		String transitions = "";
		for (IState initialState : states.values()) {
			transitions += transitionFunction.getSymbols().stream()
					.map(symbol -> String.format(
							transitionTemplate, 
							initialState.getId(), 
							transitionFunction.getNextState(initialState, symbol).getId(), 
							symbol))
					.collect(Collectors.joining());
			transitions += epsilonTransitionFunction.getExpandedStates(initialState).stream()
					.filter(state -> !state.getId().equals(initialState.getId()))
					.map(state -> String.format(
							transitionTemplate,
							initialState.getId(),
							state.getId(),
							EPSILON))
					.collect(Collectors.joining());
		}	
		
		return  String.format(graphVizTemplate, acceptingStates, transitions);
	}

}









