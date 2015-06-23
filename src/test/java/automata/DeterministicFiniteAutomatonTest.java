package automata;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automata.interfaces.IState;
import automata.states.NullState;

public class DeterministicFiniteAutomatonTest {
	
	private static final boolean ACCEPTING = true;
	private static final boolean NOT_ACCEPTING = false;
	private static final String NULL_STATE_ID = NullState.getInstance().getId();
	
	private static final IState NULL_STATE = null;
	private static final String S1 = "S1";
	private static final String S2 = "S2";
	private static final String S3 = "S3";
	
	/*
	 * INVALID AUTOMATA
	 */
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void charDfaWithNullAlphabetMayNotBeCreated() {
		new DeterministicFiniteAutomaton<Character>(S1, ACCEPTING, null);
	}
	
	/*
	 * STATES
	 */
	
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
		dfa.addTransition(startingStateIdentifier, secondStateIdentifier, '0');
	}
	
	@DataProvider(name = "nonexistantStates")
	public static Object[][] nonexistantStates() {
		return new Object[][] { { NULL_STATE, S2 },
				{ S1, NULL_STATE }, { NULL_STATE, NULL_STATE } };
	}

	/*
	 * EVALUATION
	 */
	
	@Test
	public void charDfaEvaluatesCharacters() {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		dfa.addState(S2, NOT_ACCEPTING);
		dfa.addTransition(S1, S2, '0');
		dfa.addTransition(S1, S1, '1');
		dfa.addTransition(S2, S1, '0');
		dfa.addTransition(S2, S2, '1');
		
		ArrayList<Character> symbolList = new ArrayList<>();
		symbolList.add('0');
		Assert.assertEquals(dfa.evaluate(symbolList),
				dfa.getState(S2));
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void charDfaThrowsIllegalArgumentExceptionForinputContainingIllegalCharacters() {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		dfa.addTransition(S1, S1, '0');
		dfa.addTransition(S1, S1, '1');
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
		original.addTransition(S1, S2, '0');
		
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
	 * GraphViz
	 */
	
	@Test
	public void graphVizForBasicAutomatonIsCorrect() {
		DeterministicFiniteAutomaton<Character> original = newBoolCharDfa(S1, ACCEPTING);
		String expected = "digraph automaton "
				+ "{\nrankdir=LR;\nsize=\"8,5\"\nnode [shape = doublecircle];"
				+ "S1;\nnode [shape = circle];\n" 
				+ "S1 -> " + NULL_STATE_ID 
				+ " [ label = \"0\" ];\n"
				+ "S1 -> " + NULL_STATE_ID 
				+ " [ label = \"1\" ];\n}";
		
		assertEquals(original.toGraphViz(), expected);
	}
	
	@Test
	public void graphVizTransitionsImplementedCorrectly() {
		DeterministicFiniteAutomaton<Character> original = newBoolCharDfa(S1, ACCEPTING);
		original.addState(S2, NOT_ACCEPTING);
		original.addTransition(S1, S2, '0');
		String expected = "digraph automaton "
				+ "{\nrankdir=LR;\nsize=\"8,5\"\nnode [shape = doublecircle];"
				+ "S1;\nnode [shape = circle];\n" 
				+ "S1 -> S2 [ label = \"0\" ];\n"
				+ "S1 -> " + NULL_STATE_ID + " [ label = \"1\" ];\n"
				+ "S2 -> " + NULL_STATE_ID + " [ label = \"0\" ];\n"
				+ "S2 -> " + NULL_STATE_ID + " [ label = \"1\" ];\n}";
		
		System.out.println(expected);
		
		assertEquals(original.toGraphViz(), expected);
	}

	/*
	 * Helper Methods
	 */
	
	private DeterministicFiniteAutomaton<Character> newBoolCharDfa(String identifier, boolean isAccepting) {
		Set<Character> alphabet = new HashSet<>();
		alphabet.add('0');
		alphabet.add('1');
		
		return new DeterministicFiniteAutomaton<>(identifier, isAccepting, alphabet);	
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
