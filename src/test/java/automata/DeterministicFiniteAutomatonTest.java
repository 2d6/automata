package automata;

import static org.testng.Assert.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class DeterministicFiniteAutomatonTest {

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
				{"\0"}
		};
	}
	
	@Test(dataProvider = "sampleStates")
	public void dfaContainsStartingState(String identifier, boolean isAccepting) {
		DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton(identifier, isAccepting);
		State startingState = dfa.getStartingState();
		assertEquals(identifier, startingState.getIdentifier());
		assertEquals(isAccepting, startingState.isAccepting());
	}
	
	@Test(dataProvider = "sampleStates")
	public void statesMayBeQueriedByIdentifier(String identifier, boolean isAccepting) {
		DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton(identifier, isAccepting);
		State startingState = dfa.getState(identifier);
		assertEquals(identifier, startingState.getIdentifier());
		assertEquals(isAccepting, startingState.isAccepting());
	}
	
	@Test(dataProvider = "sampleStates")
	public void statesMayBeAdded(String identifier, boolean isAccepting) {
		DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton("startingState", true);
		dfa.addState(identifier, isAccepting);
		State state = dfa.getState(identifier);
		assertEquals(identifier, state.getIdentifier());
		assertEquals(isAccepting, state.isAccepting());
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void duplicateStatesMayNotBeAdded() {
		DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton("startingState", true);
		dfa.addState("startingState", false);
	}
	
	@Test
	public void StringsMayBeEvaluated() {
		DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton("startingState", true);
		dfa.addState("secondState", false);
		dfa.addTransition("startingState", "secondState", '0');
		assertEquals(dfa.evaluate("0"), dfa.getState("secondState"));
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
		DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton("startingState", true);
		dfa.addState("secondState", false);
		dfa.addTransition(startingStateIdentifier, secondStateIdentifier, '0');
	}
	
	@Test(dataProvider = "whitespaceOrEmptyIdentifiers", expectedExceptions = NullPointerException.class)
	public void whitespaceOrEmptyIdentifiersAreNotAllowed(String identifier) {
		DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton("startingState", true);
		dfa.addState(identifier, true);
	}
	
}
