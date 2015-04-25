package automata;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


public class SimpleTransitionFunctionTest {
	
	@DataProvider(name = "testSymbols")
	public static Object[][] symbols() {
		return new Object[][] {
				{'0'},
				{'a'},
				{'§'},
				{'ä'}
		};
	}
	
	@Test(dataProvider = "testSymbols")
	public void transitionsMayBeAddedAndRetrieved(char symbol) {
		SimpleTransitionFunction function = new SimpleTransitionFunction();
		State initialState = new State("initialState", true);
		State targetState = new State("targetState", false);
		function.add(initialState, targetState, symbol);
		assertEquals(function.get(initialState, symbol), targetState);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void duplicateTransitionsAreNotAllowed() {
		SimpleTransitionFunction function = new SimpleTransitionFunction();
		State initialState = new State("initialState", true);
		State targetState = new State("targetState", false);
		char symbol = '0';
		function.add(initialState, targetState, symbol);
		function.add(initialState, targetState, symbol);
	}
}
