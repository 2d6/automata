package de._2d6.dfa;

import static org.junit.Assert.*;

import org.junit.Test;

import de._2d6.dfa.State;

public class StateTest {

	/**
	 * It should be discernible whether a State is an accepting state
	 */
	@Test
	public void isAcceptingMayBeRetrieved() {
		State state = new State(true);
		assertEquals(true, state.isAccepting());
		state = new State(false);
		assertEquals(false, state.isAccepting());
	}

	/**
	 * States may be identified by a human-readable identifier string (i.e. a
	 * name)
	 */
	@Test
	public void identifierMayBeRetrieved() {
		State state = new State("Identifier", true);
		assertEquals("Identifier", state.getIdentifier());
	}

}
