package de._2d6.dfa;

import java.util.HashMap;

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

	HashMap<StateEvent, State> transitions;

	public TransitionFunction() {
		this.transitions = new HashMap<StateEvent, State>();
	}

	/**
	 * Performs a transition from the input state according to a given symbol.
	 * If no transition has been defined for the given arguments, the inputState
	 * is returned.
	 * 
	 * @param inputState
	 *            The input state
	 * @param symbol
	 *            The symbol for which a transition should be performed
	 * @return The State according to the transition.
	 */
	public State getNewState(State inputState, Character symbol) {
		StateEvent event = new StateEvent(inputState, symbol);
		if (transitions.containsKey(event)) {
			return transitions.get(event);
		}
		return inputState;

	}

	/**
	 * Define a new transition within the transition function, which connects an
	 * input state and symbol with an output state.
	 * 
	 * @param inputState
	 *            The original state
	 * @param symbol
	 *            The symbol occurring during the inputState
	 * @param outputState
	 *            The state associated with inputState and symbol
	 */
	public void defineTransition(State inputState, Character symbol,
			State outputState) {
		if (inputState != null && symbol != null && outputState != null) {
			this.transitions.put(new StateEvent(inputState, symbol),
					outputState);
		}
	}

	/**
	 * Helper class to create a single key from a given State and symbol.
	 * Necessary for the transitions HashMap.
	 * 
	 * @author 2d6
	 */
	private class StateEvent {
		private State inputState;
		private char symbol;

		public StateEvent(State inputState, char symbol) {
			this.inputState = inputState;
			this.symbol = symbol;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((inputState == null) ? 0 : inputState.hashCode());
			result = prime * result + symbol;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			StateEvent other = (StateEvent) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (inputState == null) {
				if (other.inputState != null)
					return false;
			} else if (!inputState.equals(other.inputState))
				return false;
			if (symbol != other.symbol)
				return false;
			return true;
		}

		private TransitionFunction getOuterType() {
			return TransitionFunction.this;
		}

	}

}
