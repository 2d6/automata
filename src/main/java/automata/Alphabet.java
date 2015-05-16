package automata;

import java.util.HashSet;
import java.util.Set;

import automata.interfaces.IAlphabet;

/**
 * Implements a simple T-based alphabet
 * 
 * @author 2d6
 *
 */
public class Alphabet<T> implements IAlphabet<T> {

	private Set<T> symbols;
	
	/**
	 * Creates a new CharAlphabet
	 */
	public Alphabet() {
		symbols = new HashSet<>();
	}

	/**
	 * Creates a new CharAlphabet with the given symbols
	 * @param symbols Set of symbols in the alphabet
	 */
	public Alphabet(Set<T> symbols) {
		this();
		addAll(symbols);
	}
	
	@Override
	public void addAll(Set<T> symbols) {
		if (symbols == null) {
			throw new NullPointerException("List of symbol Ts may not be null");
		}
		for (T symbol : symbols) {
			this.symbols.add(symbol);
		}
	}
	
	@Override
	public void add(T symbol) {
		if (symbol == null) {
			throw new NullPointerException("Symbol T may not be null");
		}
		symbols.add(symbol);
	}

	@Override
	public boolean isValid(T symbol) {
		return symbols.contains(symbol);
	}
	
	 /*
	  * Necessary for cloning
	  */
	@Override
	public Set<T> getSymbols() {
		return symbols;
	}
}
