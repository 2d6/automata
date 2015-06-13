package automata;

/**
 * An epsilon transition, i.e. a transition connecting an initial state to a target
 * state wit triggering symbol.
 */
public class EpsilonTransition {
	
	private final State initialState;
	private final State targetState;
	
	public State getInitialState() {
		return initialState;
	}
	
	public State getTargetState() {
		return targetState;
	}
	
	/**
	 * @param initialState
	 * @param targetState
	 */
	public EpsilonTransition(State initialState, State targetState) {
		if (initialState == null) {
			throw new IllegalArgumentException("Initial State may not be null");
		}
		else if (targetState == null) {
			throw new IllegalArgumentException("Target State may not be null");
		}
		this.initialState = initialState;
		this.targetState = targetState;
	}
	
}
