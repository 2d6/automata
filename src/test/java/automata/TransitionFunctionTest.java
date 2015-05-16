package automata;

import static org.testng.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class TransitionFunctionTest {
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void alphabetMayNotBeNull() {
		new TransitionFunction<Character>(null);
	}
	
	@Test(dataProvider = "testSymbols")
	public void transitionsMayBeAddedAndRetrieved(Character symbol) {
		TransitionFunction<Character> function = newSingleCharTransitionFunction(symbol);
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
		TransitionFunction<Character> function = newSingleCharTransitionFunction(symbol);
		State initialState = new State("initialState", true);
		State targetState = new State("targetState", false);
		function.addTransition(initialState, targetState, symbol);
		function.addTransition(initialState, targetState, symbol);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void transitionsWithSymbolsNotInAlphabetAreRejected() {
		TransitionFunction<Character> function = new TransitionFunction<>();
		State initialState = new State("initialState", true);
		State targetState = new State("targetState", false);
		Set<Character> symbols = new HashSet<>();
		symbols.add('1');
		function.setSymbols(symbols);
		function.addTransition(initialState, targetState, '0');
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void throwsIllegalArgumentExceptionIfSymbolsSetTwice() {
		TransitionFunction<Character> function = newSingleCharTransitionFunction('x');
		Set<Character> symbols = new HashSet<>();
		function.setSymbols(symbols);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void throwsIllegalArgumentExceptionIfSymbolsAreNull() {
		TransitionFunction<Character> function = new TransitionFunction<>();
		function.setSymbols(null);
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void alphabetMustBeSetBeforeAddingTransitions() {
		TransitionFunction<Character> function = new TransitionFunction<>();
		State initialState = new State("initialState", true);
		State targetState = new State("targetState", false);
		function.addTransition(initialState, targetState, '0');
	}
	
	private TransitionFunction<Character> newSingleCharTransitionFunction(Character symbol) {
		Set<Character> symbols = new HashSet<>();
		symbols.add(symbol);
		return new TransitionFunction<>(symbols);
	}
}

