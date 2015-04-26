package automata;

/**
 * An alphabet of symbols
 * @author 2d6
 *
 * @param <T>
 */
public interface Alphabet<T> {
	
	/**
	 * Defines the alphabet symbols. This should only be possible once. 
	 * @param alphabet Array of symbols contained in the alphabet
	 */
	public void set(T[] symbolArray);
	
	/**
	 * Determines whether a given symbol is contained in the alphabet
	 * @param symbol Symbol under scrutiny
	 * @return True if the symbol is contained in the alphabet
	 */
	public boolean isValid(T symbol);
}
