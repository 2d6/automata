package automata;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a simple Character-based alphabet
 * 
 * @author 2d6
 *
 */
public class CharAlphabet implements Alphabet<Character> {

	private List<Character> symbols;
	
	/**
	 * Creates a new CharAlphabet with the given symbols
	 * @param symbolArray Array of symbols in the alphabet
	 */
	public CharAlphabet(Character[] symbolArray) {
		set(symbolArray);
	}
	
	/**
	 * Creates a new CharAlphabet
	 */
	public CharAlphabet() {
	}

	@Override
	public void set(Character[] symbolArray) {
		if (this.symbols == null && symbolArray != null) {

			this.symbols = new ArrayList<Character>();
			for (Character symbol : symbolArray) {
				if (Character.isLetterOrDigit(symbol)) {
					this.symbols.add(symbol);
				}
			}
		} else if (symbolArray == null) {
			throw new NullPointerException(
					"Alphabet symbol array may not be null");
		} else {
			throw new IllegalArgumentException(
					"Alphabet symbols are already defined");
		}
	}

	@Override
	public boolean isValid(Character symbol) {
		if (symbols != null) {
			return symbols.contains(symbol);
		} else {
			throw new NullPointerException(
					"No alphabet symbols have been defined");
		}
	}
}
