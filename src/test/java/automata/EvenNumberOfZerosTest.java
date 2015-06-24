package automata;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automata.interfaces.IState;

public class EvenNumberOfZerosTest {
	
	@Test(dataProvider = "evenZeros")
	public void evenNumberOfZerosInInputShouldEvaluateAccepting(String input) {
		EvenNumberOfZeros automaton = new EvenNumberOfZeros();
		IState finalState = automaton.evaluate(input);
		assertTrue(finalState.isAccepting());
	}
	
	@DataProvider(name = "evenZeros")
	public Object[][] evenZeros() {
		return new Object[][] {
				{""},
				{"00"},
				{"010"},
				{"010100"},
		};
	}
	
	@Test(dataProvider = "unevenZeros")
	public void unevenNumberOfZerosInInputShouldEvaluateNotAccepting(String input) {
		EvenNumberOfZeros automaton = new EvenNumberOfZeros();
		IState finalState = automaton.evaluate(input);
		assertFalse(finalState.isAccepting());
	}
	
	@DataProvider(name = "unevenZeros")
	public Object[][] unevenZeros() {
		return new Object[][] {
				{"0"},
				{"0100"},
				{"0100100"},
		};
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void AutomatonThrowsIllegalArgumentExceptionIfNonZeroNonOneInput() {
		EvenNumberOfZeros automaton = new EvenNumberOfZeros();
		automaton.evaluate("2");
	}
	
	/*
	 * GraphViz
	 */
	
	@Test
	public void graphVizImplementedCorrectly() {
		EvenNumberOfZeros automaton = new EvenNumberOfZeros();
		String expected = "digraph automaton "
				+ "{\nrankdir=LR;\nsize=\"8,5\"\nnode [shape = doublecircle];"
				+ "S1;\nnode [shape = circle];\n" 
				+ "S1 -> S2 [ label = \"0\" ];\n"
				+ "S1 -> S1 [ label = \"1\" ];\n"
				+ "S2 -> S1 [ label = \"0\" ];\n"
				+ "S2 -> S2 [ label = \"1\" ];\n"
				+ "}";
		
		System.out.println(expected);
		
		assertEquals(automaton.toGraphViz(), expected);
	}
}
