package automata.clone;

/**
 * A class that implements the Cloneable interface and possesses a _protected_
 * clone() function of type Object with no parameters
 */
public class CloneableObjectWithProtectedCloneMethod implements Cloneable {
	
	@Override
	protected Object clone() {
		return new Object();
	}
}
