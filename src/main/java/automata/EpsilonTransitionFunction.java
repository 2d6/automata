package automata;

import java.util.ArrayList;
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
		
		Set<State> expandedStates =  epsilonTransitions.stream()
				.filter(transition -> transition.getInitialState().equals(initialState))
				.map(transition -> transition.getTargetState())
				.collect(Collectors.toSet());
		
		expandedStates.add(initialState);
		return expandedStates;
	}
}
