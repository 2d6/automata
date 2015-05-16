package automata;

import java.util.Set;

/**
 * An alphabet of symbols
 * @author 2d6
 *
 * @param <T>
 */
public interface Alphabet<T> {
	
	/**
	 * Adds a symbol to the alphabet.
	 * @param symbol Symbol to be added
	 */
	public void add(T symbol);
	
	/**
	 * Adds all symbols from a set to the alphabet.
	 * @param symbols Set of symbols to be added
	 */
	public void addAll(Set<T> symbols);
	
	/**
	 * Determines whether a given symbol is contained in the alphabet
	 * @param symbol Symbol under scrutiny
	 * @return True if the symbol is contained in the alphabet
	 */
	public boolean isValid(T symbol);
	
	/**
	 * Returns the symbols contained in the alphabet
	 * @return
	 */
	public Set<T> getSymbols();
}
