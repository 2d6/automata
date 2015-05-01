package automata;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class StateTest {

	@Test
	public void stateIsAcceptingMayBeSet() {
		State state = new State("", true);
		assertTrue(state.isAccepting());

		state = new State("", false);
		assertFalse(state.isAccepting());
	}

	@Test
	public void identifierMayBeSet() {
		State state = new State("Identifier", true);
		assertEquals("Identifier", state.getIdentifier());
	}

	/*
	 * Cloning
	 */

	/**
	 * This checks whether the clone() method works as per the documentation of
	 * theObject.clone() method given here:
	 * https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html#clone%28%29
	 */
	@Test
	public void cloningFulfilsCriteria() {
		State state = new State("Identifier", true);
		assertTrue(state != state.clone());
		assertEquals(state.getClass(), state.clone().getClass());
	}
	
	@Test
	public void clonedStatesAttributesAreEqual() {
		State state = new State("Identifier", true);
		State clone = (State) state.clone();
		assertEquals(state.getIdentifier(), clone.getIdentifier());
		assertEquals(state.isAccepting(), clone.isAccepting());
	}
}
