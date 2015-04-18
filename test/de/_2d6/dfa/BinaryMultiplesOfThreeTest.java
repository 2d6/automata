package de._2d6.dfa;

import static org.junit.Assert.*;

import org.junit.Test;

public class BinaryMultiplesOfThreeTest {

	/**
	 * Binary representation of three (11) should be accepted
	 */
	@Test
	public void threeShouldBeAccepted() {
		BinaryMultiplesOfThree automaton = new BinaryMultiplesOfThree();
		automaton.evaluateInput("11");
		assertTrue(automaton.isCurrentStateAccepting());
	}
	
	/**
	 * Binary representation of two (10) should not be accepted
	 */
	@Test
	public void twoShouldNotBeAccepted() {
		BinaryMultiplesOfThree automaton = new BinaryMultiplesOfThree();
		automaton.evaluateInput("10");
		assertFalse(automaton.isCurrentStateAccepting());
	}
	
	/**
	 * Binary representation of 999 (1111100111) should be accepted
	 */
	@Test
	public void nineHundredNinetyNineShouldBeAccepted() {
		BinaryMultiplesOfThree automaton = new BinaryMultiplesOfThree();
		automaton.evaluateInput("1111100111");
		assertTrue(automaton.isCurrentStateAccepting());
	}
	
	/**
	 * Binary representation of 1000 (1111101000) should be accepted
	 */
	@Test
	public void thousandShouldNotBeAccepted() {
		BinaryMultiplesOfThree automaton = new BinaryMultiplesOfThree();
		automaton.evaluateInput("1111101000");
		assertFalse(automaton.isCurrentStateAccepting());
	}

}
