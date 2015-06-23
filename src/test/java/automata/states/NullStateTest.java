package automata.states;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;


public class NullStateTest {

	private static final boolean ACCEPTING = true;
	private static final String S1 = "S1";
	private static final String NULL_STATE_ID = "NullState";

	@Test
	public void nullStateIdIsCorrect() {
		assertEquals(NullState.getInstance().getId(), NULL_STATE_ID);
	}
	
	@Test
	public void nullStateIsNotAccepting() {
		assertFalse(NullState.getInstance().isAccepting());
	}
	
	@Test
	public void nullStateIsSingleton() {
		NullState stateA = NullState.getInstance();
		NullState stateB = NullState.getInstance();
		
		assertTrue(stateA == stateB);
	}
	
	@Test
	public void isNullStateReturnsTrueIfStateIsNullState() {
		assertTrue(NullState.isNullState(NullState.getInstance()));
	}
	
	@Test
	public void isNullStateReturnsFalseIfStateNotNullState() {
		assertFalse(NullState.isNullState(new State(S1, ACCEPTING)));
	}
}
