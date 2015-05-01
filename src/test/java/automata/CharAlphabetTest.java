package automata;

import static org.testng.Assert.*;

import java.util.ArrayList;

import org.testng.annotations.Test;

public class CharAlphabetTest {
	
	@Test
	public void symbolsMayBeAdded() {
		CharAlphabet charAlphabet = new CharAlphabet();
		charAlphabet.add('0');
		charAlphabet.add('1');
		charAlphabet.add('ü');
		assertTrue(charAlphabet.isValid('1'));
		assertTrue(charAlphabet.isValid('ü'));
		assertFalse(charAlphabet.isValid('e'));
	}
	
	@Test
	public void listOfSymbolsMayBeAdded() {
		ArrayList<Character> symbols = new ArrayList<>();
		symbols.add('0');
		symbols.add('1');
		symbols.add('ü');
		CharAlphabet charAlphabet = new CharAlphabet(symbols);
		assertTrue(charAlphabet.isValid('1'));
		assertTrue(charAlphabet.isValid('ü'));
		assertFalse(charAlphabet.isValid('e'));
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void noEmptySymbolMayBeAdded() {
		CharAlphabet charAlphabet = new CharAlphabet();
		charAlphabet.add(null);
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void noEmptyListOfSymbolsMayBeAdded() {
		CharAlphabet charAlphabet = new CharAlphabet();
		charAlphabet.addAll(null);
	}
	
	/*
	 * Cloning
	 */

	/**
	 * This checks whether the clone() method works as per the documentation of
	 * theObject.clone() method given here:
	 * https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html#clone%28%29
	 */
	@Test
	public void cloningFulfilsCriteria() {
		CharAlphabet alphabet = new CharAlphabet();
		
		assertTrue(alphabet != alphabet.clone());
		assertEquals(alphabet.getClass(), alphabet.clone().getClass());
	}
	
	@Test
	public void clonedAlphabetAttributesAreEqual() {
		CharAlphabet alphabet = new CharAlphabet();
		alphabet.add('a');
		
		CharAlphabet clone = (CharAlphabet) alphabet.clone();
		
		assertEquals(alphabet.isValid('a'), clone.isValid('a'));
		assertEquals(alphabet.isValid('b'), clone.isValid('b'));
	}
	
	@Test
	public void clonedAlphabetDoesNotChangeWithOriginal() {
		CharAlphabet alphabet = new CharAlphabet();
		
		alphabet.add('a');
		CharAlphabet clone = (CharAlphabet) alphabet.clone();
		alphabet.add('b');
		
		assertEquals(alphabet.isValid('a'), clone.isValid('a'));
		assertNotEquals(alphabet.isValid('b'), clone.isValid('b'));
	}
}
