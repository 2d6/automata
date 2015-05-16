package automata;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

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
		final CharDfa a = newBoolCharDfa(S1, NOT_ACCEPTING);
		assertStructurallyEqual(a, a);
	}

	@Test
	public void testNewAutomataAreStructurallyEqual() {
		final CharDfa a = newBoolCharDfa(S1, NOT_ACCEPTING);
		final CharDfa b = newBoolCharDfa(T1, NOT_ACCEPTING);
		assertStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithDifferentAcceptingStatesAreStructurallyNotEqual() {
		final CharDfa a = newBoolCharDfa(S1, ACCEPTING);
		final CharDfa b = newBoolCharDfa(T1, NOT_ACCEPTING);
		assertNotStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithDifferentAlphabetsAreStructurallyEqual() {
		final CharDfa a = newBoolCharDfa(S1, ACCEPTING);
		final Alphabet<Character> alphabet = new CharAlphabet();
		final SimpleTransitionFunction transitionFunction = new SimpleTransitionFunction(alphabet);
		final CharDfa b = new CharDfa(T1, ACCEPTING, transitionFunction);
		assertStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithDifferentStatesAreNotStructurallyEqual() {
		final CharDfa a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		final CharDfa b = newBoolCharDfa(T1, NOT_ACCEPTING);
		assertNotStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithSingleTransitionAreStructurallyEqual() {
		final CharDfa a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		final CharDfa b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		assertStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithSingleTransitionAreStructurallyNotEqual() {
		final CharDfa a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		final CharDfa b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addTransition(T1, T2, '1');
		assertNotStructurallyEqual(a, b);
	}

	@Test
	public void testAutomataWithDifferingSecondaryStatesAreNotEqual() {
		final CharDfa a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		final CharDfa b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, ACCEPTING);
		b.addTransition(T1, T2, '0');
		assertNotStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithBifurcationAreEqual() {
		final CharDfa a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addState(S3, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S1, S3, '1');
		final CharDfa b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addState(T3, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T1, T3, '1');
		
		assertStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithBifurcationAreNotEqual() {
		final CharDfa a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addState(S3, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S1, S3, '1');
		final CharDfa b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addState(T3, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T1, T2, '1'); // transition to state 2 instead of transition to state 3
		
		assertNotStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithChainAreEqual() {
		final CharDfa a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addState(S3, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S2, S3, '1');
		final CharDfa b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addState(T3, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T2, T3, '1');
		
		assertStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithChainAreNotEqual() {
		final CharDfa a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addState(S3, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S2, S3, '1');
		final CharDfa b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addState(T3, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T2, T2, '1'); // transition to state 2 instead of transition to state 3
		
		assertNotStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithLoopAreEqual() {
		final CharDfa a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S2, S1, '1');
		final CharDfa b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T2, T1, '1');
		
		assertStructurallyEqual(a, b);
	}
	
	@Test
	public void testAutomataWithLoopAreNotEqual() {
		final CharDfa a = newBoolCharDfa(S1, NOT_ACCEPTING);
		a.addState(S2, NOT_ACCEPTING);
		a.addTransition(S1, S2, '0');
		a.addTransition(S2, S1, '1');
		final CharDfa b = newBoolCharDfa(T1, NOT_ACCEPTING);
		b.addState(T2, NOT_ACCEPTING);
		b.addTransition(T1, T2, '0');
		b.addTransition(T2, T2, '1'); // Loop back to same state
		
		assertNotStructurallyEqual(a, b);
	}

	private void assertStructurallyEqual(CharDfa a, CharDfa b) {
		assertTrue(a.isStructurallyEqualTo(b));
		assertTrue(b.isStructurallyEqualTo(a));
	}

	private void assertNotStructurallyEqual(CharDfa a, CharDfa b) {
		assertFalse(a.isStructurallyEqualTo(b));
		assertFalse(b.isStructurallyEqualTo(a));
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