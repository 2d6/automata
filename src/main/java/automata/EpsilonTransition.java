package automata;

import automata.interfaces.IState;

/**
 * An epsilon transition, i.e. a transition connecting an initial state to a target
 * state wit triggering symbol.
 */
public class EpsilonTransition {
	
	private final IState initialState;
	private final IState targetState;
	
	public IState getInitialState() {
		return initialState;
	}
	
	public IState getTargetState() {
		return targetState;
	}
	
	/**
	 * @param initialState
	 * @param targetState
	 */
	public EpsilonTransition(IState initialState, IState targetState) {
		if (initialState == null) {
			throw new IllegalArgumentException("Initial State may not be null");
		}
		else if (targetState == null) {
			throw new IllegalArgumentException("Target State may not be null");
		}
		this.initialState = initialState;
		this.targetState = targetState;
	}
	
	@Override
	public String toString() {
		return initialState.toString() + " -> " + targetState.toString();
	}
	
}
