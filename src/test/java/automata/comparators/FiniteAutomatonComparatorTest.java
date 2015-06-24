package automata.comparators;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automata.AbstractFiniteAutomaton;
import automata.DeterministicFiniteAutomaton;
import automata.NondeterministicFiniteAutomaton;


public class FiniteAutomatonComparatorTest {
	
	private static final String T3 = "T3";
	private static final String S3 = "S3";
	private static final String S1 = "S1";
	private static final String S2 = "S2";
	private static final String T1 = "T1";
	private static final String T2 = "T2";
	private static final boolean ACCEPTING = true;
	private static final boolean NOT_ACCEPTING = false;
	
	private FiniteAutomatonComparator<Character> comparator;
	
	@BeforeMethod
	public void init() {
		comparator = new FiniteAutomatonComparator<>();
	}

	@Test
	public void testIdenticalAutomatonIsStructurallyEqual() {
		final AbstractFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		assertStructurallyEqual(a, a);
	}

	@Test
	public void testNewAutomataAreStructurallyEqual() {
		final AbstractFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		final AbstractFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		assertStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithDifferentAcceptingStatesAreStructurallyNotEqual() {
		final AbstractFiniteAutomaton<Character> a = newBoolCharDfa(S1, ACCEPTING);
		final AbstractFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		assertNotStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithDifferentAlphabetsAreStructurallyEqual() {
		final AbstractFiniteAutomaton<Character> a = newBoolCharDfa(S1, ACCEPTING);
		final Set<Character> symbols = new HashSet<>();
		final AbstractFiniteAutomaton<Character> b = new DeterministicFiniteAutomaton<>(T1, ACCEPTING, symbols);
		assertStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithDifferentStatesAreNotStructurallyEqual() {
		final AbstractFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		final AbstractFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		assertNotStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithSingleTransitionAreStructurallyEqual() {
		final AbstractFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		final AbstractFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		assertStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithSingleTransitionAreStructurallyNotEqual() {
		final AbstractFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		final AbstractFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addTransition(T1, T2, '1');
		assertNotStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithDifferingSecondaryStatesAreNotEqual() {
		final AbstractFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		final AbstractFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, ACCEPTING);
		b.addTransition(T1, T2, '0');
		assertNotStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithUnconnectedStatesAreEqual() {
		final AbstractFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		final AbstractFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, ACCEPTING);
		assertStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithBifurcationAreEqual() {
		final AbstractFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addState(S3, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S1, S3, '1');
		final AbstractFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addState(T3, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T1, T3, '1');
		
		assertStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithBifurcationAreNotEqual() {
		final AbstractFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addState(S3, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S1, S3, '1');
		final AbstractFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addState(T3, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T1, T2, '1'); // transition to state 2 instead of transition to state 3
		
		assertNotStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithChainAreEqual() {
		final AbstractFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addState(S3, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S2, S3, '1');
		final AbstractFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addState(T3, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T2, T3, '1');
		
		assertStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithChainAreNotEqual() {
		final AbstractFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addState(S3, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S2, S3, '1');
		final AbstractFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addState(T3, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T2, T2, '1'); // transition to state 2 instead of transition to state 3
		
		assertNotStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithLoopAreEqual() {
		final AbstractFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S2, S1, '1');
		final AbstractFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T2, T1, '1');
		
		assertStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithLoopAreNotEqual() {
		final AbstractFiniteAutomaton<Character> a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S2, S1, '1');
		final AbstractFiniteAutomaton<Character> b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T2, T2, '1'); // Loop back to same state
		
		assertNotStructurallyEqual(a, b);
	}

	/*
	 * Helper Methods
	 */

	private void assertStructurallyEqual(AbstractFiniteAutomaton<Character> a, AbstractFiniteAutomaton<Character> b) {
		assertTrue(comparator.structurallyEqual(a, b));
		assertTrue(comparator.structurallyEqual(b, a));
	}

	private void assertNotStructurallyEqual(AbstractFiniteAutomaton<Character> a, AbstractFiniteAutomaton<Character> b) {
		assertFalse(comparator.structurallyEqual(a, b));
		assertFalse(comparator.structurallyEqual(b, a));
	}
	
	private AbstractFiniteAutomaton<Character> newBoolCharDfa(String identifier, boolean isAccepting) {
		Set<Character> symbols = new HashSet<>();
		symbols.add('0');
		symbols.add('1');
		return new DeterministicFiniteAutomaton<Character>(identifier, isAccepting, symbols);
	}
	
	private NondeterministicFiniteAutomaton<Character> newBoolCharNfa(String identifier, boolean isAccepting) {
		Set<Character> symbols = new HashSet<>();
		symbols.add('0');
		symbols.add('1');
		return new NondeterministicFiniteAutomaton<Character>(identifier, isAccepting, symbols);
	}
}
