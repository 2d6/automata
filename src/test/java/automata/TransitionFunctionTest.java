package automata;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class TransitionFunctionTest {
	
	private static final char OTHER_SYMBOL = '1';

	private static final char SYMBOL = '0';

	private TransitionFunction<Character> functionUnderTest;
	
	@Mock
	private State initialState;
	
	@Mock
	private State targetState;
	
	@Mock
	private State anotherState;
	
	@BeforeMethod
	public void init() {
		MockitoAnnotations.initMocks(this);
		functionUnderTest = newCharTransitionFunction(SYMBOL);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void alphabetMayNotBeNull() {
		new TransitionFunction<Character>(null);
	}
	
	@Test
	public void symbolsMayBeSetAndRetrieved() {
		functionUnderTest = newCharTransitionFunction(SYMBOL, OTHER_SYMBOL);
		Set<Character> expectedSymbols= createCharacterSet(SYMBOL, OTHER_SYMBOL);

		Set<Character> validSymbols = functionUnderTest.getSymbols();
		
		assertEquals(validSymbols, expectedSymbols);
	}

	@Test(dataProvider = "testSymbols")
	public void transitionsMayBeAddedAndRetrieved(Character symbol) {
		functionUnderTest = newCharTransitionFunction(symbol);
		functionUnderTest.addTransition(initialState, targetState, symbol);
		assertEquals(functionUnderTest.getNextState(initialState, symbol), targetState);
	}
	
	@DataProvider(name = "testSymbols")
	public static Object[][] symbols() {
		return new Object[][] {
				{'0'},
				{'a'},
				{'ä'}
		};
	}
	
	@Test
	public void validSymbolsForStateMayBeRetrieved() {
		functionUnderTest = newCharTransitionFunction(SYMBOL, OTHER_SYMBOL);
		functionUnderTest.addTransition(initialState, targetState, SYMBOL);
		functionUnderTest.addTransition(initialState, targetState, OTHER_SYMBOL);
		functionUnderTest.addTransition(anotherState, targetState, SYMBOL);
		Set<Character> expectedSymbols= createCharacterSet(SYMBOL, OTHER_SYMBOL);
		
		Set<Character> validSymbols = functionUnderTest.getValidSymbols(initialState);
		
		assertEquals(validSymbols, expectedSymbols);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void duplicateTransitionsAreNotAllowed() {
		functionUnderTest.addTransition(initialState, targetState, SYMBOL);
		functionUnderTest.addTransition(initialState, targetState, SYMBOL);
	}
	
	@Test
	public void multipleTransitionsWithDifferentSymbolsMayBeAdded() {
		functionUnderTest = newCharTransitionFunction(SYMBOL, OTHER_SYMBOL);
		functionUnderTest.addTransition(initialState, targetState, SYMBOL);
		functionUnderTest.addTransition(initialState, anotherState, OTHER_SYMBOL);
		
		State state = functionUnderTest.getNextState(initialState, OTHER_SYMBOL);
		
		assertEquals(state, anotherState);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void transitionsWithSymbolsNotInAlphabetAreRejected() {
		functionUnderTest.addTransition(initialState, targetState, OTHER_SYMBOL);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void throwsIllegalArgumentExceptionIfSymbolsSetTwice() {
		functionUnderTest.setSymbols(new HashSet<>());
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void throwsIllegalArgumentExceptionIfSymbolsAreNull() {
		functionUnderTest = new TransitionFunction<>();
		functionUnderTest.setSymbols(null);
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void alphabetMustBeSetBeforeAddingTransitions() {
		functionUnderTest = new TransitionFunction<>();
		functionUnderTest.addTransition(initialState, targetState, SYMBOL);
	}
	
	@Test
	public void nonAcceptingDefaultStateIsReturnedIfNoTransitionsDefinedForState() {
		State state = functionUnderTest.getNextState(initialState, SYMBOL);
		
		assertTrue(isDefaultState(state, SYMBOL));
	}
	
	@Test
	public void onlyTransitionsWithCurrentSymbolAreEvaluated() {
		functionUnderTest = newCharTransitionFunction(SYMBOL, OTHER_SYMBOL);
		functionUnderTest.addTransition(initialState, targetState, SYMBOL);
		
		State state = functionUnderTest.getNextState(initialState, OTHER_SYMBOL);
		
		assertTrue(isDefaultState(state, OTHER_SYMBOL));
	}
	
	@Test
	public void onlyTransitionsForCurrentStateAreEvaluated() {
		functionUnderTest.addTransition(initialState, targetState, SYMBOL);
		State state = functionUnderTest.getNextState(targetState, SYMBOL);
		
		assertTrue(isDefaultState(state, SYMBOL));
	}
	
	
	private boolean isDefaultState(State state, Character symbol) {
		return state.getIdentifier().equals(new String(new char[]{symbol})) 
				&& !state.isAccepting();
	}
	
	private TransitionFunction<Character> newCharTransitionFunction(Character... symbols) {
		return new TransitionFunction<>(createCharacterSet(symbols));
	}
	
	private Set<Character> createCharacterSet(Character... symbols) {
		Set<Character> symbolSet = new HashSet<>();
		for (Character symbol : symbols) {
			symbolSet.add(symbol);
		}
		return symbolSet;
	}
}


