package automata;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class TransitionTest {
	
	@Test
	public void transitionMayBeCreated() {
		State initialState = new State("initialState", true);
		State targetState = new State("targetState", false);
		Character symbol = '1';
		Transition<Character> transition = new Transition<>(initialState, targetState, symbol);
		assertEquals(transition.getTargetState(), targetState);
		assertEquals(transition.getInitialState(), initialState);
		assertEquals(transition.getSymbol(), symbol);
	}
	
	@Test(dataProvider = "nullStates", expectedExceptions = NullPointerException.class)
	public void nullStatesAreNotAccepted(State initialState, State targetState) {
		new Transition<>(initialState, targetState, '1');
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void nullSymbolsAreNotAccepted() {
		new Transition<Character>(new State("S1", true), new State("S2", false), null);
	}
	
	@DataProvider(name = "nullStates")
	public static Object[][] nullStates() {
		return new Object[][] {
				{null, new State("targetState", false)},
				{new State("initialState", true), null},
				{null, null}
		};
	}
	
	@DataProvider(name = "emptySymbols")
	public static Object[][] invalidSymbols() {
		return new Object[][] {
				{'\0'},
				{' '},
		};
	}
}
