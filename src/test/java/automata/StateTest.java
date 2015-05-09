package automata;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class StateTest {
	
	@Test
	public void stateIsAcceptingMayBeSet() {
		State state = new State("",true);
		assertTrue(state.isAccepting());
		
		state = new State("",false);
		assertFalse(state.isAccepting());
	}
	
	@Test
	public void stateIdentifierMayBeSet() {
		State state = new State("Identifier", true);
		assertEquals("Identifier", state.getIdentifier());
	}
}
