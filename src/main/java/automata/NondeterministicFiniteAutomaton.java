package automata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import automata.interfaces.IEpsilonTransitionFunction;

public class NondeterministicFiniteAutomaton<T> extends AbstractFiniteAutomaton<T> implements INondeterministicFiniteAutomaton<T> {
	
	private IEpsilonTransitionFunction<T> epsilonTransitionFunction;
	
	public NondeterministicFiniteAutomaton(String identifier, boolean isAccepting,
			IEpsilonTransitionFunction<T> transitionFunction) {
		states = new HashMap<>();
		startingState = new State(identifier, isAccepting);
		states.put(identifier, startingState);
		
		if (transitionFunction == null) {
			throw new IllegalArgumentException("Transition function may not be null");
		}
		this.transitionFunction = transitionFunction;
		this.epsilonTransitionFunction = transitionFunction;
	}
	
	/* (non-Javadoc)
	 * @see automata.INondeterministicFiniteAutomaton#addEpsilonTransition(java.lang.String, java.lang.String)
	 */
	@Override
	public void addEpsilonTransition(String initialStateIdentifier, String targetStateIdentifier) {
		State initialState = getState(initialStateIdentifier);
		State targetState = getState(targetStateIdentifier);
		epsilonTransitionFunction.addEpsilonTransition(initialState, targetState);
	}
	
	/* (non-Javadoc)
	 * @see automata.INondeterministicFiniteAutomaton#evaluate(java.util.List)
	 */
	@Override
	public Set<State> evaluate(List<T> input) {
		Set<State> states = new HashSet<>();
		states.addAll(epsilonTransitionFunction.getExpandedStates(startingState));
		
		for (T symbol : input) {
			
			if (!transitionFunction.getSymbols().contains(symbol)) {
				throw new IllegalArgumentException("Illegal Symbol encountered: " + symbol);
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
			expandedStates.addAll(epsilonTransitionFunction.getExpandedStates(state));
		}
		
		return expandedStates.stream()
				.map(state -> transitionFunction.getNextState(state, symbol))
				.collect(Collectors.toSet());
	}

}
