package automata;

import static org.junit.Assert.*;

import org.junit.Test;

public class StateTest {
	
	@Test
	public void stateIsAcceptingMayBeSet() {
		State state = new State("",true);
		assertTrue(state.isAccepting());
		
		state = new State("",false);
		assertFalse(state.isAccepting());
	}
	
	@Test
	public void identifierMayBeSet() {
		State state = new State("Identifier", true);
		assertEquals("Identifier", state.getIdentifier());
	}
}
