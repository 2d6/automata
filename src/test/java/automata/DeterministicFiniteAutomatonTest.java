package automata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DeterministicFiniteAutomatonTest {
	
	private static final boolean ACCEPTING = true;
	private static final boolean NOT_ACCEPTING = false;
	
	private static final State NULL_STATE = null;
	private static final String S1 = "S1";
	private static final String S2 = "S2";
	private static final String S3 = "S3";
	
	/*
	 * INVALID AUTOMATA
	 */
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void charDfaWithNullTransitionFunctionMayNotBeCreated() {
		new DeterministicFiniteAutomaton<Character>(S1, ACCEPTING, null);
	}
	
	/*
	 * STATES
	 */
	
	@Test(dataProvider = "sampleStates")
	public void charDfaHasStartingState(String identifier, boolean isAccepting) {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(identifier, isAccepting);
		State startingState = dfa.getStartingState();
		Assert.assertEquals(identifier, startingState.getIdentifier());
		Assert.assertEquals(isAccepting, startingState.isAccepting());
	}

	@Test(dataProvider = "sampleStates")
	public void charDfaReturnsStatesByTheirIdentifier(String identifier,
			boolean isAccepting) {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(identifier, isAccepting);
		State startingState = dfa.getState(identifier);
		Assert.assertEquals(identifier, startingState.getIdentifier());
		Assert.assertEquals(isAccepting, startingState.isAccepting());
	}

	@Test(dataProvider = "sampleStates")
	public void statesMayBeAddedToCharDfa(String identifier, boolean isAccepting) {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		dfa.addState(identifier, isAccepting);
		State state = dfa.getState(identifier);
		Assert.assertEquals(identifier, state.getIdentifier());
		Assert.assertEquals(isAccepting, state.isAccepting());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void duplicateStatesMayNotBeAddedToCharDfa() {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		dfa.addState(S1, NOT_ACCEPTING);
	}
	
	/*
	 * TRANSITIONS
	 */

	@Test(dataProvider = "nonexistantStates", expectedExceptions = NullPointerException.class)
	public void transitionsContainingNonexistantStatesAreForbidden(
			String startingStateIdentifier, String secondStateIdentifier) {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		dfa.addState(S2, NOT_ACCEPTING);
		dfa.addTransition(startingStateIdentifier, secondStateIdentifier, '0');
	}
	
	/*
	 * EVALUATION
	 */
	
	@Test
	public void charDfaEvaluatesCharacters() {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		dfa.addState(S2, NOT_ACCEPTING);
		dfa.addTransition(S1, S2, '0');
		ArrayList<Character> symbolList = new ArrayList<>();
		symbolList.add('0');
		Assert.assertEquals(dfa.evaluate(symbolList),
				dfa.getState(S2));
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void charDfaThrowsIllegalArgumentExceptionForinputContainingIllegalCharacters() {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		String inputString = "102";
		dfa.evaluate(stringToCharacterList(inputString));
	}
	
	/*
	 * TRANSITIONS
	 */
	
	@DataProvider(name = "sampleStates")
	public static Object[][] getSampleStates() {
		return new Object[][] { { S2, ACCEPTING }, { S3, NOT_ACCEPTING }, };
	}

	@DataProvider(name = "nonexistantStates")
	public static Object[][] nonexistantStates() {
		return new Object[][] { { NULL_STATE, S2 },
				{ S1, NULL_STATE }, { NULL_STATE, NULL_STATE } };
	}
	
	/*
	 * COPYING / CLONING
	 */
	
	@Test(dataProvider = "testStrings")
	public void copiedDfaEvaluatesSameAsOriginal(String testString) {
		DeterministicFiniteAutomaton<Character> original = newBoolCharDfa(S1, ACCEPTING);
		original.addState(S2, NOT_ACCEPTING);
		original.addTransition(S1, S2, '0');
		
		DeterministicFiniteAutomaton<Character> copy = original.copy();	
		State originalFinalState = original.evaluate(stringToCharacterList(testString));
		State copyFinalState = copy.evaluate(stringToCharacterList(testString));
		
		Assert.assertEquals(copyFinalState.getIdentifier(), originalFinalState.getIdentifier());
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
		Set<Character> alphabet = new HashSet<>();
		alphabet.add('0');
		alphabet.add('1');
		TransitionFunction<Character> transitionFunction = new TransitionFunction<>(
				alphabet);
		return new DeterministicFiniteAutomaton<Character>(identifier, isAccepting, transitionFunction);
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
