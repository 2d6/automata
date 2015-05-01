package automata.clone;

/**
 * An object that implements the Cloneable interface AND possesses a public
 * clone() function of type Object with no parameters
 */
public class CloneableObject implements Cloneable {
	@Override
	public Object clone() {
		return new Object();
	}
}
