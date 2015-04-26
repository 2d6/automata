package automata;

import static org.testng.Assert.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class DeterministicFiniteAutomatonTest {
	
	private DeterministicFiniteAutomaton<Character> newBoolCharDfa(String identifier, boolean isAccepting) {
		CharAlphabet alphabet = new CharAlphabet(new Character[]{'0','1'});
		SimpleTransitionFunction transitionFunction = new SimpleTransitionFunction(alphabet);
		return new DeterministicFiniteAutomaton<Character>(identifier, isAccepting, transitionFunction);
	}

	@DataProvider(name = "sampleStates") 
	public static Object[][] identifiersAndStates() {
		return new Object[][] {
				{"S1", true},
				{"S2", false},
		};
	}
	
	@DataProvider(name = "whitespaceOrEmptyIdentifiers") 
	public static Object[][] whitespaceOrEmptyIdentifiers() {
		return new Object[][] {
				{""},
				{" "},
				{"  "},
				{"\0"},
				{"\0\0"}
		};
	}
	
	@Test(dataProvider = "sampleStates")
	public void dfaContainsStartingState(String identifier, boolean isAccepting) {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(identifier, isAccepting);
		State startingState = dfa.getStartingState();
		assertEquals(identifier, startingState.getIdentifier());
		assertEquals(isAccepting, startingState.isAccepting());
	}
	
	@Test(dataProvider = "sampleStates")
	public void statesMayBeQueriedByIdentifier(String identifier, boolean isAccepting) {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(identifier, isAccepting);
		State startingState = dfa.getState(identifier);
		assertEquals(identifier, startingState.getIdentifier());
		assertEquals(isAccepting, startingState.isAccepting());
	}
	
	@Test(dataProvider = "sampleStates")
	public void statesMayBeAdded(String identifier, boolean isAccepting) {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa("startingState", true);
		dfa.addState(identifier, isAccepting);
		State state = dfa.getState(identifier);
		assertEquals(identifier, state.getIdentifier());
		assertEquals(isAccepting, state.isAccepting());
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void duplicateStatesMayNotBeAdded() {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa("startingState", true);
		dfa.addState("startingState", false);
	}
	
	@Test
	public void CharactersMayBeEvaluated() {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa("startingState", true);
		dfa.addState("secondState", false);
		dfa.addTransition("startingState", "secondState", '0');
		assertEquals(dfa.evaluate(new Character[]{'0'}), dfa.getState("secondState"));
	}
	
	@DataProvider(name = "nonexistantStates")
	public static Object[][] nonexistantStates() {
		return new Object[][] {
				{null, "secondState"},
				{"startingState", null},
				{null, null}
		};
	}
	
	@Test(dataProvider = "nonexistantStates", expectedExceptions = NullPointerException.class)
	public void transitionsToAndFromNonexistantStatesAreForbidden(String startingStateIdentifier, String secondStateIdentifier) {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa("startingState", true);
		dfa.addState("secondState", false);
		dfa.addTransition(startingStateIdentifier, secondStateIdentifier, '0');
	}
	
	@Test(dataProvider = "whitespaceOrEmptyIdentifiers", expectedExceptions = IllegalArgumentException.class)
	public void whitespaceOrEmptyIdentifiersAreNotAllowed(String identifier) {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa("startingState", true);
		dfa.addState(identifier, true);
	}
	
}
