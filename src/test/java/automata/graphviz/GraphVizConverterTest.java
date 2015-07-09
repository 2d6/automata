package automata.graphviz;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

import automata.DeterministicFiniteAutomaton;
import automata.NondeterministicFiniteAutomaton;
import automata.interfaces.IDeterministicFiniteAutomaton;
import automata.interfaces.INondeterministicFiniteAutomaton;
import automata.states.NullState;


public class GraphVizConverterTest {
	
	private static final String S1 = "S1";
	private static final String S2 = "S2";
	private static final String NULL_STATE_ID = NullState.getInstance().getId();
	
	private static final boolean ACCEPTING = true;
	private static final boolean NOT_ACCEPTING = false;
	
	private static final Character ZERO = '0';
	private static final Character ONE = '1';
	private static final String EPSILON = NondeterministicFiniteAutomaton.EPSILON;
	private static final Set<Character> ALPHABET = new HashSet<>(Arrays.asList(ZERO, ONE));

	/*
	 * DFA
	 */
	
	@Test
	public void graphVizForBasicAutomatonIsCorrect() {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		String expected = "digraph automaton "
				+ "{\nrankdir=LR;\nsize=\"8,5\"\nnode [shape = doublecircle];"
				+ "S1;\nnode [shape = circle];\n" 
				+ "S1 -> " + NULL_STATE_ID 
				+ " [ label = \"0\" ];\n"
				+ "S1 -> " + NULL_STATE_ID 
				+ " [ label = \"1\" ];\n}";
		
		String graphViz = GraphVizConverter.convert(dfa);
		
		assertEquals(graphViz, expected);
	}
	
	@Test
	public void graphVizTransitionsImplementedCorrectly() {
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		dfa.addState(S2, NOT_ACCEPTING);
		dfa.addTransition(S1, S2, ZERO);
		String expected = "digraph automaton "
				+ "{\nrankdir=LR;\nsize=\"8,5\"\nnode [shape = doublecircle];"
				+ "S1;\nnode [shape = circle];\n" 
				+ "S1 -> S2 [ label = \"0\" ];\n"
				+ "S1 -> " + NULL_STATE_ID + " [ label = \"1\" ];\n"
				+ "S2 -> " + NULL_STATE_ID + " [ label = \"0\" ];\n"
				+ "S2 -> " + NULL_STATE_ID + " [ label = \"1\" ];\n}";
		
		String graphViz = GraphVizConverter.convert(dfa);
		
		assertEquals(graphViz, expected);
	}
	
	/*
	 * NFA
	 */
	
	@Test
	public void nfaGraphVizForAutomatonIsCorrect() {
		NondeterministicFiniteAutomaton<Character> nfa = newBoolCharNfa(S1, ACCEPTING);
		nfa.addState(S2, ACCEPTING);
		
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		dfa.addState(S2, ACCEPTING);
		
		assertEquals(GraphVizConverter.convert(nfa), GraphVizConverter.convert(dfa));
	}
	
	@Test
	public void nfaGraphVizTransitionsImplementedCorrectly() {
		NondeterministicFiniteAutomaton<Character> nfa = newBoolCharNfa(S1, ACCEPTING);
		nfa.addState(S2, ACCEPTING);
		nfa.addTransition(S1, S2, ZERO);
		
		DeterministicFiniteAutomaton<Character> dfa = newBoolCharDfa(S1, ACCEPTING);
		dfa.addState(S2, ACCEPTING);
		dfa.addTransition(S1, S2, ZERO);
		
		assertEquals(GraphVizConverter.convert(nfa), GraphVizConverter.convert(dfa));
	}
	
	@Test
	public void nfaGraphVizEpsilonTransitionsImplementedCorrectly() {
		NondeterministicFiniteAutomaton<Character> nfa = newBoolCharNfa(S1, ACCEPTING);
		nfa.addState(S2, NOT_ACCEPTING);
		nfa.addEpsilonTransition(S1, S2);
		String expected = "digraph automaton "
				+ "{\nrankdir=LR;\nsize=\"8,5\"\nnode [shape = doublecircle];"
				+ "S1;\nnode [shape = circle];\n" 
				+ "S1 -> " + NULL_STATE_ID + " [ label = \"0\" ];\n"
				+ "S1 -> " + NULL_STATE_ID + " [ label = \"1\" ];\n"
				+ "S2 -> " + NULL_STATE_ID + " [ label = \"0\" ];\n"
				+ "S2 -> " + NULL_STATE_ID + " [ label = \"1\" ];\n"
				+ "S1 -> S2 [ label = \"" + EPSILON + "\" ];\n"
				+ "}";
		
		System.out.println(expected);
		
		assertEquals(GraphVizConverter.convert(nfa), expected);
	}
	
	/*
	 * NullChecks
	 */
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void toGraphVizThrowsIllegalArgExceptionIfDfaNull() {
		GraphVizConverter.convert((IDeterministicFiniteAutomaton<Character>) null);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void toGraphVizThrowsIllegalArgExceptionIfNfaNull() {
		GraphVizConverter.convert((INondeterministicFiniteAutomaton<Character>) null);
	}
	
	/*
	 * Helper Methods
	 */
	
	private DeterministicFiniteAutomaton<Character> newBoolCharDfa(String identifier, boolean isAccepting) {
		return new DeterministicFiniteAutomaton<>(identifier, isAccepting, ALPHABET);	
	}
	
	private NondeterministicFiniteAutomaton<Character> newBoolCharNfa(String identifier, boolean isAccepting) {
		return new NondeterministicFiniteAutomaton<Character>(identifier, isAccepting, ALPHABET);	
	}
}
