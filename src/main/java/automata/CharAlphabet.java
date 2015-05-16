package automata;

import java.util.HashSet;
import java.util.Set;

import automata.interfaces.IAlphabet;

/**
 * Implements a simple Character-based alphabet
 * 
 * @author 2d6
 *
 */
public class CharAlphabet implements IAlphabet<Character> {

	private Set<Character> symbols;
	
	/**
	 * Creates a new CharAlphabet
	 */
	public CharAlphabet() {
		symbols = new HashSet<>();
	}

	/**
	 * Creates a new CharAlphabet with the given symbols
	 * @param symbols Set of symbols in the alphabet
	 */
	public CharAlphabet(Set<Character> symbols) {
		this();
		addAll(symbols);
	}
	
	@Override
	public void addAll(Set<Character> symbols) {
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
	
	 /*
	  * Necessary for cloning
	  */
	@Override
	public Set<Character> getSymbols() {
		return symbols;
	}
}
