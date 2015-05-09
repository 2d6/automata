package automata;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class SimpleTransitionFunctionTest {
	
	private SimpleTransitionFunction newSingleCharTransitionFunction(Character symbol) {
		CharAlphabet alphabet = new CharAlphabet();
		alphabet.add(symbol);
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
		function.addTransition(initialState, targetState, symbol);
		assertEquals(function.getNextState(initialState, symbol), targetState);
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
		List<Character> symbols = new ArrayList<>();
		symbols.add('1');
		function.setSymbols(symbols);
		function.addTransition(initialState, targetState, '0');
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void throwsIllegalArgumentExceptionIfSymbolsSetTwice() {
		SimpleTransitionFunction function = newSingleCharTransitionFunction('x');
		List<Character> symbols = new ArrayList<>();
		function.setSymbols(symbols);
	}
	
	@Test(expectedExceptions = NullPointerException.class)
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
}
