package automata;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class EvenNumberOfZerosTest {

	@DataProvider(name = "evenZeros")
	public Object[][] evenZeros() {
		return new Object[][] {
				{""},
				{"00"},
				{"010"},
				{"010100"},
		};
	}
	
	@DataProvider(name = "unevenZeros")
	public Object[][] unevenZeros() {
		return new Object[][] {
				{"0"},
				{"0100"},
				{"0100100"},
		};
	}
	
	@Test(dataProvider = "evenZeros")
	public void evenNumberOfZerosInInputShouldEvaluateAccepting(String input) {
		EvenNumberOfZeros automaton = new EvenNumberOfZeros();
		State finalState = automaton.evaluate(input.toCharArray());
		assertTrue(finalState.isAccepting());
	}
	
	@Test(dataProvider = "unevenZeros")
	public void unevenNumberOfZerosInInputShouldEvaluateNotAccepting(String input) {
		EvenNumberOfZeros automaton = new EvenNumberOfZeros();
		State finalState = automaton.evaluate(input.toCharArray());
		assertFalse(finalState.isAccepting());
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nonZeroNonOneInputShouldRaiseException() {
		EvenNumberOfZeros automaton = new EvenNumberOfZeros();
		automaton.evaluate(new Character[]{'2'});
	}
}
