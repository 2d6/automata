package de._2d6.dfa;

/**
 * Implements a transition function (see
 * https://en.wikipedia.org/wiki/Deterministic_finite_automaton). A transition
 * function yields a predetermined State for a given input State and symbol. The
 * transition function may only operate on symbols of type Char.
 * 
 * @author 2d6
 * @see Transition
 */
public class TransitionFunction {

	public TransitionFunction() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Performs a transition from the input state according to a given symbol
	 * 
	 * @param inputState
	 *            The input state
	 * @param symbol
	 *            The symbol for which a transition should be performed
	 * @return The State according to the transition
	 */
	public State getNewState(State inputState, Character symbol) {
		return inputState;
	}

}
