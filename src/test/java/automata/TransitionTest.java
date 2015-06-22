package automata;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class TransitionTest {
	
	
	private static final String TARGET_STATE = "targetState";
	private static final String INITIAL_STATE = "initialState";
	private static final char SYMBOL = '1';

	@Test
	public void transitionMayBeCreated() {
		State initialState = new State(INITIAL_STATE, true);
		State targetState = new State(TARGET_STATE, false);
		Character symbol = '1';
		Transition<Character> transition = new Transition<>(initialState, targetState, symbol);
		assertEquals(transition.getTargetState(), targetState);
		assertEquals(transition.getInitialState(), initialState);
		assertEquals(transition.getSymbol(), symbol);
	}
	
	@Test(dataProvider = "nullStates", expectedExceptions = NullPointerException.class)
	public void transitionDoesNotAcceptNullStates(State initialState, State targetState) {
		new Transition<>(initialState, targetState, '1');
	}
	
	@DataProvider(name = "nullStates")
	public static Object[][] nullStates() {
		return new Object[][] {
				{null, new State(TARGET_STATE, false)},
				{new State(INITIAL_STATE, true), null},
				{null, null}
		};
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void transitionDoesNotAcceptNullSymbols() {
		new Transition<Character>(new State("S1", true), new State("S2", false), null);
	}
	
	@Test
	public void toStringYieldsStateStringsAndSymbol() {
		State initialState = new State(INITIAL_STATE, true);
		State targetState = new State(TARGET_STATE, false);
		Character symbol = SYMBOL;
		Transition<Character> transition = new Transition<>(initialState, targetState, symbol);
		
		assertEquals(transition.toString(), initialState.toString() + " -> " + symbol.toString() + " -> " + targetState.toString());
	}
}
