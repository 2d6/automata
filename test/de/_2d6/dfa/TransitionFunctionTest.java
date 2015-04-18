package de._2d6.dfa;

import static org.junit.Assert.*;

import org.junit.Test;

public class TransitionFunctionTest {

	/**
	 * If no transition has previously been defined, the transition function
	 * should return the input state
	 */
	@Test
	public void shouldReturnInputStateIfNoRelevantTransitionExists() {
		TransitionFunction trans = new TransitionFunction();
		State inputState = new State("inputState", true);
		Character symbol = 'a';

		State newState = trans.getNewState(inputState, symbol);
		assertEquals(inputState, newState);
	}

	/**
	 * If a transition for an input state and a given symbol has been defined,
	 * the TransitionFunction should return the new state according to the
	 * transition.
	 */
	@Test
	public void shouldReturnPredefinedOutputStateIfRelevantTransitionExists() {
		// TransitionFunction trans = new TransitionFunction();
		// State inputState = new State("inputState", true);
		// State outputState = new State("outputState", false);
		// Character symbol = 'a';
		//
		// trans.defineTransition(inputState, symbol, outputState);
		// assertEquals(outputState,)
	}
}
