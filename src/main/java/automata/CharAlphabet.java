package automata;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a simple Character-based alphabet
 * 
 * @author 2d6
 *
 */
public class CharAlphabet implements Alphabet<Character>, Cloneable {

	private List<Character> symbols;
	
	/**
	 * Creates a new CharAlphabet
	 */
	public CharAlphabet() {
		symbols = new ArrayList<Character>();
	}

	/**
	 * Creates a new CharAlphabet with the given symbols
	 * @param symbolArray Array of symbols in the alphabet
	 */
	public CharAlphabet(List<Character> symbols) {
		this();
		addAll(symbols);
	}
	
	@Override
	public void addAll(List<Character> symbols) {
		if (symbols == null) {
			throw new NullPointerException("List of symbol characters may not be null");
		}
		for (Character symbol : symbols) {
			this.symbols.add(symbol);
		}
	}
	
	@Override
	public void add(Character symbol) {
		if (symbol == null) {
			throw new NullPointerException("Symbol character may not be null");
		}
		symbols.add(symbol);
	}

	@Override
	public boolean isValid(Character symbol) {
		return symbols.contains(symbol);
	}
	
	@Override
	public Object clone() {
		CharAlphabet clonedAlphabet =  new CharAlphabet();
		clonedAlphabet.addAll(getSymbols());
		return clonedAlphabet;
	}
	
	 /*
	  * Necessary for cloning
	  */
	private List<Character> getSymbols() {
		return symbols;
	}
}
