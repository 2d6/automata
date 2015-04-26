package automata;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CharAlphabetTest {
	
	@Test
	public void alphabetMayBeSet() {
		CharAlphabet charAlphabet = new CharAlphabet();
		charAlphabet.set(new Character[]{'0','1','ü'});
		assertTrue(charAlphabet.isValid('1'));
		assertTrue(charAlphabet.isValid('ü'));
		assertFalse(charAlphabet.isValid('e'));
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void alphabetMayOnlyBeSetOnce() {
		CharAlphabet charAlphabet = new CharAlphabet();
		charAlphabet.set(new Character[]{'0','1'});
		charAlphabet.set(new Character[]{'1','2'});
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void noEmptyAlphabetMayBeSet() {
		CharAlphabet charAlphabet = new CharAlphabet();
		charAlphabet.set(null);
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void alphabetMustBeSetBeforeQueryingValidity() {
		CharAlphabet charAlphabet = new CharAlphabet();
		assertTrue(charAlphabet.isValid('1'));
	}
	
	@DataProvider(name = "nonLetterNonDigitSymbols")
	public Object[][] invalidSymbols() {
		return new Object[][] {
				{' '},
				{'\0'},
				{'%'}
		};
	}
	
	@Test(dataProvider = "nonLetterNonDigitSymbols")
	public void nonDigitNonLetterCharactersAreIgnored(Character symbol) {
		CharAlphabet charAlphabet = new CharAlphabet();
		charAlphabet.set(new Character[]{symbol});
		assertFalse(charAlphabet.isValid(symbol));
	}
}
