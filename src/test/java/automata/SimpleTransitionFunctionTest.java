package automata;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class SimpleTransitionFunctionTest {
	
	private SimpleTransitionFunction newSingleCharTransitionFunction(Character symbol) {
		CharAlphabet alphabet = new CharAlphabet(new Character[]{symbol});
		return new SimpleTransitionFunction(alphabet);
	}
	
	@DataProvider(name = "testSymbols")
	public static Object[][] symbols() {
		return new Object[][] {
				{'0'},
				{'a'},
				{'ä'}
		};
	}
	
	@Test(dataProvider = "testSymbols")
	public void transitionsMayBeAddedAndRetrieved(Character symbol) {
		SimpleTransitionFunction function = newSingleCharTransitionFunction(symbol);
		State initialState = new State("initialState", true);
		State targetState = new State("targetState", false);
		function.add(initialState, targetState, symbol);
		assertEquals(function.get(initialState, symbol), targetState);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void duplicateTransitionsAreNotAllowed() {
		Character symbol = '0';
		SimpleTransitionFunction function = newSingleCharTransitionFunction(symbol);
		State initialState = new State("initialState", true);
		State targetState = new State("targetState", false);
		function.add(initialState, targetState, symbol);
		function.add(initialState, targetState, symbol);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void transitionsWithSymbolsNotInAlphabetAreRejected() {
		SimpleTransitionFunction function = new SimpleTransitionFunction();
		State initialState = new State("initialState", true);
		State targetState = new State("targetState", false);
		function.setAlphabet(new CharAlphabet(new Character[]{'1'}));
		function.add(initialState, targetState, '0');
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void alphabetMayOnlyBeSetOnce() {
		SimpleTransitionFunction function = newSingleCharTransitionFunction('x');
		function.setAlphabet(new CharAlphabet(new Character[]{'y'}));
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void alphabetMayNotBeNull() {
		SimpleTransitionFunction function = new SimpleTransitionFunction();
		function.setAlphabet(null);
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void alphabetMustBeSetBeforeAddingTransitions() {
		SimpleTransitionFunction function = new SimpleTransitionFunction();
		State initialState = new State("initialState", true);
		State targetState = new State("targetState", false);
		function.add(initialState, targetState, '0');
	}
}
