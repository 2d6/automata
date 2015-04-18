package de._2d6.dfa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeterministicFiniteAutomatonTest {

	/**
	 * The current state of the automaton may be identified via name
	 */
	@Test
	public void currentStateMayBeIdentified() {
		DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton();
		dfa.addState("State", true);
		assertEquals("State", dfa.getCurrentStateIdentifier());
	}

	/**
	 * The first state added to the automaton is its current state
	 */
	@Test
	public void firstStateShouldBeCurrentState() {
		DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton();
		dfa.addState("State", true);
		assertEquals("State", dfa.getCurrentStateIdentifier());
	}

	/**
	 * It may be discerned whether the current state of the Automaton is
	 * accepting
	 */
	@Test
	public void currentAcceptStatusMayBeDiscerned() {
		DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton();
		dfa.addState("State", true);
		assertTrue(dfa.isCurrentStateAccepting());

		dfa = new DeterministicFiniteAutomaton();
		dfa.addState("State", false);
		assertFalse(dfa.isCurrentStateAccepting());
	}

	/**
	 * If an empty input String is supplied, the automaton should be in its
	 * starting position
	 */
	@Test
	public void noStateChangeIfInputIsEmptyList() {
		DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton();
		dfa.addState("Start State", true);
		dfa.evaluateInput("");
		assertEquals(dfa.getStartingStateIdentifier(),
				dfa.getCurrentStateIdentifier());
	}

	/**
	 * The state of the automaton does not change if no transition between
	 * states is defined
	 */
	@Test
	public void noStateChangeIfNoRelevantTransitionIsDefined() {
		DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton();
		dfa.addState("S1", true); // As this is the first state to be added, it
									// is the starting state
		dfa.addState("S2", false);

		String input = "A";

		dfa.evaluateInput(input);
		assertEquals(dfa.getStartingStateIdentifier(),
				dfa.getCurrentStateIdentifier());
	}

	 /**
	 * The state of the automaton should change, according to the transition function.
	 to an element of the input list exists for the current state
	 */
	 @Test
	 public void stateChangesIfRelevantTransitionIsDefined() {
		 DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton();
			dfa.addState("S1", true); // As this is the first state to be added, it
										// is the starting state
			dfa.addState("S2", false);

			String input = "A";
			
			dfa.addTransition("S1",'A',"S2");

			dfa.evaluateInput(input);
			assertEquals("S2", dfa.getCurrentStateIdentifier());
	 }
}
