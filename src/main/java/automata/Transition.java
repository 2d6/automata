package automata;

/**
 * A transition in a @see{TransitionFunction}. Connects an initial state to a
 * target state with the triggering symbol as a condition.
 * @author 2d6
 *
 */
public class Transition<T> {

	private final State initialState;
	private final State targetState;
	private final T symbol;

	public State getInitialState() {
		return initialState;
	}

	public State getTargetState() {
		return targetState;
	}

	public T getSymbol() {
		return symbol;
	}

	/**
	 * Creates a Transition
	 * @param initialState The initial state of the transition
	 * @param targetState The target state of the transition
	 * @param symbol The triggering symbol of the transition
	 */
	public Transition(State initialState, State targetState, T symbol) {
		if (initialState == null) {
			throw new NullPointerException("Initial state may not be null");
		}
		else if (targetState == null) {
			throw new NullPointerException("Target state may not be null");
		}
		else if (symbol == null) {
			throw new NullPointerException("Symbol may not be null");
		}
		
		this.initialState = initialState;
		this.targetState = targetState;
		this.symbol = symbol;
	}	

}
