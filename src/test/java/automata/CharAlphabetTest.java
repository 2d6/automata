package automata;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;

import org.testng.annotations.Test;

public class CharAlphabetTest {
	
	@Test
	public void symbolsMayBeAddedToAlphabet() {
		CharAlphabet charAlphabet = new CharAlphabet();
		charAlphabet.add('0');
		charAlphabet.add('1');
		charAlphabet.add('ü');
		assertTrue(charAlphabet.isValid('1'));
		assertTrue(charAlphabet.isValid('ü'));
		assertFalse(charAlphabet.isValid('e'));
	}
	
	@Test
	public void listOfSymbolsMayBeAddedtoAlphabet() {
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
	public void noEmptySymbolMayBeAddedtoAlphabet() {
		CharAlphabet charAlphabet = new CharAlphabet();
		charAlphabet.add(null);
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void noEmptyListOfSymbolsMayBeAddedtoAlphabet() {
		CharAlphabet charAlphabet = new CharAlphabet();
		charAlphabet.addAll(null);
	}
}
