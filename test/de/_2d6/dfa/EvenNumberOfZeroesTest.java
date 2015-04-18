package de._2d6.dfa;

import static org.junit.Assert.*;

import org.junit.Test;

public class EvenNumberOfZeroesTest {

	/**
	 * Empty string input should be accepted
	 */
	@Test
	public void emptyInputShouldBeAccepted() {
		EvenNumberOfZeroes automaton = new EvenNumberOfZeroes();
		automaton.evaluateInput("");
		assertTrue(automaton.isCurrentStateAccepting());
	}

	/**
	 * Input of "0" should not be accepted
	 */
	@Test
	public void inputSingleZeroShouldBeAccepted() {
		EvenNumberOfZeroes automaton = new EvenNumberOfZeroes();
		automaton.evaluateInput("0");
		assertFalse(automaton.isCurrentStateAccepting());
	}

	/**
	 * Input of "01011110110" should be accepted
	 */
	@Test
	public void evenNumberOfZeroesShouldBeAccepted() {
		EvenNumberOfZeroes automaton = new EvenNumberOfZeroes();
		automaton.evaluateInput("01011110110");
		assertTrue(automaton.isCurrentStateAccepting());
	}

	/**
	 * Input of "01011110111" should not be accepted
	 */
	@Test
	public void oddNumberOfZeroesShouldNotBeAccepted() {
		EvenNumberOfZeroes automaton = new EvenNumberOfZeroes();
		automaton.evaluateInput("01011110111");
		assertFalse(automaton.isCurrentStateAccepting());
	}

}
