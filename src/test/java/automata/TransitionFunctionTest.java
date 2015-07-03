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

import automata.interfaces.IState;
import automata.states.NullState;


public class TransitionFunctionTest {
	
	private static final char OTHER_SYMBOL = '1';

	private static final char SYMBOL = '0';

	private TransitionFunction<Character> functionUnderTest;
	
	@Mock
	private IState initialState;
	
	@Mock
	private IState targetState;
	
	@Mock
	private IState anotherState;
	
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
	
	@Test
	public void symbolExistenceMayBeQueried() {
		functionUnderTest = newCharTransitionFunction(SYMBOL);
		
		assertTrue(functionUnderTest.containsSymbol(SYMBOL));
		assertFalse(functionUnderTest.containsSymbol(OTHER_SYMBOL));
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
		
		IState state = functionUnderTest.getNextState(initialState, OTHER_SYMBOL);
		
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
	public void nullStateIsReturnedIfNoTransitionsDefinedForState() {
		IState state = functionUnderTest.getNextState(initialState, SYMBOL);
		
		assertTrue(NullState.isNullState(state));
	}
	
	@Test
	public void onlyTransitionsWithCurrentSymbolAreEvaluated() {
		functionUnderTest = newCharTransitionFunction(SYMBOL, OTHER_SYMBOL);
		functionUnderTest.addTransition(initialState, targetState, SYMBOL);
		
		IState state = functionUnderTest.getNextState(initialState, OTHER_SYMBOL);
		
		assertTrue(NullState.isNullState(state));
	}
	
	@Test
	public void onlyTransitionsForCurrentStateAreEvaluated() {
		functionUnderTest.addTransition(initialState, targetState, SYMBOL);
		IState state = functionUnderTest.getNextState(targetState, SYMBOL);
		
		assertTrue(NullState.isNullState(state));
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void transitionFunctionThrowsIllegalArgExceptionOnTransitionFromNullState() {
		functionUnderTest.addTransition(NullState.getInstance(), targetState, SYMBOL);
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


