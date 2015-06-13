package automata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import automata.interfaces.IAlphabet;
import automata.interfaces.IEpsilonTransitionFunction;

/**
 * Implements an {@link IEpsilonTransitionFunction epsilon transition function} with symbols
 * of type T
 * @param <T> the type of the symbols used in regular transitions
 */
public class EpsilonTransitionFunction<T> extends TransitionFunction<T>
		implements IEpsilonTransitionFunction<T> {
	
	private List<EpsilonTransition> epsilonTransitions =  new ArrayList<>();

	/**
	 * Creates a new SimpleTransitionFunction with a given {@link IAlphabet}.
	 * 
	 * @param alphabet
	 */
	public EpsilonTransitionFunction(Set<T> symbols) {
		super(symbols);
	}

	/**
	 * Creates a new EpsilonTransitionFunction.
	 * 
	 * @param alphabet
	 */
	public EpsilonTransitionFunction() {
		super();
	}

	@Override
	public void addEpsilonTransition(State initialState, State targetState) {
		epsilonTransitions.add(new EpsilonTransition(initialState, targetState));
	}

	@Override
	public Set<State> getExpandedStates(State initialState) {
		if (initialState == null) {
			throw new IllegalArgumentException("Initial State May not be null");
		}
		
		Set<State> expandedStates = new HashSet<>(Arrays.asList(initialState));
		
		return getAllExpandedStates(expandedStates);
	}
	
	/**
	 * Recursively add all {@link State States} which are reachable via epsilon transitions 
	 * from the States in the initial Set to a Set of States.
	 * @param expandedStates The initial Set of states
	 * @return A Set containing all States reachable via epsilon transitions
	 */
	private Set<State> getAllExpandedStates(Set<State> expandedStates) {
		Set<State> allExpandedStates = new HashSet<>();
		allExpandedStates.addAll(expandedStates);
		
		for (State state : expandedStates) {
			allExpandedStates.addAll(epsilonTransitions.stream()
					.filter(transition -> transition.getInitialState().equals(state))
					.map(transition -> transition.getTargetState())
					.collect(Collectors.toSet()));
		}
		
		if (!expandedStates.containsAll(allExpandedStates)) {
			allExpandedStates.addAll(getAllExpandedStates(allExpandedStates));
		}
		
		return allExpandedStates;
			
	}
}
