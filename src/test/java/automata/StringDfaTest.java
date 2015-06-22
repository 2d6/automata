package automata;

import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;


public class StringDfaTest {
	
	private static final boolean ACCEPTING = true;
	private static final boolean NOT_ACCEPTING = false;
	
	private static final String S1 = "S1";
	private static final String S2 = "S2";
	
	@Test
	public void stringDfaEvaluatesStrings() {
		StringDfa dfa = newBoolStringDfa(S1, ACCEPTING);
		dfa.addState(S2, NOT_ACCEPTING);
		dfa.addTransition(S1, S2, '0');
		dfa.addTransition(S1, S1, '1');
		Assert.assertEquals(dfa.evaluate("110"),
				dfa.getState(S2));
	}
	
	/*
	 * Helper Methods
	 */
	
	private StringDfa newBoolStringDfa(String identifier, boolean isAccepting) {
		Set<Character> alphabet = new HashSet<>();
		alphabet.add('0');
		alphabet.add('1');
		return new StringDfa(identifier, isAccepting, alphabet);
	}
}
