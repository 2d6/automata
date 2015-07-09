package automata.interfaces;

import java.util.Set;

import automata.EpsilonTransition;
import automata.states.State;

/**
 * A {@link ITransitionFunction transition function} which also allows for epsilon
 * transitions, i.e. transitions between states without a triggering symbol.
 */
public interface IEpsilonTransitionFunction<T> extends ITransitionFunction<T> {

	/**
	 * Adds an epsilon transition to the transition function
	 * @param initialState The initial {@link State} of the epsilon transition
	 * @param targetState the target {@link State} of the epsilon transition
	 */
	public void addEpsilonTransition(IState initialState, IState targetState);
	
	/**
	 * Returns the Set of {@link State States} connected to the initial state 
	 * via {@link EpsilonTransition epsilon transitions}.
	 * @param initialState
	 * @return The Set of connected States
	 */
	public Set<IState> getExpandedStates(IState initialState);
	
	/**
	 * Returns the Set of {@link State States} connected to a Set of States 
	 * via {@link EpsilonTransition epsilon transitions}.
	 * @param initialState
	 * @return The Set of connected States
	 */
	public Set<IState> getExpandedStates(Set<IState> initialState);

}
