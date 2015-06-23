package automata;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automata.interfaces.IState;
import static org.testng.Assert.*;

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
}
