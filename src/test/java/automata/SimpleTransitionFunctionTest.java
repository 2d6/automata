package automata;

import static org.testng.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class SimpleTransitionFunctionTest {
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void alphabetMayNotBeNull() {
		new SimpleTransitionFunction(null);
	}
	
	@Test(dataProvider = "testSymbols")
	public void transitionsMayBeAddedAndRetrieved(Character symbol) {
		SimpleTransitionFunction function = newSingleCharTransitionFunction(symbol);
		State initialState = new State("initialState", true);
		State targetState = new State("targetState", false);
		function.addTransition(initialState, targetState, symbol);
		assertEquals(function.getNextState(initialState, symbol), targetState);
	}
	
	@DataProvider(name = "testSymbols")
	public static Object[][] symbols() {
		return new Object[][] {
				{'0'},
				{'a'},
				{'ä'}
		};
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void duplicateTransitionsAreNotAllowed() {
		Character symbol = '0';
		SimpleTransitionFunction function = newSingleCharTransitionFunction(symbol);
		State initialState = new State("initialState", true);
		State targetState = new State("targetState", false);
		function.addTransition(initialState, targetState, symbol);
		function.addTransition(initialState, targetState, symbol);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void transitionsWithSymbolsNotInAlphabetAreRejected() {
		SimpleTransitionFunction function = new SimpleTransitionFunction();
		State initialState = new State("initialState", true);
		State targetState = new State("targetState", false);
		Set<Character> symbols = new HashSet<>();
		symbols.add('1');
		function.setSymbols(symbols);
		function.addTransition(initialState, targetState, '0');
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void throwsIllegalArgumentExceptionIfSymbolsSetTwice() {
		SimpleTransitionFunction function = newSingleCharTransitionFunction('x');
		Set<Character> symbols = new HashSet<>();
		function.setSymbols(symbols);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void throwsIllegalArgumentExceptionIfSymbolsAreNull() {
		SimpleTransitionFunction function = new SimpleTransitionFunction();
		function.setSymbols(null);
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void alphabetMustBeSetBeforeAddingTransitions() {
		SimpleTransitionFunction function = new SimpleTransitionFunction();
		State initialState = new State("initialState", true);
		State targetState = new State("targetState", false);
		function.addTransition(initialState, targetState, '0');
	}
	
	private SimpleTransitionFunction newSingleCharTransitionFunction(Character symbol) {
		Set<Character> symbols = new HashSet<>();
		symbols.add(symbol);
		return new SimpleTransitionFunction(symbols);
	}
}

