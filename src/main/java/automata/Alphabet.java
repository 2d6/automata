package automata;

import java.util.List;

/**
 * An alphabet of symbols
 * @author 2d6
 *
 * @param <T>
 */
public interface Alphabet<T> {
	
	/**
	 * Adds a symbol to the alphabet.
	 * @param alphabet Symbol to be added
	 */
	public void add(T symbol);
	
	/**
	 * Adds all symbols from a list to the alphabet.
	 * @param symbols
	 */
	public void addAll(List<T> symbols);
	
	/**
	 * Determines whether a given symbol is contained in the alphabet
	 * @param symbol Symbol under scrutiny
	 * @return True if the symbol is contained in the alphabet
	 */
	public boolean isValid(T symbol);
}
