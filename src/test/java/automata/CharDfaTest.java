package automata;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CharDfaTest {
	
	private static final State NULL_STATE = null;
	private static final String IDENTIFIER_S1 = "S1";
	private static final String IDENTIFIER_S2 = "S2";
	
	/*
	 * STATES
	 */
	
	@Test(dataProvider = "sampleStates")
	public void charDfaHasStartingState(String identifier, boolean isAccepting) {
		CharDfa dfa = newBoolCharDfa(identifier, isAccepting);
		State startingState = dfa.getStartingState();
		Assert.assertEquals(identifier, startingState.getIdentifier());
		Assert.assertEquals(isAccepting, startingState.isAccepting());
	}

	@Test(dataProvider = "sampleStates")
	public void charDfaReturnsStatesByTheirIdentifier(String identifier,
			boolean isAccepting) {
		CharDfa dfa = newBoolCharDfa(identifier, isAccepting);
		State startingState = dfa.getState(identifier);
		Assert.assertEquals(identifier, startingState.getIdentifier());
		Assert.assertEquals(isAccepting, startingState.isAccepting());
	}

	@Test(dataProvider = "sampleStates")
	public void statesMayBeAddedToCharDfa(String identifier, boolean isAccepting) {
		CharDfa dfa = newBoolCharDfa("startingState", true);
		dfa.addState(identifier, isAccepting);
		State state = dfa.getState(identifier);
		Assert.assertEquals(identifier, state.getIdentifier());
		Assert.assertEquals(isAccepting, state.isAccepting());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void duplicateStatesMayNotBeAddedToCharDfa() {
		CharDfa dfa = newBoolCharDfa("startingState", true);
		dfa.addState("startingState", false);
	}
	
	/*
	 * TRANSITIONS
	 */

	@Test(dataProvider = "nonexistantStates", expectedExceptions = NullPointerException.class)
	public void transitionsContainingNonexistantStatesAreForbidden(
			String startingStateIdentifier, String secondStateIdentifier) {
		CharDfa dfa = newBoolCharDfa("startingState", true);
		dfa.addState("secondState", false);
		dfa.addTransition(startingStateIdentifier, secondStateIdentifier, '0');
	}
	
	/*
	 * EVALUATION
	 */
	
	@Test
	public void charDfaEvaluatesCharacters() {
		CharDfa dfa = newBoolCharDfa("startingState", true);
		dfa.addState("secondState", false);
		dfa.addTransition("startingState", "secondState", '0');
		ArrayList<Character> symbolList = new ArrayList<>();
		symbolList.add('0');
		Assert.assertEquals(dfa.evaluate(symbolList),
				dfa.getState("secondState"));
	}
	
	@Test
	public void charDfaEvaluatesStrings() {
		CharDfa dfa = newBoolCharDfa("startingState", true);
		dfa.addState("secondState", false);
		dfa.addTransition("startingState", "secondState", '0');
		Assert.assertEquals(dfa.evaluate("110"),
				dfa.getState("secondState"));
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void charDfaThrowsIllegalArgumentExceptionForinputContainingIllegalCharacters() {
		CharDfa dfa = newBoolCharDfa("startingState", true);
		String inputString = "102";
		dfa.evaluate(inputString);
	}
	
	@DataProvider(name = "sampleStates")
	public static Object[][] identifiersAndStates() {
		return new Object[][] { { IDENTIFIER_S1, true }, { IDENTIFIER_S2, false }, };
	}

	@DataProvider(name = "nonexistantStates")
	public static Object[][] nonexistantStates() {
		return new Object[][] { { NULL_STATE, "secondState" },
				{ "startingState", NULL_STATE }, { NULL_STATE, NULL_STATE } };
	}
	
	/*
	 * COPYING / CLONING
	 */
	
	@Test(dataProvider = "testStrings")
	public void copiedDfaEvaluatesSameAsOriginal(String testString) {
		CharDfa original = newBoolCharDfa(IDENTIFIER_S1, true);
		original.addState(IDENTIFIER_S2, false);
		original.addTransition(IDENTIFIER_S1, IDENTIFIER_S2, '0');
		
		CharDfa copy = original.copy();	
		State originalFinalState = original.evaluate(testString);
		State copyFinalState = copy.evaluate(testString);
		
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
		CharDfa original = newBoolCharDfa(IDENTIFIER_S1, true);
		
		CharDfa copy = original.copy();	
		
		Assert.assertFalse(original.getState(IDENTIFIER_S1) == copy.getState(IDENTIFIER_S1));
	}

	/*
	 * Helper Methods
	 */
	
	private CharDfa newBoolCharDfa(String identifier, boolean isAccepting) {
		CharAlphabet alphabet = new CharAlphabet();
		alphabet.add('0');
		alphabet.add('1');
		SimpleTransitionFunction transitionFunction = new SimpleTransitionFunction(
				alphabet);
		return new CharDfa(identifier, isAccepting, transitionFunction);
	}
}
