package automata.states;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automata.interfaces.IState;
import automata.states.State;
import static org.testng.Assert.*;

public class StateTest {
	
	private static final boolean NOT_ACCEPTING = false;
	private static final boolean ACCEPTING = true;
	private static final String ID = "id";

	@Test
	public void stateIsAcceptingMayBeSet() {
		IState state = new State("", ACCEPTING);
		assertTrue(state.isAccepting());
		
		state = new State("", NOT_ACCEPTING);
		assertFalse(state.isAccepting());
	}
	
	@Test
	public void stateIdentifierMayBeSet() {
		IState state = new State(ID, ACCEPTING);
		assertEquals(ID, state.getId());
	}
	
	@Test(dataProvider = "idAndAcceptance")
	public void toStringYieldsIdAndAcceptanceState(String id, boolean isAccepting, String expected) {
		IState state = new State(ID, ACCEPTING);
		
		assertEquals(state.toString(), "id (true)");
	}

	@DataProvider(name = "idAndAcceptance")
	public Object[][] getIdAndAcceptance() {
		return new Object[][] {
				{ ID, ACCEPTING, "id (true)" },
				{ ID, NOT_ACCEPTING, "id (false)" },
				{ ID+ID, ACCEPTING, ID+ID + " (true)" },
		};
	}
}
