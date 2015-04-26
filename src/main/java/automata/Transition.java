package automata;

/**
 * A transition in a @see{TransitionFunction}. Connects an initial state to a
 * target state with the triggering symbol as a condition.
 * @author 2d6
 *
 */
public class Transition {

	private final State initialState;
	private final State targetState;
	private final char symbol;

	public State getInitialState() {
		return initialState;
	}

	public State getTargetState() {
		return targetState;
	}

	public char getSymbol() {
		return symbol;
	}

	/**
	 * Creates a Transition
	 * @param initialState The initial state of the transition
	 * @param targetState The target state of the transition
	 * @param symbol The triggering symbol of the transition
	 */
	public Transition(State initialState, State targetState, char symbol) {
		if (initialState == null) {
			throw new NullPointerException("Initial state may not be null");
		}
		else if (targetState == null) {
			throw new NullPointerException("Target state may not be null");
		}
		else if (Character.isWhitespace(symbol) || symbol == '\0') {
			throw new IllegalArgumentException("Symbol may not be whitespace character or empty ('\0')");
		}
		
		this.initialState = initialState;
		this.targetState = targetState;
		this.symbol = symbol;
	}	

}
