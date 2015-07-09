package automata;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automata.interfaces.IState;
import automata.states.State;

public class DeterministicFiniteAutomatonTest {
	
	private static final boolean ACCEPTING = true;
	private static final boolean NOT_ACCEPTING = false;
	
	private static final IState NONEXISTANT_STATE = null;
	private static final String S1 = "S1";
	private static final String S2 = "S2";
	private static final String S3 = "S3";
	private static final String ANOTHER_STATE = "another State";
	
	private static final Character ZERO = '0';
	private static final Character ONE = '1';
	private static final Character INVALID_CHARACTER = '2';
	private static final Set<Character> ALPHABET = new HashSet<>(Arrays.asList(ZERO, ONE));
	
	/*
	 * INVALID AUTOMATA
	 */
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void charDfaWithNullAlphabetMayNotBeCreated() {
		new DeterministicFiniteAutomaton<Character>(S1, ACCEPTING, null);
	}
	
	/*
	 * SYMBOLS
	 */
	
	@Test
	public void allSymbolsMayBeRetrieved() {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		
		Set<Character> actualSymbols = dfa.getAllSymbols();
		
		assertEquals(actualSymbols, ALPHABET);
	}
	
	@Test
	public void changesToExposedSymbolsDoNotAlterAutomaton() {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		dfa.getAllSymbols().add(INVALID_CHARACTER);
		
		Set<Character> actualSymbols = dfa.getAllSymbols();
		
		assertEquals(actualSymbols, ALPHABET);
	}
	
	/*
	 * STATES
	 */
	
	@Test(dataProvider = "sampleStates")
	public void charDfaProvidesSetOfStates(String identifier, boolean isAccepting) {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(identifier, isAccepting);
		
		Set<IState> stateSet = dfa.getStates();
		
		assertTrue(stateSet.contains(dfa.getState(identifier)));
	}
	
	@Test(dataProvider = "sampleStates")
	public void changesToSetOfStatesDoNotModifyAutomaton(String identifier, boolean isAccepting) {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(identifier, isAccepting);
		Set<IState> modifiedStateSet = dfa.getStates();
		modifiedStateSet.add(new State(ANOTHER_STATE, isAccepting));
		
		Set<IState> newStateSet = dfa.getStates();
		
		assertNotEquals(newStateSet, modifiedStateSet);
	}
	
	@Test(dataProvider = "sampleStates")
	public void charDfaHasStartingState(String identifier, boolean isAccepting) {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(identifier, isAccepting);
		IState startingState = dfa.getStartingState();
		Assert.assertEquals(identifier, startingState.getId());
		Assert.assertEquals(isAccepting, startingState.isAccepting());
	}

	@Test(dataProvider = "sampleStates")
	public void charDfaReturnsStatesByTheirIdentifier(String identifier,
			boolean isAccepting) {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(identifier, isAccepting);
		IState startingState = dfa.getState(identifier);
		Assert.assertEquals(identifier, startingState.getId());
		Assert.assertEquals(isAccepting, startingState.isAccepting());
	}

	@Test(dataProvider = "sampleStates")
	public void statesMayBeAddedToCharDfa(String identifier, boolean isAccepting) {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		dfa.addState(identifier, isAccepting);
		IState state = dfa.getState(identifier);
		Assert.assertEquals(identifier, state.getId());
		Assert.assertEquals(isAccepting, state.isAccepting());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void duplicateStatesMayNotBeAddedToCharDfa() {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		dfa.addState(S1, NOT_ACCEPTING);
	}
	
	@DataProvider(name = "sampleStates")
	public static Object[][] getSampleStates() {
		return new Object[][] { { S2, ACCEPTING }, { S3, NOT_ACCEPTING }, };
	}
	
	/*
	 * TRANSITIONS
	 */

	@Test(dataProvider = "nonexistantStates", expectedExceptions = NullPointerException.class)
	public void transitionsContainingNonexistantStatesAreForbidden(
			String startingStateIdentifier, String secondStateIdentifier) {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		dfa.addState(S2, NOT_ACCEPTING);
		dfa.addTransition(startingStateIdentifier, secondStateIdentifier, ZERO);
	}
	
	@DataProvider(name = "nonexistantStates")
	public static Object[][] nonexistantStates() {
		return new Object[][] { { NONEXISTANT_STATE, S2 },
				{ S1, NONEXISTANT_STATE }, { NONEXISTANT_STATE, NONEXISTANT_STATE } };
	}

	/*
	 * EVALUATION
	 */
	
	@Test
	public void charDfaEvaluatesCharacters() {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		dfa.addState(S2, NOT_ACCEPTING);
		dfa.addTransition(S1, S2, ZERO);
		dfa.addTransition(S1, S1, ONE);
		dfa.addTransition(S2, S1, ZERO);
		dfa.addTransition(S2, S2, ONE);
		
		ArrayList<Character> symbolList = new ArrayList<>();
		symbolList.add(ZERO);
		Assert.assertEquals(dfa.evaluate(symbolList),
				dfa.getState(S2));
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void charDfaThrowsIllegalArgumentExceptionForinputContainingIllegalCharacters() {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		dfa.addTransition(S1, S1, ZERO);
		dfa.addTransition(S1, S1, ONE);
		String inputString = "102";
		
		dfa.evaluate(stringToCharacterList(inputString));
	}
	
	/*
	 * COPYING / CLONING
	 */
	
	@Test(dataProvider = "testStrings")
	public void copiedDfaEvaluatesSameAsOriginal(String testString) {
		DeterministicFiniteAutomaton<Character> original = newBoolCharDfa(S1, ACCEPTING);
		original.addState(S2, NOT_ACCEPTING);
		original.addTransition(S1, S2, ZERO);
		
		DeterministicFiniteAutomaton<Character> copy = original.copy();	
		IState originalFinalState = original.evaluate(stringToCharacterList(testString));
		IState copyFinalState = copy.evaluate(stringToCharacterList(testString));
		
		Assert.assertEquals(copyFinalState.getId(), originalFinalState.getId());
	}
	
	@DataProvider(name = "testStrings")
	private Object[][] getStrings() {
		return new Object[][] {
				{"0"},
				{"1"},
				{"1010"},
				{""}
		};
	}
	
	@Test
	public void copiedDfaStartingStatesAreNotIdenticalToOriginalStates() {
		DeterministicFiniteAutomaton<Character> original = newBoolCharDfa(S1, ACCEPTING);
		
		DeterministicFiniteAutomaton<Character> copy = original.copy();	
		
		Assert.assertFalse(original.getState(S1) == copy.getState(S1));
	}

	/*
	 * Helper Methods
	 */
	
	private DeterministicFiniteAutomaton<Character> newBoolCharDfa(String identifier, boolean isAccepting) {
		return new DeterministicFiniteAutomaton<>(identifier, isAccepting, ALPHABET);	
	}
	
	private List<Character> stringToCharacterList(String string) {
		char[] charArray = string.toCharArray();
		ArrayList<Character> characterList = new ArrayList<>();
		for (char ch : charArray) {
			characterList.add(ch);
		}
		return characterList;
	}
}
