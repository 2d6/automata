package automata;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class TransitionTest {
	
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
	
	@Test
	public void transitionMayBeCreated() {
		State initialState = new State("initialState", true);
		State targetState = new State("targetState", false);
		char symbol = '1';
		Transition transition = new Transition(initialState, targetState, symbol);
		assertEquals(transition.getTargetState(), targetState);
		assertEquals(transition.getInitialState(), initialState);
		assertEquals(transition.getSymbol(), symbol);
	}
	
	@Test(dataProvider = "nullStates", expectedExceptions = NullPointerException.class)
	public void nullStatesAreNotAccepted(State initialState, State targetState) {
		new Transition(initialState, targetState, '1');
	}
	
	@Test(dataProvider = "emptySymbols", expectedExceptions = IllegalArgumentException.class)
	public void emptySymbolsAreNotAccepted(char symbol) {
		new Transition(new State("S1", true), new State("S2", false), symbol);
	}
	
	@Test
	public void equalityWorksCorrectly() {
		State initialState = new State("initialState", true);
		State targetState = new State("targetState", false);
		char symbol = '1';
		Transition transition = new Transition(initialState, targetState, symbol);
		Transition otherTransition = new Transition(initialState, targetState, symbol);
		assertEquals(transition, otherTransition);
	}
}
