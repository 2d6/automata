package automata.clone;

/**
 * A class that, while it implements the Cloneable interface, does not possess a
 * public clone() function
 */
public class NonCloneableObjectWithPublicCloneMethod {
	@Override
	public Object clone() {
		return new Object();
	}
}
