package automata;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

public class AlphabetTest {
	
	@Test
	public void symbolsMayBeAddedToAlphabet() {
		Alphabet<Character> charAlphabet = new Alphabet<>();
		charAlphabet.add('0');
		charAlphabet.add('1');
		charAlphabet.add('ü');
		assertTrue(charAlphabet.isValid('1'));
		assertTrue(charAlphabet.isValid('ü'));
		assertFalse(charAlphabet.isValid('e'));
	}
	
	@Test
	public void listOfSymbolsMayBeAddedtoAlphabet() {
		Set<Character> symbols = new HashSet<>();
		symbols.add('0');
		symbols.add('1');
		symbols.add('ü');
		Alphabet<Character> charAlphabet = new Alphabet<>(symbols);
		assertTrue(charAlphabet.isValid('1'));
		assertTrue(charAlphabet.isValid('ü'));
		assertFalse(charAlphabet.isValid('e'));
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void noEmptySymbolMayBeAddedtoAlphabet() {
		Alphabet<Character> charAlphabet = new Alphabet<>();
		charAlphabet.add(null);
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void noEmptyListOfSymbolsMayBeAddedtoAlphabet() {
		Alphabet<Character> charAlphabet = new Alphabet<>();
		charAlphabet.addAll(null);
	}
}
