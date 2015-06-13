package automata;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automata.interfaces.IEpsilonTransitionFunction;


public class NondeterministicFiniteAutomatonTest {

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
	public void nfaWithNullTransitionFunctionMayNotBeCreated() {
		new NondeterministicFiniteAutomaton<Character>(S1, ACCEPTING, null);
	}
	
	/*
	 * STATES
	 */
	
	@Test(dataProvider = "sampleStates")
	public void nfaHasStartingState(String identifier, boolean isAccepting) {
		NondeterministicFiniteAutomaton<Character> nfa = newBoolCharDfa(identifier, isAccepting);
		State startingState = nfa.getStartingState();
		Assert.assertEquals(identifier, startingState.getIdentifier());
		Assert.assertEquals(isAccepting, startingState.isAccepting());
	}

	@Test(dataProvider = "sampleStates")
	public void nfaReturnsStatesByTheirIdentifier(String identifier,
			boolean isAccepting) {
		NondeterministicFiniteAutomaton<Character> nfa = newBoolCharDfa(identifier, isAccepting);
		State startingState = nfa.getState(identifier);
		Assert.assertEquals(identifier, startingState.getIdentifier());
		Assert.assertEquals(isAccepting, startingState.isAccepting());
	}

	@Test(dataProvider = "sampleStates")
	public void statesMayBeAddedToCharDfa(String identifier, boolean isAccepting) {
		NondeterministicFiniteAutomaton<Character> nfa = newBoolCharDfa(S1, ACCEPTING);
		nfa.addState(identifier, isAccepting);
		State state = nfa.getState(identifier);
		Assert.assertEquals(identifier, state.getIdentifier());
		Assert.assertEquals(isAccepting, state.isAccepting());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void duplicateStatesMayNotBeAddedToCharDfa() {
		NondeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
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
		NondeterministicFiniteAutomaton<Character> nfa = newBoolCharDfa(S1, ACCEPTING);
		nfa.addState(S2, NOT_ACCEPTING);
		nfa.addTransition(startingStateIdentifier, secondStateIdentifier, '0');
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
	public void nfaEvaluatesCharacters() {
		NondeterministicFiniteAutomaton<Character> nfa = newBoolCharDfa(S1, ACCEPTING);
		nfa.addState(S2, NOT_ACCEPTING);
		nfa.addTransition(S1, S2, '0');
		
		Set<State> states = nfa.evaluate(Arrays.asList('0'));
		
		Assert.assertEquals(states.size(), 1);
		State state = new LinkedList<>(states).getFirst();
		Assert.assertEquals(state.getIdentifier(), S2);
		Assert.assertFalse(state.isAccepting());
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nfaThrowsIllegalArgumentExceptionForinputContainingIllegalCharacters() {
		NondeterministicFiniteAutomaton<Character> nfa = newBoolCharDfa(S1, ACCEPTING);
		nfa.addTransition(S1, S1, '0');
		nfa.addTransition(S1, S1, '1');
		String inputString = "102";
		
		nfa.evaluate(stringToCharacterList(inputString));
	}
	
	@Test
	public void nfaReturnsDefaultStateIfNoMoreTransitionsAvailable() {
		NondeterministicFiniteAutomaton<Character> nfa = newBoolCharDfa(S1, ACCEPTING);
		
		Set<State> states = nfa.evaluate(stringToCharacterList("10"));
		
		Assert.assertEquals(states.size(), 1);
		State state = new LinkedList<>(states).getFirst();
		Assert.assertEquals(state.getIdentifier(), "1");
		Assert.assertFalse(state.isAccepting());
	}
	
	/*
	 * COPYING / CLONING
	 */
	
	@Test(dataProvider = "testStrings", enabled = false)
	public void copiedNfaEvaluatesSameAsOriginal(String testString) {
		NondeterministicFiniteAutomaton<Character> original = newBoolCharDfa(S1, ACCEPTING);
		original.addState(S2, NOT_ACCEPTING);
		original.addTransition(S1, S2, '0');
		
		INondeterministicFiniteAutomaton<Character> copy = original.copy();	
		Set<State> originalFinalStates = original.evaluate(stringToCharacterList(testString));
		Set<State> copyFinalStates = copy.evaluate(stringToCharacterList(testString));
		
		Assert.assertTrue(stateSetsContainSameIdentifiers(originalFinalStates, copyFinalStates));
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
	public void copiedNfaStartingStateIsNotIdenticalToOriginalState() {
		NondeterministicFiniteAutomaton<Character> original = newBoolCharDfa(S1, ACCEPTING);
		
		NondeterministicFiniteAutomaton<Character> copy = original.copy();	
		
		Assert.assertFalse(original.getState(S1) == copy.getState(S1));
	}
	
	/*
	 * Epsilon Transitions
	 */
	
	@Test
	public void evaluatingEpsilonTransitionsIsPossible() {
		NondeterministicFiniteAutomaton<Character> original = newBoolCharDfa(S1, NOT_ACCEPTING);
		original.addState(S2, NOT_ACCEPTING);
		original.addState(S3, ACCEPTING);
		original.addTransition(S2, S3, '0');
		original.addEpsilonTransition(S1, S2);
		
		Set<State> states = original.evaluate(stringToCharacterList("0"));
		
		assertTrue(setContainsOnlyStatesWithGivenIdentifiers(states, Arrays.asList(S3, "0")));
	}
	
	@Test
	public void evaluationOfEmptyInputReturnsExpandedStatesOfStartingState() {
		NondeterministicFiniteAutomaton<Character> original = newBoolCharDfa(S1, NOT_ACCEPTING);
		original.addState(S2, NOT_ACCEPTING);
		original.addEpsilonTransition(S1, S2);
		
		Set<State> states = original.evaluate(Collections.emptyList());
		
		Assert.assertTrue(setContainsOnlyStatesWithGivenIdentifiers(states, Arrays.asList(S1, S2)));
	}
	
	@Test
	public void cyclesOfEpsilonTransitionsMayBeEvaluated() {
		NondeterministicFiniteAutomaton<Character> original = newBoolCharDfa(S1, NOT_ACCEPTING);
		original.addState(S2, NOT_ACCEPTING);
		original.addState(S3, ACCEPTING);
		original.addTransition(S2, S3, '0');
		original.addEpsilonTransition(S1, S2);
		original.addEpsilonTransition(S2, S1);
		
		Set<State> states = original.evaluate(stringToCharacterList("0"));
		
		Assert.assertTrue(setContainsOnlyStatesWithGivenIdentifiers(states, Arrays.asList(S3, "0")));
	}

	/*
	 * Helper Methods
	 */
	
	private NondeterministicFiniteAutomaton<Character> newBoolCharDfa(String identifier, boolean isAccepting) {
		Set<Character> alphabet = new HashSet<>();
		alphabet.add('0');
		alphabet.add('1');
		
		IEpsilonTransitionFunction<Character> transitionFunction = new EpsilonTransitionFunction<Character>(
				alphabet);	
		
		NondeterministicFiniteAutomaton<Character> nfa = 
				new NondeterministicFiniteAutomaton<Character>(identifier, isAccepting, transitionFunction);	
		return nfa;
	}
	
	private List<Character> stringToCharacterList(String string) {
		char[] charArray = string.toCharArray();
		ArrayList<Character> characterList = new ArrayList<>();
		for (char ch : charArray) {
			characterList.add(ch);
		}
		return characterList;
	}
	
	private boolean setContainsOnlyStatesWithGivenIdentifiers(Set<State> states, List<String> identifiers) {
		return states.stream().allMatch(state -> identifiers.contains(state.getIdentifier()));
	}
	
	private boolean stateSetsContainSameIdentifiers(
			Set<State> originalStates, Set<State> copyStates) {
		return getIdentifiers(copyStates).equals(getIdentifiers(originalStates));
	}
	
	private Set<String> getIdentifiers(Set<State> states) {
		return 	states.stream()
				.map(state -> state.getIdentifier())
				.collect(Collectors.toSet()); 
	}
	
}
