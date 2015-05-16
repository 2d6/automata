package automata;

/**
 * A Deterministic Finite Automaton with an alphabet of type T
 * 
 * @author 2d6
 *
 * @param <T>
 */
public interface DeterministicFiniteAutomaton<T> {

	/**
	 * @return The starting state of the automaton
	 */
	public State getStartingState();

	/**
	 * Returns the state corresponding to a given identifier, if the automaton
	 * contains such a state.
	 * 
	 * @param identifier
	 *            The identifier of the requested state
	 * @return The state corresponding to the identifier, or null if it does not
	 *         exist.
	 */
	public State getState(String identifier);

	/**
	 * Adds a state with the given identifier and acceptance state to the
	 * automaton. If the automaton already contains a state with the given
	 * identifier, an IllegalArgumentException is thrown.
	 * 
	 * @param identifier
	 *            The identifier of the new state.
	 * @param isAccepting
	 *            The acceptance status of the new state. True if the state is
	 *            accepting.
	 */
	public void addState(String identifier, boolean isAccepting);

	/**
	 * Adds a transition from a state with a given identifier to a target state.
	 * The symbol is the trigger of the transition, i.e. the transition is only
	 * considered during evaluation if the automaton is currently in the
	 * original state of the transition and the symbol is also currently being
	 * evaluated.
	 * 
	 * @param initialStateIdentifier
	 *            The state the automaton is in when before symbol is evaluated
	 * @param targetStateIdentifier
	 *            The state the automaton should transfer to after the symbol
	 *            has been evaluated
	 * @param symbol
	 *            The symbol, i.e. trigger of the transition
	 */
	public void addTransition(String initialStateIdentifier,
			String targetStateIdentifier, T symbol);

	/**
	 * Evaluates a List of symbols according to the logic of the automaton given
	 * by its states and transition function. Returns the state the automaton
	 * was in after evaluating the last symbol of the input. Each evaluation
	 * begins with the automaton in its starting state.
	 * 
	 * @param input
	 *            list of symbols to be evaluated
	 * @return The state the automaton was in after evaluating the last symbol
	 *         of the input.
	 */
	public State evaluate(Iterable<T> input);

	/**
	 * Create a semantically identical copy of the automaton. The copy should
	 * share references to the same symbols as the original automaton.
	 * 
	 * @return The copied automaton
	 */
	public DeterministicFiniteAutomaton<T> copy();

	/**
	 * Determines whether two automata are structurally identical, i.e. will
	 * evaluate identical input to identical output
	 * 
	 * @param otherCharDfa
	 *            CharDfa to compare to
	 * @return True, if the CharDfa are structurally identical
	 */
	boolean isStructurallyEqualTo(CharDfa otherCharDfa);

}