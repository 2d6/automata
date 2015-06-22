package automata;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

public class CharDfaCopyingTest {

	private static final String T3 = "T3";
	private static final String S3 = "S3";
	private static final String S1 = "S1";
	private static final String S2 = "S2";
	private static final String T1 = "T1";
	private static final String T2 = "T2";
	private static final boolean ACCEPTING = true;
	private static final boolean NOT_ACCEPTING = false;

	@Test
	public void testIdenticalAutomatonIsStructurallyEqual() {
		final DeterministicFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		assertStructurallyEqual(a, a);
	}

	@Test
	public void testNewAutomataAreStructurallyEqual() {
		final DeterministicFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		final DeterministicFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		assertStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithDifferentAcceptingStatesAreStructurallyNotEqual() {
		final DeterministicFiniteAutomaton<Character> a = newBoolCharDfa(S1, ACCEPTING);
		final DeterministicFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		assertNotStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithDifferentAlphabetsAreStructurallyEqual() {
		final DeterministicFiniteAutomaton<Character> a = newBoolCharDfa(S1, ACCEPTING);
		final Set<Character> symbols = new HashSet<>();
		final DeterministicFiniteAutomaton<Character> b = new DeterministicFiniteAutomaton<Character>(T1, ACCEPTING, symbols);
		assertStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithDifferentStatesAreNotStructurallyEqual() {
		final DeterministicFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		final DeterministicFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		assertNotStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithSingleTransitionAreStructurallyEqual() {
		final DeterministicFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		final DeterministicFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		assertStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithSingleTransitionAreStructurallyNotEqual() {
		final DeterministicFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		final DeterministicFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addTransition(T1, T2, '1');
		assertNotStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithDifferingSecondaryStatesAreNotEqual() {
		final DeterministicFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		final DeterministicFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, ACCEPTING);
		b.addTransition(T1, T2, '0');
		assertNotStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithUnconnectedStatesAreEqual() {
		final DeterministicFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		final DeterministicFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, ACCEPTING);
		assertStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithBifurcationAreEqual() {
		final DeterministicFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addState(S3, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S1, S3, '1');
		final DeterministicFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addState(T3, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T1, T3, '1');
		
		assertStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithBifurcationAreNotEqual() {
		final DeterministicFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addState(S3, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S1, S3, '1');
		final DeterministicFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addState(T3, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T1, T2, '1'); // transition to state 2 instead of transition to state 3
		
		assertNotStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithChainAreEqual() {
		final DeterministicFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addState(S3, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S2, S3, '1');
		final DeterministicFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addState(T3, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T2, T3, '1');
		
		assertStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithChainAreNotEqual() {
		final DeterministicFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addState(S3, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S2, S3, '1');
		final DeterministicFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addState(T3, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T2, T2, '1'); // transition to state 2 instead of transition to state 3
		
		assertNotStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithLoopAreEqual() {
		final DeterministicFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S2, S1, '1');
		final DeterministicFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T2, T1, '1');
		
		assertStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithLoopAreNotEqual() {
		final DeterministicFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S2, S1, '1');
		final DeterministicFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T2, T2, '1'); // Loop back to same state
		
		assertNotStructurallyEqual(a, b);
	}

	private void assertStructurallyEqual(DeterministicFiniteAutomaton<Character> a, DeterministicFiniteAutomaton<Character> b) {
		assertTrue(a.isStructurallyEqualTo(b));
		assertTrue(b.isStructurallyEqualTo(a));
	}

	private void assertNotStructurallyEqual(DeterministicFiniteAutomaton<Character> a, DeterministicFiniteAutomaton<Character> b) {
		assertFalse(a.isStructurallyEqualTo(b));
		assertFalse(b.isStructurallyEqualTo(a));
	}

	/*
	 * Helper Methods
	 */

	private DeterministicFiniteAutomaton<Character> newBoolCharDfa(String identifier, boolean isAccepting) {
		Set<Character> symbols = new HashSet<>();
		symbols.add('0');
		symbols.add('1');
		return new DeterministicFiniteAutomaton<Character>(identifier, isAccepting, symbols);
	}
}
